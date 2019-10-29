package com.example.smartcalendar;

import android.content.Context;
import android.content.Intent;
import android.graphics.Bitmap;
import android.support.v4.app.Fragment;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;

import java.util.UUID;

public class EventActivity extends AppCompatActivity {

    private static final String EXTRA_EVENT_ID = "event_id";


    @Override
    public Fragment createFragment() {
        UUID eventId = (UUID) getIntent().getSerializableExtra(EXTRA_EVENT_ID);

        return MemoryFragment.newInstance(eventId);

    }

    public static Intent newIntent(Context packageContext, UUID eventId){
        Intent intent = new Intent(packageContext,MemoryActivity.class);
        intent.putExtra(EXTRA_EVENT_ID,eventId);
        return intent;
    }
}
