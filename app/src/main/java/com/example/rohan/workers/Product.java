package com.example.rohan.workers;

public class Product {
    String pid;
    String pname;
    String pbags;
    String pweight;

    Product(){

    }

    public Product(String pid,String pname, String pbags, String pweight) {
        this.pid =pid;
        this.pname = pname;
        this.pbags = pbags;
        this.pweight = pweight;
    }

    public String getPname() {
        return pname;
    }

    public String getPbags() {
        return pbags;
    }

    public String getPweight() {
        return pweight;
    }

    public String getPid() {
        return pid;
    }
}
