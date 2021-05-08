package org.uom.cse.cs4262.api;

import org.uom.cse.cs4262.File;

import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;
import java.util.*;
import java.util.concurrent.TimeUnit;
import java.util.regex.Pattern;


public class Node {

    private Cred cred;
    private List<String> fileList;
    private List<Cred> routingTable;
    private List<NetworkDetails> networkDetailsTable;
    private Cred bs;
    private int numberReceivedQuery;
    private int numberForwardedQuery;
    private int numberAnsweredQuery;
    private int numberSearchedQuery;
    private LinkedHashMap<String, ArrayList<String>> detailsTable;
    private List<NetworkDetails> cachedFilesWithOriginalFileNameTable;
    private HashMap<String, File> downloadTable;
    private List<QueryDetails> queryTable;

    private float requestSuccessRatio;
    private float averageLatency;
    private float averageHopCount;


    public void setCachedFilesWithOriginalFileNameTable(List<NetworkDetails> cachedFilesWithOriginalFileNameTable) {
        this.cachedFilesWithOriginalFileNameTable = cachedFilesWithOriginalFileNameTable;
    }

    public void setDownloadTable(HashMap<String, File> downloadTable) {
        this.downloadTable = downloadTable;
    }

    public List<NetworkDetails> getCachedFilesWithOriginalFileNameTable() {
        return cachedFilesWithOriginalFileNameTable;
    }

    public Node(Cred cred, List<String> fileList, List<Cred> routingTable, List<NetworkDetails> networkDetailsTable, Cred bs, int numberReceivedQuery, int numberForwardedQuery, int numberAnsweredQuery, int numberSearchedQuery, LinkedHashMap<String, ArrayList<String>> detailsTable, List<QueryDetails> queryTable) {
        this.cred = cred;
        this.fileList = fileList;
        this.routingTable = routingTable;
        this.networkDetailsTable = networkDetailsTable;
        this.bs = bs;
        this.numberReceivedQuery = numberReceivedQuery;
        this.numberForwardedQuery = numberForwardedQuery;
        this.numberAnsweredQuery = numberAnsweredQuery;
        this.numberSearchedQuery = numberSearchedQuery;
        this.detailsTable = detailsTable;
        this.queryTable = queryTable;
        this.downloadTable = new HashMap<>();
        this.cachedFilesWithOriginalFileNameTable = new ArrayList<>();
        fileList.forEach(fileName -> {
            int fileSize = getRandFileSize();
            String fileData = createDataSize(fileSize);
            try {
                String hash = generateSHAHash(fileData);
                downloadTable.put(fileName, new File(fileSize, hash, fileName));
            } catch (NoSuchAlgorithmException e) {
                e.printStackTrace();
            }
        });
        requestSuccessRatio = 0;
        averageLatency = 0;
        averageHopCount = 0;
    }

    public Cred getCred() {
        return cred;
    }

    public void setCred(Cred cred) {
        this.cred = cred;
    }

    public List<String> getFileList() {
        return fileList;
    }

    public void setFileList(List<String> fileList) {
        this.fileList = fileList;
    }

    public List<Cred> getRoutingTable() {
        return routingTable;
    }

    public void setRoutingTable(List<Cred> routingTable) {
        synchronized (Node.class){
            this.routingTable = routingTable;
        }
    }

    public List<NetworkDetails> getNetworkDetailsTable() {
        return networkDetailsTable;
    }

    public void setNetworkDetailsTable(List<NetworkDetails> networkDetailsTable) {
        this.networkDetailsTable = networkDetailsTable;

    }

    public HashMap<String, File > getDownloadTable() {
        return this.downloadTable;
    }

    public void addToDownloadTable(String fileName, String fileHash, int fileSize) {
        this.downloadTable.put(fileName, new File(fileSize, fileHash, fileName));
    }

    public Cred getDownloadCred(String query) {
        Pattern pattern = Pattern.compile(query);
        List<NetworkDetails> networkTableSearchResult = new ArrayList();
        for (NetworkDetails networkDetails : networkDetailsTable) {
            System.out.println(networkDetails.getQuery());
            if (pattern.matcher(networkDetails.getQuery()).find()) {
                networkTableSearchResult.add(networkDetails);
                return networkDetails.getServedNode();
            }
        }
        return null;
    }

    public Cred getBs() {
        return bs;
    }

    public void setBs(Cred bs) {
        this.bs = bs;
    }

    public int getNumberReceivedQuery() {
        return numberReceivedQuery;
    }

