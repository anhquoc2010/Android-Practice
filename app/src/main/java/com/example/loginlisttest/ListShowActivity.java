package com.example.loginlisttest;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.os.Bundle;
import android.util.Log;
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
    private ArrayList<CayThuocNam> cayThuocNamArrayList;
    private CayThuocNamAdapter cayThuocNamAdapter;
    private FirebaseFirestore db;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        binding = ActivityListShowBinding.inflate(getLayoutInflater());
        setContentView(binding.getRoot());

        // initializing our variable for firebase
        // firestore and getting its instance.
        db = FirebaseFirestore.getInstance();

        // creating our new array list
        cayThuocNamArrayList = new ArrayList<>();
        binding.rvListShow.setHasFixedSize(true);
        binding.rvListShow.setLayoutManager(new LinearLayoutManager(this));

        // adding our array list to our recycler view adapter class.
        cayThuocNamAdapter = new CayThuocNamAdapter(cayThuocNamArrayList, this);

        // setting adapter to our recycler view.
        binding.rvListShow.setAdapter(cayThuocNamAdapter);

        // below line is use to get the data from Firebase Firestore.
        // previously we were saving data on a reference of Courses
        // now we will be getting the data from the same reference.
        db.collection("ThuocNam").get()
                .addOnSuccessListener(queryDocumentSnapshots -> {
                    // after getting the data we are calling on success method
                    // and inside this method we are checking if the received
                    // query snapshot is empty or not.
                    if (!queryDocumentSnapshots.isEmpty()) {
                        // if the snapshot is not empty we are
                        // hiding our progress bar and adding
                        // our data in a list.
                        binding.progressBar.setVisibility(View.GONE);
                        List<DocumentSnapshot> list = queryDocumentSnapshots.getDocuments();
                        for (DocumentSnapshot d : list) {
                            // after getting this list we are passing
                            // that list to our object class.
                            CayThuocNam c = d.toObject(CayThuocNam.class);

                            // and we will pass this object class
                            // inside our arraylist which we have
                            // created for recycler view.
                            cayThuocNamArrayList.add(c);
                        }
                        // after adding the data to recycler view.
                        // we are calling recycler view notifuDataSetChanged
                        // method to notify that data has been changed in recycler view.
                        cayThuocNamAdapter.notifyDataSetChanged();
                    } else {
                        // if the snapshot is empty we are displaying a toast message.
                        Toast.makeText(ListShowActivity.this, "No data found in Database", Toast.LENGTH_SHORT).show();
                    }
                }).addOnFailureListener(e -> {
                    // if we do not get any data or any error we are displaying
                    // a toast message that we do not get any data
                    Toast.makeText(ListShowActivity.this, "Fail to get the data.", Toast.LENGTH_SHORT).show();
                });

    }
}