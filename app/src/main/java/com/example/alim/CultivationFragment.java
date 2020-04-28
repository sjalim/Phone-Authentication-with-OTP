package com.example.alim;

import androidx.constraintlayout.widget.ConstraintLayout;

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

import android.widget.EditText;

import java.util.ArrayList;
import java.util.List;

public class CultivationFragment extends Fragment {

    RecyclerView CultivationRecylerView;
    ListAdapter_Cultivation cultivationListAdapterCultivation;
    List<CustomListItem_Cultivation> mData;
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
        mData.add(new CustomListItem_Cultivation("Apple",R.raw.apple));
        mData.add(new CustomListItem_Cultivation("Jack fruit",R.raw.jackfruit));
        mData.add(new CustomListItem_Cultivation("Ladies Finger",R.raw.ladies_finger));
        mData.add(new CustomListItem_Cultivation("Peanut",R.raw.peanut));
        mData.add(new CustomListItem_Cultivation("Potato",R.raw.potato));
        mData.add(new CustomListItem_Cultivation("Red Amarnath",R.raw.red_amarnath));
        mData.add(new CustomListItem_Cultivation("Tomato",R.raw.tomato));
        mData.add(new CustomListItem_Cultivation("Apple",R.raw.apple));
        mData.add(new CustomListItem_Cultivation("Jack fruit",R.raw.jackfruit));
        mData.add(new CustomListItem_Cultivation("Ladies Finger",R.raw.ladies_finger));
        mData.add(new CustomListItem_Cultivation("Peanut",R.raw.peanut));
        mData.add(new CustomListItem_Cultivation("Potato",R.raw.potato));
        mData.add(new CustomListItem_Cultivation("Red Amarnath",R.raw.red_amarnath));
        mData.add(new CustomListItem_Cultivation("Tomato",R.raw.tomato));


        cultivationListAdapterCultivation = new ListAdapter_Cultivation(this.getContext(),mData);
        CultivationRecylerView.setAdapter(cultivationListAdapterCultivation);
        CultivationRecylerView.setLayoutManager(new LinearLayoutManager(this.getContext()));

        searchInput.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence s, int start, int count, int after) {

            }

            @Override
            public void onTextChanged(CharSequence s, int start, int before, int count) {


                cultivationListAdapterCultivation.getFilter().filter(s);
                search = s;


            }

            @Override
            public void afterTextChanged(Editable s) {

            }
        });

        return contentView;
    }


}
