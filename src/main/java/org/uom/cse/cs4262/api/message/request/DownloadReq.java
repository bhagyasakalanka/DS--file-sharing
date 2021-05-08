package org.uom.cse.cs4262.api.message.request;

import org.uom.cse.cs4262.api.Cred;
import org.uom.cse.cs4262.api.message.Message;

public class DownloadReq extends Message {


    private Cred dCred;
    private String fileName;


    public DownloadReq(Cred dCred, String fileName){

        this.dCred = dCred;
        this.fileName = fileName;

    }
    @Override
    public String getMessageAsString(String message) {
        message +=  this.getCred().getNodeIp() + " " + this.getCred().getNodePort() + " " + this.getFileName() + " " + "download request";
        return super.getMessageAsString(message);
    }
    public Cred getCred() {
        return dCred;
    }

    public void setCredential(Cred cred) {
        this.dCred = cred;
    }

    public void setdCred(Cred dCred) {
        this.dCred = dCred;
    }

    public void setFileName(String fileName) {
        this.fileName = fileName;
    }

    public Cred getdCred() {
        return dCred;
    }

    public String getFileName() {
        return fileName;
    }



}
