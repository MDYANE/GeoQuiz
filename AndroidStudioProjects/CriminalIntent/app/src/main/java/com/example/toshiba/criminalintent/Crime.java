package com.example.toshiba.criminalintent;

import java.util.Date;
import java.util.UUID;


public class Crime {

    private UUID mld;
    private String mTitle;
    private Date mDate;
    private boolean mSolved;

    public Crime() {
        mld = UUID.randomUUID();
        mDate = new Date();
    }
    public UUID getId() { return mld;}
    public String getTitle(){return mTitle;}

    public UUID getMld() {
        return mld;
    }

    public void setMld(UUID mld) {
        this.mld = mld;
    }

    public void setTitle(String title) {
        mTitle = title;
    }

    public Date getDate() {
        return mDate;
    }

    public void setDate(Date date) {
        mDate = date;
    }

    public boolean isSolved() {
        return mSolved;
    }

    public void setSolved(boolean solved) {
        mSolved = solved;
    }
}
