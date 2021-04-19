package org.example.com.ds.api.message.res;

import org.example.com.ds.api.Cred;
import org.example.com.ds.api.message.Message;

public class DownloadRes extends Message {
    private String hash;
    private int fileSize;
    private Cred downloadCred;

    public String getHash() {
        return hash;
    }

    public int getFileSize() {
        return fileSize;
    }

    public DownloadRes(Cred downloadCred, String hash, int fileSize) {
        this.hash = hash;
        this.fileSize = fileSize;
        this.downloadCred = downloadCred;

    }






    public Cred getDownloadCred() {
        return downloadCred;
    }

    public void setDownloadCred(Cred downloadCred) {
        this.downloadCred = downloadCred;
    }


    @Override
    public String getMessageAsString(String message) {
        message += " " +this.hash + " " + this.fileSize+ " " + this.getDownloadCred().getNodeIP() + " " + this.getDownloadCred().getNodePort() + " " ;

        return super.getMessageAsString(message);
    }

    @Override
    public String toString() {
        String response = "Search Results:" +
                "\nHash: " + this.hash +
                "\nFile Size: " + fileSize +
                "\nIP: " + this.getDownloadCred().getNodeIP() +
                "\nPort: " + this.getDownloadCred().getNodePort();

        return response;
    }
}
