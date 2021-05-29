package com.congnghephanmem.filmhay.manager.viewer;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;

import com.congnghephanmem.filmhay.Model.User;
import com.congnghephanmem.filmhay.R;
import com.google.firebase.database.ChildEventListener;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;

public class ChiTiet_ThanhVien extends AppCompatActivity {
    TextView tv1,tv2,tv3,tv4,tv5;
    Intent intent;
    Button btn_xoatv;
    DatabaseReference reference;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_chi_tiet__thanh_vien);
        intent= getIntent();
        reference = FirebaseDatabase.getInstance().getReference();
        tv1 = (TextView) findViewById(R.id.name_tv);
        tv2 = (TextView) findViewById(R.id.mail_tv);
        tv3 = (TextView) findViewById(R.id.phone_tv);
        tv4 = (TextView) findViewById(R.id.gender_tv);
        tv5 = (TextView) findViewById(R.id.birthday_tv);
        btn_xoatv =(Button) findViewById(R.id.btn_xoatv);
        btn_xoatv.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                reference.child("User").addChildEventListener(new ChildEventListener() {
                    @Override
                    public void onChildAdded(@NonNull DataSnapshot snapshot, @Nullable String previousChildName) {
                        User user = snapshot.getValue(User.class);
                        if (user.getEmail().equals(intent.getStringExtra("mail"))){
                            reference.child("User").child(snapshot.getKey()).removeValue();
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
        tv1.setText(tv1.getText().toString()+": "+intent.getStringExtra("name"));
        tv2.setText(tv2.getText().toString()+": "+intent.getStringExtra("mail"));
        tv3.setText(tv3.getText().toString()+": "+intent.getStringExtra("phone"));
        tv4.setText(tv4.getText().toString()+": "+intent.getStringExtra("gender"));
        tv5.setText(tv5.getText().toString()+": "+intent.getStringExtra("birthday"));

    }
}