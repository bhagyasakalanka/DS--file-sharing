package org.uom.cse.cs4262.controller;

import com.google.gson.Gson;
import org.apache.commons.lang.StringUtils;
import org.springframework.web.client.HttpServerErrorException;
import org.springframework.web.client.ResourceAccessException;
import org.springframework.web.client.RestTemplate;
import org.uom.cse.cs4262.File;
import org.uom.cse.cs4262.api.*;
import org.uom.cse.cs4262.api.message.Message;
import org.uom.cse.cs4262.api.message.request.*;
import org.uom.cse.cs4262.api.message.response.DownloadRes;
import org.uom.cse.cs4262.api.message.response.RegisterRes;
import org.uom.cse.cs4262.api.message.response.SearchRes;
import org.uom.cse.cs4262.api.message.response.UnregisterRes;
import org.uom.cse.cs4262.feature.ConnectionHandler;

import java.io.IOException;
import java.net.DatagramPacket;
import java.net.DatagramSocket;
import java.net.InetAddress;
import java.net.SocketException;
import java.security.NoSuchAlgorithmException;
import java.text.SimpleDateFormat;
import java.util.*;
import java.util.regex.Matcher;
import java.util.regex.Pattern;


public class NodeOperationsImpl implements NodeOperations, Runnable {

    RestTemplate restTemplate = new RestTemplate();
    private Node node;
    private DatagramSocket socket;
    private boolean registerOk = false;
    private int TTL = 5;
    private List<String> logDisplay;
    private boolean logFlag;

    public NodeOperationsImpl(Node node) {
        this.node = node;
        this.logDisplay = new ArrayList<>();
    }

    public Node getNode() {
        return node;
    }

    public List<String> getLogDisplay() {
        return logDisplay;
    }

    public boolean isLogFlag() {
        return logFlag;
    }

    public void setLogFlag(boolean logFlag) {
        this.logFlag = logFlag;
    }

