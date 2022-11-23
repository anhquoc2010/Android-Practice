package com.example.loginlisttest.adapter;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.example.loginlisttest.R;
import com.example.loginlisttest.model.CayThuocNam;

import java.util.ArrayList;

public class CayThuocNamAdapter extends RecyclerView.Adapter<CayThuocNamAdapter.ViewHolder> {
    // creating variables for our ArrayList and context
    private ArrayList<CayThuocNam> cayThuocNamArrayList;
    private Context context;

    // creating constructor for our adapter class
    public CayThuocNamAdapter(ArrayList<CayThuocNam> cayThuocNamArrayList, Context context) {
        this.cayThuocNamArrayList = cayThuocNamArrayList;
        this.context = context;
    }

    @NonNull
    @Override
    public CayThuocNamAdapter.ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        // passing our layout file for displaying our card item
        return new ViewHolder(LayoutInflater.from(context).inflate(R.layout.layout_item, parent, false));
    }

    @Override
    public void onBindViewHolder(@NonNull CayThuocNamAdapter.ViewHolder holder, int position) {
        // setting data to our text views from our modal class.
        CayThuocNam cayThuocNam = cayThuocNamArrayList.get(position);
        holder.ctnName.setText(cayThuocNam.getName());
        holder.ctnColor.setText(cayThuocNam.getColor());
        holder.ctnDes.setText(cayThuocNam.getDescription());
//        holder.ctnImage.getResources().getIdentifier(cayThuocNam.getImage(), "drawable", context.getPackageName());
        holder.ctnImage.setImageResource(context.getResources().getIdentifier(cayThuocNam.getImage(), "drawable", context.getPackageName()));

        if (holder.ctnMark.getDrawable() == context.getResources().getDrawable(R.drawable.ic_baseline_favorite_border_24)) {
            holder.ctnMark.setTag("unmarked");
        } else {
            holder.ctnMark.setTag("marked");
        }

        holder.ctnMark.setOnClickListener(v -> {
            if (holder.ctnMark.getTag().equals("unmarked")) {
                holder.ctnMark.setImageResource(R.drawable.ic_baseline_favorite_24);
                holder.ctnMark.setTag("marked");
            } else {
                holder.ctnMark.setImageResource(R.drawable.ic_baseline_favorite_border_24);
                holder.ctnMark.setTag("unmarked");
            }
        });
    }

    @Override
    public int getItemCount() {
        // returning the size of our array list.
        return cayThuocNamArrayList.size();
    }

    class ViewHolder extends RecyclerView.ViewHolder {
        // creating variables for our text views.
        private final TextView ctnName;
        private final TextView ctnColor;
        private final TextView ctnDes;
        private final ImageView ctnImage;

        private final ImageView ctnMark;

        public ViewHolder(@NonNull View itemView) {
            super(itemView);
            // initializing our text views.
            ctnName = itemView.findViewById(R.id.tv_name);
            ctnColor = itemView.findViewById(R.id.tv_color);
            ctnDes = itemView.findViewById(R.id.tv_des);
            ctnImage = itemView.findViewById(R.id.iv_avatar);

            ctnMark = itemView.findViewById(R.id.iv_mark);
        }
    }
}
