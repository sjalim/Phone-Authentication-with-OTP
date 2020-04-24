package com.example.alim;

import androidx.constraintlayout.widget.ConstraintLayout;
import androidx.lifecycle.ViewModelProviders;

import android.app.LauncherActivity;
import android.os.Bundle;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.text.Editable;
import android.text.TextWatcher;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.Window;
import android.view.WindowManager;
import android.widget.EditText;

import java.util.ArrayList;
import java.util.List;

public class CultivationFragment extends Fragment {

    RecyclerView CultivationRecylerView;
    ListAdapter cultivationListAdapter;
    List<CustomListItem> mData;
    ConstraintLayout rootLayout;
    EditText searchInput;
    CharSequence search = "";



    public static CultivationFragment newInstance() {
        return new CultivationFragment();
    }

    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container,
                             @Nullable Bundle savedInstanceState) {
        Log.d("check","cultivation_fragment");
        View contentView = inflater.inflate(R.layout.cultivation_fragment, container, false);

        rootLayout = contentView.findViewById(R.id.root_layout);
        searchInput = contentView.findViewById(R.id.search_input);
        CultivationRecylerView = contentView.findViewById(R.id.cultivation_rv);
        mData = new ArrayList<>();

        searchInput.setBackgroundResource(R.drawable.search_input_style);
        rootLayout.setBackgroundColor(getResources().getColor(R.color.white));
        mData.add(new CustomListItem("Apple",R.raw.apple));
        mData.add(new CustomListItem("kola",R.raw.apple));
        mData.add(new CustomListItem("Apple",R.raw.apple));
        mData.add(new CustomListItem("Apple",R.raw.apple));
        mData.add(new CustomListItem("Apple",R.raw.apple));
        mData.add(new CustomListItem("Apple",R.raw.apple));
        mData.add(new CustomListItem("Apple",R.raw.apple));
        mData.add(new CustomListItem("Apple",R.raw.apple));


        cultivationListAdapter = new ListAdapter(this.getContext(),mData);
        CultivationRecylerView.setAdapter(cultivationListAdapter);
        CultivationRecylerView.setLayoutManager(new LinearLayoutManager(this.getContext()));

        searchInput.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence s, int start, int count, int after) {

            }

            @Override
            public void onTextChanged(CharSequence s, int start, int before, int count) {


                cultivationListAdapter.getFilter().filter(s);
                search = s;


            }

            @Override
            public void afterTextChanged(Editable s) {

            }
        });

        return contentView;
    }


}
