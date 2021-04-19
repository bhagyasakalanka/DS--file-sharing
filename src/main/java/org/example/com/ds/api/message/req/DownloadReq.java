package org.example.com.ds.api.message.req;

import org.example.com.ds.api.Cred;
import org.example.com.ds.api.message.Message;

public class DownloadReq extends Message {


    private Cred triggeredCred;
    private String fileName;


    public DownloadReq(Cred triggeredCred, String fileName){

        this.triggeredCred = triggeredCred;
        this.fileName = fileName;

    }

    public Cred getCredential() {
        return triggeredCred;
    }

    public void setCredential(Cred cred) {
        this.triggeredCred = cred;
    }


    public String getFileName() {
        return fileName;
    }

    @Override
    public String getMessageAsString(String message) {
        message +=  this.getCredential().getNodeIP() + " " + this.getCredential().getNodePort() + " " + this.getFileName() + " " + "download request";
        return super.getMessageAsString(message);
    }

}
