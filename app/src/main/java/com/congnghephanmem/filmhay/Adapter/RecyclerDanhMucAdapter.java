package com.congnghephanmem.filmhay.Adapter;

import android.content.Context;
import android.content.Intent;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.congnghephanmem.filmhay.DanhMuc.Click_DanhMuc;
import com.congnghephanmem.filmhay.Interface.InterfaceDanhMuc;
import com.congnghephanmem.filmhay.Model.Film;
import com.congnghephanmem.filmhay.R;

import java.util.List;

import butterknife.BindView;
import butterknife.ButterKnife;

public class RecyclerDanhMucAdapter extends RecyclerView.Adapter<RecyclerView.ViewHolder> {

    private List<Film> filmList;
    private Context context;
    InterfaceDanhMuc interfaceDanhMuc;

    public RecyclerDanhMucAdapter(List<Film> filmList, Context context, InterfaceDanhMuc interfaceDanhMuc) {
        this.filmList = filmList;
        this.context = context;
        this.interfaceDanhMuc = interfaceDanhMuc;
    }

    @NonNull
    @Override
    public RecyclerView.ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(context).inflate(R.layout.item_danh_muc,null);
        return new MyRecyclerDanhMuc(view, interfaceDanhMuc);
    }

    @Override
    public void onBindViewHolder(@NonNull RecyclerView.ViewHolder holder, int position) {
        MyRecyclerDanhMuc myRecyclerDanhMuc = (MyRecyclerDanhMuc) holder;

        myRecyclerDanhMuc.img.setImageResource(filmList.get(position).getImg());
        myRecyclerDanhMuc.tv.setText(filmList.get(position).getTenTheLoai());

        holder.itemView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                interfaceDanhMuc.click(holder.getAdapterPosition());
                Intent intent= new Intent(context, Click_DanhMuc.class);
                context.startActivity(intent);
            }
        });
    }

    @Override
    public int getItemCount() {
        return filmList.size();
    }
}
class MyRecyclerDanhMuc extends RecyclerView.ViewHolder{

    InterfaceDanhMuc interfaceDanhMuc;
    @BindView(R.id.img_danh_muc)
    ImageView img;
    @BindView(R.id.tv_ten_danhmuc)
    TextView tv;
    public MyRecyclerDanhMuc(@NonNull View itemView, InterfaceDanhMuc interfaceDanhMuc) {
        super(itemView);
        this.interfaceDanhMuc = interfaceDanhMuc;

        ButterKnife.bind(this, itemView);
    }
}
