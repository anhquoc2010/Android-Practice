package com.example.android_w3_e1_switchactivities;

import androidx.activity.result.ActivityResultLauncher;
import androidx.activity.result.contract.ActivityResultContracts;
import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.ListView;

import java.util.ArrayList;

public class MainActivity extends AppCompatActivity {
    public static final int RESULT_CODE_SAVE1 = 115;
    public static final int RESULT_CODE_SAVE2 = 116;
    Button btnInputData;
    ListView lvData;
    ArrayList<Integer> arrData = new ArrayList<>();
    ArrayAdapter<Integer> adapter = null;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        /*
         * Xử lý kết quả trả về ở đây
         */
        ActivityResultLauncher<Intent> startActivityRs = registerForActivityResult(
                new ActivityResultContracts.StartActivityForResult(),
                result -> {
                    //Kiểm trả ResultCode trả về, cái này ở bên InputDataActivity truyền về
                    //có nó rồi thì xử lý trở lên thông thường
                    if (result.getResultCode() == RESULT_CODE_SAVE1) {//giá trị từ InputDataActivity
                        assert result.getData() != null;
                        int v1 = result.getData().getIntExtra("data", 0);
                        arrData.add(v1 * v1);
                        adapter.notifyDataSetChanged();
                    } else if (result.getResultCode() == RESULT_CODE_SAVE2) {//giá trị từ InputDataActivity
                        assert result.getData() != null;
                        int v2 = result.getData().getIntExtra("data", 0);
                        arrData.add(v2);
                        adapter.notifyDataSetChanged();
                    }
                });

        btnInputData = findViewById(R.id.btn_open_activity);
        btnInputData.setOnClickListener(arg0 -> {
            //Mở Activity
            Intent intent = new Intent(MainActivity.this, InputDataActivity.class);
            //gọi startActivityForResult
            startActivityRs.launch(intent);

        });

        lvData = findViewById(R.id.lv_data);
        adapter = new ArrayAdapter<>
                (this,
                        android.R.layout.simple_list_item_1,
                        arrData);
        lvData.setAdapter(adapter);
    }
}