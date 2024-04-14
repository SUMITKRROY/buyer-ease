package com.buyereasefsl;

import android.animation.Animator;
import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.net.Uri;
import androidx.recyclerview.widget.RecyclerView;
import android.text.TextUtils;
import android.view.GestureDetector;
import android.view.LayoutInflater;
import android.view.MotionEvent;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import com.General.EnclosureModal;
import com.bumptech.glide.Glide;
import com.bumptech.glide.request.target.DrawableImageViewTarget;
import com.constant.AppConfig;
import com.constant.FClientConfig;
import com.constant.FClientConstants;
import com.constant.FEnumerations;
import com.util.FslLog;
import com.util.GenUtils;
import com.util.NetworkUtil;
import com.util.PermissionSeekingActivity;

import java.io.File;
import java.util.List;
import java.util.regex.Pattern;

import me.drakeet.support.toast.ToastCompat;

/**
 * Created by ADMIN on 12/22/2017.
 */

public class EnclosureAdaptor extends RecyclerView.Adapter<EnclosureAdaptor.ViewHolder> {

    Activity activity;
    List<EnclosureModal> ModelList;
    //    private OnItemClickListener listener;
    public Animator mCurrentAnimator;
//    onItemClickListener mListener;

    //    public interface OnItemClickListener {
//        void onItemClick(POItemDtl item);
//    }
    String TAG = "EnclosureAdaptor";

    public EnclosureAdaptor(Activity clsHomeActivity,
                            List<EnclosureModal> inspectionModalList/*, onWorkManShipClickListener listener*/) {
        activity = clsHomeActivity;
        ModelList = inspectionModalList;
//        mListener = listener;
    }


    // Provide a reference to the views for each data item
    // Complex data items may need more than one view per item, and
    // you provide access to all the views for a data item in a view holder
    public class ViewHolder extends RecyclerView.ViewHolder {
        // each data item is just a string in this case
//        public TextView txtItemRef;


        int position;
        TextView txtInspectionNo, txtInspectionDate, txtInspectionActivity, txtInspectionQR, txtInspector;
//        LinearLayout attachmentCountContainer;

        ImageView iconImportant;

        public ViewHolder(View view) {
            super(view);

            txtInspectionNo = (TextView) view.findViewById(R.id.txtInspectionNo);
            txtInspectionDate = (TextView) view.findViewById(R.id.txtInspectionDate);
            txtInspectionActivity = (TextView) view.findViewById(R.id.txtInspectionActivity);
            txtInspectionQR = (TextView) view.findViewById(R.id.txtInspectionQR);
            txtInspector = (TextView) view.findViewById(R.id.txtInspector);
            iconImportant = (ImageView) view.findViewById(R.id.iconImportant);

        }
    }


    // Create new views (invoked by the layout manager)
    @Override
    public ViewHolder onCreateViewHolder(ViewGroup parent,
                                         int viewType) {
        // create a new view
        LayoutInflater inflater = LayoutInflater.from(
                parent.getContext());
        View v =
                inflater.inflate(R.layout.history_row, parent, false);
        // set the view's size, margins, paddings and layout parameters
        ViewHolder vh = new ViewHolder(v);

        return vh;
    }

