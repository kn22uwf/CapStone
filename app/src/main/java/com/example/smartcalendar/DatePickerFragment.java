package com.example.smartcalendar;

import android.app.Activity;
import android.app.AlertDialog;
import android.app.Dialog;
import android.content.DialogInterface;
import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.v4.app.DialogFragment;
import android.support.v4.app.Fragment;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.CalendarView;
import android.widget.DatePicker;
import android.widget.ListView;
import android.widget.Switch;
import android.widget.TextView;

import com.example.smartcalendar.Models.Event;
import com.example.smartcalendar.Models.Events;

import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;
import java.util.GregorianCalendar;
import java.util.List;
import java.util.UUID;

public class DatePickerFragment extends Fragment {

    private static final String ARG_DATE = "arg_date";
    private static final String EXTRA_DATE = "extra_date";
    private static final int REQUEST_EVENT = 1;

    public class EventHolder extends RecyclerView.ViewHolder implements View.OnClickListener {

        private Event mEvent;
        private TextView mTitle;
        private TextView mDate;
        private TextView mPriority;


        public EventHolder(LayoutInflater inflater,ViewGroup parent){

            super(inflater.inflate(R.layout.list_item_event,parent,false));
            itemView.setOnClickListener(this);
            mTitle = (TextView)itemView.findViewById(R.id.event_title);
            mDate = (TextView)itemView.findViewById(R.id.event_date);
            mPriority = (TextView)itemView.findViewById(R.id.priority_id);
        }

        public void bind(Event event){
            mEvent = event;
            mTitle.setText(mEvent.getTitle());
            mDate.setText(mEvent.getDate().toString());

            try {
                switch (mEvent.getPriority()) {
                    case 1:
                        mPriority.setText("1");
                        break;
                    case 2:
                        mPriority.setText("2");
                        break;
                    case 3:
                        mPriority.setText("3");
                        break;
                    case 4:
                        mPriority.setText("4");
                        break;
                    case 5:
                        mPriority.setText("5");
                        break;

                    default:
                        mPriority.setText("0");
                        break;
                }

            } catch (Exception e) {
                System.out.println("Something went wrong.");
            }
        }

        @Override
        public void onClick(View v) {
            Intent intent = EventPagerActivity.newIntent(getActivity(),mEvent.getUUID(), mEvent.getDate(),mEvent.getPriority());
            startActivity(intent);

        }

    }

    private class EventAdapter extends RecyclerView.Adapter<EventHolder>{
        public List<Event> mEvents;
        public EventAdapter(List<Event> events){
            mEvents = events;
        }

        @NonNull
        @Override
        public EventHolder onCreateViewHolder(@NonNull ViewGroup parent, int i) {
            LayoutInflater layoutInflater = LayoutInflater.from(getActivity());
            return new EventHolder(layoutInflater,parent);
        }


        @Override
        public void onBindViewHolder(@NonNull EventHolder eventHolder, int i) {
            Event event = mEvents.get(i);
            eventHolder.bind(event);


        }

        @Override
        public int getItemCount() {
            return mEvents.size();
        }

        public void setEvents(List<Event> events){
            mEvents= events;
        }


    }


    private CalendarView mCalendarView;
    private Date picked = new Date();
    private RecyclerView mEventRecyclerView;
    private EventAdapter mAdapter;
    private TextView mView1;
    private TextView mView2;

    public static DatePickerFragment newInstance(Date date){
        Bundle args = new Bundle();
        args.putSerializable(ARG_DATE, date);

        DatePickerFragment fragment = new DatePickerFragment();
        fragment.setArguments(args);
        return fragment;
    }

