package com.buyereasefsl;

import android.animation.Animator;
import android.app.Activity;
import android.os.Bundle;
import androidx.fragment.app.FragmentActivity;
import androidx.fragment.app.FragmentTransaction;
import androidx.recyclerview.widget.RecyclerView;
import android.text.TextUtils;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.CheckBox;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.RadioButton;
import android.widget.RadioGroup;
import android.widget.Spinner;
import android.widget.TextView;

import com.constant.FEnumerations;

import java.util.ArrayList;
import java.util.List;
import java.util.regex.Pattern;

/**
 * Created by ADMIN on 12/29/2017.
 */

public class QualityParameterAdaptor extends RecyclerView.Adapter<QualityParameterAdaptor.ViewHolder> {

    Activity activity;
    List<QualityParameter> ModelList;
    //    private OnItemClickListener listener;
    public Animator mCurrentAnimator;
//    onWorkManShipClickListener mListener;

    //    public interface OnItemClickListener {
//        void onItemClick(POItemDtl item);
//    }
    RecyclerView recyclerView;
    int viewType;
    ClickListener mListener;

    public QualityParameterAdaptor(Activity clsHomeActivity,
                                   RecyclerView recyclerViewQualityParameter,
                                   List<QualityParameter> digitalsUploadModals,
                                   int mViewType,
                                   ClickListener listener) {
        activity = clsHomeActivity;
        ModelList = digitalsUploadModals;
        recyclerView = recyclerViewQualityParameter;
        viewType = mViewType;
        mListener = listener;
    }


    // Provide a reference to the views for each data item
    // Complex data items may need more than one view per item, and
    // you provide access to all the views for a data item in a view holder
    public class ViewHolder extends RecyclerView.ViewHolder {
        // each data item is just a string in this case
//        public TextView txtItemRef;

//        Spinner spinnerStatus;
        TextView spinnerStatus;
        int position;
        TextView txtParamTitle, attachmentCount;
        EditText editParamDescription;
        CheckBox chkToApplicable;
        LinearLayout spinnerAndEditDescContainer, radioActionContainer,
                chkActionContainer, spinnerContainer, attachmentCountContainer;
        RadioButton rdoYes, rdoNo;
        ImageView attachmentImage;
        RadioGroup radioGroup;

        public ViewHolder(View view) {
            super(view);
            attachmentCount = (TextView) view.findViewById(R.id.attachmentCount);
            txtParamTitle = (TextView) view.findViewById(R.id.txtParamTitle);
            editParamDescription = (EditText) view.findViewById(R.id.editParamDescription);
//            spinnerStatus = (Spinner) view.findViewById(R.id.spinnerStatus);
            spinnerStatus = (TextView) view.findViewById(R.id.spinnerStatus);
            chkToApplicable = (CheckBox) view.findViewById(R.id.chkToApplicable);
            spinnerAndEditDescContainer = (LinearLayout) view.findViewById(R.id.spinnerAndEditDescContainer);
            radioActionContainer = (LinearLayout) view.findViewById(R.id.radioActionContainer);
            chkActionContainer = (LinearLayout) view.findViewById(R.id.chkActionContainer);
            rdoYes = (RadioButton) view.findViewById(R.id.rdoYes);
            rdoNo = (RadioButton) view.findViewById(R.id.rdoNo);
            radioGroup = (RadioGroup) view.findViewById(R.id.radioGroup);
            spinnerContainer = (LinearLayout) view.findViewById(R.id.spinnerContainer);
            attachmentCountContainer = (LinearLayout) view.findViewById(R.id.attachmentCountContainer);
            attachmentImage = (ImageView) view.findViewById(R.id.attachmentImage);
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
                inflater.inflate(R.layout.quality_parameter_row, parent, false);
        // set the view's size, margins, paddings and layout parameters
        ViewHolder vh = new ViewHolder(v);

        return vh;
    }

