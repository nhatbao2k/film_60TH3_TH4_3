package com.congnghephanmem.filmhay.manager.Film;


import android.content.Intent;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuItem;
import android.widget.ListView;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;

import com.congnghephanmem.filmhay.Model.Film;
import com.congnghephanmem.filmhay.R;
import com.google.firebase.database.ChildEventListener;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;

import java.util.ArrayList;


public class ManagerFilmActivity extends AppCompatActivity {

    ListView lv_information;
    DatabaseReference reference;
    ArrayList<Film> films;
    Adapter_addfilm adapter_addfilm;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.manager_information);

        androidx.appcompat.widget.Toolbar toolbar = findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        lv_information =(ListView) findViewById(R.id.lv_information);
        films = new ArrayList<>();
        reference = FirebaseDatabase.getInstance().getReference();
        get_data();

    }
    void get_data(){
        reference.child("Data_film").addChildEventListener(new ChildEventListener() {
            @Override
            public void onChildAdded(@NonNull DataSnapshot snapshot, @Nullable String previousChildName) {
                Film f = snapshot.getValue(Film.class);
                films.add(f);
                adapter_addfilm= new Adapter_addfilm(ManagerFilmActivity.this,R.layout.item_addfilm,films);
                lv_information.setAdapter(adapter_addfilm);
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
            //Toast.makeText(ManagerFilmActivity.this, "Alo", Toast.LENGTH_SHORT).show();
            Intent intent= new Intent(ManagerFilmActivity.this,Add_film.class);
            startActivity(intent);
        }
        return super.onOptionsItemSelected(item);
    }
}
