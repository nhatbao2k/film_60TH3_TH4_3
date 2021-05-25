package com.congnghephanmem.filmhay.Search;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.text.Editable;
import android.text.TextWatcher;
import android.view.View;
import android.widget.AdapterView;
import android.widget.EditText;
import android.widget.ListView;
import android.widget.Toast;

import com.congnghephanmem.filmhay.Adapter.Adapter_search;
import com.congnghephanmem.filmhay.ChitietPhim.ChitietPhim;
import com.congnghephanmem.filmhay.DanhMuc.Click_DanhMuc;
import com.congnghephanmem.filmhay.Model.DanhMuc;
import com.congnghephanmem.filmhay.R;
import com.congnghephanmem.filmhay.fragment.home;

import java.util.ArrayList;

public class Search extends AppCompatActivity {
    ArrayList<DanhMuc> danhMucs;
    ListView listView;
    Adapter_search adapter_search;
    EditText search;
    DanhMuc danhMuc;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_search);
        search = (EditText) findViewById(R.id.search_text);
        listView = (ListView) findViewById(R.id.list_item_search);
        danhMucs = new ArrayList<>();
        search.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence s, int start, int count, int after) {

            }

            @Override
            public void onTextChanged(CharSequence s, int start, int before, int count) {
                danhMucs.clear();
                 danhMuc= home.serach(search.getText().toString().trim());
                if (danhMuc.getTenTheLoai().equals("")){
                    danhMuc= home.serach_theloai(search.getText().toString().trim());
                    if (danhMuc.getTenTheLoai().equals("")){
                        Toast.makeText(Search.this, "Không có dữ liệu", Toast.LENGTH_SHORT).show();
                        return;
                    }else {

                    }
                }


                danhMucs.add(danhMuc);
                adapter_search = new Adapter_search(Search.this,R.layout.item_list_search,danhMucs);
                listView.setAdapter(adapter_search);
            }

            @Override
            public void afterTextChanged(Editable s) {


            }
        });
        listView.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                if (danhMuc.getId()>100){
                    Intent intent= new Intent(Search.this, Click_DanhMuc.class);
                    startActivity(intent);
                }else {
                    Intent intent = new Intent(Search.this, ChitietPhim.class);
                    intent.putExtra("img",danhMuc.getImg());
                    intent.putExtra("name",danhMuc.getTenTheLoai());
                    intent.putExtra("detal",danhMuc.getMota());
                    intent.putExtra("link",danhMuc.getLink());
                    intent.putExtra("id",danhMuc.getId());
                    startActivity(intent);
                }

            }
        });


    }
}