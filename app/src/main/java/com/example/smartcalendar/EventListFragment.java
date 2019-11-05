package com.example.smartcalendar;

import android.content.Intent;
import android.graphics.Bitmap;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
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
import java.util.List;

public class EventListFragment extends Fragment {




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
            Intent intent = EventPagerActivity.newIntent(getActivity(),mEvent.getUUID());
            startActivity(intent);

        }

    }

    private class EventAdapter extends RecyclerView.Adapter<EventHolder>{
        public List<Event> mEvents;
        public EventAdapter(List<Event> memories){
            mEvents = memories;
        }

        @NonNull
        @Override
        public EventHolder onCreateViewHolder(@NonNull ViewGroup parent, int i) {
            LayoutInflater layoutInflater = LayoutInflater.from(getActivity());
            return new EventHolder(layoutInflater,parent);
        }

        @Override
        public void onBindViewHolder(@NonNull EventAdapter eventHolder, int i) {
            Event event = mEvents.get(i);
            eventHolder.bind(event);


        }

        @Override
        public int getItemCount() {
            return mMemories.size();
        }

        public void setMemories(List<Memory> memories){
            mMemories = memories;
        }

        
    }

    private RecyclerView mMemoryRecyclerView;
    private MemoryAdapter mAdapter;

    @Override
    public void onCreate(Bundle savedInstanceState){
        super.onCreate(savedInstanceState);

        setHasOptionsMenu(true);
    }

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_memory_list,container,false);
        mMemoryRecyclerView = (RecyclerView)view.findViewById(R.id.memory_recycler_view);
        mMemoryRecyclerView.setLayoutManager(new LinearLayoutManager(getActivity()));
        updateUI();
        return view;
    }

    @Override
    public void onCreateOptionsMenu(Menu menu, MenuInflater inflater){
        super.onCreateOptionsMenu(menu,inflater);
        inflater.inflate(R.menu.fragment_memory_list,menu);
    }
    @Override
    public boolean onOptionsItemSelected(MenuItem item){
        switch (item.getItemId()){
            case R.id.new_memory:
                Memory memory = new Memory();
                MemoryLab.get(getActivity()).addMemory(memory);
                Intent intent = MemoryPagerActivity.newIntent(getActivity(),memory.getId());
                startActivity(intent);
                return true;
            case R.id.show_favorite:
                MemoryLab.get(getActivity()).setShowOnlyFav(true);
                updateUI();
                return true;
            case R.id.show_all:
                MemoryLab.get(getActivity()).setShowAllFav(false);
                updateUI();
                return true;
            default:
                return super.onOptionsItemSelected(item);
        }
    }

    @Override
    public void onResume(){
        super.onResume();

        updateUI();
    }

    private void updateUI() {

        MemoryLab memoryLab = MemoryLab.get(getActivity());
        List<Memory> memories = memoryLab.getMemories();

        if (mAdapter == null) {
            mAdapter = new MemoryAdapter(memories);
            mMemoryRecyclerView.setAdapter(mAdapter);
        } else {

            mAdapter.setMemories(memories);
            mAdapter.notifyDataSetChanged();
        }
    }

}
