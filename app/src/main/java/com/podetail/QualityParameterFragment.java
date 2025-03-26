package com.podetail;

import android.content.Intent;
import android.os.Bundle;

import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.DividerItemDecoration;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.buyereasefsl.AddQualityParameter;
import com.buyereasefsl.ItemInspectionDetailHandler;
import com.buyereasefsl.QualityParameter;
import com.buyereasefsl.QualityParameterAdaptor;
import com.buyereasefsl.R;
import com.constant.FEnumerations;
import com.util.FslLog;
import com.util.GenUtils;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by ADMIN on 2/2/2018.
 */

public class QualityParameterFragment extends Fragment implements QualityParameterAdaptor.ClickListener {

    public static QualityParameterFragment newInstance() {
        QualityParameterFragment homeFragment = new QualityParameterFragment();
        return homeFragment;
    }

    QualityParameterFragment qualityParameterFragment;
    View yourFragmentUI;

    RecyclerView recyclerViewQualityParameter;
    QualityParameterAdaptor qualityParameterAdaptor;
    private List<QualityParameter> qualityParameterList;
    int _qualityParameterAttachmentPos = -1;
    String TAG = "QualityParameterFragment";

    //Overriden method onCreateView
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {

        yourFragmentUI = inflater.inflate(R.layout.quality_parameter_fragment, container, false);
//        yourFragmentUI.findViewById(R.id.inputLayout).setVisibility(View.GONE);


        qualityParameterFragment = this;
        qualityParameterFragment.recyclerViewQualityParameter = (RecyclerView) qualityParameterFragment.yourFragmentUI.findViewById(R.id.recyclerView);
        recyclerViewQualityParameter.setHasFixedSize(true);
        recyclerViewQualityParameter.addItemDecoration(new DividerItemDecoration(qualityParameterFragment.getActivity(),
                DividerItemDecoration.VERTICAL));
        LinearLayoutManager mLayoutManager = new LinearLayoutManager(qualityParameterFragment.getActivity());
        recyclerViewQualityParameter.setLayoutManager(mLayoutManager);

        setHasOptionsMenu(true);
        handleToQualityParameter();
        return yourFragmentUI;


    }

    private void handleToQualityParameter() {
        updateQualityParameterUi();
    }

    private void updateQualityParameterUi() {
        List<QualityParameter> list = ItemInspectionDetailHandler.getListQualityParameterForItemLevel(qualityParameterFragment.getActivity(), POItemTabActivity.poItemTabActivity.inspectionModal);
        if (qualityParameterList != null) {
            qualityParameterList.clear();
        } else {
            qualityParameterList = new ArrayList<>();
        }
        qualityParameterList.addAll(list);


       /* recyclerViewQualityParameter.addOnItemTouchListener(new QualityParameterAdaptor.RecyclerTouchListener(qualityParameterFragment.getActivity()
                , recyclerViewQualityParameter, new QualityParameterAdaptor.ClickListener() {
            @Override
            public void onClick(View view, int position) {

            }

            @Override
            public void onLongClick(View view, int position) {

            }
        }));*/
        setQualityParameterAdaptor();
    }

    private void setQualityParameterAdaptor() {

        if (qualityParameterAdaptor == null) {
            qualityParameterAdaptor = new QualityParameterAdaptor(qualityParameterFragment.getActivity(), recyclerViewQualityParameter
                    , qualityParameterList
                    , FEnumerations.E_ADAPTOR_VIEW_TYPE_QUALITY_PARAMETER, this);
            recyclerViewQualityParameter.setAdapter(qualityParameterAdaptor);
        } else {
            qualityParameterAdaptor.notifyDataSetChanged();
        }
    }

    /*@Override
    public void onQualityClick(View view, int position) {
        _qualityParameterAttachmentPos = position;
        handlePickUpQuality();
    }*/

    @Override
    public void onQualityClick(QualityParameter qualityParameter) {
        Intent intent = new Intent(qualityParameterFragment.getActivity(), AddQualityParameter.class);
        intent.putExtra("detail", GenUtils.serializeInspectionModal(POItemTabActivity.poItemTabActivity.inspectionModal));
        intent.putExtra("qualityParameter", GenUtils.serializeQualityParameter(qualityParameter));
        intent.putExtra("viewType", FEnumerations.VIEW_TYPE_QUALITY_PARAMETER_INSPECTION_LEVEL);
        startActivityForResult(intent, FEnumerations.VIEW_TYPE_QUALITY_PARAMETER_INSPECTION_LEVEL);
    }


    @Override
    public void onClickImageTestReport(QualityParameter qualityParameter) {

    }

    /*private void handlePickUpQuality() {
        POItemTabActivity.poItemTabActivity.PICKED_UP = FEnumerations.REQUEST_FOR_QUALITY_PARAMETER_ATTACHMENT;
        String valueToReturn = FEnumerations.REQUEST_FOR_QUALITY_PARAMETER_ATTACHMENT + "";
        MultipleImageHandler.showDialog(qualityParameterFragment.getActivity(), MultipleImageHandler.DOCUMENT, valueToReturn);


    }*/

    @Override
    public void onNoApplicableClickOnQuality(QualityParameter mQualityParameter) {
        FslLog.d(TAG, " updating on No Applicable  QualityParameter inspection level");
        ItemInspectionDetailHandler.updateQualityParameterForItemLevel(qualityParameterFragment.getActivity(), mQualityParameter, POItemTabActivity.poItemTabActivity.inspectionModal);

    }

    @Override
    public void onNoApplicableClickOnInternalTest(QualityParameter mQualityParameter) {

    }

