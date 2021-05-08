package org.uom.cse.cs4262.api.message.response;

import org.uom.cse.cs4262.api.Cred;
import org.uom.cse.cs4262.api.message.Message;

import java.util.List;


public class SearchRes extends Message {

    private int seqNumber;
    private int totalNumberOfFiles;
    private Cred cred;
    private int hopCount;
    private List<String> fileList;

    public SearchRes(int seqNumber, int totalNumberOfFiles, Cred cred, int hopCount, List<String> fileList) {
        this.seqNumber = seqNumber;
        this.totalNumberOfFiles = totalNumberOfFiles;
        this.cred = cred;
        this.hopCount = hopCount;
        this.fileList = fileList;
    }

    @Override
    public String getMessageAsString(String message) {
        message += " " + seqNumber + " " + this.getTotalNumberOfFiles() + " " + this.getCred().getNodeIp() + " " + this.getCred().getNodePort() + " " + this.getHopCount();
        for (String file : fileList) {
            message += " " + file;
        }
        return super.getMessageAsString(message);
    }

    @Override
    public String toString() {
        String response = "Search Results:" +
                "\nSequence No: " + this.getSeqNumber() +
                "\nNo of files: " + fileList.size() +
                "\nIP: " + this.getCred().getNodeIp() +
                "\nPort: " + this.getCred().getNodePort() +
                "\nHop count: " + this.getHopCount();
        for (int i = 1; i <= fileList.size(); i++) {
            response += "\nFile " + i + ": " + fileList.get(i - 1);
        }

        return response;
    }

    public int getTotalNumberOfFiles() {
        return totalNumberOfFiles;
    }

    public void setTotalNumberOfFiles(int totalNumberOfFiles) {
        this.totalNumberOfFiles = totalNumberOfFiles;
    }

    public Cred getCred() {
        return cred;
    }

    public void setCred(Cred cred) {
        this.cred = cred;
    }

    public int getSeqNumber() {
        return seqNumber;
    }

    public void setSeqNumber(int seqNumber) {
        this.seqNumber = seqNumber;
    }

    public int getHopCount() {
        return hopCount;
    }

    public void setHopCount(int hopCount) {
        this.hopCount = hopCount;
    }

    public List<String> getFileList() {
        return fileList;
    }

    public void setFileList(List<String> fileList) {
        this.fileList = fileList;
    }


}
