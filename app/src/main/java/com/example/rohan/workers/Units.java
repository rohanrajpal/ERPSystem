package com.example.rohan.workers;

public class Units {
    String id;
    String unitname;

    public Units(){
    }

    public Units(String id,String unitname) {
        this.id = id;
        this.unitname = unitname;
    }

    public String getUnitname() {
        return unitname;
    }
    public String getId(){
        return id;
    }
}
