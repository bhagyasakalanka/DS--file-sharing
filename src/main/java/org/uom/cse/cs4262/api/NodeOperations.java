package org.uom.cse.cs4262.api;

import org.uom.cse.cs4262.api.message.Message;
import org.uom.cse.cs4262.api.message.request.DownloadReq;
import org.uom.cse.cs4262.api.message.request.JoinReq;
import org.uom.cse.cs4262.api.message.request.LeaveReq;
import org.uom.cse.cs4262.api.message.request.SearchReq;
import org.uom.cse.cs4262.api.message.response.DownloadRes;
import org.uom.cse.cs4262.api.message.response.SearchRes;

import java.security.NoSuchAlgorithmException;
import java.util.List;



public interface NodeOperations {

    void start();

    void register();

    void unRegister();

    void join(Cred neighbourCred);

//    void joinOk(Credential senderCredential);

    void leave();

    void removeMe(LeaveReq leaveReq);

    void joinMe(JoinReq joinReq);

//    void leaveOk(Credential senderCredential);

    boolean search(SearchReq searchReq, Cred sendCred);

    void searchOk(SearchRes searchRes);

    void searchSuccess(SearchRes searchRes);

    void processResponse(Message response);

//    void error(Credential senderCredential);

    boolean isRegisterOk();

    List<String> checkFilesInFileList(String fileName, List<String> fileList);

    List<NetworkDetails> checkFilesInStatTable(String fileName, List<NetworkDetails> statTable);

    void triggerSearchRequest(SearchReq searchReq);

    void passSearchRequest(SearchReq searchReq);

//    void printRoutingTable(List<Credential> routingTable);

    void triggerDownloadRequest(DownloadReq downloadReq, Cred servedCred);

    void passDownloadRequest(DownloadReq downloadReq);

    void removeFromStatTable(Cred cred);

    void logMe(String log);

    boolean download(DownloadReq downloadReq, Cred sendCredentials);
    void downloadOk(DownloadRes downloadRes);

    void downloadSuccess(DownloadRes downloadRes);


}
