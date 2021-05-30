package com.congnghephanmem.filmhay.ChangePass;

import android.content.BroadcastReceiver;
import android.content.IntentFilter;
import android.net.ConnectivityManager;
import android.os.Build;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.appcompat.app.ActionBar;
import androidx.appcompat.app.AppCompatActivity;

import com.congnghephanmem.filmhay.BroadcastReceiver.NetworkChangeReciever;
import com.congnghephanmem.filmhay.Model.GetData;
import com.congnghephanmem.filmhay.R;
import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.AuthCredential;
import com.google.firebase.auth.EmailAuthProvider;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;

public class ChangePassActivity extends AppCompatActivity {

    Button btn;
    EditText edit_gmail, edit_mk1, edit_mk2, edit_mkcu;
    BroadcastReceiver broadcastReceiver;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_change_pass);

        returnProfile();
        AnhXa();
        clickDoiPass();

        broadcastReceiver = new NetworkChangeReciever();
        registerNetWorkBroadcastReciver();
    }

    private void clickDoiPass() {
        btn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (checkRong() == 0){
                    if (checkMk() == 0){
                        doiPass();
                    }else {
                        Toast.makeText(ChangePassActivity.this, "Mật khẩu mới không trùng khớp", Toast.LENGTH_SHORT).show();
                    }
                }else {
                    Toast.makeText(ChangePassActivity.this, "Vui lòng nhập đủ thông tin", Toast.LENGTH_SHORT).show();
                }
            }
        });
    }

    private void doiPass() {
        FirebaseUser user = FirebaseAuth.getInstance().getCurrentUser();

        AuthCredential credential = EmailAuthProvider.getCredential(edit_gmail.getText().toString(), edit_mkcu.getText().toString());
        //Nhắc người dùng cung cấp lại thông tin đăng nhập của họ
        user.reauthenticate(credential).addOnCompleteListener(new OnCompleteListener<Void>() {
            @Override
            public void onComplete(@NonNull Task<Void> task) {
                if (task.isSuccessful()) {
                    user.updatePassword(edit_mk1.getText().toString()).addOnCompleteListener(new OnCompleteListener<Void>() {
                        @Override
                        public void onComplete(@NonNull Task<Void> task) {
                            if (task.isSuccessful()) {
                                Toast.makeText(ChangePassActivity.this, "Đổi mật khẩu thành công", Toast.LENGTH_SHORT).show();
                                Log.d("TAG", "Password updated");
                            } else {
                                Toast.makeText(ChangePassActivity.this, "Đổi mật khẩu thất bại", Toast.LENGTH_SHORT).show();
                                Log.d("TAG", "Error password not updated");
                            }
                        }
                    });
                } else {
                    Toast.makeText(ChangePassActivity.this, "Error auth failed", Toast.LENGTH_SHORT).show();

                    Log.d("TAG", "Error auth failed");
                }
            }
        });
    }

    private int checkMk() {
        if (edit_mk1.getText().toString().equals(edit_mk2.getText().toString())){
            return 0;
        }
        return 1;
    }

    private int checkRong(){
        if (edit_mk1.getText().toString().length() != 0 && edit_mk2.getText().toString().length() != 0 && edit_mkcu.getText().toString().length() != 0 && edit_gmail.getText().toString().length() != 0 ){
            return 0;
        }
        return 1;
    }
    private void AnhXa() {
        btn = (Button) findViewById(R.id.btn_xn_mkpw);
        edit_gmail = (EditText) findViewById(R.id.Edit_gmail_wd);
        edit_mk1 = (EditText) findViewById(R.id.Edit_pwd1);
        edit_mk2 = (EditText) findViewById(R.id.Edit_pwd2);
        edit_mkcu = (EditText) findViewById(R.id.Edit_pwdold);
        edit_gmail.setText(GetData.email);
        edit_gmail.setEnabled(false);
    }

    private void returnProfile() {
        androidx.appcompat.widget.Toolbar toolbar = findViewById(R.id.toolbar_doipass);

        setSupportActionBar(toolbar);
        ActionBar actionBar = getSupportActionBar();
        actionBar.setDisplayHomeAsUpEnabled(true);
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