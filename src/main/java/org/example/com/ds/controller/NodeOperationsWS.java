package org.example.com.ds.controller;

import com.google.gson.Gson;
import org.apache.commons.lang.StringUtils;
import org.example.com.ds.api.*;
import org.example.com.ds.api.message.req.*;
import org.example.com.ds.api.message.res.RegisterRes;
import org.example.com.ds.api.message.res.SearchRes;
import org.example.com.ds.api.message.res.UnregisterRes;
import org.example.com.ds.feature.RequestHandler;
import org.springframework.web.client.HttpServerErrorException;
import org.springframework.web.client.ResourceAccessException;
import org.springframework.web.client.RestTemplate;
import org.example.com.ds.api.message.Message;
import org.example.com.ds.api.message.res.DownloadRes;

import java.io.IOException;
import java.net.DatagramPacket;
import java.net.DatagramSocket;
import java.net.InetAddress;
import java.net.SocketException;
import java.text.SimpleDateFormat;
import java.util.*;
import java.util.regex.Matcher;
import java.util.regex.Pattern;


public class NodeOperationsWS implements NodeOperations, Runnable {

    RestTemplate restTemplate = new RestTemplate();
    private Node node;
    private DatagramSocket socket;
    private boolean registerOK = false;
    private int TTL = 5;
    private List<String> detailsLog;
    private boolean logFlag;

    public NodeOperationsWS(Node node) {
        this.node = node;
        this.detailsLog = new ArrayList<>();
    }

    public Node getNode() {
        return node;
    }

    public List<String> getDetailsLog() {
        return detailsLog;
    }

    public boolean isLogFlag() {
        return logFlag;
    }

    public void setLogFlag(boolean logFlag) {
        this.logFlag = logFlag;
    }

    @Override
    public void run() {
        logDetails("Server " + this.node.getCred().getNodeName() + " created at " + this.node.getCred().getNodePort() + ". Waiting for incoming data...\n");
        byte buffer[];
        DatagramPacket datagramPacket;
        while (true) {
            buffer = new byte[65536];
            datagramPacket = new DatagramPacket(buffer, buffer.length);
            try {
                socket.receive(datagramPacket);
                String message = new String(datagramPacket.getData(), 0, datagramPacket.getLength());
                Message response = RequestHandler.handle(message);
                processResponse(response);
            } catch (IOException e) {
                e.printStackTrace();
            }
        }
    }

    // done
    @Override
    public void start() {
        try {
            socket = new DatagramSocket(this.node.getCred().getNodePort());
        } catch (SocketException e) {
            e.printStackTrace();
        }
        new Thread(this).start();
    }

