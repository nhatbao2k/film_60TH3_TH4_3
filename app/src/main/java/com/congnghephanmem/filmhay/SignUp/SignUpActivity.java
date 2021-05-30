package com.congnghephanmem.filmhay.SignUp;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;

import android.content.BroadcastReceiver;
import android.content.Intent;
import android.content.IntentFilter;
import android.net.ConnectivityManager;
import android.os.Build;
import android.os.Bundle;
import android.os.CountDownTimer;
import android.util.Log;
import android.util.Patterns;
import android.view.View;
import android.view.Window;
import android.view.WindowManager;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.RadioButton;
import android.widget.RadioGroup;
import android.widget.Toast;

import com.congnghephanmem.filmhay.BroadcastReceiver.NetworkChangeReciever;
import com.congnghephanmem.filmhay.DatePickerFragment;
import com.congnghephanmem.filmhay.Model.User;
import com.congnghephanmem.filmhay.OnSwipeTouchListener;
import com.congnghephanmem.filmhay.R;
import com.congnghephanmem.filmhay.SignIn.SignInActivity;
import com.congnghephanmem.filmhay.manager.employeer.InformationEmployeer.InformationEmployeerActivity;
import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.AuthResult;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.database.ChildEventListener;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;


import butterknife.BindView;
import butterknife.ButterKnife;

public class SignUpActivity extends AppCompatActivity implements View.OnFocusChangeListener {

    @BindView(R.id.imageView2)
    ImageView imageView;
    int count = 0;
    @BindView(R.id.Edit_date)
    EditText eNgaySinhdk;
    private FirebaseAuth mAuth;
    @BindView(R.id.btn_sign_up)
    Button btn_sign_up;
    @BindView(R.id.Edit_email_sign_up)
    EditText editMail;
    @BindView(R.id.Edit_phone_sign_up)
    EditText editPhone;
    @BindView(R.id.Edit_pass1)
    EditText editPass1;
    @BindView(R.id.Edit_pass2)
    EditText editPass2;
    @BindView(R.id.gt)
    RadioGroup radioGroup;
    @BindView(R.id.Edit_name_sign_up)
    EditText editName;
    @BindView(R.id.btn_sign_in)
    Button btnSignIn;
    User user;
    DatabaseReference mData;
    String mail, pass;
    BroadcastReceiver broadcastReceiver;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        requestWindowFeature(Window.FEATURE_NO_TITLE);
        getWindow().setFlags(WindowManager.LayoutParams.FLAG_FULLSCREEN, WindowManager.LayoutParams.FLAG_FULLSCREEN);
        setContentView(R.layout.activity_sign_up);

        ButterKnife.bind(this);


        imageView.setOnTouchListener(new OnSwipeTouchListener(getApplicationContext()){
            public void onSwipeTop() {
            }

            public void onSwipeRight() {
                if (count == 0) {
                    imageView.setImageResource(R.drawable.anh2);
                    count = 1;
                } else {
                    imageView.setImageResource(R.drawable.anh1);
                    count = 0;
                }
            }

            public void onSwipeLeft() {
                if (count == 0) {
                    imageView.setImageResource(R.drawable.anh2);
                    count = 1;
                } else {
                    imageView.setImageResource(R.drawable.anh1);
                    count = 0 ;
                }
            }

            public void onSwipeBottom() {
            }
        });

