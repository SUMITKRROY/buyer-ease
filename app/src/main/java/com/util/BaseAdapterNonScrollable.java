package com.util;

/**
 * Created by Netxylem on 18-03-2017.
 */

import android.content.Context;
import android.view.View;
import android.view.ViewGroup;

import java.util.List;

public abstract class BaseAdapterNonScrollable<T> {

    public Context context;
    private ViewGroup containerViewGroup;
    private List<T> itemObjectList;

    public BaseAdapterNonScrollable() {
    }

    public void setData(Context context, List<T> items, ViewGroup containerViewGroup) {
        this.context = context;
        this.itemObjectList = items;
        this.containerViewGroup = containerViewGroup;
    }

    public void drawItems() {
        if (containerViewGroup == null || itemObjectList.size() == 0) {
            return;
        }

        if (containerViewGroup.getChildCount() > 0) {
            containerViewGroup.removeAllViews();
        }

        //draw all items
        for (int i = 0; i < itemObjectList.size(); i++) {
            final int position = i;
            final View itemView = getView(position, containerViewGroup, itemObjectList.get(i));
            if (itemView != null) {
                containerViewGroup.addView(itemView);
            }
        }
    }

    public abstract View getView(int position, View container, T itemObject);
}