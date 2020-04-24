package com.example.alim;

import android.content.Context;
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
import androidx.recyclerview.widget.RecyclerView;

import java.util.ArrayList;
import java.util.List;

public class ListAdapter extends RecyclerView.Adapter<ListAdapter.ListViewHolder> implements Filterable {

    Context mContext;
    List<CustomListItem> mData;
    List<CustomListItem> mDataFiltered;

    public ListAdapter(Context mContext, List<CustomListItem> mData) {
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
                    List<CustomListItem> lstFiltered = new ArrayList<>();
                    for (CustomListItem row : mData) {

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


                mDataFiltered = (List<CustomListItem>) results.values;
                notifyDataSetChanged();

            }
        };



    }

    @NonNull
    @Override
    public ListAdapter.ListViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {




        View layout;
        layout = LayoutInflater.from(mContext).inflate(R.layout.customlist_item,parent,false);
        return new ListViewHolder(layout);


    }

    @Override
    public void onBindViewHolder(@NonNull ListAdapter.ListViewHolder holder, int position) {
        holder.imageView.setAnimation(AnimationUtils.loadAnimation(mContext,R.anim.fade_transition_animation));
        holder.itemContainer.setAnimation(AnimationUtils.loadAnimation(mContext,R.anim.fade_scale_animation));


        holder.title.setText(mDataFiltered.get(position).getTitle());
        holder.imageView.setImageResource(mDataFiltered.get(position).getUserPhoto());

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

            itemContainer = itemView.findViewById(R.id.item_container);
            title = itemView.findViewById(R.id.text_view_item_title);
            imageView = itemView.findViewById(R.id.image_view_item);
        }
    }
}
