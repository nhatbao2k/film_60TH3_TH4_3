package com.congnghephanmem.filmhay.manager.employeer.InformationEmployeer;

import android.content.Context;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.ImageView;
import android.widget.TextView;

import com.congnghephanmem.filmhay.Model.User;
import com.congnghephanmem.filmhay.R;
import com.congnghephanmem.filmhay.manager.employeer.ManagerEmployerActivity;
import com.squareup.picasso.Picasso;

import java.util.List;

import butterknife.BindView;
import butterknife.ButterKnife;

public class ItemEmployeerAdapter extends BaseAdapter {

    private ManagerEmployerActivity context;
    private List<User> userList;
    private int layout;

    public ItemEmployeerAdapter(ManagerEmployerActivity context, List<User> userList, int layout) {
        this.context = context;
        this.userList = userList;
        this.layout = layout;
    }

    @Override
    public int getCount() {
        return userList.size();
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
        ImageView imageView;
        TextView textView;
    }
    @Override
    public View getView(int position, View convertView, ViewGroup parent) {
        LayoutInflater inflater = (LayoutInflater) context.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
        ViewHolder viewHolder;
        if (convertView == null){
            convertView = inflater.inflate(layout, null);
            viewHolder = new ViewHolder();

            viewHolder.imageView = (ImageView) convertView.findViewById(R.id.img_manager_information);
            viewHolder.textView = (TextView) convertView.findViewById(R.id.tv_name_manage_item);
            convertView.setTag(viewHolder);
        }else {
            viewHolder = (ViewHolder) convertView.getTag();
        }

        if (userList.get(position).getAvatar().isEmpty()){
            Bitmap bitmap = BitmapFactory.decodeResource(convertView.getResources(), R.drawable.user_menu);
        }
        else {
            Picasso.get().load(userList.get(position).getAvatar()).into(viewHolder.imageView);
        }
        viewHolder.textView.setText(userList.get(position).getName());
        return convertView;
    }
}
