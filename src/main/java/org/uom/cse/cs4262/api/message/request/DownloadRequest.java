package org.uom.cse.cs4262.api.message.request;

import org.uom.cse.cs4262.api.Credential;
import org.uom.cse.cs4262.api.message.Message;

public class DownloadRequest extends Message {


    private Credential triggeredCredential;
    private String fileName;


    public DownloadRequest(Credential triggeredCredential, String fileName){

        this.triggeredCredential = triggeredCredential;
        this.fileName = fileName;

    }

    public Credential getCredential() {
        return triggeredCredential;
    }

    public void setCredential(Credential credential) {
        this.triggeredCredential = credential;
    }


    public String getFileName() {
        return fileName;
    }

    @Override
    public String getMessageAsString(String message) {
        message +=  this.getCredential().getIp() + " " + this.getCredential().getPort() + " " + this.getFileName() + " " + "download request";
        return super.getMessageAsString(message);
    }

}
