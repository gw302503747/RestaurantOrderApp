package com.example.restaurantorderapp.ui.detail;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;
import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.bumptech.glide.Glide;
import com.example.restaurantorderapp.R;
import com.example.restaurantorderapp.model.GoodsItem;
import java.util.List;
import java.util.Locale;

public class QuantityAdapter extends RecyclerView.Adapter<QuantityAdapter.QuantityViewHolder> {

    private final List<GoodsItem> goodsItemList;

    public QuantityAdapter(List<GoodsItem> goodsItemList) {
        this.goodsItemList = goodsItemList;
    }

    @NonNull
    @Override
    public QuantityViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.detail_quantity_item, parent, false);
        return new QuantityViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull QuantityViewHolder holder, int position) {
        GoodsItem goodsItem = goodsItemList.get(position);
        holder.goodsQuantityTextView.setText(String.valueOf(goodsItem.getGoodsQuantity()));
        holder.goodsPriceTextView.setText(String.format(Locale.getDefault(), "￥%d", goodsItem.getGoodsPrice()));

        Glide.with(holder.itemView.getContext())
                .load(goodsItem.getQuantityImage())
                .placeholder(R.drawable.placeholder_image)
                .into(holder.goodsImageView);
    }

    @Override
    public int getItemCount() {
        return goodsItemList.size();
    }

    public static class QuantityViewHolder extends RecyclerView.ViewHolder {

        ImageView goodsImageView;
        TextView goodsQuantityTextView;
        TextView goodsPriceTextView;

        public QuantityViewHolder(@NonNull View itemView) {
            super(itemView);
            goodsImageView = itemView.findViewById(R.id.detail_goods_quantity_image);
            goodsQuantityTextView = itemView.findViewById(R.id.detail_goods_quantity);
            goodsPriceTextView = itemView.findViewById(R.id.detail_goods_quantity_price);
        }
    }
}