package com.example.smartcalendar;

import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentStatePagerAdapter;
import android.support.v4.view.ViewPager;
import android.support.v7.app.AppCompatActivity;

import com.example.smartcalendar.Models.Event;
import com.example.smartcalendar.Models.Events;

import java.util.List;
import java.util.UUID;

public class EventPagerActivity extends AppCompatActivity {

    private ViewPager mViewPager;
    private List<Event> mEvents;

    private static final String EXTRA_MEMORY_ID = "memory_id";

    public static Intent newIntent(Context packageContext, UUID eventId){
        Intent intent = new Intent(packageContext,EventPagerActivity.class);
        intent.putExtra(EXTRA_MEMORY_ID,eventId);
        return  intent;
    }

    @Override
    protected void onCreate(Bundle savedInstanceState){
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_event_pager);

        UUID eventId = (UUID)getIntent().getSerializableExtra(EXTRA_MEMORY_ID);


        mViewPager = findViewById(R.id.event_view_pager);
        mEvents = Events.get(this).getEvents();
        FragmentManager fragmentManager = getSupportFragmentManager();
        mViewPager.setAdapter(new FragmentStatePagerAdapter(fragmentManager) {
            @Override
            public Fragment getItem(int i) {
                Event event = mEvents.get(i);
                return EventFragment.newInstance(event.getUUID());
            }

            @Override
            public int getCount() {
                return mEvents.size();
            }
        });

        for(int i = 0;i<mEvents.size();++i){
            if(mEvents.get(i).getUUID().equals(eventId)){
                mViewPager.setCurrentItem(i);
                break;
            }
        }
    }
}
