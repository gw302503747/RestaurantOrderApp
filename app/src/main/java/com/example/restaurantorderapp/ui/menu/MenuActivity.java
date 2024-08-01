package com.example.restaurantorderapp.ui.menu;

import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.ImageButton;
import android.widget.LinearLayout;
import android.widget.TextView;

import androidx.activity.EdgeToEdge;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.content.ContextCompat;
import androidx.viewpager2.widget.ViewPager2;
import androidx.work.OneTimeWorkRequest;
import androidx.work.WorkManager;
import androidx.work.WorkRequest;

import com.example.restaurantorderapp.R;
import com.example.restaurantorderapp.WelcomeActivity;
import com.example.restaurantorderapp.model.MenuItem;
import com.example.restaurantorderapp.service.MenuWorker;
import com.google.android.material.tabs.TabLayout;
import com.google.android.material.tabs.TabLayoutMediator;
import com.google.gson.Gson;
import com.google.gson.reflect.TypeToken;

import java.lang.reflect.Type;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;

public class MenuActivity extends AppCompatActivity {

    private TabLayout tabLayout;
    private ViewPager2 viewPager;
    private View viewFirstPage;
    private TextView orderTypeTextView;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        EdgeToEdge.enable(this);
        setContentView(R.layout.activity_menu);

        //隐藏顶部条
        if (getSupportActionBar() != null) {
            getSupportActionBar().hide();
        }

        tabLayout = findViewById(R.id.tab_layout);
        viewPager = findViewById(R.id.view_pager);
        viewFirstPage = findViewById(R.id.view_first_page);
        orderTypeTextView = findViewById(R.id.order_type);

        // 删除指示器
        tabLayout.setSelectedTabIndicator(null);

        //获取传参
        Intent intent = getIntent();
        int orderType = intent.getIntExtra("order_type", 0);

        // 根据传参设置文本
        switch (orderType) {
            case 0:
                orderTypeTextView.setText(R.string.order_type_0);
                break;
            case 1:
                orderTypeTextView.setText(R.string.order_type_1);
                break;
            // 添加更多 case 以处理不同的 orderType 值
            default:
                orderTypeTextView.setText(R.string.order_type_0); // 默认值
                break;
        }

        // 读取SharedPreferences中的菜单数据
        String menusJson = getMenuData("menus");
        if (menusJson != null) {
            Gson gson = new Gson();
            Type menuType = new TypeToken<Map<String, List<MenuItem>>>() {
            }.getType();
            Map<String, List<MenuItem>> menus = gson.fromJson(menusJson, menuType);

            // 获取某一类菜单的分类列表
            List<String> categories = new ArrayList<>(menus.keySet());
            setupViewPager(categories);
        } else {
            // 处理没有菜单数据的情况，例如显示错误消息或重新加载数据
            // 可以显示一个错误提示或者重新加载数据
            // Toast.makeText(this, "No menu data available", Toast.LENGTH_SHORT).show();
            // 或者重新获取数据
            startMenuWorker();
        }

        View.OnClickListener firstPageClickListener = v -> {
          Intent vintent = new Intent(this, WelcomeActivity.class);
          startActivity(vintent);
        };

        viewFirstPage.setOnClickListener(firstPageClickListener);
    }

    private void setupViewPager(List<String> categories) {
        MenuPagerAdapter adapter = new MenuPagerAdapter(this, categories);
        viewPager.setAdapter(adapter);
        new TabLayoutMediator(tabLayout, viewPager,
                (tab, position) -> {
                    // 使用自定义布局
                    View customView = LayoutInflater.from(this).inflate(R.layout.tab_custom, null);
                    TextView tabTextView = customView.findViewById(R.id.tab_text);
                    tabTextView.setText(categories.get(position));

                    // 设置默认颜色
                    tabTextView.setTextColor(ContextCompat.getColor(this, R.color.tab_text_unselected));

                    // 获取 Tab 的根布局
                    LinearLayout tabLayout = (LinearLayout) customView;

                    // 设置默认 padding 和 margin
                    LinearLayout.LayoutParams params = new LinearLayout.LayoutParams(
                            400,
                            LinearLayout.LayoutParams.MATCH_PARENT
                    );
                    params.setMargins(0, 20, 10, 20); // 示例边距
                    tabLayout.setLayoutParams(params);

                    // 设置 tab 的自定义视图
                    tab.setCustomView(customView);
                }
        ).attach();


        // 监听选中的 tab 变化
        tabLayout.addOnTabSelectedListener(new TabLayout.OnTabSelectedListener() {
            @Override
            public void onTabSelected(TabLayout.Tab tab) {
                // 更新选中 tab 的样式
                updateTabStyles(tab, true);
                updateTabTextColor(tab, R.color.tab_text_selected);
                viewPager.setCurrentItem(tab.getPosition());
            }

            @Override
            public void onTabUnselected(TabLayout.Tab tab) {
                // 恢复未选中 tab 的样式
                updateTabStyles(tab, false);
                updateTabTextColor(tab, R.color.tab_text_unselected);
            }

            @Override
            public void onTabReselected(TabLayout.Tab tab) {
                // 处理重新选择 Tab 的逻辑
                Log.d("TabLayout", "Tab reselected: " + tab.getText());
            }
        });

        // 手动选中第一个标签
        if (tabLayout.getTabCount() > 0) {
            TabLayout.Tab firstTab = tabLayout.getTabAt(0);
            if (firstTab != null) {
                firstTab.select();
                updateTabTextColor(firstTab, R.color.tab_text_selected);
                updateTabStyles(firstTab, true);
            }
        }
    }

    private String getMenuData(String key) {
        SharedPreferences sharedPreferences = getSharedPreferences("MenuData", Context.MODE_PRIVATE);
        return sharedPreferences.getString(key, null);
    }

    private void startMenuWorker() {
        WorkRequest menuWorkRequest = new OneTimeWorkRequest.Builder(MenuWorker.class).build();
        WorkManager.getInstance(this).enqueue(menuWorkRequest);
    }

    private void updateTabStyles(TabLayout.Tab tab, boolean isSelected) {
        if (tab != null && tab.getCustomView() != null) {
            LinearLayout tabLayout = (LinearLayout) tab.getCustomView();
            if (isSelected) {
                // 设置选中 tab 的特殊 padding 和 margin
                LinearLayout.LayoutParams params = (LinearLayout.LayoutParams) tabLayout.getLayoutParams();
                params.setMargins(0, 20, 0, 0); // 示例边距
                tabLayout.setLayoutParams(params);
            } else {
                // 恢复未选中 tab 的默认 padding 和 margin
                LinearLayout.LayoutParams params = (LinearLayout.LayoutParams) tabLayout.getLayoutParams();
                params.setMargins(0, 20, 10, 20); // 示例边距
                tabLayout.setLayoutParams(params);
            }
        }
    }

    private void updateTabTextColor(TabLayout.Tab tab, int colorResId) {
        if (tab != null && tab.getCustomView() != null) {
            TextView tabTextView = tab.getCustomView().findViewById(R.id.tab_text);
            tabTextView.setTextColor(ContextCompat.getColor(this, colorResId));
        }
    }
}