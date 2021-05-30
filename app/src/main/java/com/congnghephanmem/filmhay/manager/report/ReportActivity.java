package com.congnghephanmem.filmhay.manager.report;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ListView;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.appcompat.app.ActionBar;
import androidx.appcompat.app.AppCompatActivity;

import com.congnghephanmem.filmhay.R;
import com.congnghephanmem.filmhay.manager.report.InformationReport.InformationReportActivity;
import com.congnghephanmem.filmhay.manager.report.InformationReport.InformationReportAdapter;
import com.google.firebase.database.ChildEventListener;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;

import java.util.ArrayList;

import butterknife.BindView;
import butterknife.ButterKnife;

public class ReportActivity extends AppCompatActivity {

    @BindView(R.id.lv_report)
    ListView listView;
    ArrayList<String> stringArrayList;
    ReportAdapter reportAdapter;
    DatabaseReference mData;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.report_activity);

        ButterKnife.bind(this);
        mData = FirebaseDatabase.getInstance().getReference();
        stringArrayList = new ArrayList<>();
        loadData();
        reportAdapter = new ReportAdapter(ReportActivity.this, R.layout.item_manager_report, stringArrayList);
        listView.setAdapter(reportAdapter);

        listView.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                Intent intent = new Intent(ReportActivity.this, InformationReportActivity.class);
                intent.putExtra("id", stringArrayList.get(position));
                startActivity(intent);
            }
        });

        returnManager();
    }

    private void returnManager() {
        androidx.appcompat.widget.Toolbar toolbar = findViewById(R.id.toolbar_report);
        setSupportActionBar(toolbar);
        getSupportActionBar().setTitle("Báo lỗi");
        ActionBar actionBar = getSupportActionBar();
        actionBar.setDisplayHomeAsUpEnabled(true);
    }

    private void loadData() {
        mData.child("Report").addChildEventListener(new ChildEventListener() {
            @Override
            public void onChildAdded(@NonNull DataSnapshot snapshot, @Nullable String previousChildName) {
                stringArrayList.add(snapshot.getKey());
                reportAdapter.notifyDataSetChanged();
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
