package com.example.sqlite;

import androidx.appcompat.app.AppCompatActivity;

import android.app.DatePickerDialog;
import android.app.Dialog;
import android.content.ContentValues;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.os.Bundle;
import android.view.Menu;
import android.view.View;
import android.widget.AdapterView;
import android.widget.Button;
import android.widget.DatePicker;
import android.widget.EditText;
import android.widget.ListView;
import android.widget.Spinner;
import android.widget.Toast;

import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.List;

/**
 * class hiển thị thông tin Danh mục và Spinner
 * và hiển thị thông tin máy tính vào ListView
 * đồng thời cho phép thao tác với máy tính
 */

public class InsertComputerActivity extends AppCompatActivity {

    SQLiteDatabase database = null;
    List<InforData> listComputer = null;
    List<InforData> listCategory = null;
    InforData categoryData = null;
    MySimpleArrayAdapter adapter = null;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_insert_computer);
        Spinner pinner = (Spinner) findViewById(R.id.spinner1);
        listCategory = new ArrayList<InforData>();
        InforData d1 = new InforData();
        d1.setField1("_");
        d1.setField2("Show All");
        d1.setField3("_");
        listCategory.add(d1);
        //Lệnh xử lý đưa dữ liệu là Danh mục và Spinner
        database = openOrCreateDatabase("mydata.db", MODE_PRIVATE, null);
        if (database != null) {

            Cursor cursor = database.query("tblCategory", null, null, null, null, null, null);
            cursor.moveToFirst();
            int index = 1;
            while (cursor.isAfterLast() == false) {
                InforData d = new InforData();
                d.setField1(index);
                d.setField2(cursor.getInt(0));
                d.setField3(cursor.getString(1));
                listCategory.add(d);
                index++;
                cursor.moveToNext();
            }
            cursor.close();
        }
        adapter = new MySimpleArrayAdapter(InsertComputerActivity.this, R.layout.layout_show_list, listCategory);
        adapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        pinner.setAdapter(adapter);
        //Xử lý sự kiện chọn trong Spinner
        //chọn danh mục nào thì hiển thị toàn bộ máy tính của danh mục đó mà thôi
        //Nếu chọn All thì hiển thị toàn bộ không phân hiệt danh mục
        pinner.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> arg0, View arg1,
                                       int arg2, long arg3) {
                // TODO Auto-generated method stub
                if (arg2 == 0) {
                    //Hiển thị mọi máy tính  trong CSDL
                    categoryData = null;
                    loadAlllistComputer();
                } else {
                    //Hiển thị sách theo máy tính chọn trong Spinner
                    categoryData = listCategory.get(arg2);
                    loadListComputerByCategory(categoryData.getField1().toString());
                }
            }

            @Override
            public void onNothingSelected(AdapterView<?> arg0) {
                // TODO Auto-generated method stub
                categoryData = null;
            }
        });

        //Lệnh xử lý thêm mới một sản phẩm danh mục đang chọn
        Button btnInsertComputer = (Button) findViewById(R.id.buttonInsertComputerNe);
        btnInsertComputer.setOnClickListener(new View.OnClickListener() {

            @Override
            public void onClick(View v) {
                // TODO Auto-generated method stub
                if (categoryData == null) {
                    Toast.makeText(InsertComputerActivity.this, "Please choose an category to insert", Toast.LENGTH_LONG).show();
                    return;
                }
                EditText txtTitle = (EditText) findViewById(R.id.editTextTitle);
                EditText txtPrice = (EditText) findViewById(R.id.editTextPrice);
                ContentValues values = new ContentValues();
                values.put("name", txtTitle.getText().toString());
                values.put("price", txtPrice.getText().toString());
                values.put("categoryid", categoryData.getField2().toString());
                long bId = database.insert("tblComputer", null, values);
                if (bId > 0) {
                    Toast.makeText(InsertComputerActivity.this, "Insert Computer OK", Toast.LENGTH_LONG).show();
                    loadListComputerByCategory(categoryData.getField2().toString());
                } else {
                    Toast.makeText(InsertComputerActivity.this, "Insert Computer Failed", Toast.LENGTH_LONG).show();
                }
            }
        });
    }

    /*
     * Hàm hiển thị mọi máy tính trong CSDL
     */
    public void loadAlllistComputer() {
        Cursor cur = database.query("tblComputer", null, null, null, null, null, null);
        cur.moveToFirst();
        listComputer = new ArrayList<InforData>();
        while (cur.isAfterLast() == false) {
            InforData d = new InforData();
            d.setField1(cur.getInt(0));
            d.setField2(cur.getString(1));
            d.setField3(cur.getInt(2));
            listComputer.add(d);
            cur.moveToNext();
        }
        cur.close();
        adapter = new MySimpleArrayAdapter(InsertComputerActivity.this, R.layout.layout_show_list, listComputer);
        ListView lv = (ListView) findViewById(R.id.listViewComputer);
        lv.setAdapter(adapter);
    }

    /**
     * hàm hiển thị máy tính theo danh mục
     *
     * @param categoryid
     */
    public void loadListComputerByCategory(String categoryid) {
        Cursor cur = database.query("tblComputer", null, "categoryid=?", new String[]{categoryid}, null, null, null);
        cur.moveToFirst();
        listComputer = new ArrayList<InforData>();
        while (cur.isAfterLast() == false) {
            InforData d = new InforData();
            d.setField1(cur.getInt(0));
            d.setField2(cur.getString(1));
            d.setField3(cur.getInt(2));
            listComputer.add(d);
            cur.moveToNext();
        }
        cur.close();
        adapter = new MySimpleArrayAdapter(InsertComputerActivity.this, R.layout.layout_show_list, listComputer);
        ListView lv = (ListView) findViewById(R.id.listViewComputer);
        lv.setAdapter(adapter);
    }
}