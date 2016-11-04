package com.prapanca.matsya;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.DefaultItemAnimator;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.View;
import android.widget.TextView;
import android.widget.Toast;

import java.util.ArrayList;
import java.util.List;

public class PondActivity extends AppCompatActivity {

    private List<Pond> pondList = new ArrayList<>();
    private RecyclerView recyclerView;
    private PondAdapter pAdapter;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_pond);
        //   Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        //   setSupportActionBar(toolbar);

        recyclerView = (RecyclerView) findViewById(R.id.recycler_view_pond);

        pAdapter = new PondAdapter(pondList);

        recyclerView.setHasFixedSize(true);
        RecyclerView.LayoutManager mLayoutManager = new LinearLayoutManager(getApplicationContext());
        recyclerView.setLayoutManager(mLayoutManager);
        recyclerView.addItemDecoration(new DividerItemDecoration(this, LinearLayoutManager.VERTICAL));
        recyclerView.setItemAnimator(new DefaultItemAnimator());
        recyclerView.setAdapter(pAdapter);

        recyclerView.addOnItemTouchListener(new RecyclerTouchListener(getApplicationContext(), recyclerView, new RecyclerTouchListener.ClickListener() {
            @Override
            public void onClick(View view, int position) {
                Pond pond = pondList.get(position);
                Toast.makeText(getApplicationContext(), pond.getPondnumber() + " is selected!", Toast.LENGTH_SHORT).show();
                setContentView(R.layout.pond_details);
                TextView pdnum = (TextView) findViewById(R.id.pndnum) ;
                       pdnum.setText( pond.getPondnumber());
            }

            @Override
            public void onLongClick(View view, int position) {

            }
        }));

        preparePondData();
    }

    private void preparePondData() {
       Pond pond = new Pond("Pond 1", "31.83degree C", "Last updated 20:15", "15.01mg/L");
        pondList.add(pond);

        pond = new Pond("Pond 2", "27.3degree C", "Last updated 20:15", "25.01mg/L");
        pondList.add(pond);

        pond = new Pond("Pond 3", "35.83degree C", "Last updated 20:15", "15.01mg/L");
        pondList.add(pond);

        pond = new Pond("Pond 4", "31.83degree C", "Last updated 20:15", "10.01mg/L");
        pondList.add(pond);


        pAdapter.notifyDataSetChanged();
    }


}


