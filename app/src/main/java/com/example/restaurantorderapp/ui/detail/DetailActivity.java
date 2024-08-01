package com.example.restaurantorderapp.ui.detail;

import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.bumptech.glide.Glide;
import com.example.restaurantorderapp.R;
import com.example.restaurantorderapp.model.GoodsItem;
import com.example.restaurantorderapp.service.GoodsRepository;

import java.util.List;
import java.util.Map;

public class DetailActivity extends AppCompatActivity {

    private Context context;
    private TextView goodsNameTextView;
    private TextView goodsPriceTextView;
    private ImageView goodsImageView;
    private int quantity = 1;
    private TextView countTextView;
    private View increaseButton;
    private View decreaseButton;
    private RecyclerView goodsRecyclerView;
    private QuantityAdapter quantityAdapter;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_detail);

        context = this;

        if (getSupportActionBar() != null) {
            getSupportActionBar().hide();
        }

        Intent intent = getIntent();
        String goodsNo = intent.getStringExtra("goodsNo");
        Log.d("DetailActivity", "Received Goods No: " + goodsNo);

        goodsNameTextView = findViewById(R.id.detail_goods_name);
        goodsPriceTextView = findViewById(R.id.detail_goods_price);
        goodsImageView = findViewById(R.id.detail_goods_image);
        countTextView = findViewById(R.id.detail_count_text);
        increaseButton = findViewById(R.id.detail_button_increase_view);
        decreaseButton = findViewById(R.id.detail_button_decrease_view);
        goodsRecyclerView = findViewById(R.id.detail_goods_recycler);

        GoodsRepository goodsRepository = new GoodsRepository();
        goodsRepository.fetchGoods(goodsNo, new GoodsRepository.GoodsCallback() {
            @Override
            public void onSuccess(List<GoodsItem> goodsItemList) {
                if (goodsItemList != null && !goodsItemList.isEmpty()) {
                    quantityAdapter = new QuantityAdapter(goodsItemList);
                    LinearLayoutManager layoutManager = new LinearLayoutManager(context, LinearLayoutManager.HORIZONTAL, false);
                    goodsRecyclerView.setLayoutManager(layoutManager);
                    goodsRecyclerView.setAdapter(quantityAdapter);

                    GoodsItem firstGoodsItem = goodsItemList.get(0); // 获取第一个商品
                    displayGoodsDetails(firstGoodsItem); // 显示商品详细信息
                } else {
                    Log.e("DetailActivity", "GoodsItem list is empty for goodsNo: " + goodsNo);
                }
            }

            @Override
            public void onFailure(String errorMessage) {
                Log.e("DetailActivity", "Error fetching goods details: " + errorMessage);
            }
        });

        increaseButton.setOnClickListener(v -> {
            quantity++;
            updateQuantityText();
        });

        decreaseButton.setOnClickListener(v -> {
            if (quantity > 1) {
                quantity--;
                updateQuantityText();
            }
        });

        // Initial quantity update
        updateQuantityText();

    }

    private void updateQuantityText() {
        countTextView.setText(String.valueOf(quantity));
    }

    private void displayGoodsDetails(GoodsItem goodsItem) {
        goodsNameTextView.setText(goodsItem.getGoodsName());
        goodsPriceTextView.setText(getString(R.string.price_format, goodsItem.getGoodsPrice()));

        Glide.with(context)
                .load(goodsItem.getGoodsImage())
                .placeholder(R.drawable.placeholder_image)
                .into(goodsImageView);
    }
}