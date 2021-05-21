package com.congnghephanmem.filmhay.SignIn;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.appcompat.app.ActionBar;
import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
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
import android.widget.ProgressBar;
import android.widget.TextView;
import android.widget.Toast;
import android.widget.Toolbar;

import com.congnghephanmem.filmhay.ForgotPassActivity;
import com.congnghephanmem.filmhay.MainActivity;
import com.congnghephanmem.filmhay.Model.GetData;
import com.congnghephanmem.filmhay.Model.User;
import com.congnghephanmem.filmhay.OnSwipeTouchListener;
import com.congnghephanmem.filmhay.R;
import com.congnghephanmem.filmhay.SignUp.SignUpActivity;
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
import com.google.firebase.database.ValueEventListener;

import butterknife.BindView;
import butterknife.ButterKnife;

public class SignInActivity extends AppCompatActivity {

    @BindView(R.id.imageView)
    ImageView imageView;
    int count = 0;
    @BindView(R.id.btn_sign_up)
    Button btnSignUp;
    @BindView(R.id.tv_forgot_pass)
    TextView tv_fpass;
    private FirebaseAuth mAuth;
    @BindView(R.id.Edit_email)
    EditText edit_email;
    @BindView(R.id.Edit_pass)
    EditText edit_pass;
    @BindView(R.id.btn_sign_in)
    Button btnSignIn;
    User user;
    DatabaseReference mData;
    @BindView(R.id.progress_sign_in)
    ProgressBar progressBar;
    boolean check = false;
    String mail, pass;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        requestWindowFeature(Window.FEATURE_NO_TITLE);
        getWindow().setFlags(WindowManager.LayoutParams.FLAG_FULLSCREEN, WindowManager.LayoutParams.FLAG_FULLSCREEN);
        setContentView(R.layout.activity_sign_in);

        ActionBar actionBar = getSupportActionBar();
        actionBar.hide();
        ButterKnife.bind(this);

        //keo 2 ben man hinh de doi anh
        imageView.setOnTouchListener(new OnSwipeTouchListener(getApplicationContext()){
            public void onSwipeTop() {
            }

            public void onSwipeRight() {
                if (count == 0) {
                    imageView.setImageResource(R.drawable.anh1);
                    count = 1;
                } else {
                    imageView.setImageResource(R.drawable.anh2);
                    count = 0;
                }
            }

            public void onSwipeLeft() {
                if (count == 0) {
                    imageView.setImageResource(R.drawable.anh1);
                    count = 1;
                } else {
                    imageView.setImageResource(R.drawable.anh2);
                    count = 0 ;
                }
            }

            public void onSwipeBottom() {
            }
        });

        //dang ky
        btnSignUp.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                startActivity(new Intent(SignInActivity.this, SignUpActivity.class));
            }
        });
        //lấy lại mật khẩu
        tv_fpass.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                startActivity(new Intent(SignInActivity.this, ForgotPassActivity.class));
            }
        });
        mAuth = FirebaseAuth.getInstance();
        mData = FirebaseDatabase.getInstance().getReference();
        //đăng nhập
        btnSignIn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                progressBar.setVisibility(View.VISIBLE);
                new CountDownTimer(2000, 1000) {
                    @Override
                    public void onTick(long millisUntilFinished) {

                    }

                    @Override
                    public void onFinish() {
                        checkSignIn();
                        progressBar.setVisibility(View.GONE);
                    }
                }.start();
            }
        });

        //vào form lấy mật khẩu
        tv_fpass.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                startActivity(new Intent(SignInActivity.this, ForgotPassActivity.class));
            }
        });
    }

    private void checkSignIn(){
        if (edit_email.getText().toString().isEmpty()){
            edit_email.setError("Email chưa được nhập");
            return;
        }
        if (!Patterns.EMAIL_ADDRESS.matcher(edit_email.getText().toString()).matches()){
            edit_email.setError("Không phải email");
            return;
        }
        if (edit_pass.getText().toString().isEmpty()){
            edit_pass.setError("Mật khẩu chưa được nhập");
            return;
        }
        mail = edit_email.getText().toString().trim();
        pass = edit_pass.getText().toString().trim();
        SignIn();
    }

    private void checkRole(){
        mData.child("User").addChildEventListener(new ChildEventListener() {
            @Override
            public void onChildAdded(@NonNull DataSnapshot snapshot, @Nullable String previousChildName) {
                User user1 = snapshot.getValue(User.class);
                if (edit_email.getText().toString().equals(user1.getEmail())){
                    if (user1.getRole().equals("viewer")){
                        check = true;
                        GetData.ten = user1.getName();
                        GetData.email = user1.getEmail();
                        GetData.avatar = user1.getAvatar();
                    }
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

    private void SignIn(){
        mAuth.signInWithEmailAndPassword(mail, pass)
                .addOnCompleteListener(this, new OnCompleteListener<AuthResult>() {
                    @Override
                    public void onComplete(@NonNull Task<AuthResult> task) {
                        if (task.isSuccessful()) {
                            // Sign in success, update UI with the signed-in user's information
                            Log.d("TAG", "signInWithEmail:success");
                            FirebaseUser user = mAuth.getCurrentUser();

                            checkRole();
                            //nếu check bằng true thì vào vs giao diện người xem, false thì dành cho admin
                            new CountDownTimer(1000, 1000) {
                                @Override
                                public void onTick(long millisUntilFinished) {

                                }

                                @Override
                                public void onFinish() {
                                    Toast.makeText(SignInActivity.this, check+"", Toast.LENGTH_SHORT).show();
                                    if (check == true){
                                        startActivity(new Intent(SignInActivity.this, MainActivity.class));
                                    }
                                }
                            }.start();

                        } else {
                            // If sign in fails, display a message to the user.
                            Log.w("TAG", "signInWithEmail:failure", task.getException());
                            Toast.makeText(SignInActivity.this, "Authentication failed.", Toast.LENGTH_SHORT).show();

                        }
                    }
                });
    }
}