package com.example.w2_e1_ui_profile;

import androidx.appcompat.app.AppCompatActivity;
import androidx.core.content.ContextCompat;
import androidx.core.view.WindowCompat;
import androidx.core.view.WindowInsetsCompat;

import android.graphics.Color;
import android.os.Build;
import android.os.Bundle;
import android.view.View;
import android.view.WindowManager;
import android.widget.GridView;

import java.util.Objects;

public class MainActivity extends AppCompatActivity {

    GridView gridView_top_tokens;
    GridView gridView_top_networks;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        gridView_top_tokens = findViewById(R.id.gv_top_tokens);
        gridView_top_networks = findViewById(R.id.gv_top_networks);

        transparentStatus();

        int[] images_list_top_tokens = {
                R.drawable.ada,
                0,
                R.drawable.adt,
                0,
                R.drawable.adx,
                0,
                R.drawable.aeon,
                0,
                R.drawable.agrs,
                0
        };
        ImageAdapter imageAdapter_top_tokens = new ImageAdapter(images_list_top_tokens, this);
        gridView_top_tokens.setAdapter(imageAdapter_top_tokens);

        int[] images_list_top_networks = {
                R.drawable.bsd,
                0,
                R.drawable.btc,
                0,
                R.drawable.btcp,
                0,
                R.drawable.btg,
                0,
                R.drawable.bts,
                0
        };
        ImageAdapter imageAdapter_top_networks = new ImageAdapter(images_list_top_networks, this);
        gridView_top_networks.setAdapter(imageAdapter_top_networks);
    }

    private void transparentStatus() {
        if (Build.VERSION.SDK_INT >= 19 && Build.VERSION.SDK_INT < 21) {
            setWindowFlag(WindowManager.LayoutParams.FLAG_TRANSLUCENT_STATUS, true);
        }
        if (Build.VERSION.SDK_INT >= 19) {
            getWindow().getDecorView().setSystemUiVisibility(
                    View.SYSTEM_UI_FLAG_LAYOUT_STABLE
                            | View.SYSTEM_UI_FLAG_LAYOUT_FULLSCREEN);
        }
        if (Build.VERSION.SDK_INT >= 21) {
            setWindowFlag(WindowManager.LayoutParams.FLAG_TRANSLUCENT_STATUS, false);
            getWindow().setStatusBarColor(Color.TRANSPARENT);
        }
    }

    private void setWindowFlag(int i, boolean b) {
        WindowManager.LayoutParams layoutParams = getWindow().getAttributes();
        if (b) {
            layoutParams.flags |= i;
        } else {
            layoutParams.flags &= ~i;
        }
        getWindow().setAttributes(layoutParams);
    }
}