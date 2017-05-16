package id.bhagaskara.simplestorageexplorer;

import android.content.Intent;
import android.os.Bundle;
import android.support.design.widget.FloatingActionButton;
import android.support.design.widget.Snackbar;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.view.View;
import android.widget.CheckBox;
import android.widget.EditText;
import android.widget.RadioGroup;
import android.widget.Toast;

import com.google.gson.Gson;

import java.util.ArrayList;
import java.util.List;

import id.bhagaskara.simplestorageexplorer.enums.Endpoint;
import id.bhagaskara.simplestorageexplorer.model.StorageAccount;

public class AddStorageAccount_Activity extends AppCompatActivity {
    private RadioGroup mRGendpoint;
    private CheckBox mCBhttps;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_add_storage_account_);
        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);
    }

    public void onClickBtnSave(View v)
    {
        Toast.makeText(this, "Clicked on Button", Toast.LENGTH_LONG).show();
        String name = ((EditText)findViewById(R.id.mTVaccountname)).getText().toString();
        String key = ((EditText)findViewById(R.id.mTVkey)).getText().toString();

        mRGendpoint = (RadioGroup)findViewById(R.id.mRGendpoint);
        int index = mRGendpoint.indexOfChild(findViewById(mRGendpoint.getCheckedRadioButtonId()));

        Endpoint endpoint;
        if (index == 1){
            endpoint = Endpoint.DEFAULT;
        }else{
            endpoint = Endpoint.CHINA;
        }

        mCBhttps = (CheckBox)findViewById(R.id.mCBhttps);
        boolean isHttp = mCBhttps.isChecked();
        StorageAccount storageAccount = new StorageAccount(name,key,endpoint,isHttp);
        Gson gson = new Gson();
        String storageAccountJson = gson.toJson(storageAccount);

        Intent intent = new Intent();
        intent.putExtra("newStorageAccount",storageAccountJson);
        setResult(RESULT_OK,intent);
        finish();

//        Type type = new TypeToken<List<StorageAccount>>(){}.getType();
//
//        SharedPreferences storageAccountsPref = getSharedPreferences("storageaccounts",MODE_PRIVATE);
//        String getStorageAccountListJson = storageAccountsPref.getString("storageaccountlist", "");
//        SharedPreferences.Editor storageAccountsPrefEditor = storageAccountsPref.edit();
//
//        storageAccountList = gson.fromJson(getStorageAccountListJson, type);
//        storageAccountList.add(storageAccount);
//
//        String setStorageAccountListJson = gson.toJson(storageAccountList);
//        storageAccountsPrefEditor.putString("storageaccountlist", setStorageAccountListJson);
//        storageAccountsPrefEditor.apply();
//        finish();
    }
}
