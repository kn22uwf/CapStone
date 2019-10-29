package com.example.smartcalendar.Models;
import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;

import com.example.smartcalendar.database.EventBaseHelper;
import com.example.smartcalendar.database.EventCursorWrapper;
import com.example.smartcalendar.database.EventDbSchema;

import java.io.File;
import java.util.ArrayList;
import java.util.List;
import java.util.UUID;

public class Events
{

    private static Events sEvents;

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
        mContext = context.getApplicationContext();
        mDatabase = new EventBaseHelper(mContext).getWritableDatabase();
    }

    public List<Event> getEvents()
    {

        List<Event> events = new ArrayList<>();

        EventCursorWrapper cursor = queryMemories(null, null);

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

    public List<Event> getFavorites()
    {
        List<Events> events = getEvents();
        List<Events> favorites = new ArrayList<>();
        for (int i = 0; i < events.size(); i++)
        {
            if (events.get(i).isFavorite())
            {
                favorites.add(events.get(i));
            }
        }

        return favorites;
    }

    public void addMemory(Memory memory)
    {
        ContentValues values = getContentValues(memory);
        mDatabase.insert(MemoryDbSchema.MemoryTable.NAME, null, values);
    }

    public void deleteMemory(Memory memory)
    {
        String uuidString = memory.getId().toString();
        mDatabase.delete(MemoryDbSchema.MemoryTable.NAME,
                MemoryDbSchema.MemoryTable.Cols.UUID + " =?",
                new String[] { uuidString });
    }

    public Memory getMemory(UUID id)
    {
        MemoryCursorWrapper cursor = queryMemories(MemoryDbSchema.MemoryTable.Cols.UUID + " =?",
                new String[] { id.toString()});

        try {
            if (cursor.getCount() == 0)
            {
                return null;
            }

            cursor.moveToFirst();
            return cursor.getMemory();
        }
        finally {
            cursor.close();
        }
    }

    public void updateMemory(Memory memory)
    {
        String uuidString = memory.getId().toString();
        ContentValues values = getContentValues(memory);

        mDatabase.update(MemoryDbSchema.MemoryTable.NAME, values,
                MemoryDbSchema.MemoryTable.Cols.UUID + " =?",
                new String[] { uuidString });
    }

    private static ContentValues getContentValues(Event event)
    {
        ContentValues values = new ContentValues();
        values.put(MemoryDbSchema.MemoryTable.Cols.UUID, memory.getId().toString());
        values.put(MemoryDbSchema.MemoryTable.Cols.TITLE, memory.getTitle());
        values.put(MemoryDbSchema.MemoryTable.Cols.DATE, memory.getDate().getTime());
        values.put(MemoryDbSchema.MemoryTable.Cols.FAVORITE, memory.isFavorite() ? 1 : 0);
        values.put(MemoryDbSchema.MemoryTable.Cols.DESCRIPTION, memory.getDescription());

        return values;
    }

    private EventCursorWrapper queryMemories(String whereClause, String[] whereArgs)
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