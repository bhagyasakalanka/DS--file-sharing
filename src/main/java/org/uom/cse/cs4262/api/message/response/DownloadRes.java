package org.uom.cse.cs4262.api.message.response;

import org.uom.cse.cs4262.api.Cred;
import org.uom.cse.cs4262.api.message.Message;

public class DownloadRes extends Message {
    private String hash;
    private String fileName;

    public void setHash(String hash) {
        this.hash = hash;
    }

    public void setFileSize(int fileSize) {
        this.fileSize = fileSize;
    }

    private int fileSize;
    private Cred cred;

    public String getHash() {
        return hash;
    }

    public int getFileSize() {
        return fileSize;
    }

    public DownloadRes(Cred cred, String fileName, String hash, int fileSize) {
        this.hash = hash;
        this.fileSize = fileSize;
        this.cred = cred;
        this.fileName = fileName;

    }

    public String getFileName() {
        return fileName;
    }

    public Cred getCred() {
        return cred;
    }

    public void setCred(Cred cred) {
        this.cred = cred;
    }


    @Override
    public String getMessageAsString(String message) {
        message += " " +this.hash + " " + this.fileSize+ " " + this.getCred().getNodeIp() + " " + this.getCred().getNodePort() + " " ;

        return super.getMessageAsString(message);
    }

    @Override
    public String toString() {
        String response = "Search Results:" +
                "\nHash: " + this.hash +
                "\nFile Size: " + fileSize +
                "\nIP: " + this.getCred().getNodeIp() +
                "\nPort: " + this.getCred().getNodePort();

        return response;
    }
}
