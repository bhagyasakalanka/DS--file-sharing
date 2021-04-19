package org.example.com.ds.api;

import java.util.Date;
import java.util.List;


public class NetworkRecord {
    private String query;
    private Date trigTime;
    private Date deliveryTime;
    private int hopsRequired;
    private Cred sNode;
    private List<String> fileList;

    public NetworkRecord(String query, Date trigTime, Date deliveryTime, int hopsRequired, Cred sNode, List<String> fileList) {
        this.query = query;
        this.trigTime = trigTime;
        this.deliveryTime = deliveryTime;
        this.hopsRequired = hopsRequired;
        this.sNode = sNode;
        this.fileList = fileList;
    }

    public Date getTrigTime() {
        return trigTime;
    }

    public Date getDeliveryTime() {
        return deliveryTime;
    }

    public int getHopsRequired() {
        return hopsRequired;
    }

    public Cred getsNode() {
        return sNode;
    }
    public List<String> getFileList() {
        return fileList;
    }

    public String getQuery() {
        return query;
    }



}