    // done
    @Override
    public void register() {
        RegisterReq registerReq = new RegisterReq(node.getCred());
        String msg = registerReq.getMessageAsString(Constant.Task.REGISTER);
        try {
            socket.send(new DatagramPacket(msg.getBytes(), msg.getBytes().length, InetAddress.getByName(node.getBs_cred().getNodeIP()), node.getBs_cred().getNodePort()));
            logDetails("Sent REGISTER at " + getCurrentTime());
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    // done
    @Override
    public void unRegister() {
        UnregisterReq unregisterReq = new UnregisterReq(node.getCred());
        String msg = unregisterReq.getMessageAsString(Constant.Task.UNREGISTER);
        try {
            socket.send(new DatagramPacket(msg.getBytes(), msg.getBytes().length, InetAddress.getByName(node.getBs_cred().getNodeIP()), node.getBs_cred().getNodePort()));
            logDetails("Sent UNREGISTER at " + getCurrentTime());
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    /**
     * @param neighbourCred Called when I'm 'JOIN'-ing
     */
    // done
    @Override
    public void join(Cred neighbourCred) {
        JoinReq joinReq = new JoinReq(node.getCred());
        String msg = joinReq.getMessageAsString(Constant.Task.JOIN);
        String uri = Constant.HTTP + neighbourCred.getNodeIP() + ":" + neighbourCred.getNodePort() + Constant.UrlPath.JOIN;
        String result = "";
        try {
            result = restTemplate.postForObject(uri, new Gson().toJson(joinReq), String.class);
            logDetails("Sent JOIN to " + neighbourCred.getNodeIP() + ":" + neighbourCred.getNodePort() + " at " + getCurrentTime());
        } catch (ResourceAccessException exception) {
            //connection refused to the api end point
            if (node.getRoutingTable().contains(neighbourCred)) {
                node.getRoutingTable().remove(neighbourCred);
                logDetails(neighbourCred.getNodeIP() + "node is not available. Removed it from routing table.");
            }
            //Todo: Remove this neighbour from stat table
        } catch (HttpServerErrorException exception){
            //
        }
        if (result.equals(Constant.Task.JOIN_OK)) {
            node.incReceivedQueryCount();
            node.getRoutingTable().add(neighbourCred);
            logDetails("Added " + neighbourCred.getNodeIP() + ":" + neighbourCred.getNodePort() + " to Routing Table");
        }
    }


    @Override
    public void joinMe(JoinReq joinReq) {
        logDetails("Received JOIN from " + joinReq.getJoinCred().getNodeIP() + ":" + joinReq.getJoinCred().getNodePort() + " at " + getCurrentTime());
        //check if already exist
        if (node.getRoutingTable().contains(joinReq.getJoinCred())) {
            logDetails("But he's already in...");
        } else {
            node.getRoutingTable().add(joinReq.getJoinCred());
            logDetails("Added " + joinReq.getJoinCred().getNodeIP() + ":" + joinReq.getJoinCred().getNodePort() + " to Routing Table");
        }

    }

    /**
     * Called when I'm 'LEAVE'-ing
     */
    @Override
    public void leave() {
        LeaveReq leaveReq = new LeaveReq(node.getCred());
        String msg = leaveReq.getMessageAsString(Constant.Task.LEAVE);
        for (Cred neighbourCred : node.getRoutingTable()) {
            String uri = Constant.HTTP + neighbourCred.getNodeIP() + ":" + neighbourCred.getNodePort() + Constant.UrlPath.LEAVE;
            try {
                String result = restTemplate.postForObject(uri, new Gson().toJson(leaveReq), String.class);
                if (result.equals("200")) {
                    logDetails("Neighbor left successfully at " + getCurrentTime());
                }
            } catch (ResourceAccessException exception) {
                //connection refused to the api end point
            } catch (HttpServerErrorException exception){
                //
            }
        }
    }

    /**
     * @param leaveReq Called when I amd listening and someone else sends me a 'leave'
     */
    @Override
    public void removeMe(LeaveReq leaveReq) {
        //check my routing table to see if leaveRequest exist
//        for (Credential credential : node.getRoutingTable()) {
        if (node.getRoutingTable().contains(leaveReq.getLeaveCred())) {
            node.getRoutingTable().remove(leaveReq.getLeaveCred());
        }
//        }
        removeFromStatTable(leaveReq.getLeaveCred());
        logDetails("Received LEAVE from " + leaveReq.getLeaveCred().getNodeName() + ":" + leaveReq.getLeaveCred().getNodePort() + " at " + getCurrentTime());
    }


    /**
     * @param searchReq
     * @param sendCredentials API Call to send search request to others
     */
    @Override
    public boolean search(SearchReq searchReq, Cred sendCredentials) {
        String msg = searchReq.getMessageAsString(Constant.Task.SEARCH);
        String uri = Constant.HTTP + sendCredentials.getNodeIP() + ":" + sendCredentials.getNodePort() + Constant.UrlPath.SEARCH;
        try {
            String result = restTemplate.postForObject(uri, new Gson().toJson(searchReq), String.class);
            if (result.equals("202")) {
                return true;
            }
            logDetails("Sent SEARCH for \"" + searchReq.getFileName() + "\" to " + sendCredentials.getNodeIP() + ":" + sendCredentials.getNodePort() + " at " + getCurrentTime());
        } catch (ResourceAccessException exception) {
            //connection refused to the api end point
            if (node.getRoutingTable().contains(sendCredentials)) {
                node.getRoutingTable().remove(sendCredentials);
                logDetails(sendCredentials.getNodeIP() + "node is not available and removed from routing table.");
            }
            //Todo: Remove this neighbour from stat table
        } catch (HttpServerErrorException exception){
            //
        }
        return false;
    }

    /**
     * @param searchRes API Call to send SEARCHOK to others
     */
    @Override
    public void searchOk(SearchRes searchRes) {
        node.incForwardedQueryCount();
        node.incAnsweredQueryCount();
        String msg = searchRes.getMessageAsString(Constant.Task.SEARCH_OK);

        String uri = Constant.HTTP + searchRes.getCred().getNodeIP() + ":" + searchRes.getCred().getNodePort() + Constant.UrlPath.SEARCH_OK;
        try {
            searchRes.setCred(node.getCred());
            String result = restTemplate.postForObject(uri, new Gson().toJson(searchRes), String.class);
            logDetails("Sent SEARCHOK to " + searchRes.getCred().getNodeIP() + ":" + searchRes.getCred().getNodePort() + " at " + getCurrentTime());
        } catch (ResourceAccessException exception) {
            //connection refused to the api end point
            if (node.getRoutingTable().contains(searchRes.getCred())) {
                node.getRoutingTable().remove(searchRes.getCred());
//                logMe(searchResponse.getCredential().getIp() + "node is not available and removed from routing table.");
            }
            //Todo: Remove this neighbour from stat table
        } catch (HttpServerErrorException exception){
            //
        }
    }

    /**
     * @param searchRes Called when I get a successful response from someone else for my search
     */
    @Override
    public void searchSuccess(SearchRes searchRes) {
        //update statTable
        int sequenceNo = searchRes.getSeqNo();
        QRecord qRecord = null;
        for (QRecord qr : node.getQueryTable()) {
            if (qr.getSeqNo() == sequenceNo) {
                qRecord = qr;
                break;
            }
        }
        if (qRecord != null) {
            String query = qRecord.getQuery();
            List<String> fileList = searchRes.getmList();

            NetworkRecord networkRecord = new NetworkRecord(query, qRecord.getTime(), new Date(), searchRes.getHopCount(), searchRes.getCred(), fileList);
            boolean isFileAlreadyReceived = false;
            for (NetworkRecord sr : node.getNetworkTable()) {
                if (sr.getQuery().equals(networkRecord.getQuery()) && sr.getsNode().equals(networkRecord.getsNode())) {
                    isFileAlreadyReceived = true;
                    break;
                }
            }
            if (!isFileAlreadyReceived) {
                node.getNetworkTable().add(networkRecord);
                node.getDetailsTable().get(query).addAll(fileList);
            }
            logDetails("\"" + networkRecord.getQuery() + "\" found at: " + searchRes.getCred().getNodeIP() + ":" + searchRes.getCred().getNodePort() + " at " + getCurrentTime());
        }
    }


    @Override
    public void processResponse(Message response) {
        if (response instanceof RegisterRes) {
            RegisterRes registerRes = (RegisterRes) response;
            if (registerRes.getNodeCount() == Constant.Codes.RegisterCodes.NODE_EXISTING_ERROR) {
                logDetails("Already registered at Bootstrap with same username\n");
                Cred cred = node.getCred();
                cred.setNodeName(UUID.randomUUID().toString());
                node.setCred(cred);
                register();
            } else if (registerRes.getNodeCount() == Constant.Codes.RegisterCodes.MULTIPLE_IP_ERROR) {
                logDetails("Already registered at Bootstrap with same port\n");
                Cred cred = node.getCred();
                cred.setNodePort(cred.getNodePort() + 1);
                node.setCred(cred);
                register();
            } else if (registerRes.getNodeCount() == Constant.Codes.RegisterCodes.REGISTER_ERROR) {
                logDetails("Can’t register. Bootstrap server full. Try again later\n");
            } else if (registerRes.getNodeCount() == Constant.Codes.RegisterCodes.ERROR) {
                logDetails("Error in command");
            } else {
                List<Cred> credList = registerRes.getCreds();
                for (Cred cred : credList) {
                    join(cred);
                }
                System.setProperty(Constant.S_PORT, String.valueOf(node.getCred().getNodePort()));
                this.registerOK = true;
            }

        } else if (response instanceof UnregisterRes) {
            //TODO: set leave request for all of the nodes at routing table
            node.setRoutingTable(new ArrayList<>());
            node.setNodeFileList(new ArrayList<>());
            node.setNetworkTable(new ArrayList<>());
            this.registerOK = false;
        }
    }

    @Override
    public boolean isRegisterOK() {
        return registerOK;
    }

    @Override
    public List<String> checkFilesInFileList(String fileName, List<String> fileList) {
        List<String> result = new ArrayList<>();
        for (String file : fileList){
            String temp = file.toLowerCase();
            List<String> tokens = new ArrayList<>();
            tokens.add(fileName.toLowerCase());
            Matcher matcher= Pattern.compile("\\b(" + StringUtils.join(tokens, "|") + ")\\b").matcher(temp);
            if (matcher.find()){
                result.add(file);
            }
        }
        return result;
    }

    @Override
    public List<NetworkRecord> checkFilesInStatTable(String fileName, List<NetworkRecord> statTable) {
        Pattern pattern = Pattern.compile(fileName);
        List<NetworkRecord> StatTableSearchResult = new ArrayList();
        for (NetworkRecord networkRecord : statTable) {
            if (pattern.matcher(networkRecord.getQuery()).find()) {
                StatTableSearchResult.add(networkRecord);
            }
        }
        return StatTableSearchResult;
    }


    /**
     * @param searchReq Make a new search request from local node
     */
    @Override
    public void triggerSearchRequest(SearchReq searchReq) {
        String query = searchReq.getFileName();
//        logMe("Sent SEARCH to others for \"" + query + "\" at " + getCurrentTime());
        node.incSearchedQueryCount();

        searchReq.setHops(searchReq.incHops());
        node.getQueryTable().add(new QRecord(searchReq.getSequenceNo(), query, new Date()));
        List<NetworkRecord> StatTableSearchResult = checkFilesInStatTable(query, node.getNetworkTable());
        // Send search request to stat table members
        for (NetworkRecord networkRecord : StatTableSearchResult) {
            if (networkRecord.getQuery().equals(searchReq.getFileName())) {
                Cred cred = networkRecord.getsNode();
                search(searchReq, cred);
//                logMe("Send SER request message to stat table member " + credential.getIp() + " : " + credential.getPort() + "\n");
            }
        }
        //TODO: Wait and see for stat members rather flooding whole routing table
        // Send search request to routing table members
        for (Cred cred : node.getRoutingTable()) {
            if (search(searchReq, cred)) {
                break;
            }
        }
    }


    /**
     * Check a search request from neighbour node and pass if needed
     */
    @Override
    public void passSearchRequest(SearchReq searchReq) {

//        if (searchRequest.getCredential().getIp() == node.getCredential().getIp() && searchRequest.getCredential().getPort() == node.getCredential().getPort()) {
        if (searchReq.getCredential().equals(node.getCred())) {
            return; // search query loop has eliminated
        }
        List<String> searchResult = checkFilesInFileList(searchReq.getFileName(), node.getNodeFileList());
        logDetails("Received SEARCH for \"" + searchReq.getFileName() + "\" from " + searchReq.getCredential().getNodeIP() + ":" + searchReq.getCredential().getNodePort() + " at " + getCurrentTime());
        if (!searchResult.isEmpty()) {
            SearchRes searchRes = new SearchRes(searchReq.getSequenceNo(), searchResult.size(), searchReq.getCredential(), searchReq.getHops(), searchResult);
            searchOk(searchRes);
        } else {
            //logMe("File is not available at " + node.getCredential().getIp() + " : " + node.getCredential().getPort() + "\n");
            if (searchReq.getHops() <= TTL) {
                searchReq.setHops(searchReq.incHops());
                List<NetworkRecord> StatTableSearchResult = checkFilesInStatTable(searchReq.getFileName(), node.getNetworkTable());
                // Send search request to stat table members
                for (NetworkRecord networkRecord : StatTableSearchResult) {
                    if (networkRecord.getQuery().equals(searchReq.getFileName())) {
                        Cred cred = networkRecord.getsNode();
                        node.incForwardedQueryCount();
                        search(searchReq, cred);
                    }
                }
                //TODO: Wait and see for stat members rather flooding whole routing table
                // Send search request to routing table members
                for (Cred cred : node.getRoutingTable()) {
                    node.incForwardedQueryCount();
                    if (search(searchReq, cred)) {
                        break;
                    }
                }
            } else {
//                logMe("Search request from" + searchRequest.getCredential().getIp() + ":" + searchRequest.getCredential().getPort() + "is blocked by hop TTL\n");
            }
        }
//        Thread.currentThread().interrupt();
    }

    @Override
    public void removeFromStatTable(Cred cred) {
        List<NetworkRecord> statTable = node.getNetworkTable();
        for (NetworkRecord networkRecord : statTable) {
            if (cred.equals(networkRecord.getsNode())) {
                statTable.remove(networkRecord);
            }
        }
    }

    @Override
    public void logDetails(String log) {
        this.detailsLog.add(log);
        this.logFlag = true;
        System.out.println(log);
    }

    @Override
    public boolean download(DownloadReq downloadReq, Cred sendCredentials) {
        String msg = downloadReq.getMessageAsString(Constant.Task.DOWNLOAD);
        String uri = Constant.HTTP + sendCredentials.getNodeIP() + ":" + sendCredentials.getNodePort() + Constant.UrlPath.DOWNLOAD;
        try {
            String result = restTemplate.postForObject(uri, new Gson().toJson(downloadReq), String.class);
            if (result.equals("202")) {
                System.out.println("returning true");
                return true;
            }
            logDetails("Sent Download for \"" + downloadReq.getFileName() + "\" to " + sendCredentials.getNodeIP() + ":" + sendCredentials.getNodePort() + " at " + getCurrentTime());
        } catch (ResourceAccessException exception) {
            //connection refused to the api end point

                logDetails(sendCredentials.getNodeIP() + "node is not available and removed from routing table.");
            //Todo: Remove this neighbour from stat table
        } catch (HttpServerErrorException exception){
            logDetails(sendCredentials.getNodeIP() + "connection issue.");
            //
        }
        return false;
    }

    @Override
    public void downloadOK(DownloadRes downloadRes) {

        String msg = downloadRes.getMessageAsString(Constant.Task.DOWNLOAD_OK);

        String uri = Constant.HTTP + downloadRes.getDownloadCred().getNodeIP() + ":" + downloadRes.getDownloadCred().getNodePort() + Constant.UrlPath.DOWNLOAD_OK;
        try {
            downloadRes.setDownloadCred(node.getCred());
            String result = restTemplate.postForObject(uri, new Gson().toJson(downloadRes), String.class);
            logDetails("Sent DOWNLOADOK to " + downloadRes.getDownloadCred().getNodeIP() + ":" + downloadRes.getDownloadCred().getNodePort() + " at " + getCurrentTime());
        } catch (ResourceAccessException exception) {
            //connection refused to the api end point
            if (node.getRoutingTable().contains(downloadRes.getDownloadCred())) {
                node.getRoutingTable().remove(downloadRes.getDownloadCred());
//                logMe(searchResponse.getCredential().getIp() + "node is not available and removed from routing table.");
            }
            //Todo: Remove this neighbour from stat table
        } catch (HttpServerErrorException exception){
            //
        }
    }

    @Override
    public void downloadSuccess(DownloadRes downloadRes) {
        //update statTable
        String hash = downloadRes.getHash();
        int fileSize = downloadRes.getFileSize();

        logDetails("\"" + hash + " " + fileSize +" found at: " + downloadRes.getDownloadCred().getNodeIP() + ":" + downloadRes.getDownloadCred().getNodePort() + " at " + getCurrentTime());

    }
    @Override
    public void triggerDownloadRequest(DownloadReq downloadReq, Cred downloadCredentials) {
        String query = downloadReq.getFileName();
//        logMe("Sent SEARCH to others for \"" + query + "\" at " + getCurrentTime());

        download(downloadReq, downloadCredentials);


    }

    public String getCurrentTime() {
        return new SimpleDateFormat("HH:mm:ss").format(Calendar.getInstance().getTime());
    }

}