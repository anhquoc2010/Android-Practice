package com.example.android_w3_e1_switchactivities;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.widget.Button;
import android.widget.EditText;

public class InputDataActivity extends AppCompatActivity {
    Button btnSave1, btnSave2;
    EditText editNumber;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_input_data);

        btnSave1 = findViewById(R.id.btnSave1);
        btnSave2 = findViewById(R.id.btnSave2);
        btnSave1.setOnClickListener(arg0 -> {
            //Gửi thông điệp là lưu bình phương
            sendToMain(MainActivity.RESULT_CODE_SAVE1);
        });
        editNumber = findViewById(R.id.editNumber);
        btnSave2.setOnClickListener(v -> {
            //Gửi thông điệp là lưu số gốc
            sendToMain(MainActivity.RESULT_CODE_SAVE2);
        });
    }

    /**
     * hàm xử lý gửi kết quả về mainactivity
     * khi hàm này được gọi thì lập tức onActivityResult
     * ở MainActivity sẽ xảy ra đem theo ResultCode và Intent
     *
     * @param resultcode
     */
    public void sendToMain(int resultcode) {
        Intent intent = getIntent();
        int value = Integer.parseInt(editNumber.getText() + "");
        intent.putExtra("data", value);
        setResult(resultcode, intent);
        finish();
    }
}