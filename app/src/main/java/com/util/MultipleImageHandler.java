package com.util;

import android.app.Activity;
import android.app.Dialog;
import android.content.ContentUris;
import android.content.Context;
import android.content.Intent;
import android.database.Cursor;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.Matrix;
import android.media.ExifInterface;
import android.net.Uri;
import android.os.Build;
import android.os.Environment;
import android.provider.DocumentsContract;
import android.provider.MediaStore;
import android.provider.OpenableColumns;
import android.text.TextUtils;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.Toast;

import androidx.core.content.FileProvider;

import com.buyereasefsl.BuildConfig;
import com.buyereasefsl.R;
import com.constant.FClientConstants;
import com.constant.FEnumerations;
import com.darsh.multipleimageselect.activities.AlbumSelectActivity;
import com.darsh.multipleimageselect.helpers.Constants;
import com.darsh.multipleimageselect.models.Image;
import com.data.UserSession;

import java.io.BufferedReader;
import java.io.ByteArrayOutputStream;
import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.FileReader;
import java.io.IOException;
import java.io.InputStream;
import java.net.URISyntaxException;
import java.util.ArrayList;
import java.util.Collections;

import me.drakeet.support.toast.ToastCompat;

/**
 * Created by ADMIN on 1/15/2018.
 */

public class MultipleImageHandler {

    private static String TAG = "MultipleImageHandler";
    public static final int CAR_PHOTO = 1;
    public static final int PROFILE_PHOTO = 2;
    public static final int DOCUMENT = 3;

    private static String valueToBeReturnedToSource = null;
    private static final String SHARED_PROVIDER_AUTHORITY = BuildConfig.APPLICATION_ID + ".myfileprovider";
    private static int Photo_Source = 0;

    /*private static final int RESULT_LOAD_IMAGE=222;
    private static final int IMAGE_CAPTURE = 1111;*/
    private static Uri imageUri;

    private static Activity currentActivity;
    static ArrayList<String> imagesArrayList;
    //working
    public static int pickerViewer = -1;

    // working for getting only Camera permission
    public static void showDialog(Activity context, int photoSource, String valueToBeReturned) {
        valueToBeReturnedToSource = valueToBeReturned;
        showDialog(context, photoSource);
    }

    // working for getting only Camera permission
    public static void showDialog(Activity context, int photoSource, String valueToBeReturned, int selected) {
        valueToBeReturnedToSource = valueToBeReturned;
        currentActivity = context;
        Photo_Source = photoSource;

        if (selected == FEnumerations.RESULT_GALLERY) {
            pick_Image_From_Gallery(context);
        } else if (selected == FEnumerations.RESULT_CAMERA) {
            pick_Image_From_Camera(context);
        } else {
            showDialog(context, photoSource);
        }
    }

    public static void showDialogForFileOrImages(Activity context, int photoSource) {
//        valueToBeReturnedToSource = valueToBeReturned;
        checkPermissionShowDialog(context, photoSource);
    }

