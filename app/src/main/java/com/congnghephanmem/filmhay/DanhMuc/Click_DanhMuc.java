package com.congnghephanmem.filmhay.DanhMuc;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ListView;

import com.congnghephanmem.filmhay.Adapter.Adapter_search;
import com.congnghephanmem.filmhay.ChitietPhim.ChitietPhim;
import com.congnghephanmem.filmhay.Model.DanhMuc;
import com.congnghephanmem.filmhay.R;
import com.congnghephanmem.filmhay.Search.Search;
import com.congnghephanmem.filmhay.fragment.home;

import java.util.ArrayList;

public class Click_DanhMuc extends AppCompatActivity {

    ArrayList<DanhMuc> danhMucs;
    ListView listView;
    Adapter_search adapter_search;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_click__danh_muc);
        listView = (ListView) findViewById(R.id.list_item_danhmuc);
        danhMucs = new ArrayList<>();
        danhMucs = home.dm();
        adapter_search = new Adapter_search(Click_DanhMuc.this,R.layout.item_list_search,danhMucs);
        listView.setAdapter(adapter_search);
        listView.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                Intent intent = new Intent(Click_DanhMuc.this, ChitietPhim.class);
                intent.putExtra("img",danhMucs.get(position).getImg());
                intent.putExtra("name",danhMucs.get(position).getTenTheLoai());
                intent.putExtra("detal",danhMucs.get(position).getMota());
                intent.putExtra("link",danhMucs.get(position).getLink());
                intent.putExtra("id",danhMucs.get(position).getId());
                startActivity(intent);
            }
        });
    }
}