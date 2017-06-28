package id.bhagaskara.simplestorageexplorer.adapter;

import android.content.SharedPreferences;
import android.os.AsyncTask;
import android.preference.PreferenceManager;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import com.google.common.collect.Lists;
import com.google.gson.Gson;
import com.microsoft.azure.storage.CloudStorageAccount;
import com.microsoft.azure.storage.StorageException;
import com.microsoft.azure.storage.blob.CloudBlob;
import com.microsoft.azure.storage.blob.CloudBlobClient;
import com.microsoft.azure.storage.blob.CloudBlobContainer;
import com.microsoft.azure.storage.blob.CloudBlobDirectory;
import com.microsoft.azure.storage.blob.CloudBlockBlob;
import com.microsoft.azure.storage.blob.ListBlobItem;

import java.net.URISyntaxException;
import java.security.InvalidKeyException;
import java.util.List;

import id.bhagaskara.simplestorageexplorer.ExploreActivity;
import id.bhagaskara.simplestorageexplorer.R;
import id.bhagaskara.simplestorageexplorer.model.Blob;
import id.bhagaskara.simplestorageexplorer.model.Container;
import id.bhagaskara.simplestorageexplorer.model.StorageAccount;

import static android.content.Context.MODE_PRIVATE;
import static id.bhagaskara.simplestorageexplorer.MainActivity.MY_PREFS_NAME;

/**
 * Created by gilan on 6/13/2017.
 */

public class BlobAdapter extends RecyclerView.Adapter<BlobAdapter.MyViewHolder>{
    private Container container;
    private List<Blob> blobList;
    private ExploreActivity exploreActivity;
    private Gson gson = new Gson();

    @Override
    public BlobAdapter.MyViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View itemView = LayoutInflater.from(parent.getContext()).inflate(R.layout.blob_list_row,parent,false);
        return new BlobAdapter.MyViewHolder(itemView);
    }

    public BlobAdapter(List<Blob> blobList,Container container,ExploreActivity exploreActivity){
        this.blobList = blobList;
        this.container = container;
        this.exploreActivity = exploreActivity;
        new BlobAdapter.RetrieveBlobListTask().execute(container.getName());
    }

    @Override
    public void onBindViewHolder(BlobAdapter.MyViewHolder holder, int position) {
        Blob blob = blobList.get(position);
        holder.name.setText(blob.getName());
    }

    @Override
    public int getItemCount() {
        return blobList.size();
    }

    public class MyViewHolder extends RecyclerView.ViewHolder {
        public TextView name;

        public MyViewHolder(View view){
            super(view);
            name = (TextView) view.findViewById(R.id.mTVname);
        }
    }

    class RetrieveBlobListTask extends AsyncTask<String,Void,List<Blob>> {

        @Override
        protected List<Blob> doInBackground(String... params) {

            try{
                //SharedPreferences.Editor editor = exploreActivity.getSharedPreferences(MY_PREFS_NAME, MODE_PRIVATE).edit();
                SharedPreferences prefs = PreferenceManager.getDefaultSharedPreferences(exploreActivity);

                String storageAccountJson = prefs.getString("currentActiveStorage",null);
                if(storageAccountJson != null){
                    StorageAccount storageAccount = gson.fromJson(storageAccountJson,StorageAccount.class);

                    CloudStorageAccount account = CloudStorageAccount.parse(storageAccount.getConnectionString());
                    CloudBlobClient blobClient = account.createCloudBlobClient();
                    CloudBlobContainer blobContainer = blobClient.getContainerReference(params[0]);
                    List<ListBlobItem> listBlobItems = Lists.newArrayList(blobContainer.listBlobs());

                    //List<CloudBlobContainer> blobsFromClient = Lists.newArrayList(blobClient.listContainers());

                    blobList.clear();
                    for (ListBlobItem item : listBlobItems) {
                        if(item instanceof CloudBlob){
                            Blob blob = new Blob(((CloudBlob)item).getName());
                            blob.setBlobType("CloudBlob");
                            blobList.add(blob);
                        }
                        else
                        if(item instanceof CloudBlobDirectory){
                            Blob blob = new Blob(((CloudBlobDirectory)item).getPrefix());
                            blob.setBlobType("CloudBlobDirectory");
                            blobList.add(blob);
                        }
                    }
                }
            } catch (InvalidKeyException e) {
                Log.e("LISTBLOBTASK", e.getMessage());
                e.printStackTrace();
            } catch (URISyntaxException e) {
                Log.e("LISTBLOBTASK", e.getMessage());
                e.printStackTrace();
            } catch (StorageException e) {
                Log.e("LISTBLOBTASK", e.getMessage());
                e.printStackTrace();
            }

            return blobList;
        }

        @Override
        protected void onPostExecute(List<Blob> blobList) {
            if (blobList != null){
                BlobAdapter.this.notifyDataSetChanged();
            }
        }
    }
}