    public void setNumberReceivedQuery(int numberReceivedQuery) {
        this.numberReceivedQuery = numberReceivedQuery;
    }


    public int getNumberForwardedQuery() {
        return numberForwardedQuery;
    }

    public void setNumberForwardedQuery(int numberForwardedQuery) {
        this.numberForwardedQuery = numberForwardedQuery;
    }

    public int getNumberAnsweredQuery() {
        return numberAnsweredQuery;
    }

    public void setNumberAnsweredQuery(int numberAnsweredQuery) {
        this.numberAnsweredQuery = numberAnsweredQuery;
    }

    public int getNumberSearchedQuery() {
        return numberSearchedQuery;
    }

    public void setNumberSearchedQuery(int numberSearchedQuery) {
        this.numberSearchedQuery = numberSearchedQuery;
    }

    public LinkedHashMap<String, ArrayList<String>> getDetailsTable() {
        return detailsTable;
    }

    public void setDetailsTable(LinkedHashMap<String, ArrayList<String>> detailsTable) {
        this.detailsTable = detailsTable;
    }

    public List<QueryDetails> getQueryTable() {
        return queryTable;
    }

    public void setQueryTable(List<QueryDetails> queryTable) {
        this.queryTable = queryTable;
    }

    public float getRequestSuccessRatio() {
        return requestSuccessRatio;
    }

    public void setRequestSuccessRatio(float requestSuccessRatio) {
        this.requestSuccessRatio = requestSuccessRatio;
    }

    public float getAverageLatency() {
        return averageLatency;
    }

    public void setAverageLatency(float averageLatency) {
        this.averageLatency = averageLatency;
    }

    public float getAverageHopCount() {
        return averageHopCount;
    }

    public void setAverageHopCount(float averageHopCount) {
        this.averageHopCount = averageHopCount;
    }

    public float calcAverageLatency() {
        long totalDifference = 0;
        for (int i = 0; i < this.networkDetailsTable.size(); i++) {
            NetworkDetails networkDetails = this.networkDetailsTable.get(i);
            totalDifference += getDateDiff(networkDetails.getTrigTime(), networkDetails.getDeliveryTime());
        }
        try {
            synchronized (Node.class){
                this.averageLatency = totalDifference / numberSearchedQuery;
            }
        }catch(Exception e){
            return -1;
        }
        return averageLatency;
    }

    public float calcAverageHopCountPerSearch() {
        float totalHopCount = 0;
        for (int i = 0; i < this.networkDetailsTable.size(); i++) {
            NetworkDetails networkDetails = this.networkDetailsTable.get(i);
            totalHopCount += networkDetails.getHopsNeeded();
        }
        try{
            synchronized (Node.class){
                averageHopCount = totalHopCount / numberSearchedQuery;
            }
        }catch (Exception e){
            return -1;
        }
        return averageHopCount;
    }

    public float calcRequestSuccessRatio() {
        try {
            synchronized (Node.class){
                this.requestSuccessRatio = (float) numberAnsweredQuery / numberReceivedQuery;
            }
        }catch(Exception e){
            return -1;
        }
        return requestSuccessRatio;
    }

    public long getDateDiff(Date date1, Date date2) {
        //time difference will return in miliseconds
        TimeUnit timeUnit = TimeUnit.MILLISECONDS;
        long diffInMillies = date2.getTime() - date1.getTime();
        return timeUnit.convert(diffInMillies, TimeUnit.MILLISECONDS);
    }

    public void incReceivedQueryCount() {
        synchronized (Node.class){
            this.numberReceivedQuery++;
        }
    }

    public void incForwardedQueryCount() {
        synchronized (Node.class){
            this.numberForwardedQuery++;
        }
    }

    public void incAnsweredQueryCount() {
        synchronized (Node.class){
            this.numberAnsweredQuery++;
        }
    }

    public void incSearchedQueryCount() {
        synchronized (Node.class){
            this.numberSearchedQuery++;
        }
    }

    public int getRandFileSize() { return (int)( Math.random() * 9 + 2); }

    public String createDataSize(int msgSize) {
        int sizeInMb = msgSize * 1024 * 1024;
        StringBuilder sb = new StringBuilder(msgSize);
        for (int i=0; i<sizeInMb; i++) {
            sb.append(Math.round(Math.random()));
        }
        return sb.toString();
    }
    public String generateSHAHash(String file) throws NoSuchAlgorithmException {
        MessageDigest md = MessageDigest.getInstance("SHA-256");
        byte[] a =  md.digest(file.getBytes());
        return ( Base64.getEncoder().encodeToString(a));
    }




}
