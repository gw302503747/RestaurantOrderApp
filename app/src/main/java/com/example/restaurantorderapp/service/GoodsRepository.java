package com.example.restaurantorderapp.service;

import android.util.Log;
import androidx.annotation.NonNull;
import com.example.restaurantorderapp.model.GoodsItem;
import java.util.List;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;


public class GoodsRepository {
    private final ApiService apiService;

    public GoodsRepository() {
        apiService = RetrofitClient.getClient().create(ApiService.class);
    }

    public void fetchGoods(String goodsNo, GoodsCallback goodsCallback) {
        Call<List<GoodsItem>> call = apiService.getDetail(goodsNo);
        call.enqueue(new Callback<List<GoodsItem>>() {
            @Override
            public void onResponse(@NonNull Call<List<GoodsItem>> call, @NonNull Response<List<GoodsItem>> response) {
                if (response.isSuccessful() && response.body() != null) {
                    List<GoodsItem> goodsItemList = response.body();
                    if (!goodsItemList.isEmpty()) {
                        goodsCallback.onSuccess(goodsItemList);
                    } else {
                        goodsCallback.onFailure("GoodsItem list is empty or null");
                    }
                } else {
                    goodsCallback.onFailure("Response not successful or body is null");
                }
            }

            @Override
            public void onFailure(@NonNull Call<List<GoodsItem>> call, Throwable t) {
                goodsCallback.onFailure(t.getMessage());
            }
        });
    }
    public interface GoodsCallback {
        void onSuccess(List<GoodsItem> goodsItemList);
        void onFailure(String errorMessage);
    }
}