    //: for getting two permissions - Camera & Read storage
    public static void showDialog(final Activity context, final int photoSource) {
        //public static void showDialogAfterPermissions(final Activity context, final int photoSource) {

        currentActivity = context;
        Photo_Source = photoSource;

        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.M) {

            FslLog.d(TAG, "checking for camera & read storage permissions");
            boolean lPermissionFlag = PermissionSeekingActivity.checkPermission(context
                    , FEnumerations.PHOTO_PERMISSION);

            if (lPermissionFlag) {
                FslLog.d(TAG, "camera or read storage permission not available");
                showCompleteDialogNow();
            } else {
                FslLog.d(TAG, "seeking camera or read storage permission");
                Intent intent = new Intent(context, PermissionSeekingActivity.class);
                intent.putExtra(FClientConstants.PERMISSION_INTENT, FEnumerations.PHOTO_PERMISSION);
                context.startActivityForResult(intent, FEnumerations.PERMISSION_REQUEST);
            }
        } else {
            showCompleteDialogNow();
        }
    }


    //: for getting two permissions - Camera & Read storage
    public static void checkPermissionShowDialog(final Activity context, final int photoSource) {
        //public static void showDialogAfterPermissions(final Activity context, final int photoSource) {

        currentActivity = context;
        Photo_Source = photoSource;

        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.M) {

            FslLog.d(TAG, "checking for camera & read storage permissions");
            boolean lPermissionFlag = PermissionSeekingActivity.checkPermission(context
                    , FEnumerations.PHOTO_PERMISSION);

            if (lPermissionFlag) {
                FslLog.d(TAG, "camera or read storage permission not available");
                showCompleteForFileDialogNow();
            } else {
                FslLog.d(TAG, "seeking camera or read storage permission");
                Intent intent = new Intent(context, PermissionSeekingActivity.class);
                intent.putExtra(FClientConstants.PERMISSION_INTENT, FEnumerations.PHOTO_PERMISSION);
                context.startActivityForResult(intent, FEnumerations.PERMISSION_REQUEST);
            }
        } else {
            showCompleteForFileDialogNow();
        }
    }


    public static void showCompleteDialogNow() {
        final Dialog importPicdialog = new Dialog(currentActivity);
        //importPicdialog.getWindow().setBackgroundDrawable(new ColorDrawable(android.graphics.Color.TRANSPARENT));
        importPicdialog.setContentView(R.layout.importpiclayout);
        importPicdialog.setTitle(FClientConstants.TEXT_SELECT_PHOTO);
        importPicdialog.setCancelable(false);

        Button camera = (Button) importPicdialog.findViewById(R.id.mCamera);
        Button gallery = (Button) importPicdialog.findViewById(R.id.mGallery);
        Button cancel = (Button) importPicdialog.findViewById(R.id.mCancel);


        camera.setOnClickListener(new View.OnClickListener() {

            @Override
            public void onClick(View arg0) {
                // TODO Auto-generated method stub
                pick_Image_From_Camera(currentActivity);
                pickerViewer = FEnumerations.RESULT_CAMERA;
                importPicdialog.dismiss();

            }
        });

        gallery.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View arg0) {
                // TODO Auto-generated method stub
                pick_Image_From_Gallery(currentActivity);
                pickerViewer = FEnumerations.RESULT_GALLERY;
                importPicdialog.dismiss();
            }
        });

        cancel.setOnClickListener(new View.OnClickListener() {

            @Override
            public void onClick(View arg0) {
                // TODO Auto-generated method stub

                importPicdialog.dismiss();

            }
        });
        importPicdialog.show();
    }

    public static void showCompleteForFileDialogNow() {
        final Dialog importPicdialog = new Dialog(currentActivity);
        //importPicdialog.getWindow().setBackgroundDrawable(new ColorDrawable(android.graphics.Color.TRANSPARENT));
        importPicdialog.setContentView(R.layout.importpiclayout);
        importPicdialog.setTitle(FClientConstants.TEXT_SELECT_PHOTO);
        importPicdialog.setCancelable(false);

        Button camera = (Button) importPicdialog.findViewById(R.id.mCamera);
        Button gallery = (Button) importPicdialog.findViewById(R.id.mGallery);
        Button cancel = (Button) importPicdialog.findViewById(R.id.mCancel);


        camera.setText("File");
        gallery.setText("Image");
        camera.setOnClickListener(new View.OnClickListener() {

            @Override
            public void onClick(View arg0) {
                // TODO Auto-generated method stub
                pick_File(currentActivity);
                importPicdialog.dismiss();
            }
        });

        gallery.setOnClickListener(new View.OnClickListener() {

            @Override
            public void onClick(View arg0) {
                // TODO Auto-generated method stub
                pick_Image_From_Gallery(currentActivity);
                importPicdialog.dismiss();
            }
        });

        cancel.setOnClickListener(new View.OnClickListener() {

            @Override
            public void onClick(View arg0) {
                // TODO Auto-generated method stub

                importPicdialog.dismiss();

            }
        });
        importPicdialog.show();
    }

    public static void showGalleryDialogNow() {

        final Dialog importPicdialog = new Dialog(currentActivity);
        importPicdialog.setTitle(FClientConstants.TEXT_SELECT_PHOTO);
        importPicdialog.setContentView(R.layout.importpiclayout);
        importPicdialog.setCancelable(false);

        ((Button) importPicdialog.findViewById(R.id.mCamera)).setVisibility(View.GONE);
        Button gallery = (Button) importPicdialog.findViewById(R.id.mGallery);
        Button cancel = (Button) importPicdialog.findViewById(R.id.mCancel);

        gallery.setOnClickListener(new View.OnClickListener() {

            @Override
            public void onClick(View arg0) {
                // TODO Auto-generated method stub
                pick_Image_From_Gallery(currentActivity);
                importPicdialog.dismiss();
            }
        });

        cancel.setOnClickListener(new View.OnClickListener() {

            @Override
            public void onClick(View arg0) {
                // TODO Auto-generated method stub
                importPicdialog.dismiss();

            }
        });
        importPicdialog.show();
    }

    public static void showCameraDialogNow() {

        final Dialog importPicdialog = new Dialog(currentActivity);
        //importPicdialog.getWindow().setBackgroundDrawable(new ColorDrawable(android.graphics.Color.TRANSPARENT));
        importPicdialog.setContentView(R.layout.importpiclayout);
        importPicdialog.setTitle(FClientConstants.TEXT_SELECT_PHOTO);
        importPicdialog.setCancelable(false);

        Button camera = (Button) importPicdialog.findViewById(R.id.mCamera);
        Button gallery = (Button) importPicdialog.findViewById(R.id.mGallery);
        gallery.setVisibility(View.GONE);
        Button cancel = (Button) importPicdialog.findViewById(R.id.mCancel);

        camera.setOnClickListener(new View.OnClickListener() {

            @Override
            public void onClick(View arg0) {
                // TODO Auto-generated method stub
                pick_Image_From_Camera(currentActivity);
                importPicdialog.dismiss();
            }
        });

        cancel.setOnClickListener(new View.OnClickListener() {

            @Override
            public void onClick(View arg0) {
                // TODO Auto-generated method stub

                importPicdialog.dismiss();

            }
        });
        importPicdialog.show();
    }

    public static void pick_Image_From_Camera(Activity context) {
        // TODO Auto-generated method stub
        Intent intent = new Intent(MediaStore.ACTION_IMAGE_CAPTURE);
        imageUri = getImageUri(context);
        if (imageUri != null) {
            intent.putExtra(MediaStore.EXTRA_OUTPUT, imageUri);
            // to handle FileUriExposedException in using file: to take photo from camera.
            //Change because of Nougat Android version
            GenUtils.grantAllUriPermissions(context, intent, imageUri);
            if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.N) {
                intent.setFlags(Intent.FLAG_GRANT_READ_URI_PERMISSION | Intent.FLAG_GRANT_WRITE_URI_PERMISSION);
            }
            context.startActivityForResult(intent, FEnumerations.IMAGE_CAPTURE);
        } else
            FslLog.d(TAG, "Could not create file path to capture image");
    }

    public static void pick_File(Activity context) {
        // TODO Auto-generated method stub
        Intent intent = new Intent(Intent.ACTION_GET_CONTENT);
//        intent.AddCategory(Intent.CategoryOpenable);
        intent.setType("*/*");
        String[] mimeTypes = {"pdf/*"};
        intent.addCategory(Intent.CATEGORY_OPENABLE);
//        intent.putExtra(Intent.EXTRA_MIME_TYPES, mimeTypes);
        try {
            intent.addFlags(Intent.FLAG_GRANT_READ_URI_PERMISSION);
            context.startActivityForResult(Intent.createChooser(intent, "Select a File to Upload"), FEnumerations.PICKFILE_RESULT_CODE);
        } catch (android.content.ActivityNotFoundException ex) {
            // Potentially direct the user to the Market with a Dialog
            Toast toast = ToastCompat.makeText(context, "Please install a File Manager.", Toast.LENGTH_SHORT);
            GenUtils.safeToastShow(TAG, context, toast);
        }

    }

    public static void pick_Image_From_Gallery(Activity context) {

        Intent intent = new Intent(Intent.ACTION_PICK);
        intent.setType("image/*");
        intent.putExtra(Intent.EXTRA_ALLOW_MULTIPLE, true);
        intent.setAction(Intent.ACTION_GET_CONTENT);
        context.startActivityForResult(intent, FEnumerations.RESULT_LOAD_IMAGE);

//        Intent intent = new Intent(context, AlbumSelectActivity.class);
////set limit on number of images that can be selected, default is 10
//        intent.putExtra(Constants.INTENT_EXTRA_LIMIT, 100);
////            startActivityForResult(intent, Constants.REQUEST_CODE);
//
//        try {
//            context.startActivityForResult(intent, FEnumerations.RESULT_LOAD_IMAGE);
//        } catch (android.content.ActivityNotFoundException e) {
//            Toast toast = ToastCompat.makeText(currentActivity, "Couldn't find any photo viewer app", Toast.LENGTH_LONG);
//            GenUtils.safeToastShow(TAG, context, toast);
//            e.printStackTrace();
//        } catch (Exception e) {
//            e.printStackTrace();
//        }
//        } else {
//            ToastCompat.makeText(currentActivity, "Couldn't find any photo viewer app", Toast.LENGTH_LONG).show();
//        }
    }

    private static Uri getImageUri(Activity context) {
        Uri m_imgUri = null;
        File m_file;
        try {
            String folderName;
            UserSession userSession = new UserSession(context);
            folderName = userSession.getDeLNO() + "_" + userSession.getInspectionDt();
            File folderPath = new File(context.getExternalFilesDir(null) + File.separator + folderName);
            if (!folderPath.exists()) {
                folderPath.mkdirs();
                FslLog.d(TAG, folderName + " new folder created");
            } else {
                FslLog.d(TAG, folderName + " folder is already folder created");
            }


            long dtMili = System.currentTimeMillis();
            File tempFil = new File(folderPath, dtMili + ".jpg");//Environment.getExternalStorageDirectory() //Environment.getExternalStoragePublicDirectory(Environment.DIRECTORY_DCIM)
            FslLog.d(TAG, " path of storage " + tempFil);


            String m_imagePath = tempFil.getAbsolutePath();
            m_file = new File(m_imagePath);
            //Change because of Nougat Android version
            if (Build.VERSION.SDK_INT < Build.VERSION_CODES.N) {
                m_imgUri = Uri.fromFile(m_file);
            } else {
                m_imgUri = FileProvider.getUriForFile(currentActivity,
                        "com.buyereasefsl",
                        m_file);//SHARED_PROVIDER_AUTHORITY
            }
            FslLog.d(TAG, "Temporary file to handle image " + m_imgUri.toString());
        } catch (Exception p_e) {
            FslLog.e(TAG, "Exception " + p_e.toString());
            Toast toast = ToastCompat.makeText(currentActivity, "Couldn't initiate camera. Please pick photo from your Gallery", Toast.LENGTH_LONG);
            GenUtils.safeToastShow(TAG, context, toast);
        }

      /*  long dtMili = System.currentTimeMillis();
        String fileName =dtMili+ ".jpg";

        // Create parameters for Intent with filename

        ContentValues values = new ContentValues();

        values.put(MediaStore.Images.Media.TITLE, fileName);

        values.put(MediaStore.Images.Media.DESCRIPTION,
                "Image capture by camera");

        // imageUri is the current activity attribute, define and save it
        // for later usage

        m_imgUri = currentActivity.getContentResolver().insert(
                MediaStore.Images.Media.EXTERNAL_CONTENT_URI, values);*/
        FslLog.d(TAG, " egt URI to capture image  " + m_imgUri);
        return m_imgUri;
    }


    public static void onActivityResult(Activity activity, int requestCode, int resultCode, Intent data, GetBitmap getBitmap) {
        FslLog.d(TAG, "onActivityResult " + "requestCode : " + requestCode + "," + " resultCode : " + resultCode);
        Log.e(TAG, "onActivityResult " + "requestCode : " + requestCode + "," + " resultCode : " + resultCode);
        try {
            if (activity != null && requestCode == FEnumerations.RESULT_LOAD_IMAGE && resultCode == Activity.RESULT_OK) {

                if (data != null) {
                    // Check if multiple images are selected
                    if (data.getClipData() != null) {
                        int count = data.getClipData().getItemCount(); // Number of picked images
                        for (int i = 0; i < count; i++) {
                            Uri imageUri = data.getClipData().getItemAt(i).getUri();
                            // Do something with the image (save Uri, display, etc.)
                            startFeatherFromCamera(activity, imageUri, getBitmap, true);
                        }
                    } else if (data.getData() != null) {
                        // Single image selected
                        Uri imageUri = data.getData();
                        // Do something with the image (save Uri, display, etc.)
                        startFeatherFromCamera(activity, imageUri, getBitmap, true);
                    }
                }

//                ArrayList<Image> images = data.getParcelableArrayListExtra(Constants.INTENT_EXTRA_IMAGES);
//
//                try {
//                    if (images != null && images.size() > 0) {
//                        if (imagesArrayList == null)
//                            imagesArrayList = new ArrayList<>();
//                        else imagesArrayList.clear();
//                        for (int i = 0; i < images.size(); i++) {
//
//                            FslLog.d(TAG, " path selected image " + images.get(i).path);
//                            Log.d(TAG, " path selected image " + images.get(i).path);
//                            imagesArrayList.add(images.get(i).path);
//                        }
//                        //reverse imagesArrayList
//                        Collections.reverse(imagesArrayList);//added by shekhar
//                        getBitmap.onGetBitamp(null, imagesArrayList, valueToBeReturnedToSource);
//                    }
//                } catch (SecurityException e) {
//                    FslLog.e(TAG, "SecurityException " + e.toString());
//                    Log.e(TAG, "SecurityException " + e.toString());
//                    e.printStackTrace();
//                    // lets take read permission
//                    if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.M) {
////                        showDialogAfterPermissions(currentActivity, Photo_Source);
//                    }
//                }
                return;
            }
            if (requestCode == FEnumerations.IMAGE_CAPTURE && resultCode == Activity.RESULT_OK) {
                //Anand handle to send Uri To bitmap without crop

                startFeatherFromCamera(activity, imageUri, getBitmap, false);


            }

           /* if (requestCode == Crop.REQUEST_CROP && resultCode == Activity.RESULT_OK) {
                handleCrop(activity, resultCode, result, getBitmap);
            }*/

        } catch (OutOfMemoryError e) {


            FslLog.e(TAG, "could not load image due to low memory");
            GenUtils.forInfoAlertDialog(activity
                    , "OK"
                    , "Out of Memory"
                    , "Insufficient memory to load image. Close few running apps and try again."
                    , null);
            //ToastCompat.makeText(activity, "Insufficient memory to load image. Close few running apps and try again.", Toast.LENGTH_SHORT).show();
            return;
        }

        if (requestCode == FEnumerations.PERMISSION_REQUEST) {
            if (currentActivity != null) {
                if (resultCode == Activity.RESULT_OK) {
                    showCompleteDialogNow();
                } else if (resultCode == FEnumerations.RESULT_CAMERA_DENIED) {
                    showGalleryDialogNow();
                } else if (resultCode == FEnumerations.RESULT_READ_STORAGE_DENIED) {
                    showCameraDialogNow();
                } else {
                    FslLog.e(TAG, "Required permission denied");
                    Toast toast = ToastCompat.makeText(currentActivity, "Couldn't select photo as required permission denied", Toast.LENGTH_LONG);
                    GenUtils.safeToastShow(TAG, currentActivity, toast);
                }
            } else {
                FslLog.e(TAG, "currentActivity is null");
            }
        }
    }


    private static void startFeatherFromCamera(final Activity activity, Uri selectedImage, final GetBitmap getBitmap, boolean isGallery) {

        InputStream is = null;

        try {


            is = activity.getContentResolver().openInputStream(selectedImage);//this can throw SecurityException for not having permission
            Bitmap bitmap = BitmapFactory.decodeStream(is);//this can throw OutOfMemoryError
            FslLog.d(TAG, "selected image width :" + bitmap.getWidth() + " height :" + bitmap.getHeight());
            FslLog.d(TAG, "bytes size of selected image :" + bitmap.getByteCount());// +" height :"+bitmap.getHeight());
            //CustomScaleBitmap(bitmap);

//            if (isPhotoOfMinSize(activity, bitmap)) {

            try {
                Bitmap bitmap1 = rotateImageIfRequired(activity, bitmap, selectedImage);

                //No need to scale image
               /* Bitmap scaled_bitmap = null;

                if (Photo_Source == MultipleImageHandler.CAR_PHOTO) {
//                    scaled_bitmap = BitmapUtil.CarScaleBitmap(bitmap);
                } else if (Photo_Source == MultipleImageHandler.PROFILE_PHOTO) {
                    scaled_bitmap = BitmapUtil.CustomScaleBitmap(bitmap1);
                } else if (Photo_Source == MultipleImageHandler.DOCUMENT) {
                    scaled_bitmap = BitmapUtil.SquareScaleBitmap(bitmap1, FClientConfig.DOCUMENT_IMAGE_SIZE);
                }*/
//			if(bitmap!=null){
//				bitmap=null;
//				bitmap.recycle();
//				System.gc();
//
//			}
                String filename = getFileName(activity, selectedImage);
                FslLog.d(TAG, " selected image name " + filename);
//                Uri uri = getImageUri(activity, bitmap1, filename);// Uri uri = getImageUri(activity, scaled_bitmap);
                String imagePath = getImageUri(activity, bitmap1, filename);
                if (imagesArrayList == null)
                    imagesArrayList = new ArrayList<>();
                else imagesArrayList.clear();
                //                    String imagePath=RealPathUtil.getRealPath(activity, uri);
                if (!TextUtils.isEmpty(imagePath)) {
                    imagesArrayList.add(imagePath);
                } else {
                    FslLog.d(TAG, "You selected image not correct path...????????????????");
                }
                getBitmap.onGetBitamp(bitmap1, imagesArrayList, valueToBeReturnedToSource, isGallery);// getBitmap.onGetBitamp(scaled_bitmap, imagesArrayList, valueToBeReturnedToSource);
//            }
            } catch (IOException e) {
                e.printStackTrace();
            }


        } catch (FileNotFoundException e) {
            e.printStackTrace();
        }


    }

    private static void storeCameraPhotoInSDCard(Activity activity, Bitmap bitmap, String path) {
        long dtMili = System.currentTimeMillis();
        File outputFile;


        if (!isExternalStorageAvailable()) {
            outputFile = new File(Environment.getExternalStoragePublicDirectory(Environment.DIRECTORY_DCIM), dtMili + ".jpg");//Environment.getExternalStorageDirectory()
        } else {
            String sUri = "content://com.android.externalstorage.documents/tree/29F4-5A7D%3A";
            Uri myUri = Uri.parse(sUri);
            File BaseNewFile = new File(myUri.getPath());//3/storage/3C70-E899
            File newFile = new File(BaseNewFile.getAbsolutePath(), "/DEL");
            if (!newFile.exists()) {
                newFile.mkdirs();
                FslLog.d(TAG, "DEL new folder created ");
            } else {
                FslLog.d(TAG, "DEL  folder is already created.. ");
            }


//            outputFile = new File("/storage/3C70-E899/", dtMili + ".jpg");//Environment.getExternalStorageDirectory()
            if (newFile.canWrite()) {
                FslLog.d(TAG, "file can write.. ");
            }
            outputFile = new File(newFile, dtMili + ".jpg");


        }
        try {
//            outputFile.createNewFile();
            ByteArrayOutputStream baos = new ByteArrayOutputStream();
            bitmap.compress(Bitmap.CompressFormat.JPEG, 100, baos);
            FileOutputStream fileOutputStream = new FileOutputStream(outputFile);
//            bitmap.compress(Bitmap.CompressFormat.JPEG, 100, fileOutputStream);
//            fileOutputStream.flush();
//            fileOutputStream.close();
            if (fileOutputStream != null) {
                fileOutputStream.write(baos.toByteArray());
                fileOutputStream.flush();
                fileOutputStream.close();
            }
            new ImageSaveTask(activity).execute(path, outputFile.toString());
//            MediaStore.Images.Media.insertImage(activity.getContentResolver(), outputFile.getAbsolutePath(), outputFile.getName(), outputFile.getName());
        } catch (FileNotFoundException e) {
            e.printStackTrace();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    public static String getImageUri(Context inContext, Bitmap inImage, String fileName) {

        String folderName;
        UserSession userSession = new UserSession(inContext);
        folderName = userSession.getDeLNO() + "_" + userSession.getInspectionDt();
        File folderPath = new File(inContext.getExternalFilesDir(null) + File.separator + folderName);
        if (!folderPath.exists()) {
            folderPath.mkdirs();
            FslLog.d(TAG, folderName + " new folder created");
        } else {
            FslLog.d(TAG, folderName + " folder is already folder created");
        }

//        ByteArrayOutputStream bytes = new ByteArrayOutputStream();
//        inImage.compress(Bitmap.CompressFormat.JPEG, 100, bytes);

        File file = new File(folderPath, fileName);
        if (file.exists()) {
            file.delete();
            FslLog.d(TAG, " image already exist ");
        }

        FslLog.d(TAG, " image after rotated : " + file.getAbsolutePath());
        try {
            ByteArrayOutputStream baos = new ByteArrayOutputStream();
            inImage.compress(Bitmap.CompressFormat.JPEG, 100, baos);
            FileOutputStream fileOutputStream = new FileOutputStream(file);

            fileOutputStream.write(baos.toByteArray());
            fileOutputStream.flush();
            fileOutputStream.close();

        } catch (IOException e) {
            e.printStackTrace();
        }
        Uri m_imgUri;
        if (Build.VERSION.SDK_INT < Build.VERSION_CODES.N) {
            m_imgUri = Uri.fromFile(file);
        } else {
            m_imgUri = FileProvider.getUriForFile(inContext,
                    "com.buyereasefsl",
                    file);//SHARED_PROVIDER_AUTHORITY
        }
//        long fileN = System.currentTimeMillis();
//        String path = MediaStore.Images.Media.insertImage(inContext.getContentResolver(), inImage, "" + fileN, null);
        FslLog.d(TAG, " image uri  after rotated saved : " + m_imgUri);
        return file.getAbsolutePath();
    }

    public static String getRealPathFromURI(Context context, Uri contentUri) {
        Cursor cursor = null;
        try {
            String[] proj = {MediaStore.Images.Media.DATA};
            cursor = context.getContentResolver().query(contentUri, proj, null, null, null);
            int column_index = cursor.getColumnIndexOrThrow(MediaStore.Images.Media.DATA);
            cursor.moveToFirst();
            return cursor.getString(column_index);
        } finally {
            if (cursor != null) {
                cursor.close();
            }
        }
    }

    public static String getPath(Context context, Uri uri) throws URISyntaxException {
        final boolean needToCheckUri = Build.VERSION.SDK_INT >= 19;
        String selection = null;
        String[] selectionArgs = null;
        // Uri is different in versions after KITKAT (Android 4.4), we need to
        // deal with different Uris.
        if (needToCheckUri && DocumentsContract.isDocumentUri(context.getApplicationContext(), uri)) {
            if (isExternalStorageDocument(uri)) {
                final String docId = DocumentsContract.getDocumentId(uri);
                final String[] split = docId.split(":");
                return Environment.getExternalStorageDirectory() + "/" + split[1];
            } else if (isDownloadsDocument(uri)) {
                final String id = DocumentsContract.getDocumentId(uri);
                uri = ContentUris.withAppendedId(
                        Uri.parse("content://downloads/public_downloads"), Long.valueOf(id));
            } else if (isMediaDocument(uri)) {
                final String docId = DocumentsContract.getDocumentId(uri);
                final String[] split = docId.split(":");
                final String type = split[0];
                if ("image".equals(type)) {
                    uri = MediaStore.Images.Media.EXTERNAL_CONTENT_URI;
                } else if ("video".equals(type)) {
                    uri = MediaStore.Video.Media.EXTERNAL_CONTENT_URI;
                } else if ("audio".equals(type)) {
                    uri = MediaStore.Audio.Media.EXTERNAL_CONTENT_URI;
                }
                selection = "_id=?";
                selectionArgs = new String[]{split[1]};
            }
        }
        if ("content".equalsIgnoreCase(uri.getScheme())) {
           /* String[] projection = {MediaStore.Images.Media.DATA};
            Cursor cursor = null;
            try {
                cursor = context.getContentResolver().query(uri, projection, selection, selectionArgs, null);
                int column_index = cursor.getColumnIndexOrThrow(MediaStore.Images.Media.DATA);
                if (cursor.moveToFirst()) {
                    return cursor.getString(column_index);
                }
            } catch (Exception e) {
            }*/
           return uri.getPath();
        } else if ("file".equalsIgnoreCase(uri.getScheme())) {
            return uri.getPath();
        }
        return null;
    }

    public static boolean isExternalStorageDocument(Uri uri) {
        return "com.android.externalstorage.documents".equals(uri.getAuthority());
    }

    /**
     * @param uri The Uri to check.
     * @return Whether the Uri authority is DownloadsProvider.
     */
    public static boolean isDownloadsDocument(Uri uri) {
        return "com.android.providers.downloads.documents".equals(uri.getAuthority());
    }

    /**
     * @param uri The Uri to check.
     * @return Whether the Uri authority is MediaProvider.
     */
    public static boolean isMediaDocument(Uri uri) {
        return "com.android.providers.media.documents".equals(uri.getAuthority());
    }

    public static String getFileName(Context context, Uri uri) {
        String result = null;
        if (uri.getScheme().equals("content")) {
            Cursor cursor = context.getContentResolver().query(uri, null, null, null, null);
            try {
                if (cursor != null && cursor.moveToFirst()) {
                    result = cursor.getString(cursor.getColumnIndex(OpenableColumns.DISPLAY_NAME));
                }
            } finally {
                cursor.close();
            }
        }
        if (result == null) {
            result = uri.getPath();
            int cut = result.lastIndexOf('/');
            if (cut != -1) {
                result = result.substring(cut + 1);
            }
        }
        return result;
    }

    public interface GetBitmap {
        void onGetBitamp(Bitmap serverBitmap, ArrayList<String> imagePathArrayList, String valueReturned, boolean isGallery);
    }

    private static Bitmap rotateImageIfRequired(Context context, Bitmap img, Uri selectedImage) throws IOException {

        InputStream input = context.getContentResolver().openInputStream(selectedImage);
        ExifInterface ei;
        if (Build.VERSION.SDK_INT > 23)
            ei = new ExifInterface(input);
        else
            ei = new ExifInterface(selectedImage.getPath());

        int orientation = ei.getAttributeInt(ExifInterface.TAG_ORIENTATION, ExifInterface.ORIENTATION_NORMAL);

        switch (orientation) {
            case ExifInterface.ORIENTATION_ROTATE_90:
                return rotateImage(img, 90);
            case ExifInterface.ORIENTATION_ROTATE_180:
                return rotateImage(img, 180);
            case ExifInterface.ORIENTATION_ROTATE_270:
                return rotateImage(img, 270);
            default:
                return img;
        }
    }

    private static Bitmap rotateImage(Bitmap img, int degree) {
        Matrix matrix = new Matrix();
        matrix.postRotate(degree);
        Bitmap rotatedImg = Bitmap.createBitmap(img, 0, 0, img.getWidth(), img.getHeight(), matrix, true);
        img.recycle();
        return rotatedImg;
    }

    private static boolean isExternalStorageAvailable() {
        String extStorageState = Environment.getExternalStorageState();
        if (Environment.MEDIA_MOUNTED.equals(extStorageState)) {
            return true;
        } else if (extStorageState.equals(Environment.MEDIA_MOUNTED_READ_ONLY)) {
            // Storage is only readable - RUH ROH
            FslLog.e(TAG, " Storage is read ONLY ");
        }
        return false;
    }


    private static String[] getRemovableStoragePaths() {
        String[] directories;
        String[] splits;
        ArrayList<String> pathList = new ArrayList<String>();
        BufferedReader bufferedReader = null;
        String lineRead;

        try {
            bufferedReader = new BufferedReader(new FileReader("/proc/mounts"));

            while ((lineRead = bufferedReader.readLine()) != null) {
                Log.d(TAG, "lineRead: " + lineRead);
                splits = lineRead.split(" ");
                Log.d(TAG, "Testing path: " + splits[1]);

                if (!splits[1].contains("/storage")) {
                    continue;
                }

                if (splits[1].contains("/emulated")) {
                    // emulated indicates an internal storage location, so skip it.
                    continue;
                }

                // Eliminate if not a directory or fully accessible
                FslLog.d(TAG, "Path found: " + splits[1]);

                // Met all the criteria, assume sdcard
                pathList.add(splits[1]);
            }

        } catch (FileNotFoundException e) {
        } catch (IOException e) {
        } finally {
            if (bufferedReader != null) {
                try {
                    bufferedReader.close();
                } catch (IOException e) {
                }
            }
        }

        // Send list back to caller

        if (pathList.size() == 0) {
            pathList.add("sdcard not found");
        } else {
            FslLog.d(TAG, "Found potential removable storage locations: " + pathList);
        }
        directories = new String[pathList.size()];
        for (int i = 0; i < pathList.size(); i++) {
            directories[i] = pathList.get(i);
        }
        return directories;
    }

}
