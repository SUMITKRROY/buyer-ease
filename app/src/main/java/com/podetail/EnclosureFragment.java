package com.podetail;

import android.app.Activity;
import android.content.ContentResolver;
import android.content.Context;
import android.content.Intent;
import android.database.Cursor;
import android.graphics.Bitmap;
import android.net.Uri;
import android.os.Bundle;
import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.DividerItemDecoration;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;
import android.text.TextUtils;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.webkit.MimeTypeMap;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.CheckBox;
import android.widget.EditText;
import android.widget.RelativeLayout;
import android.widget.Spinner;
import android.widget.TextView;
import android.widget.Toast;

import com.General.EnclosureModal;
import com.buyereasefsl.ItemInspectionDetailHandler;
import com.buyereasefsl.R;
import com.constant.FEnumerations;
import com.util.FslLog;
import com.util.GenUtils;
import com.util.MultipleImageHandler;
import com.util.RealPathUtil;

import java.io.File;
import java.net.URISyntaxException;
import java.util.ArrayList;
import java.util.List;

import me.drakeet.support.toast.ToastCompat;

/**
 * Created by ADMIN on 2/2/2018.
 */

public class EnclosureFragment extends Fragment {

    public static EnclosureFragment newInstance() {
        EnclosureFragment homeFragment = new EnclosureFragment();
        return homeFragment;
    }

    static EnclosureFragment enclosureFragment;
    View yourFragmentUI;

    RecyclerView recyclerViewUploadsFile;
    EnclosureUploadAdaptor enclosureUploadAdaptor;
    private List<EnclosureModal> enclosureModalList;
    RelativeLayout addUploadContainer;
    Spinner spinnerType;
    EditText editName;
    Button addDgitalContainer;
    TextView txtSelectedFile;
    ArrayList<String> _fileContent = new ArrayList<>();
    String _fileExtn = null, _fileName, _enclType;
    String TAG = "EnclosureFragment";
    CheckBox chkSendAsMail;

    //Overriden method onCreateView
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {

        yourFragmentUI = inflater.inflate(R.layout.enclosure_upload_layout, container, false);
//        yourFragmentUI.findViewById(R.id.inputLayout).setVisibility(View.GONE);


        enclosureFragment = this;
        FslLog.d(TAG, " Enclosure  fragment creating...");
        handleUploadsFileHandle();

        setHasOptionsMenu(true);
        return yourFragmentUI;


    }

    private void setFileUploadAdaptor() {
        if (enclosureUploadAdaptor == null) {
            enclosureUploadAdaptor = new EnclosureUploadAdaptor(enclosureFragment.getActivity()
                    , enclosureModalList, new EnclosureUploadAdaptor.OnItemClickListener() {
                @Override
                public void onItemClick(int pos) {
                    if (enclosureModalList != null && enclosureModalList.size() > 0) {
                        ItemInspectionDetailHandler.deleteEnclosure(enclosureFragment.getActivity(), enclosureModalList.get(pos));
                        enclosureModalList.remove(pos);
                        enclosureUploadAdaptor.notifyDataSetChanged();
                    }
                }
            });
            recyclerViewUploadsFile.setAdapter(enclosureUploadAdaptor);
        } else {
            enclosureUploadAdaptor.notifyDataSetChanged();
        }
    }

    private void handleUploadsFileHandle() {
        txtSelectedFile = (TextView) enclosureFragment.yourFragmentUI.findViewById(R.id.txtSelectedFile);
        spinnerType = (Spinner) enclosureFragment.yourFragmentUI.findViewById(R.id.spinnerType);
        editName = (EditText) enclosureFragment.yourFragmentUI.findViewById(R.id.editName);
        chkSendAsMail = (CheckBox) enclosureFragment.yourFragmentUI.findViewById(R.id.chkSendAsMail);
        addDgitalContainer = (Button) enclosureFragment.yourFragmentUI.findViewById(R.id.addDgitalContainer);
        addUploadContainer = (RelativeLayout) enclosureFragment.yourFragmentUI.findViewById(R.id.addUploadContainer);
        List<String> list = new ArrayList<>();
        list.add("General");
        list.add("Other");

        //Creating the ArrayAdapter instance having the country list
        ArrayAdapter masterArrary = new ArrayAdapter(enclosureFragment.getActivity(), R.layout.dialog_list_item, list);
        masterArrary.setDropDownViewResource(R.layout.dialog_list_item);
        //Setting the ArrayAdapter data on the Spinner
        spinnerType.setAdapter(masterArrary);
        spinnerType.setSelection(0);
        _enclType = "05";

        spinnerType.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> adapterView, View view, int i, long l) {
                if (i == 0) {
                    _enclType = "05";
                } else {
                    _enclType = "99";
                }
            }

