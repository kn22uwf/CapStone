package com.example.smartcalendar;

import android.support.v4.app.Fragment;

public class EventListActivity extends SingleFragmentActivity {
    @Override
    public Fragment createFragment() {

        return new EventListFragment();

    }
}
