package com.example.smartcalendar;

import android.content.Context;
import android.content.Intent;
import android.graphics.Bitmap;
import android.support.v4.app.Fragment;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;

import java.util.Date;
import java.util.UUID;

public class EventActivity extends SingleFragmentActivity {

    private static final String EXTRA_EVENT_ID = "event_id";
    private static final String EXTRA_EVENT_DATE = "date";


    @Override
    public Fragment createFragment() {

        UUID eventId = (UUID) getIntent().getSerializableExtra(EXTRA_EVENT_ID);
        System.out.println("eat my ass daddy" + eventId);
        Date date = (Date) getIntent().getSerializableExtra(EXTRA_EVENT_DATE);
        System.out.println("THE DATE IS " + date.toString());

        return EventFragment.newInstance(eventId, date);

    }

    public static Intent newIntent(Context packageContext, UUID eventId, Date date){
        Intent intent = new Intent(packageContext,EventActivity.class);
        intent.putExtra(EXTRA_EVENT_ID,eventId);
        intent.putExtra(EXTRA_EVENT_DATE, date);
        return intent;
    }
}
