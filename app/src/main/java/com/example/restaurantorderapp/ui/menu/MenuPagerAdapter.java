package com.example.restaurantorderapp.ui.menu;

import androidx.annotation.NonNull;
import androidx.fragment.app.Fragment;
import androidx.viewpager2.adapter.FragmentStateAdapter;

import java.util.List;

public class MenuPagerAdapter extends FragmentStateAdapter {
    private final List<String> categories;

    public MenuPagerAdapter(MenuActivity fragment, List<String> categories) {
        super(fragment);
        this.categories = categories;
    }

    @NonNull
    @Override
    public Fragment createFragment(int position) {
        return MenuFragment.newInstance(categories.get(position));
    }

    @Override
    public int getItemCount() {
        return categories.size();
    }
}
