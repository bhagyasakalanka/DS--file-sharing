package org.uom.cse.cs4262.api.message.request;

import org.uom.cse.cs4262.api.Cred;
import org.uom.cse.cs4262.api.message.Message;


public class SearchReq extends Message {

    private int seqNumber;
    private Cred triggeredCred;
    private String fileName;
    private int hopCount;

    public SearchReq(int seqNumber, Cred triggeredCred, String fileName, int hopCount) {
        this.seqNumber = seqNumber;
        this.triggeredCred = triggeredCred;
        this.fileName = fileName;
        this.hopCount = hopCount;
    }

    @Override
    public String getMessageAsString(String message) {
        message += " " + seqNumber + " " + this.getCred().getNodeIp() + " " + this.getCred().getNodePort() + " " + this.getFileName() + " " + this.getHopCount();
        return super.getMessageAsString(message);
    }

    public Cred getCred() {
        return triggeredCred;
    }

    public void setCredential(Cred cred) {
        this.triggeredCred = cred;
    }

    public int getSeqNumber() {
        return seqNumber;
    }

    public void setSeqNumber(int seqNumber) {
        this.seqNumber = seqNumber;
    }

    public String getFileName() {
        return fileName;
    }

    public void setFileName(String fileName) {
        this.fileName = fileName;
    }

    public int getHopCount() {
        return hopCount;
    }

    public void setHopCount(int hopCount) {
        this.hopCount = hopCount;
    }


    public int incHops() {
        return ++hopCount;
    }
}