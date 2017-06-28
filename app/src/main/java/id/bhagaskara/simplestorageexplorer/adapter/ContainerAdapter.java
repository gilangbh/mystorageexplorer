package id.bhagaskara.simplestorageexplorer.adapter;

import android.os.AsyncTask;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import com.google.common.collect.Lists;
import com.microsoft.azure.storage.CloudStorageAccount;
import com.microsoft.azure.storage.blob.CloudBlob;
import com.microsoft.azure.storage.blob.CloudBlobClient;
import com.microsoft.azure.storage.blob.CloudBlobContainer;

import java.net.URISyntaxException;
import java.security.InvalidKeyException;
import java.util.ArrayList;
import java.util.List;

import id.bhagaskara.simplestorageexplorer.R;
import id.bhagaskara.simplestorageexplorer.model.Container;

/**
 * Created by gilan on 6/8/2017.
 */

public class ContainerAdapter extends RecyclerView.Adapter<ContainerAdapter.MyViewHolder> {
    private String connectionString;
    private List<Container> containerList;

    @Override
    public MyViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View itemView = LayoutInflater.from(parent.getContext()).inflate(R.layout.container_list_row,parent,false);
        return new MyViewHolder(itemView);
    }

    public ContainerAdapter(List<Container> containerList,String connectionString){
        this.containerList = containerList;
        this.connectionString = connectionString;
        new RetrieveContainerListTask().execute(connectionString);
    }
    @Override
    public void onBindViewHolder(MyViewHolder holder, int position) {
        Container container = containerList.get(position);
        holder.name.setText(container.getName());
    }

    @Override
    public int getItemCount() {
        return containerList.size();
    }

    public class MyViewHolder extends RecyclerView.ViewHolder {
        public TextView name;

        public MyViewHolder(View view){
            super(view);
            name = (TextView) view.findViewById(R.id.mTVname);
        }
    }

    class RetrieveContainerListTask extends AsyncTask<String,Void,List<Container>>{

        @Override
        protected List<Container> doInBackground(String... params) {

            try{
                Log.e("LISTCONTAINERTASK", "Connection String: " + params[0]);
                CloudStorageAccount account = CloudStorageAccount.parse(params[0]);
                CloudBlobClient blobClient = account.createCloudBlobClient();
                List<CloudBlobContainer> containersFromClient = Lists.newArrayList(blobClient.listContainers());

                containerList.clear();
                for (CloudBlobContainer item : containersFromClient) {
                    Container container = new Container(item.getName());
                    containerList.add(container);
                }

            } catch (InvalidKeyException e) {
                Log.e("LISTCONTAINERTASK", e.getMessage());
                e.printStackTrace();
            } catch (URISyntaxException e) {
                Log.e("LISTCONTAINERTASK", e.getMessage());
                e.printStackTrace();
            }

            return containerList;
        }
        @Override
        protected void onPostExecute(List<Container> containerList) {
            if (containerList != null){
                ContainerAdapter.this.notifyDataSetChanged();
            }
        }
    }
}