            @Override
            public void onNothingSelected(AdapterView<?> adapterView) {

            }
        });


        addUploadContainer.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                POItemTabActivity.poItemTabActivity.PICKED_UP = FEnumerations.REQUEST_FOR_ENCLOSURE_ATTACHMENT;
//                PhotoHandler.showDialogForFileOrImages(POItemListActivity.this, PhotoHandler.DOCUMENT);

                MultipleImageHandler.showDialogForFileOrImages(enclosureFragment.getActivity(), MultipleImageHandler.DOCUMENT);
            }
        });

        addDgitalContainer.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                String _edtName = editName.getText().toString();


                if (enclosureFragment._fileContent.size() == 0) {
                    Toast toast = ToastCompat.makeText(enclosureFragment.getActivity(), "Select File", Toast.LENGTH_SHORT);
                    GenUtils.safeToastShow(TAG, getActivity(), toast);
                    return;
                }
                if (TextUtils.isEmpty(_fileExtn)) {
                    Toast toast = ToastCompat.makeText(enclosureFragment.getActivity(), "Select File", Toast.LENGTH_SHORT);
                    GenUtils.safeToastShow(TAG, getActivity(), toast);
                    return;
                }
                if (enclosureFragment._fileContent.size() > 0) {
                    for (int i = 0; i < enclosureFragment._fileContent.size(); i++) {
                        EnclosureModal enclosureModal = new EnclosureModal();

                        if (enclosureFragment._fileExtn.equals(FEnumerations.E_IMAGE_EXTN)) {
                            if (TextUtils.isEmpty(_edtName)) {
                                editName.setError("File Name");
                                editName.requestFocus();
                                return;
                            } else {
                                enclosureModal.ImageName = _edtName;
                            }
                        } else {
                            if (!TextUtils.isEmpty(_edtName)) {
                                enclosureModal.ImageName = _edtName;
                            } else {
                                enclosureModal.ImageName = _fileName;
                            }
                        }
                        if (TextUtils.isEmpty(_enclType)) {
                            Toast toast = ToastCompat.makeText(enclosureFragment.getActivity(), "Select enclosure type", Toast.LENGTH_SHORT);
                            GenUtils.safeToastShow(TAG, getActivity(), toast);
                            return;
                        }
                        if (chkSendAsMail.isChecked()) {
                            enclosureModal.numVal1 = 1;
                        } else {
                            enclosureModal.numVal1 = 0;
                        }
                        enclosureModal.fileExt = enclosureFragment._fileExtn;
                        enclosureModal.fileContent = enclosureFragment._fileContent.get(i);
                        enclosureModal.Title = enclosureModal.ImageName;
                        enclosureModal.fileName = enclosureModal.ImageName;
                        enclosureModal.EnclType = enclosureFragment._enclType;
                        enclosureModal.pRowID = ItemInspectionDetailHandler.GeneratePK(enclosureFragment.getActivity(), "QREnclosure");
                        if (enclosureModalList == null)
                            enclosureModalList = new ArrayList<>();
                        enclosureModalList.add(enclosureModal);
                        updateEnclosure(enclosureModal);
                    }
                }
                setFileUploadAdaptor();
                enclosureFragment._fileExtn = null;
                enclosureFragment._fileContent.clear();
                enclosureFragment._fileName = null;
                editName.setText("");
                txtSelectedFile.setText("");
            }
        });

        recyclerViewUploadsFile = (RecyclerView) enclosureFragment.yourFragmentUI.findViewById(R.id.recyclerViewUploadsFile);
        recyclerViewUploadsFile.setHasFixedSize(true);
        recyclerViewUploadsFile.addItemDecoration(new DividerItemDecoration(enclosureFragment.getActivity(),
                DividerItemDecoration.VERTICAL));

        // use a linear layout manager
        LinearLayoutManager mLayoutManager = new LinearLayoutManager(enclosureFragment.getActivity());
        recyclerViewUploadsFile.setLayoutManager(mLayoutManager);

        enclosureModalList = new ArrayList<>();

        List<EnclosureModal> lEnclosure = ItemInspectionDetailHandler.getQREnclosureList(enclosureFragment.getActivity(), POItemTabActivity.poItemTabActivity.inspectionModal.pRowID);
        if (enclosureModalList != null)
            enclosureModalList.clear();
        else enclosureModalList = new ArrayList<>();
        enclosureModalList.addAll(lEnclosure);

        setFileUploadAdaptor();

    }

    private void handleSaveEnclosure() {
        if (enclosureModalList != null) {
            for (int i = 0; i < enclosureModalList.size(); i++) {

                updateEnclosure(enclosureModalList.get(i));
            }
            Toast toast = ToastCompat.makeText(enclosureFragment.getActivity(), "update data successfully", Toast.LENGTH_SHORT);
            GenUtils.safeToastShow(TAG, getActivity(), toast);
        }
    }

    private void updateEnclosure(EnclosureModal enclosureModal) {
        if (POItemTabActivity.poItemTabActivity.inspectionModal != null)
            ItemInspectionDetailHandler.updateEnclosure(enclosureFragment.getActivity(), POItemTabActivity.poItemTabActivity.inspectionModal, enclosureModal);
        else FslLog.d(TAG, " Inspection row id IS NULL");
    }

    @Override
    public void onActivityResult(int requestCode, int resultCode, Intent data) {


        if (requestCode == FEnumerations.PICKFILE_RESULT_CODE
                && resultCode == Activity.RESULT_OK
                && enclosureFragment != null
                && enclosureFragment.getActivity() != null) {
            Uri uri = data.getData();


            String tempFilePath = data.getData().getPath();
            String FilePath = null;
            FilePath = RealPathUtil.getRealPath(enclosureFragment.getActivity(), uri);// getPath(enclosureFragment.getActivity(), uri);
            String fileExtension = MimeTypeMap.getFileExtensionFromUrl(FilePath);

            FslLog.e(TAG, " uri :  " + uri);
            FslLog.e(TAG, " Temp file path :  " + tempFilePath);


            if (!TextUtils.isEmpty(FilePath)) {
                FslLog.d(TAG, " real FilePath IS \n" + FilePath);
                enclosureFragment._fileName = (MultipleImageHandler.getFileName(enclosureFragment.getActivity(), data.getData()));//data.getData().getLastPathSegment()
                enclosureFragment._fileExtn = getMimeType(enclosureFragment.getActivity(), uri);
                FslLog.e(TAG, " ext :  " + enclosureFragment._fileExtn);
                if (enclosureFragment._fileContent == null) {
                    enclosureFragment._fileContent = new ArrayList<>();
                }
                enclosureFragment._fileContent.clear();
                enclosureFragment._fileContent.add(FilePath);
                if (TextUtils.isEmpty(editName.getText().toString())) {
                    editName.setText(enclosureFragment._fileName);
                }
                txtSelectedFile.setText(enclosureFragment._fileName);


            } else {
                Toast.makeText(enclosureFragment.getActivity(), " File not support", Toast.LENGTH_SHORT).show();
                FslLog.d(TAG, " CONVERTED BASE64 String IS NULL");
            }

            POItemTabActivity.poItemTabActivity.PICKED_UP = -1;
        } else if (requestCode == FEnumerations.RESULT_LOAD_IMAGE) {
            MultipleImageHandler.onActivityResult(enclosureFragment.getActivity(),
                    requestCode, resultCode, data, new MultipleImageHandler.GetBitmap() {
                        @Override
                        public void onGetBitamp(Bitmap serverBitmap,
                                                ArrayList<String> imagePathArrayList,
                                                String valueReturned,
                                                boolean isGallery) {

                            if (imagePathArrayList != null && imagePathArrayList.size() > 0) {
                                _fileContent.addAll(imagePathArrayList);
                                _fileExtn = FEnumerations.E_IMAGE_EXTN;
                                _fileName = "" + (imagePathArrayList.size() + 1);
                                if (imagePathArrayList.size() == 1) {
                                    txtSelectedFile.setText(_fileName);
                                } else {
                                    txtSelectedFile.setText(imagePathArrayList.size() + " Selected Images");
                                }
                            }
                            POItemTabActivity.poItemTabActivity.PICKED_UP = -1;

                        }
                    });
        }

    }

    public static String getPath(Context context, Uri uri) throws URISyntaxException {
        if ("content".equalsIgnoreCase(uri.getScheme())) {
            String[] projection = {"_data"};
            Cursor cursor = null;

            try {
                cursor = context.getContentResolver().query(uri, projection, null, null, null);
                int column_index = cursor.getColumnIndexOrThrow("_data");
                if (cursor.moveToFirst()) {
                    return cursor.getString(column_index);
                }
            } catch (Exception e) {
                // Eat it
            }
        } else if ("file".equalsIgnoreCase(uri.getScheme())) {
            return uri.getPath();
        }

        return null;
    }

    public static String getMimeType(Context context, Uri uri) {
        String extension;

        //Check uri format to avoid null
        if (uri.getScheme().equals(ContentResolver.SCHEME_CONTENT)) {
            //If scheme is a content
            final MimeTypeMap mime = MimeTypeMap.getSingleton();
            extension = mime.getExtensionFromMimeType(context.getContentResolver().getType(uri));
        } else {
            //If scheme is a File
            //This will replace white spaces with %20 and also other special characters. This will avoid returning null values on file name with spaces and special characters.
            extension = MimeTypeMap.getFileExtensionFromUrl(Uri.fromFile(new File(uri.getPath())).toString());

        }

        return extension;
    }


}
