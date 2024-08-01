package com.example.restaurantorderapp.service;

import com.example.restaurantorderapp.model.GoodsItem;
import com.example.restaurantorderapp.model.ResponseModel;
import java.util.List;
import retrofit2.Call;
import retrofit2.http.GET;
import retrofit2.http.Path;

public interface ApiService {
    @GET("menu")
    Call<ResponseModel> getData();

    @GET("good/{goodsNo}")
    Call<List<GoodsItem>> getDetail(@Path("goodsNo") String goodsNo);
}
