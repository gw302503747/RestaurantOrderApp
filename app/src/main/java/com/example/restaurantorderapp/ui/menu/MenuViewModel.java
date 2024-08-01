package com.example.restaurantorderapp.ui.menu;

import android.content.Context;
import android.content.SharedPreferences;

import androidx.lifecycle.LiveData;
import androidx.lifecycle.MutableLiveData;
import androidx.lifecycle.ViewModel;

import com.example.restaurantorderapp.model.MenuItem;
import com.google.gson.Gson;
import com.google.gson.reflect.TypeToken;

import java.lang.reflect.Type;
import java.util.List;
import java.util.Map;

public class MenuViewModel extends ViewModel {
    private MutableLiveData<List<MenuItem>> menuItems;

    public LiveData<List<MenuItem>> getMenuItems(Context context, String category) {
        if (menuItems == null) {
            menuItems = new MutableLiveData<>();
            loadMenuItems(context, category);
        }
        return menuItems;
    }

    private void loadMenuItems(Context context, String category) {
        SharedPreferences sharedPreferences = context.getSharedPreferences("MenuData", Context.MODE_PRIVATE);
        String menusJson = sharedPreferences.getString("menus", null);

        if (menusJson != null) {
            Gson gson = new Gson();
            Type menuType = new TypeToken<Map<String, List<MenuItem>>>() {}.getType();
            Map<String, List<MenuItem>> menus = gson.fromJson(menusJson, menuType);
            List<MenuItem> items = menus.get(category);
            menuItems.setValue(items);
        }
    }
}