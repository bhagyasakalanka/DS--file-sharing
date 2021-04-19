package org.example.com.ds.api.message.req;

import org.example.com.ds.api.Cred;
import org.example.com.ds.api.message.Message;


public class SearchReq extends Message {

    private int sequenceNo;
    private Cred triggeredCred;
    private String fileName;
    private int hops;

    public SearchReq(int sequenceNo, Cred triggeredCred, String fileName, int hops) {
        this.sequenceNo = sequenceNo;
        this.triggeredCred = triggeredCred;
        this.fileName = fileName;
        this.hops = hops;
    }
    @Override
    public String getMessageAsString(String message) {
        message += " " + sequenceNo + " " + this.getCredential().getNodeIP() + " " + this.getCredential().getNodePort() + " " + this.getFileName() + " " + this.getHops();
        return super.getMessageAsString(message);
    }

    public Cred getCredential() {
        return triggeredCred;
    }

    public void setCredential(Cred cred) {
        this.triggeredCred = cred;
    }

    public int getSequenceNo() {
        return sequenceNo;
    }

    public String getFileName() {
        return fileName;
    }

    public int getHops() {
        return hops;
    }

    public void setHops(int hops) {
        this.hops = hops;
    }

    public int incHops() {
        return ++hops;
    }



}