package com.example.restaurantorderapp;

import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.view.MenuInflater;
import android.view.View;
import android.widget.PopupMenu;
import android.widget.TextView;

import androidx.activity.EdgeToEdge;
import androidx.appcompat.app.AppCompatActivity;
import androidx.work.OneTimeWorkRequest;
import androidx.work.WorkManager;
import androidx.work.WorkRequest;

import com.example.restaurantorderapp.service.MenuWorker;
import com.example.restaurantorderapp.ui.menu.MenuActivity;

public class WelcomeActivity extends AppCompatActivity {

    private Context context;
    private TextView selectedText;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        EdgeToEdge.enable(this);
        setContentView(R.layout.welcome_main);

        startMenuWorker();

        //选择语言按钮
        View welcomeLanguageButton = findViewById(R.id.welcome_language_button);
        selectedText = findViewById(R.id.welcome_language_text);

        welcomeLanguageButton.setOnClickListener(view -> {
            //创建下拉菜单
            PopupMenu popupMenu = new PopupMenu(this, view);
            MenuInflater inflater = popupMenu.getMenuInflater();
            inflater.inflate(R.menu.popup_menu, popupMenu.getMenu());

            //设置菜单项点击事件
            popupMenu.setOnMenuItemClickListener(item -> {
                if (item.getItemId() == R.id.welcome_language_ch) {
                    updateSelectedLanguage("中文");
                    return true;
                } else if (item.getItemId() == R.id.welcome_language_en) {
                    updateSelectedLanguage("English");
                    return true;
                } else {
                    updateSelectedLanguage("日本語");
                    return false;
                }
            });
            popupMenu.show();
        });

        //选择店内按钮
        View welcomeStoreButton = findViewById(R.id.welcome_view1);
        welcomeStoreButton.setOnClickListener(view -> {
            Intent intent = new Intent(this, MenuActivity.class);
            intent.putExtra("order_type", 0);
            startActivity(intent);
        });

        //选择外卖按钮
        View welcomeDeliveryButton = findViewById(R.id.welcome_view2);
        welcomeDeliveryButton.setOnClickListener(view -> {
            Intent intent = new Intent(this, MenuActivity.class);
            intent.putExtra("order_type", 1);
            startActivity(intent);
        });
    }

    private void updateSelectedLanguage(String language) {
        selectedText.setText(language);
    }

    private void startMenuWorker() {
        // 创建一个 OneTimeWorkRequest 实例来执行 MenuWorker
        WorkRequest menuWorkRequest = new OneTimeWorkRequest.Builder(MenuWorker.class).build();
        // 使用 WorkManager 来排队执行这个任务
        WorkManager.getInstance(this).enqueue(menuWorkRequest);
    }
}