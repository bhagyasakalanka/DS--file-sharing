package org.example.com.ds.api.message.res;

import org.example.com.ds.api.Cred;
import org.example.com.ds.api.message.Message;

import java.util.List;



public class RegisterRes extends Message {

    private int nodeCount;
    private final List<Cred> creds;

    public RegisterRes(int nodeCount, List<Cred> creds) {
        this.nodeCount = nodeCount;
        this.creds = creds;
    }
    @Override
    public String getMessageAsString(String message) {
        message += " " + this.getNodeCount();
        for (Cred node : creds) {
            message += " " + node.getNodeIP() + " " + node.getNodePort();
        }
        return super.getMessageAsString(message);
    }
    public List<Cred> getCreds() {
        return creds;
    }


    public int getNodeCount() {
        return nodeCount;
    }



}