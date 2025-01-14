package com.example.smartcalendar.database;

public class EventDbSchema
{
    public static final class EventTable
    {
        public static final String NAME = "events";

        public static final class Cols
        {
            public static final String UUID = "uuid";
            public static final String TITLE = "title";
            public static final String DESCRIPTION = "description";
            public static final String DATE = "date";
            public static final String TIME = "time";
            public static final String PRIORITY = "priority";
        }
    }
}
