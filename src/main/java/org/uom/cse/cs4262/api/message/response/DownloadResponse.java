package org.uom.cse.cs4262.api.message.response;

import org.uom.cse.cs4262.api.Credential;
import org.uom.cse.cs4262.api.message.Message;

import java.util.List;

public class DownloadResponse extends Message {
    private String hash;
    private int fileSize;
    private Credential credential;

    public String getHash() {
        return hash;
    }

    public int getFileSize() {
        return fileSize;
    }

    public DownloadResponse(Credential credential, String hash, int fileSize) {
        this.hash = hash;
        this.fileSize = fileSize;
        this.credential = credential;

    }






    public Credential getCredential() {
        return credential;
    }

    public void setCredential(Credential credential) {
        this.credential = credential;
    }


    @Override
    public String getMessageAsString(String message) {
        message += " " +this.hash + " " + this.fileSize+ " " + this.getCredential().getIp() + " " + this.getCredential().getPort() + " " ;

        return super.getMessageAsString(message);
    }

    @Override
    public String toString() {
        String response = "Search Results:" +
                "\nHash: " + this.hash +
                "\nFile Size: " + fileSize +
                "\nIP: " + this.getCredential().getIp() +
                "\nPort: " + this.getCredential().getPort();

        return response;
    }
}
