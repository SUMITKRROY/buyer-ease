package com.sizeQty;

import static androidx.core.content.ContextCompat.getSystemService;

import android.annotation.SuppressLint;
import android.content.Context;
import android.text.Editable;
import android.text.TextUtils;
import android.text.TextWatcher;
import android.util.Log;
import android.view.KeyEvent;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.inputmethod.EditorInfo;
import android.view.inputmethod.InputMethodManager;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;
import com.buyereasefsl.R;
import com.util.GenUtils;

import java.util.List;

import me.drakeet.support.toast.ToastCompat;

public class SizeQtyAdapter extends RecyclerView.Adapter<SizeQtyAdapter.ItemViewHolder> {
    private List<SizeQtyModel> itemList;
    private OnItemClickListenerSize listener;
    private Context mContext;

    public interface OnItemClickListenerSize {
        void onItemClick(int position);
        void onItemAcceptedClick(int position);
    }

    public SizeQtyAdapter(List<SizeQtyModel> itemList, OnItemClickListenerSize listener,Context mContext) {
        this.itemList = itemList;
        this.listener = listener;
        this.mContext = mContext;

    }

    @NonNull
    @Override
    public ItemViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        return null;
    }

    @NonNull


    @Override
    public void onBindViewHolder(@NonNull ItemViewHolder holder, @SuppressLint("RecyclerView") int position) {
        SizeQtyModel item = itemList.get(position);
        holder.textSize.setText(item.SizeGroupDescr);
        holder.textOrderQty.setText(String.valueOf(item.OrderQty));
//        int availableQty = item.OrderQty - item.EarlierInspected;
//        item.AvailableQty=availableQty;
        int shortQty = item.OrderQty - (item.EarlierInspected+item.AcceptedQty);
        if(shortQty < 0){
            shortQty = 0;
        }
        holder.textAvailable.setText(String.valueOf(item.AvailableQty));
        holder.textAccepted.setText(String.valueOf(item.AcceptedQty));
        holder.textInspected.setText(String.valueOf(item.EarlierInspected));
        holder.textShortStockQty.setText(String.valueOf(shortQty));
        // Remove previous TextWatcher if any
        if (holder.textAvailable.getTag() instanceof TextWatcher) {
            holder.textAvailable.removeTextChangedListener((TextWatcher) holder.textAvailable.getTag());
        }

        // Add TextWatcher to update model as user types
        TextWatcher textWatcher = new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence s, int start, int count, int after) {}

            @Override
            public void onTextChanged(CharSequence s, int start, int before, int count) {
                Log.e("textAvailable","onTextChanged textAvailable"+s.toString());
                if(!s.toString().isEmpty()) {
                    item.AvailableQty = Integer.parseInt(s.toString());
                    item.AcceptedQty = item.AvailableQty;
                }
            }

            @Override
            public void afterTextChanged(Editable s) {
                Log.e("textAvailable","afterTextChanged textAvailable"+s.toString());
            }
        };

        holder.textAvailable.addTextChangedListener(textWatcher);
        holder.textAvailable.setTag(textWatcher);


        // Remove previous TextWatcher if any
        if (holder.textAccepted.getTag() instanceof TextWatcher) {
            holder.textAccepted.removeTextChangedListener((TextWatcher) holder.textAccepted.getTag());
        }

        // Add TextWatcher to update model as user types
        TextWatcher textWatcher1 = new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence s, int start, int count, int after) {}

            @Override
            public void onTextChanged(CharSequence s, int start, int before, int count) {
                if(!s.toString().isEmpty()) {
                    if (Integer.parseInt(s.toString()) <= item.AvailableQty) {
                        item.AcceptedQty = Integer.parseInt(s.toString());
                    } else {
                        Toast toast = ToastCompat.makeText(mContext, "You cannot enter a value greater than the available quantity", Toast.LENGTH_SHORT);
                        GenUtils.safeToastShow("SizeQtyAdapter", mContext, toast);
                    }
                }
            }

            @Override
            public void afterTextChanged(Editable s) {}
        };

        holder.textAccepted.addTextChangedListener(textWatcher1);
        holder.textAccepted.setTag(textWatcher1);

        /*holder.textAvailable.setOnEditorActionListener(new TextView.OnEditorActionListener() {
            @Override
            public boolean onEditorAction(TextView v, int actionId, KeyEvent event) {
                if (actionId == EditorInfo.IME_ACTION_DONE) {
                    // Perform the action
                    String inputText = holder.textAvailable.getText().toString();
//                    if(Integer.parseInt(inputText) < item.OrderQty){
                        item.AvailableQty=Integer.parseInt(inputText);
                        item.AcceptedQty=item.AvailableQty;
                        holder.textAccepted.setText(inputText);
//                        Toast.makeText(mContext, "You entered: " + inputText, Toast.LENGTH_SHORT).show();
                        if (listener != null) {
                            listener.onItemClick(position);
                        }
                   *//* }else {
                        Toast.makeText(mContext, "You Can not exceed Quantity greater than available quantity." + inputText, Toast.LENGTH_SHORT).show();
                    }*//*
                    InputMethodManager imm = (InputMethodManager) mContext.getSystemService(Context.INPUT_METHOD_SERVICE);
                    imm.hideSoftInputFromWindow(holder.textAvailable.getWindowToken(), 0);
                    // Return true to indicate the action has been handled
                    return true;
                }
                return false;
            }
        });

        holder.textAccepted.setOnEditorActionListener(new TextView.OnEditorActionListener() {
            @Override
            public boolean onEditorAction(TextView v, int actionId, KeyEvent event) {
                if (actionId == EditorInfo.IME_ACTION_DONE) {
                    // Perform the action
                    String inputText = holder.textAccepted.getText().toString();
//                    Toast.makeText(mContext, "You entered: " + inputText, Toast.LENGTH_SHORT).show();
                    if(Integer.parseInt(inputText) <= item.AvailableQty){
                        item.AcceptedQty=Integer.parseInt(inputText);
                        if (listener != null) {
                            listener.onItemAcceptedClick(position);
                        }
                    }else {
                        Toast.makeText(mContext, "You cannot enter a value greater than the available quantity." + inputText, Toast.LENGTH_SHORT).show();
                    }

                    InputMethodManager imm = (InputMethodManager) mContext.getSystemService(Context.INPUT_METHOD_SERVICE);
                    imm.hideSoftInputFromWindow(holder.textAccepted.getWindowToken(), 0);
                    // Return true to indicate the action has been handled
                    return true;
                }
                return false;
            }
        });*/
    }

    @Override
    public int getItemCount() {
        return itemList.size();
    }

    public static class ItemViewHolder extends RecyclerView.ViewHolder {
        TextView textSize,textOrderQty,textInspected,textShortStockQty;
        EditText  textAvailable,textAccepted;
        public ItemViewHolder(@NonNull View itemView, OnItemClickListenerSize listener) {
            super(itemView);
//            textSize = itemView.findViewById(R.id.textSize);
//            textOrderQty = itemView.findViewById(R.id.textOrderQty);
//            textInspected = itemView.findViewById(R.id.textInspected);
//            textAvailable = itemView.findViewById(R.id.textAvailable);
//            textAccepted = itemView.findViewById(R.id.textAccepted);
//            textShortStockQty = itemView.findViewById(R.id.textShortStockQty);

            /*itemView.setOnClickListener(v -> {
                if (listener != null) {
                    int position = getAbsoluteAdapterPosition();
                    if (position != RecyclerView.NO_POSITION) {
                        listener.onItemClick(position);
                    }
                }
            });*/
        }
    }
    // Method to retrieve updated data
    public List<SizeQtyModel> getItemList() {
        return itemList;
    }
    public void setItemList(List<SizeQtyModel> itemList){
        this.itemList = itemList;
        notifyDataSetChanged();
    }
}

