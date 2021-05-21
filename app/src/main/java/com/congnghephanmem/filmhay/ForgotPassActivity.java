package com.congnghephanmem.filmhay;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;
import android.widget.Toolbar;

import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.FirebaseAuth;

import butterknife.BindView;
import butterknife.ButterKnife;

public class ForgotPassActivity extends AppCompatActivity {

    @BindView(R.id.btn_get_pass_forgot)
    Button btn;
    @BindView(R.id.Edit_email_forgot_pass)
    EditText editMail;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_forgot_pass);
        returnSignIn();
        ButterKnife.bind(this);

        btn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (editMail.getText().toString().trim().isEmpty()){
                    editMail.setError("Chưa nhập email");
                    return;
                }
                FirebaseAuth.getInstance().sendPasswordResetEmail(editMail.getText().toString()).addOnCompleteListener(new OnCompleteListener<Void>() {
                    @Override
                    public void onComplete(@NonNull Task<Void> task) {
                        if (task.isSuccessful()){
                            Toast.makeText(ForgotPassActivity.this, "Vào email của bạn để lấy mật khẩu",Toast.LENGTH_SHORT).show();
                        }else {
                            Toast.makeText(ForgotPassActivity.this, "Email chưa được đăng ký",Toast.LENGTH_SHORT).show();
                        }
                    }
                });
            }
        });
    }

    private void returnSignIn() {
        androidx.appcompat.widget.Toolbar toolbar = findViewById(R.id.toolbar_fpass);

        setSupportActionBar(toolbar);
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);
    }
}