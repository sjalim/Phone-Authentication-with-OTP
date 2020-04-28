package com.example.alim;


import android.content.Context;
import android.os.Bundle;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.animation.AnimationUtils;
import android.widget.Filter;
import android.widget.Filterable;
import android.widget.ImageView;
import android.widget.RelativeLayout;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentActivity;
import androidx.fragment.app.FragmentManager;
import androidx.fragment.app.FragmentTransaction;
import androidx.recyclerview.widget.RecyclerView;

import java.util.ArrayList;
import java.util.List;

public class ListAdapter_Market extends RecyclerView.Adapter<ListAdapter_Market.ListViewHolder> implements Filterable {

    Context mContext;
    List<CustomListItem_Market> mData;
    List<CustomListItem_Market> mDataFiltered;

    public ListAdapter_Market(Context mContext, List<CustomListItem_Market> mData) {
        this.mContext = mContext;
        this.mData = mData;
        this.mDataFiltered = mData;
    }

    @Override
    public Filter getFilter() {


        return new Filter() {
            @Override
            protected FilterResults performFiltering(CharSequence constraint) {

                String Key = constraint.toString();
                if (Key.isEmpty()) {

                    mDataFiltered = mData ;

                }
                else {
                    List<CustomListItem_Market> lstFiltered = new ArrayList<>();
                    for (CustomListItem_Market row : mData) {

                        if (row.getTitle().toLowerCase().contains(Key.toLowerCase())){
                            lstFiltered.add(row);
                        }

                    }

                    mDataFiltered = lstFiltered;

                }


                FilterResults filterResults = new FilterResults();
                filterResults.values= mDataFiltered;
                return filterResults;

            }

            @Override
            protected void publishResults(CharSequence constraint, FilterResults results) {


                mDataFiltered = (List<CustomListItem_Market>) results.values;
                notifyDataSetChanged();

            }
        };



    }

    @NonNull
    @Override
    public ListAdapter_Market.ListViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {




        View layout;
        layout = LayoutInflater.from(mContext).inflate(R.layout.customlist_item_cultivation,parent,false);
        return new ListViewHolder(layout);


    }

    @Override
    public void onBindViewHolder(@NonNull ListAdapter_Market.ListViewHolder holder, int position) {
        holder.imageView.setAnimation(AnimationUtils.loadAnimation(mContext,R.anim.fade_transition_animation));
        holder.itemContainer.setAnimation(AnimationUtils.loadAnimation(mContext,R.anim.fade_scale_animation));


        holder.title.setText(mDataFiltered.get(position).getTitle());
//        holder.imageView.setImageResource(mDataFiltered.get(position).getUserPhoto());

    }

    @Override
    public int getItemCount() {
        return  mDataFiltered.size();
    }
    public class ListViewHolder extends RecyclerView.ViewHolder{

        TextView title ;
        ImageView imageView;
        RelativeLayout itemContainer;

        public ListViewHolder(@NonNull View itemView) {
            super(itemView);
            itemView.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {


                    Fragment itemFragment =  CultivationItemDetails.newInstance("","");
                    if(itemFragment!=null) {

                        FragmentManager fragmentManager = ((FragmentActivity)mContext).getSupportFragmentManager();
                        FragmentTransaction fragmentTransaction = fragmentManager.beginTransaction();

                        Bundle args = new Bundle();

                        args.putString("pass", title.getText().toString());
                        itemFragment.setArguments(args);

                        fragmentTransaction.setCustomAnimations(R.anim.enter_from_right, R.anim.exit_to_right, R.anim.enter_from_right, R.anim.exit_to_right);
                        fragmentTransaction.replace(R.id.container, itemFragment);
                        fragmentTransaction.addToBackStack(null);
                        fragmentTransaction.commit();
                    }
                    else
                    {
                        Log.d("error","null exception");
                    }
                }
            });

            itemContainer = itemView.findViewById(R.id.item_container_cultivation);
            title = itemView.findViewById(R.id.text_view_item_title);
            imageView = itemView.findViewById(R.id.image_view_item);
        }
    }
}
