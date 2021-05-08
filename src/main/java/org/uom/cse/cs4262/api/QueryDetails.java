package org.uom.cse.cs4262.api;

import java.util.Date;


public class QueryDetails {
    private int seqNumber;
    private String query;
    private Date trigTime;

    public QueryDetails(int seqNumber, String query, Date trigTime) {
        this.seqNumber = seqNumber;
        this.query = query;
        this.trigTime = trigTime;
    }

    public int getSeqNumber() {
        return seqNumber;
    }

    public void setSeqNumber(int seqNumber) {
        this.seqNumber = seqNumber;
    }

    public String getQuery() {
        return query;
    }

    public void setQuery(String query) {
        this.query = query;
    }

    public Date getTrigTime() {
        return trigTime;
    }

    public void setTrigTime(Date trigTime) {
        this.trigTime = trigTime;
    }
}
