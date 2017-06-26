package id.bhagaskara.simplestorageexplorer.model;

import id.bhagaskara.simplestorageexplorer.enums.Endpoint;

/**
 * Created by gilan on 4/30/2017.
 */

public class StorageAccount {
    private String name,key,endpointText;
    private boolean isHttp;
    private Endpoint endpoint;

    public StorageAccount(){

    }

    public StorageAccount(String name, String key, Endpoint endpoint, boolean isHttp){
        this.name = name;
        this.key = key;
        this.endpoint = endpoint;
        this.isHttp = isHttp;
        switch (endpoint){
            case DEFAULT:
                this.endpointText = "core.windows.net";
                break;
            case CHINA:
                this.endpointText = "core.chinacloudapi.cn";
                break;
        }
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getKey() {
        return key;
    }

    public void setKey(String key) {
        this.key = key;
    }

    public Endpoint getEndpoint() {
        return endpoint;
    }

    public void setEndpoint(Endpoint endpoint) {
        this.endpoint = endpoint;
    }

    public boolean isHttp() {
        return isHttp;
    }

    public void setHttp(boolean http) {
        isHttp = http;
    }

    public String getFullendpoint() {
        return this.name + "." + this.endpointText;
    }

    public String getHttporHttps(){
        if(isHttp)
            return "http";
        else
            return "https";
    }

    public String getConnectionString() {
        String DefaultEndPoints = "DefaultEndpointsProtocol=" + getHttporHttps();
        String AccountName = "AccountName=" + name;
        String AccountKey = "AccountKey=" + key;

        String conn = DefaultEndPoints + ";" + AccountName + ";" + AccountKey;
        return conn;
    }
}
