package org.example.com.ds.api;


public class Cred {
    private String nodeIP;
    private int NodePort;
    private String nodeName;

    public Cred(String nodeIP, int NodePort, String nodeName) {
        this.nodeIP = nodeIP;
        this.NodePort = NodePort;
        this.nodeName = nodeName;
    }

    @Override
    public boolean equals(Object other) {
        if (!(other instanceof Cred)) {
            return false;
        }
        Cred that = (Cred) other;
        return (this.nodeIP.equals(that.getNodeIP())
                && this.NodePort == that.getNodePort());
    }

    public String getNodeIP() {
        return nodeIP;
    }


    public String getNodeName() {
        return nodeName;
    }

    public void setNodeName(String nodeName) {
        this.nodeName = nodeName;
    }

    public int getNodePort() {
        return NodePort;
    }

    public void setNodePort(int nodePort) {
        this.NodePort = nodePort;
    }




}
