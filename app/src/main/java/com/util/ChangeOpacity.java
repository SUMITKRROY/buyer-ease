package com.util;

import android.content.Context;
import android.graphics.drawable.Drawable;
import android.support.v4.content.ContextCompat;

/**
 * Created by 14 on 09-08-2016.
 */
public class ChangeOpacity {


    public static Drawable getFadedImage(Context context,int iconId) {
        Drawable dfMap = ContextCompat.getDrawable(context, iconId);
        // setting the opacity (alpha)
        dfMap.setAlpha(18);
        return dfMap;
    }

}
