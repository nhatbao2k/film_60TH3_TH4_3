package com.congnghephanmem.filmhay.manager.Film;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.appcompat.app.ActionBar;
import androidx.appcompat.app.AppCompatActivity;

import android.content.BroadcastReceiver;
import android.content.Intent;
import android.content.IntentFilter;
import android.net.ConnectivityManager;
import android.net.Uri;
import android.os.Build;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.ProgressBar;
import android.widget.Toast;

import com.congnghephanmem.filmhay.BroadcastReceiver.NetworkChangeReciever;
import com.congnghephanmem.filmhay.Model.Film;
import com.congnghephanmem.filmhay.R;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.database.ChildEventListener;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.storage.FirebaseStorage;
import com.google.firebase.storage.StorageReference;
import com.google.firebase.storage.UploadTask;
import com.squareup.picasso.Picasso;

public class Add_film extends AppCompatActivity {

    FirebaseStorage storage1;
    FirebaseDatabase database1;
    String name,SDT;
    Button btn_themvideo,btn_dang;
    EditText txt_mota,txt_tenphim;
    ProgressBar progressBar;
    Uri uriaddfile_video=null;
    Uri uriaddfile_img=null;
    Intent intent;
    ImageView im;
    long id;
    String key;
    BroadcastReceiver broadcastReceiver;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_add_film);

        btn_dang =(Button) findViewById(R.id.btn_dang);
        btn_dang.setActivated(false);
        im = (ImageView) findViewById(R.id.im);
        txt_tenphim = (EditText) findViewById(R.id.tenphim_themphim);
        progressBar =(ProgressBar) findViewById(R.id.load_dang);
        progressBar.setVisibility(View.INVISIBLE);
        storage1 = FirebaseStorage.getInstance();
        database1 = FirebaseDatabase.getInstance();
        txt_mota = (EditText) findViewById(R.id.mota_video);
        btn_themvideo =(Button) findViewById(R.id.btn_themvideo);
        //getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        btn_themvideo.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Themvideo();
            }
        });
        //try {
        intent= getIntent();
            Picasso.get().load(intent.getStringExtra("img")).into(im);
            txt_mota.setText(intent.getStringExtra("detal"));
            txt_tenphim.setText(intent.getStringExtra("name"));
            key = intent.getStringExtra("key");
            id = intent.getLongExtra("id",0);
        //}catch (Exception e){

        //}



        setBtn_dang();
        set_inset_img();
        returnManagerFilm();

        broadcastReceiver = new NetworkChangeReciever();
        registerNetWorkBroadcastReciver();
    }

    private void returnManagerFilm() {
        androidx.appcompat.widget.Toolbar toolbar = findViewById(R.id.toolbar_add_film);
        setSupportActionBar(toolbar);
        getSupportActionBar().setTitle("Chi tiết phim");
        ActionBar actionBar = getSupportActionBar();
        actionBar.setDisplayHomeAsUpEnabled(true);
    }

    private void set_inset_img(){
        im.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(Intent.ACTION_GET_CONTENT);
                intent.setType("image/*");
                startActivityForResult(intent,28);
            }
        });
    }
    private void Themvideo(){


        Intent intent = new Intent(Intent.ACTION_GET_CONTENT);
        intent.setType("video/mp4");
        startActivityForResult(intent,27);
        //btn_dang.setActivated(true);

    }
    @Override
    protected void onActivityResult(int requestCode, int resultCode, @Nullable Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        switch(requestCode){
            case 27:
                if(resultCode==RESULT_OK){
                    uriaddfile_video= data.getData();
                    break;
                }
            case 28:
                if(resultCode==RESULT_OK){
                    uriaddfile_img= data.getData();
                    Picasso.get().load(uriaddfile_img).into(im);
                }
                break;

        }
    }
    protected  void setBtn_dang(){
        btn_dang.setOnClickListener(new View.OnClickListener() {

            String filename= System.currentTimeMillis()+"";
            Uri url_img;
            @Override
            public void onClick(View v) {
                if (uriaddfile_video!=null) {
                    progressBar.setVisibility(View.VISIBLE);

                    StorageReference storageReference = storage1.getReference();

                    UploadTask uploadTask = storageReference.child("Film").child(filename+"video").putFile(uriaddfile_video);
                    UploadTask uploadTask1 = storageReference.child("Film").child(filename+"img").putFile(uriaddfile_img);
                    uploadTask1.addOnSuccessListener(Add_film.this, new OnSuccessListener<UploadTask.TaskSnapshot>() {
                        @Override
                        public void onSuccess(UploadTask.TaskSnapshot taskSnapshot) {
                            Task<Uri> uri = taskSnapshot.getStorage().getDownloadUrl();
                            while(!uri.isComplete());
                            url_img = uri.getResult();
                        }
                    });
                    uploadTask.addOnSuccessListener(Add_film.this, new OnSuccessListener<UploadTask.TaskSnapshot>() {
                        @Override
                        public void onSuccess(UploadTask.TaskSnapshot taskSnapshot) {
                            //Uri url = taskSnapshot.getDownloadUrl();
                            Task<Uri> uri = taskSnapshot.getStorage().getDownloadUrl();
                            while(!uri.isComplete());
                            Uri url = uri.getResult();
                            DatabaseReference databaseReference= database1.getReference();
                            progressBar.setVisibility(View.INVISIBLE);
                            Toast.makeText(Add_film.this, "Đã đăng", Toast.LENGTH_SHORT).show();
                            Film v = new Film(System.currentTimeMillis(),url_img.toString(),txt_tenphim.getText().toString().trim(),txt_mota.getText().toString(),url.toString());

                            if (id==0){
                                databaseReference.child("Film").push().setValue(v);
                                Toast.makeText(Add_film.this,"cu",Toast.LENGTH_SHORT).show();
                            }else {
                                databaseReference.child("Film").addChildEventListener(new ChildEventListener() {
                                    @Override
                                    public void onChildAdded(@NonNull DataSnapshot snapshot, @Nullable String previousChildName) {
                                        Film f  = snapshot.getValue(Film.class);

                                        if (f.getId()==id){
                                            Film v1 = new Film(id,url_img.toString(),txt_tenphim.getText().toString().trim(),txt_mota.getText().toString(),url.toString());

                                            databaseReference.child("Film").child(snapshot.getKey()).setValue(v1);
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
                        }
                    });


                }
                else if (uriaddfile_video==null){
                    Toast.makeText(Add_film.this,"Bạn phải thêm Video trước!",Toast.LENGTH_LONG).show();
                }


            }

        });

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