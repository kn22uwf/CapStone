package com.example.smartcalendar.Models;

import java.sql.Time;
import java.util.Date;
import java.util.UUID;

public class Event {

    private UUID mId;
    private String mTitle;
    private String mDescription;
    private int mPriority;
    private Date mDate;
    private Date mShowOnlyDate;

    private String mTime;

    public Event()
    {
        this.mId = UUID.randomUUID();
    }

    public Event(Date date)
    {
        this.mId = UUID.randomUUID();
        this.mDate = date;
    }

    public Event(UUID mId, Date date)
    {
        this.mId = mId;
        this.mDate = date;
    }

    public UUID getUUID()
    {
        return this.mId;
    }

    public void setTitle(String mTitle)
    {
        this.mTitle = mTitle;
    }

    public String getTIme()
    {
        return this.mTime;
    }

    public void setTime(String mTime)
    {
        this.mTime = mTime;
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

    public void setPriority(int priority)
    {
        this.mPriority = priority;
    }

    public int getPriority()
    {
        return mPriority;
    }

    public void setDate(Date mDate)
    {
        this.mDate = mDate;
    }

    public Date getDate()
    {
        return mDate;
    }

    public Date isDate(){
        return  mShowOnlyDate;
    }
}

