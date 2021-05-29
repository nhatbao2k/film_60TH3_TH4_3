package com.congnghephanmem.filmhay.manager.report.InformationReport;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.TextView;

import com.congnghephanmem.filmhay.Model.Report;
import com.congnghephanmem.filmhay.R;

import java.util.List;

public class InformationReportAdapter extends BaseAdapter {

    private Context context;
    private List<Report> reportList;
    private int layout;

    public InformationReportAdapter(Context context, List<Report> reportList, int layout) {
        this.context = context;
        this.reportList = reportList;
        this.layout = layout;
    }

    @Override
    public int getCount() {
        return reportList.size();
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
        TextView tvIDF, tvIDUser, tvErr, tvErrDif, tvTime;
    }
    @Override
    public View getView(int position, View convertView, ViewGroup parent) {
        LayoutInflater inflater = (LayoutInflater) context.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
        ViewHolder viewHolder;
        if (convertView == null){
            convertView = inflater.inflate(layout, null);
            viewHolder = new ViewHolder();

            viewHolder.tvIDF = (TextView) convertView.findViewById(R.id.tv_id_film_report);
            viewHolder.tvErr = (TextView) convertView.findViewById(R.id.tv_name_report);
            viewHolder.tvIDUser = (TextView) convertView.findViewById(R.id.tv_id_user_report);
            viewHolder.tvErrDif = (TextView) convertView.findViewById(R.id.tv_name_diff_report);
            viewHolder.tvTime = (TextView) convertView.findViewById(R.id.tv_time_report);

            convertView.setTag(viewHolder);
        }else {
            viewHolder = (ViewHolder) convertView.getTag();
        }

        viewHolder.tvIDF.setText(reportList.get(position).getId());
        viewHolder.tvErr.setText(reportList.get(position).getNameReport());
        viewHolder.tvIDUser.setText(reportList.get(position).getPhone());
        viewHolder.tvErrDif.setText(reportList.get(position).getComment());
        viewHolder.tvTime.setText(reportList.get(position).getTime());
        return convertView;
    }
}
