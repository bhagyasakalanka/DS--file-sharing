package org.example.com.ds.api.message.req;

import org.example.com.ds.api.Cred;
import org.example.com.ds.api.message.Message;


public class UnregisterReq extends Message {

    private Cred unRegisterCred;

    public UnregisterReq(Cred unRegisterCred) {
        this.unRegisterCred = unRegisterCred;
    }

    public Cred getUnRegisterCred() {
        return unRegisterCred;
    }

    @Override
    public String getMessageAsString(String message) {
        message += " " + this.getUnRegisterCred().getNodeIP() + " " + this.getUnRegisterCred().getNodePort() + " " + this.getUnRegisterCred().getNodeName();
        return super.getMessageAsString(message);
    }
}