        //bam vao hien date picker
        eNgaySinhdk.setOnFocusChangeListener(this);
        mAuth = FirebaseAuth.getInstance();
        //đăng ký mail
        mData = FirebaseDatabase.getInstance().getReference();
        btn_sign_up.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                checkEmpty();
            }
        });

        btnSignIn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                startActivity(new Intent(SignUpActivity.this, SignInActivity.class));
                finish();
            }
        });

        broadcastReceiver = new NetworkChangeReciever();
        registerNetWorkBroadcastReciver();
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

    private void checkEmpty(){
        if (editMail.getText().toString().isEmpty()){
            editMail.setError("Email chưa được nhập");
            return;
        }
        if (editPhone.getText().toString().isEmpty()){
            editPhone.setError("Số điện thoại không được bỏ trống");
            return;
        }
        if (editPass1.getText().toString().isEmpty()){
            editPass1.setError("Mật khẩu chưa được nhập");
            return;
        }
        if (editPass2.getText().toString().isEmpty()){
            editPass2.setError("Mật khẩu chưa được nhập");
            return;
        }
        if (!editPass1.getText().toString().equals(editPass2.getText().toString())){
            editPass2.setError("Mật khẩu không trùng khớp");
            return;
        }
        if (editPhone.getText().toString().length() != 10){
            editPhone.setError("Số điện thoại phải đủ 10 số");
            return;
        }
        if (editPass1.getText().toString().length() < 6){
            editPass1.setError("Mật khẩu phải từ 6 ký tự trở lên");
            return;
        }
        if (!Patterns.EMAIL_ADDRESS.matcher(editMail.getText().toString()).matches()){
            editMail.setError("Không đúng đinh dạng email");
            return;
        }
        if (editName.getText().toString().isEmpty()){
            editName.setError("Tên không được bỏ trống");
            return;
        }
        int selected = radioGroup.getCheckedRadioButtonId();
        RadioButton radioButtonGioiTinh = (RadioButton) findViewById(selected);
        String gt = radioButtonGioiTinh.getText().toString();
        mail = editMail.getText().toString();
        pass = editPass1.getText().toString();
        user = new User(editMail.getText().toString(), editPhone.getText().toString(),editName.getText().toString(),gt,eNgaySinhdk.getText().toString(),"viewer", "");
        checkPhone();
        new CountDownTimer(1000, 1000) {
            @Override
            public void onTick(long millisUntilFinished) {

            }

            @Override
            public void onFinish() {
                switch (check){
                    case 1:
                        Toast.makeText(SignUpActivity.this, "Số điện thoại này đã được sử dụng", Toast.LENGTH_SHORT).show();
                        check = 0;
                        break;
                    case 0:
                        createAccount();
                        break;
                }
            }
        }.start();
    }

    public int check = 0;
    private void checkPhone(){
        mData.child("User").addChildEventListener(new ChildEventListener() {
            @Override
            public void onChildAdded(@NonNull DataSnapshot snapshot, String previousChildName) {
                User user = snapshot.getValue(User.class);
                if (editPhone.getText().toString().equals(user.getPhone())){
                    check = 1;
                }
            }

            @Override
            public void onChildChanged(@NonNull DataSnapshot snapshot, String previousChildName) {

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
    private void createAccount() {
        mAuth.createUserWithEmailAndPassword(mail, pass)
                .addOnCompleteListener(this, new OnCompleteListener<AuthResult>() {
                    @Override
                    public void onComplete(@NonNull Task<AuthResult> task) {
                        if (task.isSuccessful()) {
                            // Sign in success, update UI with the signed-in user's information
                            Log.d("TAG", "createUserWithEmail:success");
                            FirebaseUser user1 = mAuth.getCurrentUser();
                            mData.child("User").child(user.getPhone()).setValue(user);
                            Toast.makeText(SignUpActivity.this, "Đăng ký thành công", Toast.LENGTH_SHORT).show();

                        } else {
                            // If sign in fails, display a message to the user.
                            Log.w("TAG", "createUserWithEmail:failure", task.getException());
                            Toast.makeText(SignUpActivity.this, "Authentication failed.",
                                    Toast.LENGTH_SHORT).show();

                        }
                    }
                });
    }

    @Override
    public void onFocusChange(View v, boolean hasFocus) {
        int id = v.getId();
        switch (id){
            case R.id.Edit_date:
                if (hasFocus){
                    DatePickerFragment datePickerFragment = new DatePickerFragment();
                    datePickerFragment.show(getSupportFragmentManager(),"Ngày sinh");
                }
                break;
        }
    }


}