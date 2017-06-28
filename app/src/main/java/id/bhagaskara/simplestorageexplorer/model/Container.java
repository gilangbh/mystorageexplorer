package id.bhagaskara.simplestorageexplorer.model;

/**
 * Created by gilan on 6/8/2017.
 */

public class Container {
    private String name;
    private StorageAccount account;

    public Container(){}

    public Container(String name){
        this.name = name;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public StorageAccount getAccount() {
        return account;
    }

    public void setAccount(StorageAccount account) {
        this.account = account;
    }
}
