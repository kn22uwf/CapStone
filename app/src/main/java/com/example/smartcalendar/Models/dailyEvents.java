package com.example.smartcalendar.Models;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;

public class dailyEvents {

    private List<Events> mDailyEvents;
    private Date mDate;

    public dailyEvents(Date date, List<Event> events)
    {
        ArrayList<Event> daily= new ArrayList<>();
        mDate = date;

        for (int i = 0; i < events.size(); i++)
        {
            if (events.get(i).getDate() == mDate)
            {
                daily.add(events.get(i));
            }
        }
    }
}
