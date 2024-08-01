package com.example.restaurantorderapp.model;

import com.google.gson.annotations.SerializedName;

import java.util.List;
import java.util.Map;

public class ResponseModel {
    @SerializedName("setMenus")
    private Map<String, List<MenuItem>> setMenus;

    @SerializedName("menus")
    private Map<String, List<MenuItem>> menus;

    // Getters 和 Setters
    public Map<String, List<MenuItem>> getSetMenus() { return setMenus; }
    public void setSetMenus(Map<String, List<MenuItem>> setMenus) { this.setMenus = setMenus; }

    public Map<String, List<MenuItem>> getMenus() { return menus; }
    public void setMenus(Map<String, List<MenuItem>> menus) { this.menus = menus; }
}
