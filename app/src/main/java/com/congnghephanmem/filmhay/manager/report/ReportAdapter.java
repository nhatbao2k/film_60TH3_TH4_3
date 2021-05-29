package com.congnghephanmem.filmhay.manager.report;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.TextView;

import com.congnghephanmem.filmhay.Model.Report;
import com.congnghephanmem.filmhay.R;

import java.util.List;

public class ReportAdapter extends BaseAdapter {

    private Context context;
    private int layout;
    private List<String> stringList;

    public ReportAdapter(Context context, int layout, List<String> stringList) {
        this.context = context;
        this.layout = layout;
        this.stringList = stringList;
    }

    @Override
    public int getCount() {
        return stringList.size();
    }

    @Override
    public Object getItem(int position) {
        return null;
    }

    @Override
    public long getItemId(int position) {
        return 0;
    }

    public class ViewHolder{
        TextView textView;
    }
    @Override
    public View getView(int position, View convertView, ViewGroup parent) {
        LayoutInflater inflater = (LayoutInflater) context.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
        ViewHolder viewHolder;
        if (convertView == null){
            convertView = inflater.inflate(layout, null);
            viewHolder = new ViewHolder();

            viewHolder.textView = (TextView) convertView.findViewById(R.id.tv_id_film);

            convertView.setTag(viewHolder);
        }else {
            viewHolder = (ViewHolder) convertView.getTag();
        }

        viewHolder.textView.setText(stringList.get(position));
        return convertView;
    }
}
