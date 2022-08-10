package com.mikhlasnr.diaryapp10119022.view.diary;
/*
    nim                 : 10119022
    nama                : Muhammad Ikhlas Naufalsyah Ranau
    kelas               : IF-1
*/
import android.content.Intent;
import android.database.Cursor;
import android.os.Bundle;

import androidx.appcompat.app.ActionBar;
import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import com.google.android.material.floatingactionbutton.FloatingActionButton;
import com.mikhlasnr.diaryapp10119022.R;
import com.mikhlasnr.diaryapp10119022.helper.DatabaseHelper;
import com.mikhlasnr.diaryapp10119022.models.DiaryModel;

import java.util.ArrayList;
import java.util.List;

public class DiaryFragment extends Fragment {
    RecyclerView recyclerView;
    FloatingActionButton add_button;
    ImageView empty_imageView;
    TextView noData;

    DatabaseHelper db;
    List<DiaryModel> diaryList;
    DiaryAdapter diaryAdapter;


    public DiaryFragment() {
        // Required empty public constructor
    }

    public static DiaryFragment newInstance(String param1, String param2) {
        DiaryFragment fragment = new DiaryFragment();
        Bundle args = new Bundle();

        fragment.setArguments(args);
        return fragment;
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {

        // Inflate the layout for this fragment
        View view = inflater.inflate(R.layout.fragment_diary, container, false);
        recyclerView = view.findViewById(R.id.recyclerView);
        add_button = view.findViewById(R.id.fab);
        empty_imageView = view.findViewById(R.id.empty_imageview);
        noData = view.findViewById(R.id.no_data);

        add_button.setOnClickListener(view1 -> {
            Intent intent = new Intent(getActivity(), AddDiaryActivity.class);
            startActivity(intent);
        });

        db = new DatabaseHelper(getActivity());
        diaryList = new ArrayList<>();

        storeDataInListModel();

        diaryAdapter = new DiaryAdapter(getActivity(), diaryList);
        recyclerView.setAdapter(diaryAdapter);
        recyclerView.setLayoutManager(new LinearLayoutManager(getActivity()));

        return view;
    }

    public void storeDataInListModel(){
        Cursor cursor = db.getAllData();
        if(cursor.getCount() == 0){
            empty_imageView.setVisibility(View.VISIBLE);
            noData.setVisibility(View.VISIBLE);
        }else{
            while(cursor.moveToNext()){

                DiaryModel diary = new DiaryModel(
                    cursor.getString(0),
                    cursor.getString(1),
                    cursor.getString(2),
                    cursor.getString(3),
                    cursor.getString(4)
                );

                diaryList.add(diary);
            }
            empty_imageView.setVisibility(View.GONE);
            noData.setVisibility(View.GONE);
        }
    }

    @Override
    public void onDestroyView() {
        super.onDestroyView();
    }
}