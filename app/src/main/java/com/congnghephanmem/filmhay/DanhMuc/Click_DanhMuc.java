package com.congnghephanmem.filmhay.DanhMuc;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ListView;

import androidx.appcompat.app.AppCompatActivity;

import com.congnghephanmem.filmhay.Adapter.Adapter_search;
import com.congnghephanmem.filmhay.ChitietPhim.ChitietPhim;
import com.congnghephanmem.filmhay.Model.Film;
import com.congnghephanmem.filmhay.R;
import com.congnghephanmem.filmhay.fragment.home;

import java.util.ArrayList;

public class Click_DanhMuc extends AppCompatActivity {

    ArrayList<Film> films;
    ListView listView;
    Adapter_search adapter_search;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_click__danh_muc);
        listView = (ListView) findViewById(R.id.list_item_danhmuc);
        films = new ArrayList<>();
        films = home.dm();
        adapter_search = new Adapter_search(Click_DanhMuc.this, R.layout.item_list_search, films);
        listView.setAdapter(adapter_search);
        listView.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                Intent intent = new Intent(Click_DanhMuc.this, ChitietPhim.class);
                intent.putExtra("img", films.get(position).getImg());
                intent.putExtra("name", films.get(position).getTenTheLoai());
                intent.putExtra("detal", films.get(position).getMota());
                intent.putExtra("link", films.get(position).getLink());
                intent.putExtra("id", films.get(position).getId());
                startActivity(intent);
            }
        });
    }
}