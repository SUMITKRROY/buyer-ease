package com.util;



import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;
import com.buyereasefsl.R;
import androidx.recyclerview.widget.RecyclerView;

import java.util.List;

public class ItemDataAdapter extends RecyclerView.Adapter<ItemDataAdapter.FruitViewHolder>  {
    private List<String> mDataset;
    RecyclerViewItemClickListener recyclerViewItemClickListener;

    public ItemDataAdapter(List<String> myDataset, RecyclerViewItemClickListener listener) {
        mDataset = myDataset;
        this.recyclerViewItemClickListener = listener;
    }

    @Override
    public FruitViewHolder onCreateViewHolder(ViewGroup parent, int i) {

        View v = LayoutInflater.from(parent.getContext()).inflate(R.layout.dialog_list_item, parent, false);

        FruitViewHolder vh = new FruitViewHolder(v);
        return vh;

    }

    @Override
    public void onBindViewHolder(FruitViewHolder fruitViewHolder, int i) {
        fruitViewHolder.mTextView.setText(mDataset.get(i));
    }

    @Override
    public int getItemCount() {
        return mDataset.size();
    }



    public  class FruitViewHolder extends RecyclerView.ViewHolder implements View.OnClickListener {

        public TextView mTextView;

        public FruitViewHolder(View v) {
            super(v);
            mTextView = (TextView) v.findViewById(R.id.menuName);
            v.setOnClickListener(this);
        }

        @Override
        public void onClick(View v) {
//            recyclerViewItemClickListener.clickOnItem(mDataset[this.getAdapterPosition()]);
            recyclerViewItemClickListener.clickOnItem(mDataset.get(this.getItemViewType()));

        }
    }

    public interface RecyclerViewItemClickListener {
        void clickOnItem(String data);
    }
}
