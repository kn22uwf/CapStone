package com.example.smartcalendar;

import android.content.Context;
import android.content.Intent;
import android.support.v4.app.Fragment;

import java.util.UUID;

public class DatePickerActivity extends SingleFragmentActivity {
    private static final String EXTRA_EVENT_ID = "event_id";


    @Override
    public Fragment createFragment() {
        UUID eventId = (UUID) getIntent().getSerializableExtra(EXTRA_EVENT_ID);

        return new EventListFragment();

    }

    public static Intent newIntent(Context packageContext, UUID eventId){
        Intent intent = new Intent(packageContext,EventActivity.class);
        intent.putExtra(EXTRA_EVENT_ID,eventId);
        return intent;
    }
}
