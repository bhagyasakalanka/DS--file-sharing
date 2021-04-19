package org.example.com.ds.api.message.req;

import org.example.com.ds.api.Cred;
import org.example.com.ds.api.message.Message;


public class JoinReq extends Message {

    private Cred joinCred;

    public JoinReq(Cred joinCred) {
        this.joinCred = joinCred;
    }

    public Cred getJoinCred() {
        return joinCred;
    }

    public void setJoinCred(Cred joinCred) {
        this.joinCred = joinCred;
    }

    @Override
    public String getMessageAsString(String message) {
        message += " " + this.getJoinCred().getNodeIP() + " " + this.getJoinCred().getNodePort();
        return super.getMessageAsString(message);
    }
}