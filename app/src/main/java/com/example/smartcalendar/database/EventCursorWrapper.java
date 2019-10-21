package com.example.smartcalendar.database;

import android.database.Cursor;
import android.database.CursorWrapper;

import com.example.smartcalendar.Models.Event;

import java.util.Date;
import java.util.UUID;

public class EventCursorWrapper extends CursorWrapper {

    public EventCursorWrapper(Cursor cursor)
    {
        super(cursor);
    }

    public Event getEvent()
    {
        String uuidString = getString(getColumnIndex(EventDbSchema.EventTable.Cols.UUID));
        String title = getString(getColumnIndex(EventDbSchema.EventTable.Cols.TITLE));
        Long date = getLong(getColumnIndex(EventDbSchema.EventTable.Cols.DATE));
        int priority = getInt(getColumnIndex(EventDbSchema.EventTable.Cols.PRIORITY));
        String description = getString(getColumnIndex(EventDbSchema.EventTable.Cols.DESCRIPTION));

        Event event = new Event(UUID.fromString(uuidString));
        event.setTitle(title);
        event.setDate(new Date(date));
        event.setPriority(priority);
        event.setDescription(description);

        return event;
    }
}
