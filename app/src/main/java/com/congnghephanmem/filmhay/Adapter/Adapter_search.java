package com.congnghephanmem.filmhay.Adapter;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.ImageView;
import android.widget.TextView;

import com.congnghephanmem.filmhay.Model.Film;
import com.congnghephanmem.filmhay.R;
import com.squareup.picasso.Picasso;

import java.util.List;

public class Adapter_search extends BaseAdapter {
    Context context;
    int layout;
    List<Film> films;

    public Adapter_search(Context context, int layout, List<Film> films) {
        this.context = context;
        this.layout = layout;
        this.films = films;
    }

    @Override
    public int getCount() {
        return films.size();
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
        Picasso.get().load(films.get(position).getImg()).into(imageView);
        textView.setText(films.get(position).getTenTheLoai());
        return convertView;
    }
}
