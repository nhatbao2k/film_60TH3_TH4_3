package com.congnghephanmem.filmhay.manager.viewer;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.appcompat.app.ActionBar;
import androidx.appcompat.app.AppCompatActivity;

import android.content.BroadcastReceiver;
import android.content.Intent;
import android.content.IntentFilter;
import android.net.ConnectivityManager;
import android.os.Build;
import android.os.Bundle;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ListView;

import com.congnghephanmem.filmhay.BroadcastReceiver.NetworkChangeReciever;
import com.congnghephanmem.filmhay.Model.User;
import com.congnghephanmem.filmhay.R;
import com.google.firebase.database.ChildEventListener;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;

import java.util.ArrayList;

public class QuanLyThanhVien extends AppCompatActivity {
    DatabaseReference reference;
    ListView listView;
    ArrayList<User> users;
    Adapter_quanlythanhvien adapter_quanlythanhvien;
    BroadcastReceiver broadcastReceiver;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_quan_ly_thanh_vien);
        listView = (ListView) findViewById(R.id.list_item_user);
        reference = FirebaseDatabase.getInstance().getReference();
        users = new ArrayList<>();
        adapter_quanlythanhvien = new Adapter_quanlythanhvien(QuanLyThanhVien.this,R.layout.item_list_manager,users);
        listView.setAdapter(adapter_quanlythanhvien);
        reference.child("User").addChildEventListener(new ChildEventListener() {
            @Override
            public void onChildAdded(@NonNull DataSnapshot snapshot, @Nullable String previousChildName) {
                User user = snapshot.getValue(User.class);
                if (user.getRole().equals("viewer")){
                    users.add(user);
                    adapter_quanlythanhvien.notifyDataSetChanged();
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
        listView.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                Intent intent = new Intent(QuanLyThanhVien.this,ChiTiet_ThanhVien.class);
                intent.putExtra("name",users.get(position).getName());
                intent.putExtra("mail",users.get(position).getEmail());
                intent.putExtra("phone",users.get(position).getPhone());
                intent.putExtra("gender",users.get(position).getGender());
                intent.putExtra("birthday",users.get(position).getBirthday());
                startActivity(intent);
            }
        });

        androidx.appcompat.widget.Toolbar toolbar = findViewById(R.id.toolbar_quan_ly);
        setSupportActionBar(toolbar);
        getSupportActionBar().setTitle("Quản lý thành viên");
        ActionBar actionBar = getSupportActionBar();
        actionBar.setDisplayHomeAsUpEnabled(true);
        broadcastReceiver = new NetworkChangeReciever();
        registerNetWorkBroadcastReciver();
    }

    private void registerNetWorkBroadcastReciver() {
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.N){
            registerReceiver(broadcastReceiver, new IntentFilter(ConnectivityManager.CONNECTIVITY_ACTION));
        }
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.M){
            registerReceiver(broadcastReceiver, new IntentFilter(ConnectivityManager.CONNECTIVITY_ACTION));
        }
    }
    protected void unregisterNetwork(){
        try {
            unregisterReceiver(broadcastReceiver);
        }catch (IllegalArgumentException e){
            e.printStackTrace();
        }
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
        unregisterNetwork();
    }
}