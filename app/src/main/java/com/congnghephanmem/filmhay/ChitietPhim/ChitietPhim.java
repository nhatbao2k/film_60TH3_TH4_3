package com.congnghephanmem.filmhay.ChitietPhim;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;

import android.app.Dialog;
import android.content.Intent;
import android.media.MediaPlayer;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.ListView;
import android.widget.RelativeLayout;
import android.widget.TextView;
import android.widget.Toast;
import android.widget.VideoView;

import com.congnghephanmem.filmhay.Model.binhluan;
import com.congnghephanmem.filmhay.R;
import com.google.firebase.database.ChildEventListener;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.squareup.picasso.Picasso;

import java.util.ArrayList;

import jp.wasabeef.blurry.Blurry;

public class ChitietPhim extends AppCompatActivity {
    Intent intent;
    ImageView img;
    TextView tenphim_chitietphim,mota_chitietphim;
    RelativeLayout chitietphim;
    String link;
    Button btn_xemtrailer_chitietphim,btn_xemphim_chitietphim,btn_cmt;
    EditText txt_comt;
    DatabaseReference reference;
    ListView listView;
    Adapter_cmt adapterCmt;
    ArrayList<binhluan> arrayList;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_chitiet_phim);
        intent = getIntent();
        reference = FirebaseDatabase.getInstance().getReference();
        link = intent.getStringExtra("link");
        img = (ImageView) findViewById(R.id.img_chitietphim);
        chitietphim  =(RelativeLayout) findViewById(R.id.chitietphim);
        Picasso.get().load(intent.getIntExtra("img",0)).into(img);
        tenphim_chitietphim = (TextView) findViewById(R.id.tenphim_chitietphim);
        tenphim_chitietphim.setText(intent.getStringExtra("name"));
        mota_chitietphim = (TextView) findViewById(R.id.mota_chitietphim);
        mota_chitietphim.setText(intent.getStringExtra("detal"));
        //Blurry.with(this).radius(25).sampling(2).onto(chitietphim);
        listView =(ListView) findViewById(R.id.list_item_bl);
       arrayList = new ArrayList<>();

        xemtrailer();
        xemphim();
        setbtn_cmt();
        get_data_cmt();

    }
    void xemtrailer(){
        btn_xemtrailer_chitietphim = (Button) findViewById(R.id.btn_xemtrailer_chitietphim);
        btn_xemtrailer_chitietphim.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Dialog dialog= new Dialog(ChitietPhim.this);
                dialog.setContentView(R.layout.xemtrailer);
                VideoView videoView_trailer =  (VideoView) dialog.findViewById(R.id.trailer);
                videoView_trailer.setVideoPath(link);
                videoView_trailer.setOnPreparedListener(new MediaPlayer.OnPreparedListener() {
                    @Override
                    public void onPrepared(MediaPlayer mp) {
                        mp.start();
                    }
                });
                dialog.show();
            }
        });
    }
    void xemphim(){
        btn_xemphim_chitietphim = (Button) findViewById(R.id.btn_xemphim_chitietphim);
        btn_xemphim_chitietphim.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent= new Intent(ChitietPhim.this,XemPhim.class);
                intent.putExtra("link",link);
                startActivity(intent);
            }
        });
    }
    void setbtn_cmt(){
        txt_comt = (EditText) findViewById(R.id.txt_comt);
        btn_cmt = (Button) findViewById(R.id.btn_cmt);
        btn_cmt.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (txt_comt.getText().toString().trim().matches("")){
                    Toast.makeText(ChitietPhim.this,"Bình luận trống",Toast.LENGTH_SHORT).show();
                       return;
                }
                binhluan bl = new binhluan("Người dùng",txt_comt.getText().toString().trim(),System.currentTimeMillis());
                reference.child("comment").child(intent.getIntExtra("id",0)+"").push().setValue(bl);
                txt_comt.setText("");
            }
        });
    }
    void get_data_cmt(){

        reference.child("comment").child(intent.getIntExtra("id",0)+"").addChildEventListener(new ChildEventListener() {
            @Override
            public void onChildAdded(@NonNull DataSnapshot snapshot, @Nullable String previousChildName) {
                binhluan bl = snapshot.getValue(binhluan.class);
                arrayList.add(bl);
                adapterCmt= new Adapter_cmt(ChitietPhim.this,arrayList,R.layout.item_cmt);
                listView.setAdapter(adapterCmt);
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