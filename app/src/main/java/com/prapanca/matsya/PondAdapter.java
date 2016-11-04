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


public class PondAdapter extends RecyclerView.Adapter<PondAdapter.MyViewHolder> {

    private List<Pond> pondList;

    public class MyViewHolder extends RecyclerView.ViewHolder {
        public TextView pondnumber, time, tempvalue,phvalue;

        public MyViewHolder(View view) {
            super(view);
            pondnumber = (TextView) view.findViewById(R.id.pondNumber);
            tempvalue = (TextView) view.findViewById(R.id.temperature);
            time = (TextView) view.findViewById(R.id.time);
            phvalue=(TextView)view.findViewById(R.id.phvalue);
        }
    }


    public PondAdapter(List<Pond> pondList) {
        this.pondList = pondList;
    }

    @Override
    public MyViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View itemView = LayoutInflater.from(parent.getContext())
                .inflate(R.layout.pond_row_item, parent, false);

        return new MyViewHolder(itemView);
    }

    @Override
    public void onBindViewHolder(MyViewHolder holder, int position) {
        Pond pond = pondList.get(position);
        holder.pondnumber.setText(pond.getPondnumber());
        holder.tempvalue.setText(pond.getTempvalue());
        holder.time.setText(pond.getTime());
        holder.phvalue.setText(pond.getPhvalue());
    }

    // @Override
    // public int getItemCount() {
    // return moviesList.size();
    //}

    @Override
    public int getItemCount() {
        return pondList.size();
    }


    public void removeItem(int position) {
       pondList.remove(position);
        notifyItemRemoved(position);
        notifyItemRangeChanged(position, pondList.size());
    }

}


