package org.example.com.ds.api.message.req;

import org.example.com.ds.api.Cred;
import org.example.com.ds.api.message.Message;


public class LeaveReq extends Message {

    private Cred leaveCred;

    public LeaveReq(Cred leaveCred) {
        this.leaveCred = leaveCred;
    }

    public Cred getLeaveCred() {
        return leaveCred;
    }

    public void setLeaveCred(Cred leaveCred) {
        this.leaveCred = leaveCred;
    }

    @Override
    public String getMessageAsString(String message) {
        message += " " + this.getLeaveCred().getNodeIP() + " " + this.getLeaveCred().getNodePort();
        return super.getMessageAsString(message);
    }
}