    // Replace the contents of a view (invoked by the layout manager)
    @Override
    public void onBindViewHolder(ViewHolder holder, final int position) {
        // - get element from your dataset at this position
        // - replace the contents of the view with that element
//        final String name = values.get(position);


        final EnclosureModal enclosureModal = ModelList.get(position);
        holder.txtInspectionNo.setText(enclosureModal.ContextDs);
        holder.txtInspectionDate.setText(enclosureModal.ContextDs2);
        holder.txtInspectionActivity.setText("");
        holder.txtInspectionQR.setText(enclosureModal.EnclFileType);
        holder.txtInspector.setText(enclosureModal.Title);
        if (enclosureModal.IsImportant == 1 && enclosureModal.IsRead == 0) {
            holder.iconImportant.setVisibility(View.VISIBLE);
            DrawableImageViewTarget imageViewTarget = new DrawableImageViewTarget(holder.iconImportant);
            Glide.with(activity.getApplicationContext()).load(R.raw.icon_gif_test).into(imageViewTarget);
        } else {
            holder.iconImportant.setVisibility(View.INVISIBLE);
        }

        if (enclosureModal.fileExt.contains(".pdf")) {
            holder.txtInspectionDate.setCompoundDrawablesWithIntrinsicBounds(R.drawable.icons_pdf, 0, 0, 0);
        } else if (enclosureModal.fileExt.contains(".doc")) {
            holder.txtInspectionDate.setCompoundDrawablesWithIntrinsicBounds(R.drawable.ic_word, 0, 0, 0);
        } else if (enclosureModal.fileExt.contains(".xls") || enclosureModal.fileExt.contains(".xlsx")) {
            holder.txtInspectionDate.setCompoundDrawablesWithIntrinsicBounds(R.drawable.ic_excel, 0, 0, 0);
        } else {
            holder.txtInspectionDate.setCompoundDrawablesWithIntrinsicBounds(R.drawable.ic_attach_file_black_24dp, 0, 0, 0);
        }
        holder.txtInspectionDate.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                checkPermission(enclosureModal);
            }
        });
    }

    @Override
    public int getItemCount() {
        return ModelList.size();
    }

    public static class RecyclerTouchListener implements RecyclerView.OnItemTouchListener {

        private GestureDetector gestureDetector;
        private ClickListener clickListener;

        public RecyclerTouchListener(Context context, final RecyclerView recyclerView, final ClickListener clickListener) {
            this.clickListener = clickListener;
            gestureDetector = new GestureDetector(context, new GestureDetector.SimpleOnGestureListener() {
                @Override
                public boolean onSingleTapUp(MotionEvent e) {
                    return true;
                }

                @Override
                public void onLongPress(MotionEvent e) {
                    View child = recyclerView.findChildViewUnder(e.getX(), e.getY());
                    if (child != null && clickListener != null) {
                        clickListener.onLongClick(child, recyclerView.getChildPosition(child));
                    }
                }
            });
        }

        @Override
        public boolean onInterceptTouchEvent(RecyclerView rv, MotionEvent e) {

            View child = rv.findChildViewUnder(e.getX(), e.getY());
            if (child != null && clickListener != null && gestureDetector.onTouchEvent(e)) {
                clickListener.onClick(child, rv.getChildPosition(child));
            }
            return false;
        }

        @Override
        public void onTouchEvent(RecyclerView rv, MotionEvent e) {
        }

        @Override
        public void onRequestDisallowInterceptTouchEvent(boolean disallowIntercept) {

        }
    }

    public interface ClickListener {
        void onClick(View view, int position);

        void onLongClick(View view, int position);
    }

