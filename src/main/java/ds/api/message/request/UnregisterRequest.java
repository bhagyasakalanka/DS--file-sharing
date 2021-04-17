package ds.api.message.request;

import ds.api.Credential;
import ds.api.message.Message;


public class UnregisterRequest extends Message {

    private Credential credential;

    public UnregisterRequest(Credential credential) {
        this.credential = credential;
    }

    public Credential getCredential() {
        return credential;
    }

    public void setCredential(Credential credential) {
        this.credential = credential;
    }

    @Override
    public String getMessageAsString(String message) {
        message += " " + this.getCredential().getIp() + " " + this.getCredential().getPort() + " " + this.getCredential().getUsername();
        return super.getMessageAsString(message);
    }
}
