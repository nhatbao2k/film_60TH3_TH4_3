package com.congnghephanmem.filmhay.manager.report.InformationReport;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.appcompat.app.ActionBar;
import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.widget.ListView;

import com.congnghephanmem.filmhay.Model.Report;
import com.congnghephanmem.filmhay.R;
import com.google.firebase.database.ChildEventListener;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;

import java.util.ArrayList;

import butterknife.BindView;
import butterknife.ButterKnife;

public class InformationReportActivity extends AppCompatActivity {

    @BindView(R.id.lv_report_information)
    ListView listView;
    ArrayList<Report> reportArrayList;
    InformationReportAdapter informationReportAdapter;
    DatabaseReference mData;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_information_report);

        ButterKnife.bind(this);
        mData = FirebaseDatabase.getInstance().getReference();
        reportArrayList = new ArrayList<>();
        informationReportAdapter = new InformationReportAdapter(InformationReportActivity.this, reportArrayList, R.layout.item_report_information);
        listView.setAdapter(informationReportAdapter);
        loadData();
        returnReport();
    }

    private void returnReport() {
        androidx.appcompat.widget.Toolbar toolbar = findViewById(R.id.toolbar_report_information);
        setSupportActionBar(toolbar);
        getSupportActionBar().setTitle("Chi tiết lỗi");
        ActionBar actionBar = getSupportActionBar();
        actionBar.setDisplayHomeAsUpEnabled(true);
    }

    private void loadData() {
        Intent intent = getIntent();
        String id = intent.getStringExtra("id");
        if (!id.isEmpty()){
            mData.child("Report").child(id).addChildEventListener(new ChildEventListener() {
                @Override
                public void onChildAdded(@NonNull DataSnapshot snapshot, @Nullable String previousChildName) {
                    Report report = snapshot.getValue(Report.class);
                    reportArrayList.add(report);
                    informationReportAdapter.notifyDataSetChanged();
                }

                @Override
                public void onChildChanged(@NonNull DataSnapshot snapshot, @Nullable  String previousChildName) {

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
}