package org.example.com.ds.api.message.req;

import org.example.com.ds.api.Cred;
import org.example.com.ds.api.message.Message;



public class RegisterReq extends Message {

    private Cred registerCred;

    public RegisterReq(Cred registerCred) {
        this.registerCred = registerCred;
    }


    @Override
    public String getMessageAsString(String message) {
        message += " " + this.getRegisterCred().getNodeIP() + " " + this.getRegisterCred().getNodePort() + " " + this.getRegisterCred().getNodeName();
        return super.getMessageAsString(message);
    }

    public Cred getRegisterCred() {
        return registerCred;
    }

}
