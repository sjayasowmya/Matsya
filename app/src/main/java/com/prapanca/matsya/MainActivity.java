package com.prapanca.matsya;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.DefaultItemAnimator;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.View;
import android.widget.Toast;

import java.util.ArrayList;
import java.util.List;

public class MainActivity extends AppCompatActivity {

    private List<Site> siteList = new ArrayList<>();
    private RecyclerView recyclerView;
    private SiteAdapter mAdapter;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        //   Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        //   setSupportActionBar(toolbar);

        recyclerView = (RecyclerView) findViewById(R.id.recycler_view);

        mAdapter = new SiteAdapter(siteList);

        recyclerView.setHasFixedSize(true);
        RecyclerView.LayoutManager mLayoutManager = new LinearLayoutManager(getApplicationContext());
        recyclerView.setLayoutManager(mLayoutManager);
        recyclerView.addItemDecoration(new DividerItemDecoration(this, LinearLayoutManager.VERTICAL));
        recyclerView.setItemAnimator(new DefaultItemAnimator());
        recyclerView.setAdapter(mAdapter);

        recyclerView.addOnItemTouchListener(new RecyclerTouchListener(getApplicationContext(), recyclerView, new RecyclerTouchListener.ClickListener() {
            @Override
            public void onClick(View view, int position) {
                Site site = siteList.get(position);
               // Toast.makeText(getApplicationContext(), site.getSitenumber() + " is selected!", Toast.LENGTH_SHORT).show();
                Intent pondIntent = new Intent(MainActivity.this, PondActivity.class);
                // myIntent.putExtra("key", value); //Optional parameters
                startActivity(pondIntent);
            }

            @Override
            public void onLongClick(View view, int position) {

            }
        }));

        prepareSiteData();
    }

    private void prepareSiteData() {
        Site site = new Site("Site 1", "Vizag", "Last updated 20:15");
        siteList.add(site);

        site = new Site("Site 2", "RJY", "Last updated 20:15");
        siteList.add(site);

        site = new Site("Site 3", "Mumbai", "Last updated 20:15");
       siteList.add(site);

        site = new Site("Site 4", "Goa", "Last updated 20:15");
        siteList.add(site);


        mAdapter.notifyDataSetChanged();
    }


}
