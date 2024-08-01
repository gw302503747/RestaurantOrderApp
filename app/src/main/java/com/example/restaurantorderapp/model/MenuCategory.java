package com.example.restaurantorderapp.model;

import java.util.List;

public class MenuCategory {
    private String categoryName;
    private List<MenuItem> menu;

    public String getCategoryName() {
        return categoryName;
    }

    public List<MenuItem> getMenu() {
        return menu;
    }

    public void setCategoryName(String categoryName) {
        this.categoryName = categoryName;
    }

    public void setMenu(List<MenuItem> menu) {
        this.menu = menu;
    }
}
