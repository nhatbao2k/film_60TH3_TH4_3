package com.congnghephanmem.filmhay.manager;

import androidx.appcompat.app.AppCompatActivity;


import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;

import com.congnghephanmem.filmhay.Model.GetData;
import com.congnghephanmem.filmhay.manager.employeer.ManagerEmployerActivity;
import com.congnghephanmem.filmhay.manager.report.ReportActivity;
import com.congnghephanmem.filmhay.manager.viewer.QuanLyThanhVien;
import com.congnghephanmem.filmhay.R;
import com.congnghephanmem.filmhay.SignIn.SignInActivity;
import com.congnghephanmem.filmhay.manager.Film.ManagerFilmActivity;

import butterknife.BindView;
import butterknife.ButterKnife;

public class ManagerActivity extends AppCompatActivity {

    @BindView(R.id.btn_manager_film)
    Button btnManagerFilm;
    @BindView(R.id.btn_manager_employeer)
    Button btnManagerEmployeer;
    @BindView(R.id.btn_manager_report)
    Button btnManagerReport;
    @BindView(R.id.btn_manager_viewer)
    Button btnManagerViewer;
    @BindView(R.id.btn_manager_logout)
    Button btnLogout;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_manager);

        ButterKnife.bind(this);
        setButtonHidde();
        btnManagerFilm.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                startActivity(new Intent(ManagerActivity.this, ManagerFilmActivity.class));
            }
        });
        btnManagerViewer.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                startActivity(new Intent(ManagerActivity.this, QuanLyThanhVien.class));
            }
        });
        btnManagerEmployeer.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                startActivity(new Intent(ManagerActivity.this, ManagerEmployerActivity.class));
            }
        });
        btnManagerReport.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                startActivity(new Intent(ManagerActivity.this, ReportActivity.class));
            }
        });
    }

    private void setButtonHidde() {
        if (GetData.role.equals("admin")){
            btnManagerReport.setVisibility(View.GONE);
        }else if (GetData.role.equals("employeer")){
            btnManagerEmployeer.setVisibility(View.GONE);
        }
    }

    public void Logout(View view){
        startActivity(new Intent(ManagerActivity.this, SignInActivity.class));
        finish();
    }
}