package id.bhagaskara.simplestorageexplorer.model;

import java.util.HashMap;

/**
 * Created by gilan on 6/13/2017.
 */

public class Blob {

    private String name, parent, uri, storageUri;
    private String blobType;
    private HashMap<String,String> metadata;

    public Blob(String name, String parent, String uri, String storageUri, HashMap<String, String> metadata) {
        this.name = name;
        this.parent = parent;
        this.uri = uri;
        this.storageUri = storageUri;
        this.metadata = metadata;
    }
    public Blob(String name) {
        this.name = name;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getParent() {
        return parent;
    }

    public void setParent(String parent) {
        this.parent = parent;
    }

    public String getUri() {
        return uri;
    }

    public void setUri(String uri) {
        this.uri = uri;
    }

    public String getStorageUri() {
        return storageUri;
    }

    public void setStorageUri(String storageUri) {
        this.storageUri = storageUri;
    }

    public HashMap<String, String> getMetadata() {
        return metadata;
    }

    public void setMetadata(HashMap<String, String> metadata) {
        this.metadata = metadata;
    }

    public String getBlobType() {
        return blobType;
    }

    public void setBlobType(String blobType) {
        this.blobType = blobType;
    }
}
