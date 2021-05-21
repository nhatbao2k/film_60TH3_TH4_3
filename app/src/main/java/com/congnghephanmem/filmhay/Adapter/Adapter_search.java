package com.congnghephanmem.filmhay.Adapter;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.ImageView;
import android.widget.TextView;

import com.congnghephanmem.filmhay.Model.DanhMuc;
import com.congnghephanmem.filmhay.R;
import com.squareup.picasso.Picasso;

import java.util.List;

public class Adapter_search extends BaseAdapter {
    Context context;
    int layout;
    List<DanhMuc> danhMucs;

    public Adapter_search(Context context, int layout, List<DanhMuc> danhMucs) {
        this.context = context;
        this.layout = layout;
        this.danhMucs = danhMucs;
    }

    @Override
    public int getCount() {
        return danhMucs.size();
    }

    @Override
    public Object getItem(int position) {
        return null;
    }

    @Override
    public long getItemId(int position) {
        return 0;
    }

    @Override
    public View getView(int position, View convertView, ViewGroup parent) {
        LayoutInflater inflater = (LayoutInflater) context.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
        convertView = inflater.inflate(layout,null);
        ImageView imageView = convertView.findViewById(R.id.img_search);
        TextView textView = convertView.findViewById(R.id.ten_search);
        Picasso.get().load(danhMucs.get(position).getImg()).into(imageView);
        textView.setText(danhMucs.get(position).getTenTheLoai());
        return convertView;
    }
}