//
//    public interface onItemClickListener {
//        void onClick(int itemReportPosition, InspectionModal workManShipModel, int position);
//    }

    private void checkPermission(final EnclosureModal enclosureModal) {

        boolean lPermissionFlag = PermissionSeekingActivity.checkPermission(activity
                , FEnumerations.SAVE_DOC_PERMISSION);

        if (lPermissionFlag) {

            FslLog.d(TAG, "write or read storage permission  is granted");
            String fileName = enclosureModal.ImageName;//+ "." + enclosureModal.fileExt;
            if (!TextUtils.isEmpty(enclosureModal.ImageName)) {
                File file = GenUtils.getFile(FClientConfig.enclosureFolder, fileName);
                if (file.exists()) {
                    Uri path = GenUtils.getUri(activity, file);
                    openOnReader(path, enclosureModal.fileExt);
                    if (enclosureModal.IsRead == 0) {
                        enclosureModal.IsRead = 1;
                        ItemInspectionDetailHandler.updateEnClosureToRead(activity, enclosureModal);
                        ItemInspectionDetailHandler.getUpdateDownloadStaus(activity, enclosureModal);
                    }
                } else {
                    downLoadAttachment(activity, enclosureModal);
                }
            } else {
                downLoadAttachment(activity, enclosureModal);
            }


        } else {
            FslLog.d(TAG, "seeking write or read storage permission");
            Intent intent = new Intent(activity, PermissionSeekingActivity.class);
            intent.putExtra(FClientConstants.PERMISSION_INTENT, FEnumerations.SAVE_DOC_PERMISSION);
            activity.startActivityForResult(intent, FEnumerations.SAVE_DOC_PERMISSION);
        }
    }

    private void downLoadAttachment(final Activity activity, final EnclosureModal enclosureModal) {

//        if ()

        if (!NetworkUtil.isNetworkAvailable(activity)) {
            Toast toast = ToastCompat.makeText(activity, "No internet connection.", Toast.LENGTH_SHORT);
            GenUtils.safeToastShow(TAG, activity, toast);
            return;
        }

        String downloadUrl = AppConfig.URL_DOWNLOAD_BASE_URL +
                "Enclousers/" + enclosureModal.ImageName + "&PathToken=" + enclosureModal.ImagePathID;

        FslLog.d(" ENCLOSURE URL ", "" + downloadUrl);

//        String[] arrOfStr = enclosureModal.ImageName.split(".", 0);
        String[] strA = enclosureModal.ImageName.split(Pattern.quote("."));
        new DownloadHandler(this.activity,
                strA[0],
                enclosureModal.fileExt,
                FEnumerations.E_DOWNLOAD_ENCLOSURE,
                new DownloadHandler.CallBackToDownloadResult() {
                    @Override
                    public void onSuccess(String result) {
                        FslLog.d(TAG, " downloaded file " + result);


                        if (activity != null && !TextUtils.isEmpty(result)) {
                            File file = GenUtils.getFile(FClientConfig.enclosureFolder, result);

                            if (file.exists()) {
                                Uri path = GenUtils.getUri(activity, file);
                                FslLog.d(TAG, " uri " + path);
                                openOnReader(path, enclosureModal.fileExt);
                                enclosureModal.IsRead = 1;
                                ItemInspectionDetailHandler.updateEnClosureToRead(activity, enclosureModal);
                                ItemInspectionDetailHandler.getUpdateDownloadStaus(activity, enclosureModal);

                            } else {
                                Toast toast = ToastCompat.makeText(activity, "could't download file", Toast.LENGTH_SHORT);
                                GenUtils.safeToastShow(TAG, activity, toast);
                            }
                        } else {
                            Toast toast = ToastCompat.makeText(activity, "could't download file", Toast.LENGTH_SHORT);
                            GenUtils.safeToastShow(TAG, activity, toast);
                        }
                    }
                }).execute(downloadUrl);
    }

    private void openOnReader(Uri uri, String fileExtn) {

        try {
            Intent intent = new Intent(Intent.ACTION_VIEW);
            // Check what kind of file you are trying to open, by comparing the url with extensions.
            // When the if condition is matched, plugin sets the correct intent (mime) type,
            // so Android knew what application to use to open the file
            if (fileExtn.contains(".doc") || fileExtn.contains(".docx")) {
                // Word document
                intent.setDataAndType(uri, "application/msword");
            } else if (fileExtn.contains(".pdf")) {
                // PDF file
                intent.setDataAndType(uri, "application/pdf");
            } else if (fileExtn.contains(".ppt") || fileExtn.contains(".pptx")) {
                // Powerpoint file
                intent.setDataAndType(uri, "application/vnd.ms-powerpoint");
            } else if (fileExtn.contains(".xls") || fileExtn.contains(".xlsx")) {
                // Excel file
                intent.setDataAndType(uri, "application/vnd.ms-excel");
            } else if (fileExtn.contains(".zip") || fileExtn.contains(".rar")) {
                // WAV audio file
                intent.setDataAndType(uri, "application/x-wav");
            } else if (fileExtn.contains(".rtf")) {
                // RTF file
                intent.setDataAndType(uri, "application/rtf");
            } else if (fileExtn.contains(".wav") || fileExtn.contains(".mp3")) {
                // WAV audio file
                intent.setDataAndType(uri, "audio/x-wav");
            } else if (fileExtn.contains(".gif")) {
                // GIF file
                intent.setDataAndType(uri, "image/gif");
            } else if (fileExtn.contains(".jpg") || fileExtn.contains(".jpeg") || fileExtn.contains(".png")) {
                // JPG file
                intent.setDataAndType(uri, "image/jpeg");
            } else if (fileExtn.contains(".txt")) {
                // Text file
                intent.setDataAndType(uri, "text/plain");
            } else if (fileExtn.contains(".3gp") || fileExtn.contains(".mpg") || fileExtn.contains(".mpeg") || fileExtn.contains(".mpe") || fileExtn.contains(".mp4") || fileExtn.contains(".avi")) {
                // Video files
                intent.setDataAndType(uri, "video/*");
            } else {
                //if you want you can also define the intent type for any other file

                //additionally use else clause below, to manage other unknown extensions
                //in this case, Android will show all applications installed on the device
                //so you can choose which application to use
                intent.setDataAndType(uri, "*/*");
            }
            intent.addFlags(Intent.FLAG_GRANT_READ_URI_PERMISSION);
            intent.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP);
            GenUtils.grantAllUriPermissions(activity, intent, uri);
            activity.startActivity(intent);
            notifyDataSetChanged();
        } catch (Exception e) {
            FslLog.d(TAG, "Library Reader application is not installed in your device");
            Toast toast = ToastCompat.makeText(activity, "No app available to open", Toast.LENGTH_SHORT);
            GenUtils.safeToastShow(TAG, activity, toast);
            e.printStackTrace();
        }

       /* try {
            Intent intent = new Intent(Intent.ACTION_VIEW);
            intent.setDataAndType(path, "application/pdf");
            intent.addFlags(Intent.FLAG_GRANT_READ_URI_PERMISSION);
            intent.addFlags(Intent.FLAG_ACTIVITY_NO_HISTORY);
            Intent intent1 = Intent.createChooser(intent, "Open With");
            activity.startActivity(intent1);
            notifyDataSetChanged();

        } catch (ActivityNotFoundException e) {
            SocietyLog.d(TAG, "PDF Reader application is not installed in your device");
        }*/
    }

}
