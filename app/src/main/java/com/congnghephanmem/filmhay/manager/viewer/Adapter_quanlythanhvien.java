package com.congnghephanmem.filmhay.manager.viewer;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.TextView;

import com.congnghephanmem.filmhay.Model.User;
import com.congnghephanmem.filmhay.R;

import java.util.List;

public class Adapter_quanlythanhvien extends BaseAdapter {
    Context context;
    int layout;
    List<User> users;

    public Adapter_quanlythanhvien(Context context, int layout, List<User> users) {
        this.context = context;
        this.layout = layout;
        this.users = users;
    }

    @Override
    public int getCount() {
        return users.size();
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
        TextView tv_name_manage_item = (TextView) convertView.findViewById(R.id.tv_name_manage_item);
        tv_name_manage_item.setText(users.get(position).getName());
        return convertView;
    }
}
