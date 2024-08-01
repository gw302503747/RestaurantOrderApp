package com.example.restaurantorderapp.ui.menu;

import android.content.Context;
import android.content.Intent;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;
import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;
import com.bumptech.glide.Glide;
import com.example.restaurantorderapp.R;
import com.example.restaurantorderapp.model.MenuItem;
import com.example.restaurantorderapp.ui.detail.DetailActivity;

import java.util.List;

public class MenuCardAdapter extends RecyclerView.Adapter<MenuCardAdapter.MenuCardViewHolder> {

    private final Context context;
    private List<MenuItem> menuItems;

    public MenuCardAdapter(Context context, List<MenuItem> menuItems) {
        this.context = context;
        this.menuItems = menuItems;
    }

    @NonNull
    @Override
    public MenuCardViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(context).inflate(R.layout.item_menu_card, parent, false);
        return new MenuCardViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull MenuCardViewHolder holder, int position) {
        MenuItem menuItem = menuItems.get(position);

        holder.goodsName.setText(menuItem.getGoodsName());
        holder.goodsPrice.setText(context.getString(R.string.price_format, menuItem.getGoodsPrice()));

        Glide.with(context)
                .load(menuItem.getGoodsImage())
                .placeholder(R.drawable.placeholder_image)
                .into(holder.goodsImage);

        holder.itemView.setOnClickListener(v -> {
            Intent intent = new Intent(context, DetailActivity.class);
            String goodsNo = menuItem.getGoodsNo();
            intent.putExtra("goodsNo", goodsNo); // Ensure this is the correct key
            Log.d("MenuCardAdapter", "Passing Goods No: " + goodsNo);
            context.startActivity(intent);
        });
    }

    @Override
    public int getItemCount() {
        return menuItems.size();
    }

    public void updateMenuItems(List<MenuItem> newMenuItems) {
        this.menuItems = newMenuItems;
        notifyDataSetChanged();
    }

    static class MenuCardViewHolder extends RecyclerView.ViewHolder {

        ImageView goodsImage;
        TextView goodsName;
        TextView goodsPrice;

        public MenuCardViewHolder(@NonNull View itemView) {
            super(itemView);
            goodsImage = itemView.findViewById(R.id.goods_image);
            goodsName = itemView.findViewById(R.id.goods_name);
            goodsPrice = itemView.findViewById(R.id.goods_price);
        }
    }
}