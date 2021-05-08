package org.uom.cse.cs4262.api;

import java.util.Date;
import java.util.List;


public class NetworkDetails {
    private String query;
    private Date trigTime;
    private Date deliveryTime;
    private int hopsNeeded;
    private Cred servedNode;
    private List<String> fileList;

    public NetworkDetails(String query, Date trigTime, Date deliveryTime, int hopsNeeded, Cred servedNode, List<String> fileList) {
        this.query = query;
        this.trigTime = trigTime;
        this.deliveryTime = deliveryTime;
        this.hopsNeeded = hopsNeeded;
        this.servedNode = servedNode;
        this.fileList = fileList;
    }

    public List<String> getFileList() {
        return fileList;
    }

    public void setFileList(List<String> fileList) {
        this.fileList = fileList;
    }

    public String getQuery() {
        return query;
    }

    public void setQuery(String query) {
        this.query = query;
    }

    public Date getTrigTime() {
        return trigTime;
    }

    public void setTrigTime(Date trigTime) {
        this.trigTime = trigTime;
    }

    public Date getDeliveryTime() {
        return deliveryTime;
    }

    public void setDeliveryTime(Date deliveryTime) {
        this.deliveryTime = deliveryTime;
    }

    public int getHopsNeeded() {
        return hopsNeeded;
    }

    public void setHopsNeeded(int hopsNeeded) {
        this.hopsNeeded = hopsNeeded;
    }

    public Cred getServedNode() {
        return servedNode;
    }

    public void setServedNode(Cred servedNode) {
        this.servedNode = servedNode;
    }
}
