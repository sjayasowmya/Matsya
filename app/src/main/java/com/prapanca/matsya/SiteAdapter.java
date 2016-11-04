package com.prapanca.matsya;

/**
 * Created by jayasowmya on 4/11/16.
 */

import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import java.util.List;


public class SiteAdapter extends RecyclerView.Adapter<SiteAdapter.MyViewHolder> {

    private List<Site> siteList;

    public class MyViewHolder extends RecyclerView.ViewHolder {
        public TextView sitenumber, time, place;

        public MyViewHolder(View view) {
            super(view);
           sitenumber = (TextView) view.findViewById(R.id.SiteNumber);
            place = (TextView) view.findViewById(R.id.place);
            time = (TextView) view.findViewById(R.id.time);
        }
    }


    public SiteAdapter(List<Site> siteList) {
        this.siteList = siteList;
    }

    @Override
    public MyViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View itemView = LayoutInflater.from(parent.getContext())
                .inflate(R.layout.site_row_item, parent, false);

        return new MyViewHolder(itemView);
    }

    @Override
    public void onBindViewHolder(MyViewHolder holder, int position) {
        Site site = siteList.get(position);
        holder.sitenumber.setText(site.getSitenumber());
        holder.place.setText(site.getPlace());
        holder.time.setText(site.getTime());
    }

    // @Override
    // public int getItemCount() {
    // return moviesList.size();
    //}

    @Override
    public int getItemCount() {
        return siteList.size();
    }


    public void removeItem(int position) {
        siteList.remove(position);
        notifyItemRemoved(position);
        notifyItemRangeChanged(position, siteList.size());
    }

}

