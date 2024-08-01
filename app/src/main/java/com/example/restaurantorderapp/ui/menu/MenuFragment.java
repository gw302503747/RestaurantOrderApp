package com.example.restaurantorderapp.ui.menu;

import androidx.lifecycle.ViewModelProvider;

import android.os.Bundle;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.GridLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.example.restaurantorderapp.R;

import java.util.ArrayList;

public class MenuFragment extends Fragment {

    private MenuViewModel viewModel;
    private MenuCardAdapter adapter;

    private static final String ARG_CATEGORY = "category";

    public static MenuFragment newInstance(String category) {
        MenuFragment fragment = new MenuFragment();
        Bundle args = new Bundle();
        args.putString(ARG_CATEGORY, category);
        fragment.setArguments(args);
        return fragment;
    }

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container,
                             @Nullable Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.menu_fragment, container, false);
        RecyclerView recyclerView = view.findViewById(R.id.recycler_view);
        recyclerView.setLayoutManager(new GridLayoutManager(getContext(), 4));
        adapter = new MenuCardAdapter(getContext(), new ArrayList<>());
        recyclerView.setAdapter(adapter);

        if (getArguments() != null) {
            String category = getArguments().getString(ARG_CATEGORY);
            viewModel = new ViewModelProvider(this).get(MenuViewModel.class);
            viewModel.getMenuItems(getContext(), category).observe(getViewLifecycleOwner(), menuItems -> {
                adapter.updateMenuItems(menuItems);
            });
        }
        return view;
    }
}