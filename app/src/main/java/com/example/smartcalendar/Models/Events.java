package com.example.smartcalendar.Models;
import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;

import com.example.smartcalendar.database.EventBaseHelper;
import com.example.smartcalendar.database.EventCursorWrapper;
import com.example.smartcalendar.database.EventDbSchema;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.UUID;

public class Events
{

    private static Events sEvents;
    private Date mDate;

    private Context mContext;
    private SQLiteDatabase mDatabase;

    public static Events get(Context context)
    {
        if(sEvents == null)
        {
            sEvents= new Events(context);
        }

        return sEvents;
    }

    private Events(Context context)
    {
        mDate = new Date();
        mContext = context.getApplicationContext();
        mDatabase = new EventBaseHelper(mContext).getWritableDatabase();
    }

    public Date getDate()
    {

        return this.mDate;
    }

    public void setDate(Date date)
    {
        this.mDate = date;
    }

    public List<Event> getEvents()
    {

        List<Event> events = new ArrayList<>();

        EventCursorWrapper cursor = queryEvents(null, null);

        try
        {
            cursor.moveToFirst();
            while(!cursor.isAfterLast())
            {
                events.add(cursor.getEvent());
                cursor.moveToNext();
            }
        }
        finally {
            cursor.close();
        }

        return events;

    }

    public List<Event> getEvents(Date date)
    {
        List<Event> events = new ArrayList<>();

        EventCursorWrapper cursor = queryEvents(null, null);
        try
        {
            cursor.moveToFirst();
            while(!cursor.isAfterLast())
            {
                System.out.println("The date in the db is " + cursor.getEvent().getDate().getDate() + "\nThe date clicked is " + date.getDate());
                //if (cursor.getEvent().getDate().compareTo(date) == 0)

                if((cursor.getEvent().getDate().getDate()== date.getDate()) && (cursor.getEvent().getDate().getMonth()== date.getMonth()) && cursor.getEvent().getDate().getYear()== date.getYear()){
                    System.out.println("adding date to array list");
                    events.add(cursor.getEvent());
                }
                else{
                    System.out.println("the event date is: " +cursor.getEvent().getDate().getTime()+ " add the date beibg checked is " + date.getTime());
                }
                cursor.moveToNext();
            }
        }
        finally {
            cursor.close();
        }

        Event temp = null;
        int size = events.size();
        System.out.println("The size of the list is " + events.size());

        for (int i = 0; i < size; i++)
        {
            System.out.println("Current index is " + i);
            if (i != events.size() - 1)
            {
                if (events.get(i).getPriority() > events.get(i + 1).getPriority())
                {

                    temp = events.get(i);
                    events.add(i, events.get(i + 1));
                    events.remove(temp);
                    events.add(i + 1, temp);
                }
            }
        }

        return events;
    }

    public void addEvent(Event event)
    {
        //System.out.println("in addEvent, printing date " + event.getDate().toString());
        ContentValues values = getContentValues(event);
        System.out.println("The date being inserted into the db is " + values.get(EventDbSchema.EventTable.Cols.DATE));
        mDatabase.insert(EventDbSchema.EventTable.NAME, null, values);
    }


    public void deleteEvent(Event event)
    {
        String uuidString = event.getUUID().toString();
        mDatabase.delete(EventDbSchema.EventTable.NAME,
                EventDbSchema.EventTable.Cols.UUID + " =?",
                new String[] { uuidString });
    }

    public Event getEvent(UUID id)
    {
        EventCursorWrapper cursor = queryEvents(EventDbSchema.EventTable.Cols.UUID + " =?",
                new String[] { id.toString()});

        try {
            if (cursor.getCount() == 0)
            {
                return null;
            }

            cursor.moveToFirst();
            return cursor.getEvent();
        }
        finally {
            cursor.close();
        }
    }

    public void updateEvent(Event event)
    {
        String uuidString = event.getUUID().toString();
        ContentValues values = getContentValues(event);

        mDatabase.update(EventDbSchema.EventTable.NAME, values,
                EventDbSchema.EventTable.Cols.UUID + " =?",
                new String[] { uuidString });
    }

    private static ContentValues getContentValues(Event event)
    {
        System.out.println("in getContentValues, printing date " + event.getDate().toString());
        ContentValues values = new ContentValues();
        values.put(EventDbSchema.EventTable.Cols.UUID, event.getUUID().toString());
        values.put(EventDbSchema.EventTable.Cols.TITLE, event.getTitle());
        values.put(EventDbSchema.EventTable.Cols.DATE, event.getDate().getTime());
        values.put(EventDbSchema.EventTable.Cols.TIME, event.getTIme());
        values.put(EventDbSchema.EventTable.Cols.PRIORITY, event.getPriority());
        values.put(EventDbSchema.EventTable.Cols.DESCRIPTION, event.getDescription());

        return values;
    }

    private EventCursorWrapper queryEvents(String whereClause, String[] whereArgs)
    {
        Cursor cursor = mDatabase.query(EventDbSchema.EventTable.NAME,
                null,
                whereClause,
                whereArgs,
                null,
                null,
                null);

        return new EventCursorWrapper(cursor);
    }
}