  /*  public void handleQualityParameter() {

        if (qualityParameterList != null) {
            List<QualityParameter> list = ItemInspectionDetailHandler.getSavedQualityParameter(qualityParameterFragment.getActivity(), POItemTabActivity.poItemTabActivity.inspectionModal.pRowID, null);
            for (int i = 0; i < qualityParameterList.size(); i++) {
                if ( qualityParameterList.get(i).IsApplicable != 0) {
                    String returnPRowID = ItemInspectionDetailHandler.updateQualityParameterForItemLevel(qualityParameterFragment.getActivity(), qualityParameterList.get(i), POItemTabActivity.poItemTabActivity.inspectionModal);
                    if (!TextUtils.isEmpty(returnPRowID)) {
                        addASDigitalQualityParameter(qualityParameterList.get(i), returnPRowID);
                    }
                } else {
                    for (int j = 0; j < list.size(); j++) {
                        if (list.get(j).QualityParameterID.equals(qualityParameterList.get(i).QualityParameterID)) {
                            String returnPRowID = ItemInspectionDetailHandler.updateQualityParameterForItemLevel(qualityParameterFragment.getActivity(), qualityParameterList.get(i), POItemTabActivity.poItemTabActivity.inspectionModal);
                            if (!TextUtils.isEmpty(returnPRowID)) {
                                addASDigitalQualityParameter(qualityParameterList.get(i), returnPRowID);
                            }
                        }
                    }
                }

            }
        }

    }
*/
   /* private void addASDigitalQualityParameter(QualityParameter qualityParameter, String returnPRowID) {
        if (qualityParameter != null
                && qualityParameter.imageAttachmentList != null
                && qualityParameter.imageAttachmentList.size() > 0) {
            for (int i = 0; i < qualityParameter.imageAttachmentList.size(); i++) {
                if (TextUtils.isEmpty(qualityParameter.imageAttachmentList.get(i).pRowID)
                        || qualityParameter.imageAttachmentList.get(i).pRowID.equals("null")) {
                    qualityParameter.imageAttachmentList.get(i).pRowID = ItemInspectionDetailHandler.GeneratePK(qualityParameterFragment.getActivity(), FEnumerations.E_TABLE_NAME_QRPOItemDtl_Image);
                }
                String imgPRowID = updateDBWithImage(qualityParameter.imageAttachmentList.get(i));
                if (!TextUtils.isEmpty(imgPRowID)) {
                    List<QualityParameter> dtlList = ItemInspectionDetailHandler.getListQualityParameterAccordingRowId(qualityParameterFragment.getActivity(), returnPRowID);

                    if (dtlList != null && dtlList.size() > 0) {
                        QualityParameter mPo = dtlList.get(0);

                        if (!TextUtils.isEmpty(mPo.Digitals) && !mPo.Digitals.equals("null")) {
                            mPo.Digitals = mPo.Digitals + ", " + imgPRowID;
                        } else {
                            mPo.Digitals = imgPRowID;
                        }
                        ItemInspectionDetailHandler.updateDigitalsQualityMeasurement(qualityParameterFragment.getActivity(), mPo);
                    }

                }
            }
        }
    }*/

  /*  private String updateDBWithImage(DigitalsUploadModal digitalsUploadModal) {
        return ItemInspectionDetailHandler.updateImage(qualityParameterFragment.getActivity(), POItemTabActivity.poItemTabActivity.inspectionModal.pRowID, null, digitalsUploadModal);

    }*/

    @Override
    public void onActivityResult(int requestCode, int resultCode, Intent data) {
//        if (requestCode == FEnumerations.VIEW_TYPE_QUALITY_PARAMETER_INSPECTION_LEVEL) {
        updateQualityParameterUi();
//        }

      /*  if (POItemTabActivity.poItemTabActivity.PICKED_UP == FEnumerations.REQUEST_FOR_QUALITY_PARAMETER_ATTACHMENT) {

            MultipleImageHandler.onActivityResult(qualityParameterFragment.getActivity(),
                    requestCode, resultCode, data, new MultipleImageHandler.GetBitmap() {
                        @Override
                        public void onGetBitamp(Bitmap serverBitmap,
                                                ArrayList<String> imagePathArrayList,
                                                String valueReturned) {

                            if (qualityParameterList != null && qualityParameterList.size() > 0 && _qualityParameterAttachmentPos != -1) {
                                if (imagePathArrayList != null && imagePathArrayList.size() > 0) {
                                    for (int i = 0; i < imagePathArrayList.size(); i++) {
                                        DigitalsUploadModal asDigitalsUploadModal = new DigitalsUploadModal();
                                        asDigitalsUploadModal.title = qualityParameterList.get(_qualityParameterAttachmentPos).MainDescr;
                                        asDigitalsUploadModal.ImageExtn = FEnumerations.E_IMAGE_EXTN;
                                        asDigitalsUploadModal.selectedPicPath = imagePathArrayList.get(i);
//                                                    asDigitalsUploadModal.pRowID = ItemInspectionDetailHandler.GeneratePK(ItemInspectionDetail.this, FEnumerations.E_TABLE_NAME_QRPOItemDtl_Image);
                                        qualityParameterList.get(_qualityParameterAttachmentPos).imageAttachmentList.add(asDigitalsUploadModal);
                                    }
                                }
                            }
                            if (qualityParameterAdaptor != null) {
                                qualityParameterAdaptor.notifyDataSetChanged();
                            }
                            onQualityParameterSelectImage();
//                                _qualityParameterAttachmentPos = -1;


                        }

                    });
        }*/
    }

    /*private void onQualityParameterSelectImage() {
        String valueToReturn = FEnumerations.REQUEST_FOR_QUALITY_PARAMETER_ATTACHMENT + "";
        MultipleImageHandler.showDialog(qualityParameterFragment.getActivity(), MultipleImageHandler.DOCUMENT, valueToReturn, MultipleImageHandler.pickerViewer);

    }*/
}
