package com.example.rohan.workers;

public class RawMaterial {
    String rID;
    String rName;
    String rWt;

    RawMaterial() {

    }

    public RawMaterial(String rID, String rName, String rWt) {
        this.rID = rID;
        this.rName = rName;
        this.rWt = rWt;
    }

    public String getrID() {
        return rID;
    }

    public String getrName() {
        return rName;
    }

    public String getrWt() {
        return rWt;
    }
}
