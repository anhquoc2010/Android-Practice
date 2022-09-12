package com.example.w2_e1_ui_profile;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.ImageView;

public class ImageAdapter extends BaseAdapter {

    int[] images_list;
    Context ctx;

    ImageAdapter(int[] images_list, Context ctx) {
        this.images_list = images_list;
        this.ctx = ctx;
    }

    @Override
    public int getCount() {
        return images_list.length;
    }

    @Override
    public Object getItem(int position) {
        return images_list[position];
    }

    @Override
    public long getItemId(int position) {
        return position;
    }

    @Override
    public View getView(int position, View convertView, ViewGroup parent) {

        View gridView = convertView;
        if (gridView == null) {
            LayoutInflater inflater = (LayoutInflater) ctx.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
            gridView = inflater.inflate(R.layout.custom_image_layout, null);
        }

        ImageView i1 = gridView.findViewById(R.id.imageView);
        i1.setImageResource(images_list[position]);

        return gridView;
    }
}