    @Override
    public void onCreate(Bundle savedInstanceState){
        super.onCreate(savedInstanceState);
        setHasOptionsMenu(true);
    }


    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState)
    {
        View v = LayoutInflater.from(getActivity()).inflate(R.layout.calender, null);
        mEventRecyclerView = (RecyclerView)v.findViewById(R.id.date_events);
        mEventRecyclerView.setLayoutManager(new LinearLayoutManager(getActivity()));
        Events event = Events.get(getActivity());
        List<Event> events = event.getEvents();
        Date date = (Date) event.getDate();


        mView1 = (TextView)v.findViewById(R.id.view1);
        mView2 = (TextView)v.findViewById(R.id.view2);
        mView1.setText("    Events:");
        Calendar calendar = Calendar.getInstance();
        calendar.setTime(date);
        int year = calendar.get(Calendar.YEAR);
        int month = calendar.get(Calendar.MONTH);
        int day = calendar.get(Calendar.DAY_OF_MONTH);
        mCalendarView = (CalendarView) v.findViewById(R.id.date);
        mCalendarView.setOnDateChangeListener(new CalendarView.OnDateChangeListener() {
            @Override
            public void onSelectedDayChange( CalendarView view, int year, int month, int dayOfMonth) {
                //mCalendarView.setDate(view.getDate());
                picked = new Date(year, month, dayOfMonth);
                //picked.setDate(dayOfMonth);
                //picked.setMinutes(month);
                //picked.setYear(year);
                //Events.get(getActivity()).setShowOnlyDate(picked);
                updateUI(picked);
            }
        });

        return v;

    }

    @Override
    public void onCreateOptionsMenu(Menu menu, MenuInflater inflater)
    {
        super.onCreateOptionsMenu(menu, inflater);
        inflater.inflate(R.menu.fragment_event_list, menu);
        setMenuVisibility(true);

    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item)
    {
        switch(item.getItemId())
        {
            case R.id.add_event:
                Event event = new Event(picked);
                System.out.println("clicked to add event " + event.getDate().toString());
                Events.get(getActivity()).addEvent(event);
                Intent i = EventActivity.newIntent(getActivity(), event.getUUID(), picked);
                startActivityForResult(i, REQUEST_EVENT);
                return true;
            default:
                return super.onOptionsItemSelected(item);
        }
    }

    private void sendResult(int resultCode, Date date)
    {
        if(getTargetFragment() == null)
        {
            return;
        }

        Intent intent = new Intent();
        intent.putExtra(EXTRA_DATE, date);

        getTargetFragment().onActivityResult(getTargetRequestCode(), resultCode, intent);
    }

    private void updateUI(Date date) {

        Events event = Events.get(getActivity());
        List<Event> events = event.getEvents(date);
        System.out.println("Printing all elements");
        System.out.println("The size of the list is " + events.size());
        for (int i = 0; i < events.size(); i++)
        {
            System.out.println(events.get(i).getTitle());
        }
        if (mAdapter == null) {
            mAdapter = new EventAdapter(events);
            mEventRecyclerView.setAdapter(mAdapter);
        } else {
            mAdapter.setEvents(events);
            mAdapter.notifyDataSetChanged();
        }
        //FragmentManager manager = getFragmentManager();
        //DatePickerFragment dialog = DatePickerFragment.newInstance(event.getDate());
        //dialog.setTargetFragment(EventListFragment.this, REQUEST_DATE);
        //dialog.show(manager,DIALOG_DATE);
    }

    @Override
    public void onActivityResult(int requestCode, int resultCode, Intent data) {
        if (resultCode != Activity.RESULT_OK) {
            return;
        } else if (requestCode == REQUEST_EVENT) {
            Date date = (Date) data.getSerializableExtra(EventFragment.EVENT);
            Event event = new Event(UUID.randomUUID(), date);
            Events.get(getActivity()).addEvent(event);
            updateUI(picked);
            //mEvents.setDate(date);
            //mEvents.setDate(date);

        }
        else
        {
            System.out.println("something");
        }
    }
    @Override
    public void onResume(){
        super.onResume();
        updateUI(picked);

    }
}

