package id.bhagaskara.simplestorageexplorer;

import android.content.Intent;
import android.content.SharedPreferences;
import android.preference.PreferenceManager;
import android.support.design.widget.AppBarLayout;
import android.support.design.widget.FloatingActionButton;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.DefaultItemAnimator;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.Toolbar;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.Toast;

import com.google.android.gms.ads.AdRequest;
import com.google.android.gms.ads.AdView;
import com.google.gson.Gson;
import com.google.gson.reflect.TypeToken;

import java.lang.reflect.Type;
import java.util.ArrayList;
import java.util.List;

import id.bhagaskara.simplestorageexplorer.adapter.StorageAccountAdapter;
import id.bhagaskara.simplestorageexplorer.model.StorageAccount;
import id.bhagaskara.simplestorageexplorer.utilities.RecyclerItemClickListener;

public class MainActivity extends AppCompatActivity {
    private List<StorageAccount> storageAccountList = new ArrayList<>();
    private RecyclerView recyclerView;
    private StorageAccountAdapter storageAccountAdapter;
    private Gson gson = new Gson();
    public static final String MY_PREFS_NAME = "MyPrefsFile";
    SharedPreferences.Editor editor;
    private SharedPreferences prefs;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);
        AppBarLayout appBarLayout = (AppBarLayout)findViewById(R.id.appBar);
        appBarLayout.setExpanded(false, true);

        FloatingActionButton fab = (FloatingActionButton) findViewById(R.id.fab);
        fab.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                //Snackbar.make(view, "Replace with your own action", Snackbar.LENGTH_LONG).setAction("Action", null).show();
                Intent intent = new Intent(MainActivity.this,AddStorageAccountActivity.class);
                //startActivity(intent);
                startActivityForResult(intent,StorageExplorerConstants.REQUEST_CODE_NEW_ACCOUNT);
            }
        });

        // Load an ad into the AdMob banner view.
        AdView adView = (AdView) findViewById(R.id.adView);
        AdRequest adRequest = new AdRequest.Builder()
                .setRequestAgent("android_studio:ad_template").build();
        adView.loadAd(adRequest);

        // Toasts the test ad message on the screen. Remove this after defining your own ad unit ID.
        Toast.makeText(this, "Showing Ads!", Toast.LENGTH_SHORT).show();

        SharedPreferences prefs = PreferenceManager.getDefaultSharedPreferences(this);
        editor = prefs.edit();

        if (prefs.getString("currentActiveStorage",null) != null) {
            editor.remove("currentActiveStorage");
            editor.apply();
        }

        recyclerView = (RecyclerView) findViewById(R.id.mRVStorageAccountList);

        storageAccountAdapter = new StorageAccountAdapter(storageAccountList);
        RecyclerView.LayoutManager layoutManager = new LinearLayoutManager(getApplicationContext());
        recyclerView.setLayoutManager(layoutManager);
        recyclerView.setItemAnimator(new DefaultItemAnimator());
        recyclerView.setAdapter(storageAccountAdapter);
        recyclerView.addOnItemTouchListener(new RecyclerItemClickListener(this, recyclerView, new RecyclerItemClickListener.OnItemClickListener() {
            @Override
            public void onItemClick(View view, int position) {
                StorageAccount storageAccount = storageAccountList.get(position);
                String storageAccountJson = gson.toJson(storageAccount);

                editor.putString("currentActiveStorage", storageAccountJson);
                editor.apply();

                Intent intent = new Intent(MainActivity.this,ContainerActivity.class);
                intent.putExtra("storageAccount",storageAccountJson);
                startActivity(intent);
            }

            @Override
            public void onLongItemClick(View view, int position) {

            }
        }));

        prepareData();
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        // Check which request we're responding to
        if (requestCode == StorageExplorerConstants.REQUEST_CODE_NEW_ACCOUNT) {
            // Make sure the request was successful
            if (resultCode == RESULT_OK) {

                String storageAccountJson = data.getStringExtra("newStorageAccount");
                StorageAccount storageAccount = gson.fromJson(storageAccountJson,StorageAccount.class);
                storageAccountList.add(storageAccount);
                storageAccountAdapter.notifyDataSetChanged();

                SharedPreferences storageAccountsPref = getSharedPreferences("storageaccounts",MODE_PRIVATE);
                SharedPreferences.Editor storageAccountsPrefEditor = storageAccountsPref.edit();

                String json = gson.toJson(storageAccountList);
                storageAccountsPrefEditor.putString("storageaccountlist", json);
                storageAccountsPrefEditor.apply();
            }
        }
    }

    private void prepareData(){
        SharedPreferences storageAccountsPref = getSharedPreferences("storageaccounts",MODE_PRIVATE);

        String storageAccountListJson = storageAccountsPref.getString("storageaccountlist", "");
        storageAccountList.clear();

        if (!storageAccountListJson.isEmpty()) {
            Type type = new TypeToken<List<StorageAccount>>() {}.getType();
            List<StorageAccount> storageAccountListFromPref = gson.fromJson(storageAccountListJson, type);
            storageAccountList.addAll(storageAccountListFromPref);

        }
        storageAccountAdapter.notifyDataSetChanged();
    }

    public void onClickBtn(View v)
    {
        Toast.makeText(this, "Clicked on Button", Toast.LENGTH_LONG).show();
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.menu_main, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        // Handle action bar item clicks here. The action bar will
        // automatically handle clicks on the Home/Up button, so long
        // as you specify a parent activity in AndroidManifest.xml.
        int id = item.getItemId();

        if (id == R.id.action_settings) {
            return true;
        }

        return super.onOptionsItemSelected(item);
    }
}
