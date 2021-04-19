package org.example.com.ds.api.message.res;

import org.example.com.ds.api.message.Message;


public class UnregisterRes extends Message {

    private int val;

    public UnregisterRes(int val) {
        this.val = val;
    }

    @Override
    public String getMessageAsString(String message) {
        message += " " + this.getVal();
        return super.getMessageAsString(message);
    }
    public int getVal() {
        return val;
    }


}
