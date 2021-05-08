package org.uom.cse.cs4262.api;


public class Cred {
    private String nodeIp;
    private int nodePort;
    private String nodeName;

    public Cred(String nodeIp, int nodePort, String nodeName) {
        this.nodeIp = nodeIp;
        this.nodePort = nodePort;
        this.nodeName = nodeName;
    }

    public String getNodeIp() {
        return nodeIp;
    }

    public void setNodeIp(String nodeIp) {
        this.nodeIp = nodeIp;
    }

    public int getNodePort() {
        return nodePort;
    }

    public void setNodePort(int nodePort) {
        this.nodePort = nodePort;
    }

    public String getNodeName() {
        return nodeName;
    }

    public void setNodeName(String nodeName) {
        this.nodeName = nodeName;
    }

    @Override
    public boolean equals(Object other) {
        if (!(other instanceof Cred)) {
            return false;
        }
        Cred that = (Cred) other;
        return (this.nodeIp.equals(that.getNodeIp())
                && this.nodePort == that.getNodePort());
    }

}
