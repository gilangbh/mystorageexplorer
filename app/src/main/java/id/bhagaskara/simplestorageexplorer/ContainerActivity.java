package id.bhagaskara.simplestorageexplorer;

import android.content.Intent;
import android.os.Bundle;
import android.support.design.widget.FloatingActionButton;
import android.support.design.widget.Snackbar;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.DefaultItemAnimator;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.Toolbar;
import android.view.View;

import com.google.android.gms.ads.AdRequest;
import com.google.android.gms.ads.AdView;
import com.google.gson.Gson;

import java.util.ArrayList;
import java.util.List;

import id.bhagaskara.simplestorageexplorer.adapter.ContainerAdapter;
import id.bhagaskara.simplestorageexplorer.model.Container;
import id.bhagaskara.simplestorageexplorer.model.StorageAccount;
import id.bhagaskara.simplestorageexplorer.utilities.RecyclerItemClickListener;

public class ContainerActivity extends AppCompatActivity {

    private Gson gson = new Gson();
    private ContainerAdapter containerAdapter;
    private RecyclerView recyclerView;
    private List<Container> containerList = new ArrayList<>();
    private StorageAccount storageAccount;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_container);
        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);

        FloatingActionButton fab = (FloatingActionButton) findViewById(R.id.fab);
        fab.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Snackbar.make(view, "Replace with your own action", Snackbar.LENGTH_LONG)
                        .setAction("Action", null).show();
            }
        });
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);

        // Load an ad into the AdMob banner view.
        AdView adView = (AdView) findViewById(R.id.adView);
        AdRequest adRequest = new AdRequest.Builder()
                .setRequestAgent("android_studio:ad_template").build();
        adView.loadAd(adRequest);

        Intent intent = getIntent();
        String storageAccountJson = intent.getStringExtra("storageAccount");

        storageAccount = gson.fromJson(storageAccountJson,StorageAccount.class);

        prepareData();

        //new ListContainersTask(ContainerActivity.this,storageAccount.getConnectionString(),containerAdapter, containerList).execute();
    }

    private void prepareData() {

        recyclerView = (RecyclerView) findViewById(R.id.mRVContainerList);

        containerAdapter = new ContainerAdapter(containerList,storageAccount.getConnectionString());

        RecyclerView.LayoutManager layoutManager = new LinearLayoutManager(getApplicationContext());
        recyclerView.setLayoutManager(layoutManager);
        recyclerView.setItemAnimator(new DefaultItemAnimator());
        recyclerView.setAdapter(containerAdapter);
        recyclerView.addOnItemTouchListener(new RecyclerItemClickListener(this, recyclerView, new RecyclerItemClickListener.OnItemClickListener() {
            @Override
            public void onItemClick(View view, int position) {
                Container container = containerList.get(position);
                String containerJson = gson.toJson(container);

                Intent intent = new Intent(ContainerActivity.this,ExploreActivity.class);
                intent.putExtra("container",containerJson);
                startActivity(intent);
            }

            @Override
            public void onLongItemClick(View view, int position) {

            }
        }));
    }
}
