package com.example.restaurantorderapp.service;

import android.content.Context;
import android.content.SharedPreferences;
import android.util.Log;
import androidx.annotation.NonNull;
import androidx.work.Worker;
import androidx.work.WorkerParameters;

import com.example.restaurantorderapp.model.MenuItem;
import com.google.gson.Gson;
import java.util.List;
import java.util.Map;


public class MenuWorker extends Worker {

    public MenuWorker(@NonNull Context context, @NonNull WorkerParameters params) {
        super(context, params);
    }

    @NonNull
    @Override
    public Result doWork() {
        try {
            // 获取 MenuRepository 实例并进行数据获取
            MenuRepository menuRepository = new MenuRepository();
            menuRepository.fetchMenu(new MenuRepository.MenuCallback() {

                @Override
                public void onSuccess(Map<String, List<MenuItem>> menus, Map<String, List<MenuItem>> setMenus) {
                    Gson gson = new Gson();
                    String jsonMenus = gson.toJson(menus);
                    String jsonSetMenus = gson.toJson(setMenus);
                    saveMenuData(jsonMenus, jsonSetMenus);
                }

                @Override
                public void onFailure(String errorMessage) {
                    Log.e("Network error:", errorMessage);
                }
            });

            return Result.success();
        } catch (Exception e) {
            Log.e("MenuWorker", "Error fetching menu data", e);
            return Result.failure();
        }
    }

    private void saveMenuData(String menusJson, String setMenusJson) {
        SharedPreferences sharedPreferences = getApplicationContext().getSharedPreferences("MenuData", Context.MODE_PRIVATE);
        SharedPreferences.Editor editor = sharedPreferences.edit();

        editor.putString("menus", menusJson);
        editor.putString("setMenus", setMenusJson);
        editor.apply();
    }
}
