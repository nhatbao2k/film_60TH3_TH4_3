package com.congnghephanmem.filmhay.ChitietPhim;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.TextView;

import com.congnghephanmem.filmhay.Model.binhluan;
import com.congnghephanmem.filmhay.R;

import java.util.List;

public class Adapter_cmt extends BaseAdapter {
    Context context;
    List<binhluan> binhluans;
    int layout;

    public Adapter_cmt(Context context, List<binhluan> binhluans, int layout) {
        this.context = context;
        this.binhluans = binhluans;
        this.layout = layout;
    }

    @Override
    public int getCount() {
        return binhluans.size();
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
        TextView textView1 = convertView.findViewById(R.id.tennguoidung_cmt);
        TextView textView2 = convertView.findViewById(R.id.txt_cmt);
        textView1.setText(binhluans.get(position).getName());
        textView2.setText(binhluans.get(position).getText());
        return convertView;
    }
}
