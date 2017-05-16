package id.bhagaskara.simplestorageexplorer.adapter;

import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import java.util.List;

import id.bhagaskara.simplestorageexplorer.R;
import id.bhagaskara.simplestorageexplorer.model.StorageAccount;

/**
 * Created by gilan on 4/30/2017.
 */

public class StorageAccountAdapter extends RecyclerView.Adapter<StorageAccountAdapter.MyViewHolder> {
    private List<StorageAccount> storageAccountsList;

    @Override
    public MyViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View itemView = LayoutInflater.from(parent.getContext()).inflate(R.layout.account_list_row,parent,false);
        return new MyViewHolder(itemView);
    }

    @Override
    public void onBindViewHolder(MyViewHolder holder, int position) {
        StorageAccount storageAccount = storageAccountsList.get(position);
        holder.name.setText(storageAccount.getName());
        holder.fullEndpoint.setText(storageAccount.getFullendpoint());
        if(storageAccount.isHttp())
        {
            holder.http.setText("HTTP");
        }
        else{
            holder.http.setText("HTTPS");
        }
    }

    @Override
    public int getItemCount() {
        return storageAccountsList.size();
    }

    public StorageAccountAdapter(List<StorageAccount> storageAccountsList){
        this.storageAccountsList = storageAccountsList;
    }

    public class MyViewHolder extends RecyclerView.ViewHolder {
        public TextView name,fullEndpoint,http;

        public MyViewHolder(View view){
            super(view);
            name = (TextView) view.findViewById(R.id.mTVname);
            fullEndpoint = (TextView) view.findViewById(R.id.mTVfullEndpoint);
            http = (TextView)view.findViewById(R.id.mTVHttp);
        }
    }
}
