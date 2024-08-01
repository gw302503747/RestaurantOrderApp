package com.example.restaurantorderapp.service;

import android.util.Log;

import androidx.annotation.NonNull;

import com.example.restaurantorderapp.model.MenuItem;
import com.example.restaurantorderapp.model.ResponseModel;

import java.util.List;
import java.util.Map;
import java.util.Objects;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class MenuRepository {
    private final ApiService apiService;

    public MenuRepository() {
        apiService = RetrofitClient.getClient().create(ApiService.class);
    }

    public void fetchMenu(MenuCallback menuCallback) {
        Call<ResponseModel> call = apiService.getData();
        call.enqueue(new Callback<ResponseModel>() {
            @Override
            public void onResponse(@NonNull Call<ResponseModel> call, @NonNull Response<ResponseModel> response) {
                if (response.isSuccessful() && response.body() != null) {
                    ResponseModel responseModel = response.body();

                    // 处理响应数据
                    Map<String, List<MenuItem>> menus = responseModel.getMenus();
                    Map<String, List<MenuItem>> setMenus = responseModel.getSetMenus();
                    if (menus != null && setMenus != null) {
                        menuCallback.onSuccess(menus, setMenus);
                    } else {
                        menuCallback.onFailure("Menus or SetMenus are null");
                    }
                } else {
                    //处理响应错误
                    menuCallback.onFailure(response.message());
                }
            }

            @Override
            public void onFailure(@NonNull Call<ResponseModel> call, Throwable t) {
                // 处理网络错误
                menuCallback.onFailure(Objects.requireNonNull(t.getMessage()));
            }
        });
    }
    public interface MenuCallback {
        void onSuccess(Map<String, List<MenuItem>> menus, Map<String, List<MenuItem>> setMenus);
        void onFailure(String errorMessage);
    }
}