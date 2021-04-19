package org.example.com.ds.api;

import java.util.*;
import java.util.concurrent.TimeUnit;


public class Node {

    private Cred cred;
    private List<String> nodeFileList;
    private List<Cred> routingTable;
    private List<NetworkRecord> networkTable;
    private Cred bs_cred;
    private int quariesAns;
    private int quariesSearch;
    private int quariesReceived;
    private int quariesSend;

    private LinkedHashMap<String, ArrayList<String>> detailsTable;
    private List<QRecord> queryTable;

    private float ratioReqSuccess;
    private float latency;
    private float hopCount;


    public Node(Cred cred, List<String> nodeFileList, List<Cred> routingTable, List<NetworkRecord> networkTable, Cred bs_cred, int quariesReceived, int quariesSend, int quariesAns, int quariesSearch, LinkedHashMap<String, ArrayList<String>> detailsTable, List<QRecord> queryTable) {
        this.cred = cred;
        this.nodeFileList = nodeFileList;
        this.routingTable = routingTable;
        this.networkTable = networkTable;
        this.bs_cred = bs_cred;
        this.quariesReceived = quariesReceived;
        this.quariesSend = quariesSend;
        this.quariesAns = quariesAns;
        this.quariesSearch = quariesSearch;
        this.detailsTable = detailsTable;
        this.queryTable = queryTable;

        ratioReqSuccess = 0;
        latency = 0;
        hopCount = 0;
    }

    public Cred getCred() {
        return cred;
    }

    public void setCred(Cred cred) {
        this.cred = cred;
    }

    public List<String> getNodeFileList() {
        return nodeFileList;
    }

    public void setNodeFileList(List<String> nodeFileList) {
        this.nodeFileList = nodeFileList;
    }

    public Cred getBs_cred() {
        return bs_cred;
    }

    public int getQuariesReceived() {
        return quariesReceived;
    }

    public int getQuariesSend() {
        return quariesSend;
    }

    public int getQuariesAns() {
        return quariesAns;
    }

    public int getQuariesSearch() {
        return quariesSearch;
    }


    public List<Cred> getRoutingTable() {
        return routingTable;
    }

    public void setRoutingTable(List<Cred> routingTable) {
        this.routingTable = routingTable;
    }

    public List<NetworkRecord> getNetworkTable() {
        return networkTable;
    }

    public void setNetworkTable(List<NetworkRecord> networkTable) {
        this.networkTable = networkTable;
    }



    public LinkedHashMap<String, ArrayList<String>> getDetailsTable() {
        return detailsTable;
    }


    public List<QRecord> getQueryTable() {
        return queryTable;
    }

    public float calculateAverageLatency() {
        long totalDifference = 0;
        for (int i = 0; i < this.networkTable.size(); i++) {
            NetworkRecord networkRecord = this.networkTable.get(i);
            totalDifference += getDateDiff(networkRecord.getTrigTime(), networkRecord.getDeliveryTime());
        }
        try {
            this.latency = totalDifference / quariesSearch;
        }catch(Exception e){
            return -1;
        }
        return latency;
    }

    public float calculateAverageHopCountPerSearch() {
        float totalHopCount = 0;
        for (int i = 0; i < this.networkTable.size(); i++) {
            NetworkRecord networkRecord = this.networkTable.get(i);
            totalHopCount += networkRecord.getHopsRequired();
        }
        try{
        hopCount = totalHopCount / quariesSearch;
        }catch (Exception e){
            return -1;
        }
        return hopCount;
    }

    public void incReceivedQueryCount() {
        this.quariesReceived++;
    }

    public void incForwardedQueryCount() {
        this.quariesSend++;
    }

    public void incAnsweredQueryCount() {
        this.quariesAns++;
    }

    public void incSearchedQueryCount() {
        this.quariesSearch++;
    }


    public float calculateRequestSuccessRatio() {
        try {
            this.ratioReqSuccess = (float) quariesAns / quariesReceived;
        }catch(Exception e){
            return -1;
        }
        return ratioReqSuccess;
    }

    public long getDateDiff(Date date1, Date date2) {

        TimeUnit timeUnit = TimeUnit.MILLISECONDS;
        long diffInMillies = date2.getTime() - date1.getTime();
        return timeUnit.convert(diffInMillies, TimeUnit.MILLISECONDS);
    }




}
