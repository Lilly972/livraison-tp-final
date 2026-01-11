package livraison.model;

import java.util.UUID;

public class Customer {

    private String id;
    private String name;
    private String adress;

    public Customer(String name, String adress) {
        this.id = UUID.randomUUID().toString();
        this.name = name;
        this.adress = adress;
    }

    public String getId() {
        return id;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getAdress() {
        return adress;
    }

    public void setAdress(String adress) {
        this.adress = adress;
    }

    @Override
    public String toString() {
        return "Customer [id=" + id + ", name=" + name + ", adress=" + adress + "]";
    }

}