package org.example.com.ds.api;

import org.example.com.ds.api.message.req.DownloadReq;
import org.example.com.ds.api.message.req.SearchReq;
import org.example.com.ds.api.message.res.DownloadRes;
import org.example.com.ds.api.message.res.SearchRes;
import org.example.com.ds.api.message.Message;
import org.example.com.ds.api.message.req.JoinReq;
import org.example.com.ds.api.message.req.LeaveReq;

import java.util.List;



public interface NodeOperations {

    void start();

    void register();

    void unRegister();

    void join(Cred neighbourCred);

    void leave();

    void removeMe(LeaveReq leaveReq);

    void joinMe(JoinReq joinReq);

    boolean search(SearchReq searchReq, Cred sendCred);

    void searchOk(SearchRes searchRes);

    void searchSuccess(SearchRes searchRes);

    void processResponse(Message response);

    boolean isRegisterOK();

    List<String> checkFilesInFileList(String fileName, List<String> fileList);

    List<NetworkRecord> checkFilesInStatTable(String fileName, List<NetworkRecord> statTable);

    void triggerSearchRequest(SearchReq searchReq);

    void passSearchRequest(SearchReq searchReq);

    void removeFromStatTable(Cred cred);

    void logDetails(String log);

    boolean download(DownloadReq downloadReq, Cred sendCredentials);
    void downloadOK(DownloadRes downloadRes);
    void downloadSuccess(DownloadRes downloadRes);
    void triggerDownloadRequest(DownloadReq downloadReq, Cred downloadCredentials);
}
