package org.uom.cse.cs4262.api.message.response;

import org.uom.cse.cs4262.api.Cred;
import org.uom.cse.cs4262.api.message.Message;

import java.util.List;



public class RegisterRes extends Message {

    private int totalNumberOfNodes;
    private List<Cred> creds;

    public RegisterRes(int totalNumberOfNodes, List<Cred> creds) {
        this.totalNumberOfNodes = totalNumberOfNodes;
        this.creds = creds;
    }
    @Override
    public String getMessageAsString(String message) {
        message += " " + this.getTotalNumberOfNodes();
        for (Cred node : creds) {
            message += " " + node.getNodeIp() + " " + node.getNodePort();
        }
        return super.getMessageAsString(message);
    }
    public List<Cred> getCreds() {
        return creds;
    }

    public void setCreds(List<Cred> creds) {
        this.creds = creds;
    }

    public int getTotalNumberOfNodes() {
        return totalNumberOfNodes;
    }

    public void setTotalNumberOfNodes(int totalNumberOfNodes) {
        this.totalNumberOfNodes = totalNumberOfNodes;
    }


}