    // Replace the contents of a view (invoked by the layout manager)
    @Override
    public void onBindViewHolder(final ViewHolder holder, final int position) {
        // - get element from your dataset at this position
        // - replace the contents of the view with that element
//        final String name = values.get(position);


        final QualityParameter qualityParameter = ModelList.get(position);


        holder.txtParamTitle.setText(qualityParameter.MainDescr);

        holder.spinnerAndEditDescContainer.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                handleOnClickToOpenActivity(ModelList.get(position));
            }
        });

        if (qualityParameter.PromptType < 1) {
            holder.radioActionContainer.setVisibility(View.GONE);
            holder.chkActionContainer.setVisibility(View.VISIBLE);
            if (qualityParameter.IsApplicable == 1 || qualityParameter.OptionSelected == 1) {
                holder.chkToApplicable.setChecked(true);
                holder.spinnerAndEditDescContainer.setVisibility(View.VISIBLE);
            } else {
                holder.chkToApplicable.setChecked(false);
                holder.spinnerAndEditDescContainer.setVisibility(View.GONE);
            }
            holder.chkToApplicable.setTag(qualityParameter);
            holder.chkToApplicable.setOnClickListener(new View.OnClickListener() {
                public void onClick(View v) {
                    CheckBox cb = (CheckBox) v;

                    QualityParameter inspModal = (QualityParameter) cb.getTag();
                    if (cb.isChecked()) {
                        inspModal.IsApplicable = 1; //cb.isChecked();
                        ModelList.get(position).IsApplicable = 1;
                        holder.spinnerAndEditDescContainer.setVisibility(View.VISIBLE);
                        handleOnClickToOpenActivity(ModelList.get(position));
                    } else {
                        inspModal.IsApplicable = 0; //cb.isChecked();
                        ModelList.get(position).IsApplicable = 0;
                        holder.spinnerAndEditDescContainer.setVisibility(View.GONE);
                        handleOnNotApplicable(ModelList.get(position));
                    }
                }
            });
        } else {
            holder.radioActionContainer.setVisibility(View.VISIBLE);
            holder.chkActionContainer.setVisibility(View.GONE);
            if (qualityParameter.IsApplicable == 1 || qualityParameter.OptionSelected == 1) {
                holder.rdoYes.setChecked(true);
                holder.spinnerAndEditDescContainer.setVisibility(View.VISIBLE);
            } else {
                holder.rdoNo.setChecked(true);
                holder.spinnerAndEditDescContainer.setVisibility(View.GONE);
            }

            View.OnClickListener rbClick = new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    RadioButton checked_rb = (RadioButton) v;
                    boolean checked = checked_rb.isChecked();
                    QualityParameter inspModal = (QualityParameter) v.getTag();
                    // Check which radio button was clicked
                    switch (checked_rb.getId()) {
                        case R.id.rdoYes:
                            if (checked) {
                                inspModal.IsApplicable = 1;
                                ModelList.get(position).IsApplicable = 1;
                                holder.spinnerAndEditDescContainer.setVisibility(View.VISIBLE);
                                handleOnClickToOpenActivity(ModelList.get(position));
                            }
//                            holder.rdoYes = rb;
                            break;
                        case R.id.rdoNo:
                            if (checked) {
                                inspModal.IsApplicable = 0;
                                ModelList.get(position).IsApplicable = 0;
                                holder.spinnerAndEditDescContainer.setVisibility(View.GONE);
                                handleOnNotApplicable(ModelList.get(position));
                            }
//                            holder.rdoNo = rb;
                            break;
                    }
                }
            };
            holder.rdoYes.setTag(qualityParameter);
            holder.rdoNo.setTag(qualityParameter);
            holder.rdoYes.setOnClickListener(rbClick);
            holder.rdoNo.setOnClickListener(rbClick);

        }

        holder.editParamDescription.setText(qualityParameter.Remarks);
        holder.editParamDescription.setTag(position);
        holder.editParamDescription.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                handleOnClickToOpenActivity(ModelList.get(position));
            }
        });

       /* holder.editParamDescription.setOnFocusChangeListener(new View.OnFocusChangeListener() {
            public void onFocusChange(View v, boolean hasFocus) {
                if (!hasFocus) {
                    final int mPos = (int) v.getTag();
                    final EditText edt = (EditText) v;
                    ModelList.get(mPos).Remarks = edt.getText().toString();

                    if (recyclerView != null && !recyclerView.isComputingLayout()) {
                        notifyDataSetChanged();
                    }

                }
            }
        });*/

        if (!TextUtils.isEmpty(qualityParameter.OptionValue)) {

            String[] spStr = qualityParameter.OptionValue.split(Pattern.quote(")S("), 0);
            if (spStr != null && spStr.length > 0) {
                String[] spStr1 = spStr[0].split(Pattern.quote("|"));
                ArrayList<String> itemsOptionArray = new ArrayList<>();
                for (String a : spStr1) {
                    itemsOptionArray.add(a);
                }
                qualityParameter.applicableLists = new ArrayList<>();
                for (int i = 0; i < itemsOptionArray.size(); i++) {
                    ApplicableList generalModel = new ApplicableList();
                    String[] arrSub = itemsOptionArray.get(i).split("~");
                    if (arrSub.length > 1) {
                        generalModel.title = arrSub[0].trim();
                        generalModel.value = Integer.parseInt(arrSub[1].trim());
                        qualityParameter.applicableLists.add(generalModel);
                    }
                }
                if (qualityParameter.applicableLists != null && qualityParameter.applicableLists.size() > 0) {
                    int selectPos = 0;
                    String titleString = null;
                    List<String> list = new ArrayList<>();
                    for (int i = 0; i < qualityParameter.applicableLists.size(); i++) {
                        list.add(qualityParameter.applicableLists.get(i).title);
                        if (qualityParameter.OptionSelected == qualityParameter.applicableLists.get(i).value) {
                            selectPos = i;
                            titleString = qualityParameter.applicableLists.get(i).title;
                        }
                    }
                    ArrayAdapter masterArrary = new ArrayAdapter(activity, R.layout.dialog_list_item, list);
                    masterArrary.setDropDownViewResource(R.layout.dialog_list_item);
                    holder.spinnerStatus.setText(titleString);
                    //Setting the ArrayAdapter data on the Spinner
//                    holder.spinnerStatus.setAdapter(masterArrary);
//                    holder.spinnerStatus.setTag(position);
//                    holder.spinnerStatus.setSelection(selectPos);
                    /*holder.spinnerStatus.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
                        @Override
                        public void onItemSelected(AdapterView<?> adapterView, View view, int i, long l) {
//                        final int mPos = (int) view.getTag();
                            ModelList.get(position).OptionSelected = qualityParameter.applicableLists.get(i).value;
//                        packagePoItemDetalDetail.PKG_Me_Master_InspectionResultID = overAllResultStatusList.get(i).MainID;
                        }

                        @Override
                        public void onNothingSelected(AdapterView<?> adapterView) {

                        }
                    });*/
                }
            } else {
                holder.spinnerContainer.setVisibility(View.GONE);
            }

        } else {
            holder.spinnerContainer.setVisibility(View.GONE);
        }


       /* if (workManShipModel.listImages != null *//*&& workManShipModel.listImages.size() > 0*//*) {
            byte[] decodedString = Base64.decode(workManShipModel.listImages, Base64.DEFAULT);

            Glide.with(activity)
                    .load(decodedString)
                    .asBitmap()
                    .placeholder(activity.getResources().getDrawable(R.drawable.default_image_large))
                    .into(holder.attachmentImage);

            final ArrayList<String > list=new ArrayList<>();
            list.add(workManShipModel.listImages);


            holder.attachmentImage.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {
                    if (workManShipModel.listImages != null
                            *//*&& workManShipModel.listImages.size() > 0*//*) {
                        Bundle bundle = new Bundle();
                        bundle.putStringArrayList("galleryModels", list);
                        bundle.putInt("position", position);
//                    FragmentManager fragmentManager =activity.
                        FragmentTransaction ft = ((FragmentActivity) activity)
                                .getSupportFragmentManager().beginTransaction();
                        SlideshowDialogFragment newFragment = SlideshowDialogFragment.newInstance();
                        newFragment.setArguments(bundle);
                        newFragment.show(ft, "slideshow");
                    }
                }
            });
        }*/

        if (viewType == FEnumerations.E_ADAPTOR_VIEW_TYPE_QUALITY_PARAMETER) {
            holder.attachmentCount.setText(qualityParameter.imageAttachmentList.size() + "");

           /* holder.attachmentImage.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {
                    mListener.onQualityClick(view, position);
                }
            });*/

            if (qualityParameter.ImageRequired == 0) {
                holder.attachmentCountContainer.setVisibility(View.GONE);
            } else {
                holder.attachmentCountContainer.setVisibility(View.VISIBLE);
            }
        } else {

            holder.attachmentCount.setText(qualityParameter.imageAttachmentList.size() + "");
           /* holder.attachmentImage.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {
                    mListener.onClickImageTestReport(view, position);
                }
            });*/

            if (qualityParameter.ImageRequired == 0) {
                holder.attachmentCountContainer.setVisibility(View.GONE);
            } else {
                holder.attachmentCountContainer.setVisibility(View.VISIBLE);
            }
        }
        holder.attachmentCount.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if (qualityParameter.imageAttachmentList != null
                        && qualityParameter.imageAttachmentList.size() > 0) {
                    final ArrayList<String> list = new ArrayList<>();
                    for (int i = 0; i < qualityParameter.imageAttachmentList.size(); i++) {
                        list.add(qualityParameter.imageAttachmentList.get(i).selectedPicPath);
                    }

                    Bundle bundle = new Bundle();
                    bundle.putStringArrayList("galleryModels", list);
                    bundle.putInt("position", 0);
//                    FragmentManager fragmentManager =activity.
                    FragmentTransaction ft = ((FragmentActivity) activity)
                            .getSupportFragmentManager().beginTransaction();
                    SlideshowDialogFragment newFragment = SlideshowDialogFragment.newInstance();
                    newFragment.setArguments(bundle);
                    newFragment.show(ft, "slideshow");
                }
            }
        });
    }

    private void handleOnNotApplicable(QualityParameter qualityParameter) {

        if (viewType == FEnumerations.E_ADAPTOR_VIEW_TYPE_QUALITY_PARAMETER) {
            mListener.onNoApplicableClickOnQuality(qualityParameter);
        } else {
            mListener.onNoApplicableClickOnInternalTest(qualityParameter);
        }
    }

    private void handleOnClickToOpenActivity(QualityParameter qualityParameter) {
        if (viewType == FEnumerations.E_ADAPTOR_VIEW_TYPE_QUALITY_PARAMETER) {
            mListener.onQualityClick(qualityParameter);
        } else {
            mListener.onClickImageTestReport(qualityParameter);
        }
    }

    @Override
    public int getItemCount() {
        return ModelList.size();
    }


    public interface ClickListener {
        void onQualityClick(QualityParameter qualityParameter);

        void onClickImageTestReport(QualityParameter qualityParameter);

        void onNoApplicableClickOnQuality(QualityParameter qualityParameter);

        void onNoApplicableClickOnInternalTest(QualityParameter qualityParameter);
    }


}