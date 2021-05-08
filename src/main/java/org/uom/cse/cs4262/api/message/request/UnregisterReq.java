package org.uom.cse.cs4262.api.message.request;

import org.uom.cse.cs4262.api.Cred;
import org.uom.cse.cs4262.api.message.Message;


public class UnregisterReq extends Message {

    private Cred cred;

    public UnregisterReq(Cred cred) {
        this.cred = cred;
    }

    @Override
    public String getMessageAsString(String message) {
        message += " " + this.getCred().getNodeIp() + " " + this.getCred().getNodePort() + " " + this.getCred().getNodeName();
        return super.getMessageAsString(message);
    }

    public Cred getCred() {
        return cred;
    }

    public void setCred(Cred cred) {
        this.cred = cred;
    }


}
