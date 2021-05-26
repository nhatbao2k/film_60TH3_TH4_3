package com.congnghephanmem.filmhay.ChitietPhim;

import android.app.Dialog;
import android.content.BroadcastReceiver;
import android.content.Intent;
import android.content.IntentFilter;
import android.graphics.Bitmap;
import android.graphics.drawable.BitmapDrawable;
import android.media.MediaPlayer;
import android.net.ConnectivityManager;
import android.net.Uri;
import android.os.Build;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.CheckBox;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.ListView;
import android.widget.RelativeLayout;
import android.widget.TextView;
import android.widget.Toast;
import android.widget.VideoView;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;

import com.congnghephanmem.filmhay.BroadcastReceiver.NetworkChangeReciever;
import com.congnghephanmem.filmhay.Model.GetData;
import com.congnghephanmem.filmhay.Model.History;
import com.congnghephanmem.filmhay.Model.Report;
import com.congnghephanmem.filmhay.Model.binhluan;
import com.congnghephanmem.filmhay.R;
import com.google.android.gms.tasks.OnFailureListener;
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

import java.io.ByteArrayOutputStream;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;
import java.util.Locale;

import butterknife.BindView;
import butterknife.ButterKnife;

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
    FirebaseStorage firebaseStorage = FirebaseStorage.getInstance();
    Button btn_report;
    String id;
    BroadcastReceiver broadcastReceiver;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_chitiet_phim);
        intent = getIntent();
        reference = FirebaseDatabase.getInstance().getReference();
        link = intent.getStringExtra("link");
        id = String.valueOf(intent.getIntExtra("id",0));
        img = (ImageView) findViewById(R.id.img_chitietphim);
        chitietphim  =(RelativeLayout) findViewById(R.id.chitietphim);
        Picasso.get().load(intent.getIntExtra("img",0)).into(img);
        tenphim_chitietphim = (TextView) findViewById(R.id.tenphim_chitietphim);
        tenphim_chitietphim.setText(intent.getStringExtra("name"));
        mota_chitietphim = (TextView) findViewById(R.id.mota_chitietphim);
        mota_chitietphim.setText(intent.getStringExtra("detal"));
        listView =(ListView) findViewById(R.id.list_item_bl);
        arrayList = new ArrayList<>();
        xemtrailer();
        xemphim();
        setbtn_cmt();
        get_data_cmt();
        report();

        broadcastReceiver = new NetworkChangeReciever();
        registerNetWorkBroadcastReciver();
    }

    private void report() {
        btn_report = (Button) findViewById(R.id.btn_report);
        btn_report.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (GetData.ten.equals(":))")){
                    Toast.makeText(ChitietPhim.this, "Đăng nhập để thực hiện tính năng này", Toast.LENGTH_SHORT).show();
                }else {
                    showDialogReport();
                }
            }
        });
    }

    private void showDialogReport(){
        Dialog dialog = new Dialog(this);
        dialog.setContentView(R.layout.dialog_report);
        dialog.show();

        Button btn_ok = (Button) dialog.findViewById(R.id.btn_ok_report);
        Button btn_cancel = (Button) dialog.findViewById(R.id.btn_report_cancel);
        CheckBox checkBoxFilmError = (CheckBox) dialog.findViewById(R.id.checkbox_film_die);
        CheckBox checkBoxFilmQuality = (CheckBox) dialog.findViewById(R.id.checkbox_film_quality);
        CheckBox checkBoxFilmDifference = (CheckBox) dialog.findViewById(R.id.checkbox_different);
        EditText editText = (EditText) dialog.findViewById(R.id.Edit_comment_difference);

        checkBoxFilmDifference.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (checkBoxFilmDifference.isChecked()){
                    editText.setVisibility(View.VISIBLE);
                }
            }
        });

        btn_ok.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                String nameReport = "";
                if (checkBoxFilmError.isChecked()){
                    nameReport = checkBoxFilmError.getText().toString();
                }
                if (checkBoxFilmQuality.isChecked()){
                    nameReport += "/" +checkBoxFilmQuality.getText().toString();
                }
                Report report = new Report(id, nameReport, editText.getText().toString());
                reference.child("Report").child(report.getId()).push().setValue(report, new DatabaseReference.CompletionListener() {
                    @Override
                    public void onComplete(@Nullable DatabaseError error, @NonNull DatabaseReference ref) {
                        if (error == null){
                            Toast.makeText(ChitietPhim.this, "Báo cáo thành công", Toast.LENGTH_SHORT).show();
                            dialog.dismiss();
                        }
                    }
                });
            }
        });

        btn_cancel.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                dialog.dismiss();
            }
        });
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
                //Ktra neu dang nhap thi thuc hien luu ls phim
                if (!GetData.ten.equals(":))")){
                    Calendar calendar = Calendar.getInstance();
                    final StorageReference storageReference = firebaseStorage.getReferenceFromUrl("gs://appfilm-b6b73.appspot.com/");
                    StorageReference mountainRef = storageReference.child("image" + calendar.getTimeInMillis() + ".png");
                    // Get the data from an ImageView as bytes
                    img.setDrawingCacheEnabled(true);
                    img.buildDrawingCache();
                    Bitmap bitmap = ((BitmapDrawable) img.getDrawable()).getBitmap();
                    ByteArrayOutputStream baos = new ByteArrayOutputStream();
                    bitmap.compress(Bitmap.CompressFormat.PNG, 100, baos);
                    byte[] data = baos.toByteArray();

                    UploadTask uploadTask = mountainRef.putBytes(data);
                    uploadTask.addOnFailureListener(new OnFailureListener() {
                        @Override
                        public void onFailure(@NonNull Exception exception) {
                            // Handle unsuccessful uploads
                        }
                    }).addOnSuccessListener(new OnSuccessListener<UploadTask.TaskSnapshot>() {
                        @Override
                        public void onSuccess(UploadTask.TaskSnapshot taskSnapshot) {
                            // taskSnapshot.getMetadata() contains file metadata such as size, content-type, etc.
                            // ...
                            if (taskSnapshot.getMetadata() != null){
                                if (taskSnapshot.getMetadata().getReference() != null){
                                    Task<Uri> result = taskSnapshot.getStorage().getDownloadUrl();
                                    result.addOnSuccessListener(new OnSuccessListener<Uri>() {
                                        @Override
                                        public void onSuccess(Uri uri) {
                                            String imageUrl = uri.toString();
                                            SimpleDateFormat simpleDateFormat = new SimpleDateFormat("dd-MM-yyyy _ HH:mm:ss", Locale.getDefault());
                                            String time = simpleDateFormat.format(new Date());
                                            History history = new History(imageUrl, tenphim_chitietphim.getText().toString(), mota_chitietphim.getText().toString(), link, time);
                                            // update du lieu
                                            reference.child("History").child(GetData.phone).push().setValue(history);
                                        }
                                    });
                                }
                            }
                        }
                    });
                }
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

                binhluan bl;
                if (GetData.ten.equals(":))")){
//                   bl = new binhluan("Người dùng",txt_comt.getText().toString().trim(),System.currentTimeMillis());
                    Toast.makeText(ChitietPhim.this, "Đăng nhập để thực hiện bình luận", Toast.LENGTH_SHORT).show();
                }else{
                    bl = new binhluan(GetData.ten,txt_comt.getText().toString().trim(),System.currentTimeMillis());
                    reference.child("comment").child(intent.getIntExtra("id",0)+"").push().setValue(bl);
                }

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

    protected void registerNetWorkBroadcastReciver(){
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