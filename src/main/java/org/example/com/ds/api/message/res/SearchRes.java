package org.example.com.ds.api.message.res;

import org.example.com.ds.api.Cred;
import org.example.com.ds.api.message.Message;

import java.util.List;


public class SearchRes extends Message {

    private int seqNo;
    private int fileCount;
    private Cred cred;
    private int hopCount;
    private List<String> mList;

    public SearchRes(int seqNo, int fileCount, Cred cred, int hopCount, List<String> mList) {
        this.seqNo = seqNo;
        this.fileCount = fileCount;
        this.cred = cred;
        this.hopCount = hopCount;
        this.mList = mList;
    }
    @Override
    public String getMessageAsString(String message) {
        message += " " + seqNo + " " + this.getFileCount() + " " + this.getCred().getNodeIP() + " " + this.getCred().getNodePort() + " " + this.getHopCount();
        for (String file : mList) {
            message += " " + file;
        }
        return super.getMessageAsString(message);
    }

    @Override
    public String toString() {
        String response = "Search Results:" +
                "\nSequence Number: " + this.getSeqNo() +
                "\nNumber of files: " + mList.size() +
                "\nIP Address: " + this.getCred().getNodeIP() +
                "\nPort Number: " + this.getCred().getNodePort() +
                "\nHop count: " + this.getHopCount();
        for (int i = 1; i <= mList.size(); i++) {
            response += "\nFile " + i + ": " + mList.get(i - 1);
        }

        return response;
    }
    public int getFileCount() {
        return fileCount;
    }

    public Cred getCred() {
        return cred;
    }

    public void setCred(Cred cred) {
        this.cred = cred;
    }

    public int getSeqNo() {
        return seqNo;
    }

    public int getHopCount() {
        return hopCount;
    }

    public List<String> getmList() {
        return mList;
    }


}
