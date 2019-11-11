package com.example.smartcalendar;

import android.app.Activity;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.text.Editable;
import android.text.TextWatcher;
import android.view.LayoutInflater;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.CompoundButton;
import android.widget.EditText;
import android.widget.ImageButton;
import android.widget.ImageView;
import android.widget.Switch;

import com.example.smartcalendar.Models.Event;
import com.example.smartcalendar.Models.Events;

import java.io.File;
import java.util.Date;
import java.util.UUID;

public class EventFragment extends Fragment {


    //private  static final int REQUEST_PHOTO = 1;
    private static final String ARG_MEMORY_ID = "event_id";
    private static final String DIALOG_DATE = "Dialog_date";
    private  static final int REQUEST_DATE = 0;



    private Event mEvent;
    private EditText mTitleField;
    private EditText mDetailField;
    private Switch mSwitch;
    private Button mDateButton;





    public static EventFragment newInstance(UUID memoryID){
        Bundle args = new Bundle();
        args.putSerializable(ARG_MEMORY_ID,memoryID);
        EventFragment fragment = new EventFragment();
        fragment.setArguments(args);
        return fragment;

    }

    public void onCreate(Bundle savedInstanceState){
        super.onCreate(savedInstanceState);
        UUID eventId = (UUID)getArguments().getSerializable(ARG_MEMORY_ID);
        setHasOptionsMenu(true);
        mEvent = Events.get(getActivity()).getEvent(eventId);
        //mPhotoFile = Events.get(getActivity()).getPhotoFile(mMemory);
    }

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_event,container,false);

        mTitleField = view.findViewById(R.id.eventName);
        mTitleField.setText(mEvent.getTitle());
        mTitleField.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence s, int start, int count, int after) {

            }

            @Override
            public void onTextChanged(CharSequence s, int start, int before, int count) {
                mEvent.setTitle(s.toString());
            }

            @Override
            public void afterTextChanged(Editable s) {

            }
        });

        mDetailField  = view.findViewById(R.id.editText);
        mDetailField.setText(mMemory.getDescription());
        mDetailField.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence s, int start, int count, int after) {

            }

            @Override
            public void onTextChanged(CharSequence s, int start, int before, int count) {
                mMemory.setDescription(s.toString());
            }

            @Override
            public void afterTextChanged(Editable s) {

            }
        });

        mDateButton = view.findViewById(R.id.memory_date);
        updateDate();
        mDateButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                FragmentManager fragmentManager = getFragmentManager();
                DatePickerFragment dialog = DatePickerFragment.newInstnce(mMemory.getDate());
                dialog.setTargetFragment(MemoryFragment.this,REQUEST_DATE);
                dialog.show(fragmentManager,DIALOG_DATE);
            }
        });

        mSwitch = view.findViewById(R.id.switch1);
        mSwitch.setChecked(mMemory.isFavorite());
        mSwitch.setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(CompoundButton buttonView, boolean isChecked) {
                mMemory.setFavorite(isChecked);
            }
        });

        final PackageManager packageManager = getActivity().getPackageManager();


        return view;
    }

    @Override
    public void onActivityResult(int requestCode, int resultCode, Intent data){
        if(resultCode != Activity.RESULT_OK){
            return;
        }
        if(requestCode == REQUEST_DATE){
            Date date = (Date) data.getSerializableExtra(DatePickerFragment.EXTRA_DATE);
            mEvent.setDate(date);
            updateDate();
        }

    }

    private void updateDate() {
        mDateButton.setText(mEvent.getDate().toString());
    }
    @Override
    public void onCreateOptionsMenu(Menu menu, MenuInflater inflater) {
        super.onCreateOptionsMenu(menu, inflater);
        inflater.inflate(R.menu.fragment_memory, menu);
        inflater.inflate(R.menu.fragment_memory,menu);
        MenuItem deleteItem = menu.findItem(R.id.delete_button);
        MenuItem sendItem = menu.findItem(R.id.send_button);
    }
    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        switch (item.getItemId()) {
            case R.id.delete_button:
                MemoryLab.get(getActivity()).deleteMemory(mMemory);
                getActivity().finish();
                return true;
            case R.id.send_button:
                Intent intent = new Intent(Intent.ACTION_SEND);
                intent.setType("text/plain");
                intent.putExtra(Intent.EXTRA_TEXT,getMemoriesReport());
                intent.putExtra(Intent.EXTRA_SUBJECT,getString(R.string.memories_report_subject));
                intent = Intent.createChooser(intent,getString(R.string.send_report));
                startActivity(intent);
            default:
                return super.onOptionsItemSelected(item);
        }
    }
    @Override
    public void onPause()
    {
        super.onPause();

        Events.get(getActivity()).updateEvent(mEvent);
        //mCallBacks.onCrimeUpdated(mMemory);

    }




}

