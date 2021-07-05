package com.congnghephanmem.filmhay.fragment;

import android.content.Intent;
import android.os.Bundle;
import android.os.CountDownTimer;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.congnghephanmem.filmhay.Adapter.RecyclerViewHistoryFilmAdapter;
import com.congnghephanmem.filmhay.ChitietPhim.XemPhim;
import com.congnghephanmem.filmhay.Interface.InterfaceHistoryFilm;
import com.congnghephanmem.filmhay.Model.GetData;
import com.congnghephanmem.filmhay.Model.History;
import com.congnghephanmem.filmhay.R;
import com.google.firebase.database.ChildEventListener;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;

import java.util.ArrayList;
import java.util.Collections;

import butterknife.BindView;
import butterknife.ButterKnife;

/**
 * A simple {@link Fragment} subclass.
 * Use the {@link lich_su#newInstance} factory method to
 * create an instance of this fragment.
 */
public class lich_su extends Fragment implements InterfaceHistoryFilm {

    // TODO: Rename parameter arguments, choose names that match
    // the fragment initialization parameters, e.g. ARG_ITEM_NUMBER
    private static final String ARG_PARAM1 = "param1";
    private static final String ARG_PARAM2 = "param2";

    // TODO: Rename and change types of parameters
    private String mParam1;
    private String mParam2;

    public lich_su() {
        // Required empty public constructor
    }

    /**
     * Use this factory method to create a new instance of
     * this fragment using the provided parameters.
     *
     * @param param1 Parameter 1.
     * @param param2 Parameter 2.
     * @return A new instance of fragment lich_su.
     */
    // TODO: Rename and change types and number of parameters
    public static lich_su newInstance(String param1, String param2) {
        lich_su fragment = new lich_su();
        Bundle args = new Bundle();
        args.putString(ARG_PARAM1, param1);
        args.putString(ARG_PARAM2, param2);
        fragment.setArguments(args);
        return fragment;
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        if (getArguments() != null) {
            mParam1 = getArguments().getString(ARG_PARAM1);
            mParam2 = getArguments().getString(ARG_PARAM2);
        }
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        return inflater.inflate(R.layout.fragment_lich_su_phim, container, false);
    }

    @BindView(R.id.recycleView_history)
    RecyclerView recyclerView;
    RecyclerViewHistoryFilmAdapter recyclerViewHistoryFilmAdapter;
    ArrayList<History> historyArrayList;
    DatabaseReference mData;
    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);

        ButterKnife.bind(this, view);
        historyArrayList = new ArrayList<>();
        //kiem tra tai khoan da duoc dang nhap chua
        if (!GetData.ten.equals(":))")){
            mData = FirebaseDatabase.getInstance().getReference();
            loadData();

        }else {
            Toast.makeText(getActivity(), "Đăng nhập để xem lịch sử phim", Toast.LENGTH_SHORT).show();
        }
    }

    private void loadData() {
        mData.child("History").child(GetData.phone).addChildEventListener(new ChildEventListener() {
            @Override
            public void onChildAdded(@NonNull DataSnapshot snapshot, @Nullable String previousChildName) {
                History history = snapshot.getValue(History.class);
                historyArrayList.add(history);
                recyclerView.setAdapter(recyclerViewHistoryFilmAdapter);
                recyclerViewHistoryFilmAdapter.notifyDataSetChanged();
                if (historyArrayList.size() == 0){
                    Toast.makeText(getActivity(), "Bạn chưa xem bộ phim nào", Toast.LENGTH_SHORT).show();
                }
                Collections.reverse(historyArrayList);
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

        //truyen du lieu vao recycler view
        recyclerViewHistoryFilmAdapter = new RecyclerViewHistoryFilmAdapter(historyArrayList, getContext(), this);
        LinearLayoutManager linearLayoutManager = new LinearLayoutManager(getActivity(), RecyclerView.VERTICAL, false);
        recyclerView.setLayoutManager(linearLayoutManager);
    }

    @Override
    public void clickFilm(int positon, String link) {
        //click vao item phim
        Intent intent = new Intent(getActivity(), XemPhim.class);
        intent.putExtra("link",link);
        startActivity(intent);
    }
}