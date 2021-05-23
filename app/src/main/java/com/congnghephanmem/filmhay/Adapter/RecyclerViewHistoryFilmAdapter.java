package com.congnghephanmem.filmhay.Adapter;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.congnghephanmem.filmhay.Interface.InterfaceHistoryFilm;
import com.congnghephanmem.filmhay.Model.History;
import com.congnghephanmem.filmhay.R;
import com.squareup.picasso.Picasso;

import java.util.List;

import butterknife.BindView;
import butterknife.ButterKnife;

public class RecyclerViewHistoryFilmAdapter extends RecyclerView.Adapter<RecyclerView.ViewHolder> {

    private List<History> historyList;
    private Context context;
    private InterfaceHistoryFilm interfaceHistoryFilm;

    public RecyclerViewHistoryFilmAdapter(List<History> historyList, Context context, InterfaceHistoryFilm interfaceHistoryFilm) {
        this.historyList = historyList;
        this.context = context;
        this.interfaceHistoryFilm = interfaceHistoryFilm;
    }

    @NonNull
    @Override
    public RecyclerView.ViewHolder onCreateViewHolder(@NonNull  ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(context).inflate(R.layout.iteam_history_film, null);

        return new RecyclerViewHolder(view, interfaceHistoryFilm);
    }

    @Override
    public void onBindViewHolder(@NonNull  RecyclerView.ViewHolder holder, int position) {
        RecyclerViewHolder recyclerViewHolder = (RecyclerViewHolder)holder;

        if (!historyList.get(position).getImageFilm().equals("")){
            Picasso.get().load(historyList.get(position).getImageFilm()).into(recyclerViewHolder.imageView);
        }
        recyclerViewHolder.tvInformationFilm.setText(historyList.get(position).getInformation());
        recyclerViewHolder.tvNameFilm.setText(historyList.get(position).getNameFilm());
        recyclerViewHolder.tvTimeFilm.setText(historyList.get(position).getTime());
        holder.itemView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                interfaceHistoryFilm.clickFilm(holder.getAdapterPosition(), historyList.get(position).getLink());
            }
        });
    }

    @Override
    public int getItemCount() {
        return historyList.size();
    }
}
class RecyclerViewHolder extends RecyclerView.ViewHolder{

    @BindView(R.id.img_history_film)
    ImageView imageView;
    @BindView(R.id.tv_history_name_film)
    TextView tvNameFilm;
    @BindView(R.id.tv_inforation_film)
    TextView tvInformationFilm;
    @BindView(R.id.tv_history_time_film)
    TextView tvTimeFilm;
    InterfaceHistoryFilm interfaceHistoryFilm;
    public RecyclerViewHolder(@NonNull  View itemView, InterfaceHistoryFilm interfaceHistoryFilm) {
        super(itemView);
        this.interfaceHistoryFilm = interfaceHistoryFilm;
        ButterKnife.bind(this, itemView);
    }
}