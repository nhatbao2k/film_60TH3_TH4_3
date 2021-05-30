package com.congnghephanmem.filmhay.manager.employeer;

import android.content.BroadcastReceiver;
import android.content.Intent;
import android.content.IntentFilter;
import android.net.ConnectivityManager;
import android.os.Build;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ListView;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.appcompat.app.ActionBar;
import androidx.appcompat.app.AppCompatActivity;

import com.congnghephanmem.filmhay.BroadcastReceiver.NetworkChangeReciever;
import com.congnghephanmem.filmhay.Model.User;
import com.congnghephanmem.filmhay.R;
import com.congnghephanmem.filmhay.manager.employeer.InformationEmployeer.InformationEmployeerActivity;
import com.congnghephanmem.filmhay.manager.employeer.InformationEmployeer.ItemEmployeerAdapter;
import com.google.firebase.database.ChildEventListener;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;

import java.util.ArrayList;
import java.util.List;

import butterknife.BindView;
import butterknife.ButterKnife;

public class ManagerEmployerActivity extends AppCompatActivity {

    @BindView(R.id.lv_information)
    ListView lvInformation;
    ItemEmployeerAdapter itemEmployeerAdapter;
    List<User> userList;
    DatabaseReference mData;
    String method;
    BroadcastReceiver broadcastReceiver;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.manager_information);
        ButterKnife.bind(this);

        androidx.appcompat.widget.Toolbar toolbar = findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        mData = FirebaseDatabase.getInstance().getReference();
        userList = new ArrayList<>();
        itemEmployeerAdapter = new ItemEmployeerAdapter(ManagerEmployerActivity.this, userList, R.layout.item_list_manager);
        lvInformation.setAdapter(itemEmployeerAdapter);
        //hiển thị dữ liệu
        loadData();
        //click vào một item
        lvInformation.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                method = "update";
                Intent intent = new Intent(ManagerEmployerActivity.this, InformationEmployeerActivity.class);
                Bundle bundle = new Bundle();
                bundle.putString("method", method);
                User user = userList.get(position);
                bundle.putSerializable("user", user);

                intent.putExtra("data", bundle);
                startActivity(intent);
            }
        });

        returnManager();
        broadcastReceiver = new NetworkChangeReciever();
        registerNetWorkBroadcastReciver();
    }

    private void returnManager() {
        androidx.appcompat.widget.Toolbar toolbar = findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);
        getSupportActionBar().setTitle("Quản lý nhân viên");
        ActionBar actionBar = getSupportActionBar();
        actionBar.setDisplayHomeAsUpEnabled(true);
    }

    private void loadData() {
        mData.child("User").addChildEventListener(new ChildEventListener() {
            @Override
            public void onChildAdded(@NonNull DataSnapshot snapshot, @Nullable String previousChildName) {
                User user = snapshot.getValue(User.class);
                if (user.getRole().equals("employeer")){
                    userList.add(user);
                    itemEmployeerAdapter.notifyDataSetChanged();
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


    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        getMenuInflater().inflate(R.menu.add_data, menu);
        return super.onCreateOptionsMenu(menu);
    }

    @Override
    public boolean onOptionsItemSelected(@NonNull MenuItem item) {
        if (item.getItemId() == R.id.menuAdd){
            startActivity(new Intent(ManagerEmployerActivity.this, InformationEmployeerActivity.class));
        }
        return super.onOptionsItemSelected(item);
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
