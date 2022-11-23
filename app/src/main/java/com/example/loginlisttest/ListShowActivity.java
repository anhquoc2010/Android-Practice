package com.example.loginlisttest;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.os.Bundle;
import android.view.View;
import android.widget.ProgressBar;
import android.widget.Toast;

import com.example.loginlisttest.adapter.CayThuocNamAdapter;
import com.example.loginlisttest.databinding.ActivityListShowBinding;
import com.example.loginlisttest.model.CayThuocNam;
import com.google.android.gms.tasks.OnFailureListener;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.firebase.firestore.DocumentSnapshot;
import com.google.firebase.firestore.FirebaseFirestore;
import com.google.firebase.firestore.QuerySnapshot;

import java.util.ArrayList;
import java.util.List;

public class ListShowActivity extends AppCompatActivity {

    ActivityListShowBinding binding;
    private ArrayList<CayThuocNam> coursesArrayList;
    private CayThuocNamAdapter cayThuocNamAdapter;
    private FirebaseFirestore db;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        binding = ActivityListShowBinding.inflate(getLayoutInflater());
        setContentView(binding.getRoot());

    }
}