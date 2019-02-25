package com.example.rohan.workers;

public class DeliveryProduct {
    String dId;
    String dDeliverTo;
    String dname;
    String dBags;
    String dWeight;

    DeliveryProduct(){

    }

    public DeliveryProduct(String dId, String dDeliverTo, String dname, String dBags, String dWeight) {
        this.dId = dId;
        this.dDeliverTo = dDeliverTo;
        this.dname = dname;
        this.dBags = dBags;
        this.dWeight = dWeight;
    }

    public String getdId() {
        return dId;
    }

    public String getdDeliverTo() {
        return dDeliverTo;
    }

    public String getDname() {
        return dname;
    }

    public String getdBags() {
        return dBags;
    }

    public String getdWeight() {
        return dWeight;
    }
}
