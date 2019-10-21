package com.example.smartcalendar.database;

import android.content.Context;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;

public class EventBaseHelper extends SQLiteOpenHelper
{
    private static final int VERSION = 1;
    private static final String DATABASE_NAME = "memoryBase.db";

    public EventBaseHelper(Context context)
    {
        super(context, DATABASE_NAME, null, VERSION);
    }

    @Override
    public void onCreate(SQLiteDatabase sqLiteDatabase)
    {
        sqLiteDatabase.execSQL("create table " + EventDbSchema.EventTable.NAME + "(" +
                " _id integer primary key autoincrement, " +
                EventDbSchema.EventTable.Cols.UUID + ", " +
                EventDbSchema.EventTable.Cols.TITLE + ", " +
                EventDbSchema.EventTable.Cols.DATE + ", " +
                EventDbSchema.EventTable.Cols.PRIORITY + ", " +
                EventDbSchema.EventTable.Cols.DESCRIPTION + ")");
    }

    @Override
    public void onUpgrade(SQLiteDatabase sqLiteDatabase, int oldVersion, int newVersion)
    {

    }
}
