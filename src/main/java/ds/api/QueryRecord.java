package ds.api;

import java.util.Date;

public class QueryRecord {
    private int sequenceNo;
    private String searchQuery;
    private Date triggeredTime;

    public QueryRecord(int sequenceNo, String searchQuery, Date triggeredTime) {
        this.sequenceNo = sequenceNo;
        this.searchQuery = searchQuery;
        this.triggeredTime = triggeredTime;
    }

    public int getSequenceNo() {
        return sequenceNo;
    }

    public void setSequenceNo(int sequenceNo) {
        this.sequenceNo = sequenceNo;
    }

    public String getSearchQuery() {
        return searchQuery;
    }

    public void setSearchQuery(String searchQuery) {
        this.searchQuery = searchQuery;
    }

    public Date getTriggeredTime() {
        return triggeredTime;
    }

    public void setTriggeredTime(Date triggeredTime) {
        this.triggeredTime = triggeredTime;
    }
}