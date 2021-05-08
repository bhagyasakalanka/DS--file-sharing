package org.uom.cse.cs4262.api.message.response;

import org.uom.cse.cs4262.api.message.Message;


public class UnregisterRes extends Message {

    private int val;

    public UnregisterRes(int val) {
        this.val = val;
    }

    public int getVal() {
        return val;
    }

    public void setVal(int val) {
        this.val = val;
    }

    @Override
    public String getMessageAsString(String message) {
        message += " " + this.getVal();
        return super.getMessageAsString(message);
    }
}