    @Override
    public void run() {
        logMe("Server " + this.node.getCred().getNodeName() + " created at " + this.node.getCred().getNodePort() + ". Waiting for incoming data...\n");
        byte buffer[];
        DatagramPacket datagramPacket;
        while (true) {
            buffer = new byte[65536];
            datagramPacket = new DatagramPacket(buffer, buffer.length);
            try {
                socket.receive(datagramPacket);
                String message = new String(datagramPacket.getData(), 0, datagramPacket.getLength());
                Message response = ConnectionHandler.handle(message);
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
        String msg = registerReq.getMessageAsString(Constant.Action.REGISTER);
        try {
            socket.send(new DatagramPacket(msg.getBytes(), msg.getBytes().length, InetAddress.getByName(node.getBs().getNodeIp()), node.getBs().getNodePort()));
            logMe("Sent REGISTER at " + getCurrentTime());
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    // done
    @Override
    public void unRegister() {
        UnregisterReq unregisterReq = new UnregisterReq(node.getCred());
        String msg = unregisterReq.getMessageAsString(Constant.Action.UNREGISTER);
        try {
            socket.send(new DatagramPacket(msg.getBytes(), msg.getBytes().length, InetAddress.getByName(node.getBs().getNodeIp()), node.getBs().getNodePort()));
            logMe("Sent UNREGISTER at " + getCurrentTime());
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
        String msg = joinReq.getMessageAsString(Constant.Action.JOIN);
        String uri = Constant.HTTP + neighbourCred.getNodeIp() + ":" + neighbourCred.getNodePort() + Constant.EndPoint.JOIN;
        String result = "";
        try {
            result = restTemplate.postForObject(uri, new Gson().toJson(joinReq), String.class);
            logMe("Sent JOIN to " + neighbourCred.getNodeIp() + ":" + neighbourCred.getNodePort() + " at " + getCurrentTime());
        } catch (ResourceAccessException exception) {
            //connection refused to the api end point
            if (node.getRoutingTable().contains(neighbourCred)) {
                node.getRoutingTable().remove(neighbourCred);
                logMe(neighbourCred.getNodeIp() + "node is not available. Removed it from routing table.");
            }
            //Todo: Remove this neighbour from stat table
        } catch (HttpServerErrorException exception){
            //
        }
        if (result.equals(Constant.Action.JOIN_OK)) {
            node.incReceivedQueryCount();
            node.getRoutingTable().add(neighbourCred);
            logMe("Added " + neighbourCred.getNodeIp() + ":" + neighbourCred.getNodePort() + " to Routing Table");
        }
    }

    /**
     * @param joinReq Called when I'm listening and someone else sends me a 'join'
     */
    @Override
    public void joinMe(JoinReq joinReq) {
        logMe("Received JOIN from " + joinReq.getCred().getNodeIp() + ":" + joinReq.getCred().getNodePort() + " at " + getCurrentTime());
        //check if already exist
        if (node.getRoutingTable().contains(joinReq.getCred())) {
            logMe("But he's already in...");
        } else {
            node.getRoutingTable().add(joinReq.getCred());
            logMe("Added " + joinReq.getCred().getNodeIp() + ":" + joinReq.getCred().getNodePort() + " to Routing Table");
        }

    }

    /**
     * Called when I'm 'LEAVE'-ing
     */
    @Override
    public void leave() {
        LeaveReq leaveReq = new LeaveReq(node.getCred());
        String msg = leaveReq.getMessageAsString(Constant.Action.LEAVE);
        for (Cred neighbourCred : node.getRoutingTable()) {
            String uri = Constant.HTTP + neighbourCred.getNodeIp() + ":" + neighbourCred.getNodePort() + Constant.EndPoint.LEAVE;
            try {
                String result = restTemplate.postForObject(uri, new Gson().toJson(leaveReq), String.class);
                if (result.equals("200")) {
                    logMe("Neighbor left successfully at " + getCurrentTime());
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
        if (node.getRoutingTable().contains(leaveReq.getCred())) {
            node.getRoutingTable().remove(leaveReq.getCred());
        }
//        }
        removeFromStatTable(leaveReq.getCred());
        logMe("Received LEAVE from " + leaveReq.getCred().getNodeName() + ":" + leaveReq.getCred().getNodePort() + " at " + getCurrentTime());
    }


    /**
     * @param searchReq
     * @param sendCredentials API Call to send search request to others
     */
    @Override
    public boolean search(SearchReq searchReq, Cred sendCredentials) {
        String uri = Constant.HTTP + sendCredentials.getNodeIp() + ":" + sendCredentials.getNodePort() + Constant.EndPoint.SEARCH;
        System.out.println(uri);
        try {
            String result = restTemplate.postForObject(uri, new Gson().toJson(searchReq), String.class);
            if (result.equals("202")) {
                return true;
            }
            logMe("Sent SEARCH for \"" + searchReq.getFileName() + "\" to " + sendCredentials.getNodeIp() + ":" + sendCredentials.getNodePort() + " at " + getCurrentTime());
        } catch (ResourceAccessException exception) {
            //connection refused to the api end point
            if (node.getRoutingTable().contains(sendCredentials)) {
                node.getRoutingTable().remove(sendCredentials);
                logMe(sendCredentials.getNodeIp() + "node is not available and removed from routing table.");
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
        String msg = searchRes.getMessageAsString(Constant.Action.SEARCH_OK);

        String uri = Constant.HTTP + searchRes.getCred().getNodeIp() + ":" + searchRes.getCred().getNodePort() + Constant.EndPoint.SEARCH_OK;
        try {
            searchRes.setCred(node.getCred());
            String result = restTemplate.postForObject(uri, new Gson().toJson(searchRes), String.class);
            logMe("Sent SEARCHOK to " + searchRes.getCred().getNodeIp() + ":" + searchRes.getCred().getNodePort() + " at " + getCurrentTime());
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
        int sequenceNo = searchRes.getSeqNumber();
        QueryDetails queryDetails = null;
        for (QueryDetails qr : node.getQueryTable()) {
            if (qr.getSeqNumber() == sequenceNo) {
                queryDetails = qr;
                break;
            }
        }
        if (queryDetails != null) {
            String query = queryDetails.getQuery();
            List<String> fileList = searchRes.getFileList();
//            List<NetworkDetails> existingNetworkDetails = checkFilesInStatTable(query, node.getNetworkDetailsTable());
            NetworkDetails networkDetails = new NetworkDetails(query, queryDetails.getTrigTime(), new Date(), searchRes.getHopCount(), searchRes.getCred(), fileList);
            boolean isFileAlreadyReceived = false;
            for (NetworkDetails sr : node.getNetworkDetailsTable()) {
                if (sr.getQuery().equals(networkDetails.getQuery()) && sr.getServedNode().equals(networkDetails.getServedNode())) {
                    isFileAlreadyReceived = true;
                    break;
                }
            }
            if (!isFileAlreadyReceived) {
                node.getNetworkDetailsTable().add(networkDetails);
                node.getDetailsTable().get(query).addAll(fileList);
                NetworkDetails networkDetails2 = new NetworkDetails(fileList.get(0), queryDetails.getTrigTime(), new Date(), searchRes.getHopCount(), searchRes.getCred(), fileList);
                node.getCachedFilesWithOriginalFileNameTable().add(networkDetails2);
            }
            logMe("\"" + networkDetails.getQuery() + "\" found at: " + searchRes.getCred().getNodeIp() + ":" + searchRes.getCred().getNodePort() + " at " + getCurrentTime());
        }
    }


    @Override
    public void processResponse(Message response) {
        if (response instanceof RegisterRes) {
            RegisterRes registerRes = (RegisterRes) response;
            if (registerRes.getTotalNumberOfNodes() == Constant.ErrorCode.Register.ALREADY_REGISTERED_ERROR) {
                logMe("Already registered at Bootstrap with same username\n");
                Cred cred = node.getCred();
                cred.setNodeName(UUID.randomUUID().toString());
                node.setCred(cred);
                register();
            } else if (registerRes.getTotalNumberOfNodes() == Constant.ErrorCode.Register.DUPLICATE_IP_ERROR) {
                logMe("Already registered at Bootstrap with same port\n");
                Cred cred = node.getCred();
                cred.setNodePort(cred.getNodePort() + 1);
                node.setCred(cred);
                register();
            } else if (registerRes.getTotalNumberOfNodes() == Constant.ErrorCode.Register.REGISTER_ERROR) {
                logMe("Can’t register. Bootstrap server full. Try again later\n");
            } else if (registerRes.getTotalNumberOfNodes() == Constant.ErrorCode.Register.COMMAND_ERROR) {
                logMe("Error in command");
            } else {
                List<Cred> credList = registerRes.getCreds();
                for (Cred cred : credList) {
                    join(cred);
                }
                System.setProperty(Constant.SERVER_PORT, String.valueOf(node.getCred().getNodePort()));
                this.registerOk = true;
            }

        } else if (response instanceof UnregisterRes) {
            //TODO: set leave request for all of the nodes at routing table
            node.setRoutingTable(new ArrayList<>());
            node.setFileList(new ArrayList<>());
            node.setNetworkDetailsTable(new ArrayList<>());
            this.registerOk = false;
        }
    }

    @Override
    public boolean isRegisterOk() {
        return registerOk;
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
    public List<NetworkDetails> checkFilesInStatTable(String fileName, List<NetworkDetails> statTable) {
        Pattern pattern = Pattern.compile(fileName);
        List<NetworkDetails> StatTableSearchResult = new ArrayList();
        for (NetworkDetails networkDetails : statTable) {
            if (pattern.matcher(networkDetails.getQuery()).find()) {
                StatTableSearchResult.add(networkDetails);
            }
        }
        return StatTableSearchResult;
    }

    public List<NetworkDetails> checkFilesInStatTableForOriginal(String fileName, List<NetworkDetails> statTable) {
        List<NetworkDetails> StatTableSearchResult = new ArrayList();
        for (NetworkDetails networkDetails : statTable) {
            Pattern pattern = Pattern.compile(networkDetails.getQuery());
            if (pattern.matcher(networkDetails.getQuery()).find()) {
                StatTableSearchResult.add(networkDetails);

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

        searchReq.setHopCount(searchReq.incHops());
        node.getQueryTable().add(new QueryDetails(searchReq.getSeqNumber(), query, new Date()));
        List<NetworkDetails> StatTableSearchResult = checkFilesInStatTable(query, node.getNetworkDetailsTable());
        // Send search request to stat table members
        for (NetworkDetails networkDetails : StatTableSearchResult) {
            if (networkDetails.getQuery().equals(searchReq.getFileName())) {
                Cred cred = networkDetails.getServedNode();
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
        if (searchReq.getCred().equals(node.getCred())) {
            return; // search query loop has eliminated
        }
        List<String> searchResult = checkFilesInFileList(searchReq.getFileName(), node.getFileList());
        logMe("Received SEARCH for \"" + searchReq.getFileName() + "\" from " + searchReq.getCred().getNodeIp() + ":" + searchReq.getCred().getNodePort() + " at " + getCurrentTime());
        if (!searchResult.isEmpty()) {
            SearchRes searchRes = new SearchRes(searchReq.getSeqNumber(), searchResult.size(), searchReq.getCred(), searchReq.getHopCount(), searchResult);
            searchOk(searchRes);
        } else {
            //logMe("File is not available at " + node.getCredential().getIp() + " : " + node.getCredential().getPort() + "\n");
            if (searchReq.getHopCount() <= TTL) {
                searchReq.setHopCount(searchReq.incHops());
                List<NetworkDetails> StatTableSearchResult = checkFilesInStatTable(searchReq.getFileName(), node.getNetworkDetailsTable());
                // Send search request to stat table members
                for (NetworkDetails networkDetails : StatTableSearchResult) {
                    if (networkDetails.getQuery().equals(searchReq.getFileName())) {
                        Cred cred = networkDetails.getServedNode();
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
    public void triggerDownloadRequest(DownloadReq downloadReq, Cred servedCred) {
        download(downloadReq, servedCred);
    }


    /**
     * Check a search request from neighbour node and pass if needed
     */
    @Override
    public void passDownloadRequest(DownloadReq downloadReq) {

//        if (searchRequest.getCredential().getIp() == node.getCredential().getIp() && searchRequest.getCredential().getPort() == node.getCredential().getPort()) {
        if (downloadReq.getCred().equals(node.getCred())) {
            return; // search query loop has eliminated
        }
        List<String> mySearchResults = checkFilesInFileList(downloadReq.getFileName(), node.getFileList());

        File file = node.getDownloadTable().get(mySearchResults.get(0));

        DownloadRes downloadRes = new DownloadRes(downloadReq.getCred(), downloadReq.getFileName(), file.getFileHash(), file.getFileSize());
        downloadOk(downloadRes);
    }

    @Override
    public boolean download(DownloadReq downloadReq, Cred sendCredentials) {
        String uri = Constant.HTTP + sendCredentials.getNodeIp() + ":" + sendCredentials.getNodePort() + Constant.EndPoint.DOWNLOAD;
        try {
            String result = restTemplate.postForObject(uri, new Gson().toJson(downloadReq), String.class);
            if (result.equals("202")) {
                return true;
            }
            logMe("Sent DOWNLOAD for \"" + downloadReq.getFileName() + "\" to " + sendCredentials.getNodeIp() + ":" + sendCredentials.getNodePort() + " at " + getCurrentTime());
        } catch (ResourceAccessException exception) {
            //connection refused to the api end point
            exception.printStackTrace();
            //Todo: Remove this neighbour from stat table
        } catch (HttpServerErrorException exception){
            exception.printStackTrace();
            //
        }
        return false;
    }

    @Override
    public void downloadOk(DownloadRes downloadRes) {

        String uri = Constant.HTTP + downloadRes.getCred().getNodeIp() + ":" + downloadRes.getCred().getNodePort() + Constant.EndPoint.DOWNLOAD_OK;
        try {
            downloadRes.setCred(node.getCred());
            String result = restTemplate.postForObject(uri, new Gson().toJson(downloadRes), String.class);
            logMe("Sent SEARCHOK to " + downloadRes.getCred().getNodeIp() + ":" + downloadRes.getCred().getNodePort() + " at " + getCurrentTime());
        } catch (ResourceAccessException exception) {
            //connection refused to the api end point
            exception.printStackTrace();
            //Todo: Remove this neighbour from stat table
        } catch (HttpServerErrorException exception){
            //
            exception.printStackTrace();
        }
    }

    @Override
    public void downloadSuccess(DownloadRes downloadRes) {
        //update statTable
        node.addToDownloadTable(downloadRes.getFileName(), downloadRes.getHash(), downloadRes.getFileSize());
        File file = node.getDownloadTable().get(downloadRes.getFileName());
        logMe("Download " + downloadRes.getFileName()+ " from " + downloadRes.getCred().getNodeIp() + ":" + downloadRes.getCred().getNodePort() + " at " + getCurrentTime());
        logMe("\t"+file.getFileDetails());
    }



    @Override
    public void removeFromStatTable(Cred cred) {
        List<NetworkDetails> statTable = node.getNetworkDetailsTable();
        for (NetworkDetails networkDetails : statTable) {
            if (cred.equals(networkDetails.getServedNode())) {
                statTable.remove(networkDetails);
            }
        }
    }

    @Override
    public void logMe(String log) {
        this.logDisplay.add(log);
        this.logFlag = true;
        System.out.println(log);
    }


    public String getCurrentTime() {
        return new SimpleDateFormat("HH:mm:ss").format(Calendar.getInstance().getTime());
    }

}