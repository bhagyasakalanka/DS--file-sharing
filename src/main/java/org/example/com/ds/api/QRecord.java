package org.example.com.ds.api;

import java.util.Date;


public class QRecord {
    private int seqNo;
    private String query;
    private Date time;

    public QRecord(int seqNo, String query, Date time) {
        this.seqNo = seqNo;
        this.query = query;
        this.time = time;
    }

    public int getSeqNo() {
        return seqNo;
    }

    public String getQuery() {
        return query;
    }

    public Date getTime() {
        return time;
    }

}
