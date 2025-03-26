package com.buyereasefsl;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by ADMIN on 12/29/2017.
 */

public class QualityParameter {

    public String pRowID;
    public String QualityParameterID, MainDescr, Abbrv, OptionValue;
    public int PromptType; //numVal1
    public int Position;//gm.numVal2
    public int IsApplicable, OptionSelected, recDirty;
    public String Remarks, Digitals;
    public int ImageRequired = 0;//Cast(gm.chrVal3 As Integer),0) As ImageRequired
//    public int IsSelected = 0;
    List<ApplicableList> applicableLists;
    public ArrayList<DigitalsUploadModal> imageAttachmentList = new ArrayList<>();

}

class ApplicableList {

    public String title;
    public int value;


}