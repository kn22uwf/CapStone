package com.example.smartcalendar;

import android.app.Activity;
import android.content.Intent;
import android.graphics.Bitmap;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;
import android.view.ViewTreeObserver;
import android.widget.ImageView;
import android.widget.TextView;

import com.example.smartcalendar.Models.Event;
import com.example.smartcalendar.Models.Events;

import java.io.File;
import java.util.Date;
import java.util.List;

public class EventListFragment extends Fragment {


private Events mEvents;
    private static final String DIALOG_DATE = "dialog_date";
    private static final int REQUEST_DATE = 1;

    public class EventHolder extends RecyclerView.ViewHolder implements View.OnClickListener {

        private Event mEvent;
        private TextView mTitle;
        private TextView mDate;


        public EventHolder(LayoutInflater inflater,ViewGroup parent){

            super(inflater.inflate(R.layout.list_item_event,parent,false));
            itemView.setOnClickListener(this);
            mTitle = (TextView)itemView.findViewById(R.id.event_title);
            mDate = (TextView)itemView.findViewById(R.id.event_date);
            //mMemoryFav = (ImageView)itemView.findViewById(R.id.star);
            //mImageIcon = (ImageView)itemView.findViewById(R.id.image_icon);

        }

        public void bind(Event event){
            mEvent = event;
            mTitle.setText(mEvent.getTitle());
            mDate.setText(mEvent.getDate().toString());

        }

        @Override
        public void onClick(View v) {
            Intent intent = EventPagerActivity.newIntent(getActivity(),mEvent.getUUID(), mEvent.getDate());
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

    private RecyclerView mEventRecyclerView;
    private EventAdapter mAdapter;

    @Override
    public void onCreate(Bundle savedInstanceState){
        super.onCreate(savedInstanceState);
        setHasOptionsMenu(true);
    }

    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_event_list,container,false);
        mEventRecyclerView = (RecyclerView)view.findViewById(R.id.event_recycler_view);
        mEventRecyclerView.setLayoutManager(new LinearLayoutManager(getActivity()));
        updateUI();
        return view;
    }


    @Override
    public void onResume(){
        super.onResume();
        updateUI();
    }

    private void updateUI() {

        Events event = Events.get(getActivity());
        List<Event> events = event.getEvents();

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
        public void onActivityResult(int requestCode, int resultCode, Intent data){
            if(resultCode != Activity.RESULT_OK)
            {
                return;
            }

            else if(requestCode == REQUEST_DATE)
            {
                //Date date = (Date) data.getSerializableExtra(DatePickerFragment.EXTRA_DATE);
                //mEvents.setDate(date);
                //mEvents.setDate(date);

            }
        }

}
