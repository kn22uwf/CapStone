package com.example.smartcalendar.Models.sampledata;

import java.util.Date;
import java.util.UUID;

public class Event {

    private UUID mId;
    private String mTitle;
    private String mDescription;
    private Date mDate;

    public Event()
    {
        this.mId.randomUUID();
        this.mTitle = "";
        this.mDescription = "";
        this.mDate = null;

    }

    public Event(UUID mId, String mTitle, String mDescription, Date mDate)
    {
        this.mId = mId;
        this.mTitle = mTitle;
        this.mDescription = mDescription;
        this.mDate = mDate;
    }

    public UUID getUUID()
    {
        return this.mId;
    }

    public void setTitle(String mTitle)
    {
        this.mTitle = mTitle;
    }

    public String getTitle()
    {
        return mTitle;
    }

    public void setDescription(String mDescription)
    {
        this.mDescription = mDescription;
    }

    public String getDescription()
    {
        return mDescription;
    }

    public void setDate(Date mDate)
    {
        this.mDate = mDate;
    }

    public Date getDate()
    {
        return mDate;
    }
}

