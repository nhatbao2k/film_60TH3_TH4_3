package com.congnghephanmem.filmhay.manager.Film;

import android.content.Context;
import android.content.Intent;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;

import com.congnghephanmem.filmhay.Model.Film;
import com.congnghephanmem.filmhay.R;
import com.google.firebase.database.ChildEventListener;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.squareup.picasso.Picasso;

import java.util.List;

public class Adapter_addfilm extends BaseAdapter {
    Context context;
    int layout;
    List<Film> films;
    DatabaseReference reference;

    public Adapter_addfilm(Context context, int layout, List<Film> films) {
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
        reference = FirebaseDatabase.getInstance().getReference();
        LayoutInflater inflater = (LayoutInflater) context.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
        convertView =  inflater.inflate(layout,null);
        ImageView imageView = (ImageView) convertView.findViewById(R.id.img_addflim);
        TextView textView = (TextView) convertView.findViewById(R.id.name_addflim);
        Picasso.get().load(films.get(position).getImg()).into(imageView);
        textView.setText(films.get(position).getTenTheLoai());
        Button edit = (Button) convertView.findViewById(R.id.edit_addflim);
        edit.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(context,Add_film.class);
                intent.putExtra("img",films.get(position).getImg());
                intent.putExtra("name",films.get(position).getTenTheLoai());
                intent.putExtra("detal",films.get(position).getMota());
                intent.putExtra("link",films.get(position).getLink());
                intent.putExtra("id",films.get(position).getId());

                context.startActivity(intent);
            }
        });
        Button delete = (Button) convertView.findViewById(R.id.delete_addflim);
        delete.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                reference.child("Data_film").addChildEventListener(new ChildEventListener() {
                    @Override
                    public void onChildAdded(@NonNull DataSnapshot snapshot, @Nullable String previousChildName) {
                        Film f  = snapshot.getValue(Film.class);

                        if (f.getId()==films.get(position).getId()){
                            reference.child("Data_film").child(snapshot.getKey()).removeValue();
                            Toast.makeText(context,"Đã Xóa",Toast.LENGTH_SHORT).show();
                        }
                    }

                    @Override
                    public void onChildChanged(@NonNull DataSnapshot snapshot, @Nullable String previousChildName) {

                    }

                    @Override
                    public void onChildRemoved(@NonNull DataSnapshot snapshot) {

                    }

                    @Override
                    public void onChildMoved(@NonNull DataSnapshot snapshot, @Nullable String previousChildName) {

                    }

                    @Override
                    public void onCancelled(@NonNull DatabaseError error) {

                    }
                });

            }
        });

        return convertView;
    }
}
