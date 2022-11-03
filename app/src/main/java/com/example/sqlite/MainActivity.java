package com.example.sqlite;

import androidx.activity.result.ActivityResultLauncher;
import androidx.activity.result.contract.ActivityResultContracts;
import androidx.appcompat.app.AppCompatActivity;

import android.app.Activity;
import android.content.ContentValues;
import android.content.Intent;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.os.Bundle;
import android.view.Menu;
import android.view.View;
import android.widget.Button;
import android.widget.Toast;

import java.util.Locale;

public class MainActivity extends AppCompatActivity {

    Button btnInsertCategory = null;
    Button btnShowCategoryList = null;
    Button btnInsertComputer = null;
    public static final int SEND_DATA_FROM_CATEGORY_ACTIVITY = 2;
    SQLiteDatabase database = null;
    ActivityResultLauncher<Intent> startForResult;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        startForResult = registerForActivityResult(
                new ActivityResultContracts.StartActivityForResult(),
                result -> {
                    if (result.getResultCode() == SEND_DATA_FROM_CATEGORY_ACTIVITY) {
                        Intent data = result.getData();
                        Bundle bundle = data.getBundleExtra("DATA_CATEGORY");
                        String name = bundle.getString("name");
                        ContentValues content = new ContentValues();
                        content.put("name", name);
                        if (database != null) {
                            long categoryid = database.insert("tblCategory", null, content);
                            if (categoryid == -1) {
                                Toast.makeText(MainActivity.this, categoryid + " - " + name + " ==> insert error!", Toast.LENGTH_LONG).show();
                            } else {
                                Toast.makeText(MainActivity.this, categoryid + " - " + name + " ==>insert OK!", Toast.LENGTH_LONG).show();
                            }
                        }
                    }
                });

        btnInsertCategory = (Button) findViewById(R.id.btnInsertCategory);
        btnInsertCategory.setOnClickListener(new MyEvent());
        btnShowCategoryList = (Button) findViewById(R.id.buttonShowCategoryList);
        btnShowCategoryList.setOnClickListener(new MyEvent());
        btnInsertComputer = (Button) findViewById(R.id.buttonInsertComputer);
        btnInsertComputer.setOnClickListener(new MyEvent());
        getDatabase();
    }

    /**
     * hàm kiểm tra xem bảng có tồn tại trong CSDL hay chưa
     *
     * @param database  - cơ sở dữ liệu
     * @param tableName - tên bảng cần kiểm tra
     * @return trả về true nếu tồn tại
     */
    public boolean isTableExists(SQLiteDatabase database, String tableName) {
        Cursor cursor = database.rawQuery("select DISTINCT tbl_name from sqlite_master where tbl_name = '" + tableName + "'", null);
        if (cursor != null) {
            if (cursor.getCount() > 0) {
                cursor.close();
                return true;
            }
            cursor.close();
        }
        return false;
    }

    /**
     * hàm tạo CSDL và các bảng liên quan
     *
     * @return
     */
    public SQLiteDatabase getDatabase() {
        try {
            database = openOrCreateDatabase("mydata.db", MODE_PRIVATE, null);
            if (database != null) {
                if (isTableExists(database, "tblCategory"))
                    return database;
                database.setLocale(Locale.getDefault());
                database.setVersion(1);
                String sqlCategory = "create table tblCategory ("
                        + "id integer primary key autoincrement,"
                        + "name text)";
                database.execSQL(sqlCategory);
                String sqlComputer = "create table tblComputer ("
                        + "id integer primary key autoincrement,"
                        + "name text, "
                        + "price integer,"
                        + "categoryid integer not null constraint categoryid references tblCategory(id) on delete cascade)";
                database.execSQL(sqlComputer);
                //Cách tạo trigger khi nhập dữ liệu sai ràng buộc quan hệ
                String sqlTrigger = "create trigger fk_insert_computer before insert on tblComputer "
                        + " for each row "
                        + " begin "
                        + " 	select raise(rollback,'them du lieu tren bang tblComputer bi sai') "
                        + " 	where (select id from tblCategory where id=new.categoryid) is null ;"
                        + " end;";
                database.execSQL(sqlTrigger);
                Toast.makeText(MainActivity.this, "OK OK", Toast.LENGTH_LONG).show();
            }
        } catch (Exception e) {
            Toast.makeText(this, e.toString(), Toast.LENGTH_LONG).show();
        }
        return database;
    }

    public void createDatabaseAndTrigger() {
        if (database == null) {
            getDatabase();
            Toast.makeText(MainActivity.this, "OK OK", Toast.LENGTH_LONG).show();
        }
    }

    /**
     * hàm mở màn hình nhập Danh mục
     */
    public void showInsertCategoryDialog() {
        Intent intent = new Intent(MainActivity.this, NewCategoryActivity.class);
        startForResult.launch(intent);
    }

    /**
     * hàm xem danh sách danh mục dùng Activity
     */
    public void showCategoryList() {
        Intent intent = new Intent(MainActivity.this, ShowListActivity.class);
        startActivity(intent);
    }

    /**
     * class xử lý sự kiện
     */
    private class MyEvent implements View.OnClickListener {

        @Override
        public void onClick(View v) {
            // TODO Auto-generated method stub
            if (v.getId() == R.id.btnInsertCategory) {
                showInsertCategoryDialog();
            } else if (v.getId() == R.id.buttonShowCategoryList) {
                showCategoryList();
            } else if (v.getId() == R.id.buttonInsertComputer) {
                Intent intent = new Intent(MainActivity.this, InsertComputerActivity.class);
                startActivity(intent);
            }
        }
    }
}