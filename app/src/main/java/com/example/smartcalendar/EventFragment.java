package com.example.smartcalendar;

import android.app.Activity;
import android.app.TimePickerDialog;
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
import android.widget.NumberPicker;
import android.widget.Switch;
import android.widget.TextView;
import android.widget.TimePicker;

import com.example.smartcalendar.Models.Event;
import com.example.smartcalendar.Models.Events;

import java.io.File;
import java.util.Calendar;
import java.util.Date;
import java.util.UUID;

public class EventFragment extends Fragment {


    //private  static final int REQUEST_PHOTO = 1;
    private static final String ARG_EVENT_ID = "event_id";
    private static final String DATE = "Dialog_date";
    public static final String EVENT = "event";
    private  static final int REQUEST_DATE = 0;



    private Event mEvent;
    private EditText mNameField;
    private TextView mDateField;
    private NumberPicker mNumberPicker;
    private EditText mDescription;
    private TimePickerDialog mTimePickerDialog;
    private EditText mReminder;
    private Button mCreate;
    private Button mDelete;
    private Calendar calendar;




    public static EventFragment newInstance(UUID eventID, Date date){
        Bundle args = new Bundle();
        args.putSerializable(ARG_EVENT_ID,eventID);
        args.putSerializable(DATE, date);
        //System.out.println("Datey mcDateFace " + date.toString() );
        EventFragment fragment = new EventFragment();
        fragment.setArguments(args);
        return fragment;

    }

    public void onCreate(Bundle savedInstanceState){
        super.onCreate(savedInstanceState);
        UUID eventId = (UUID)getArguments().getSerializable(ARG_EVENT_ID);
        //Date date = (Date) getArguments().getSerializable(DATE);
        setHasOptionsMenu(true);
        mEvent = Events.get(getActivity()).getEvent(eventId);
        //Date date = Events.get(getActivity()).getDate();
        Date date = mEvent.getDate();
        //System.out.println("Hello fuckers" + date.toString());
    }

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_event,container,false);

        mNameField = view.findViewById(R.id.eventName);
        mNameField.setText(mEvent.getTitle());
        mNameField.addTextChangedListener(new TextWatcher() {
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

        mDateField  = view.findViewById(R.id.date);
        updateDate();

        mNumberPicker = view.findViewById(R.id.number_picker);
        mNumberPicker.setMaxValue(5);
        mNumberPicker.setMinValue(1);
        mNumberPicker.setWrapSelectorWheel(true);
        mNumberPicker.setOnValueChangedListener(new NumberPicker.OnValueChangeListener() {
            @Override
            public void onValueChange(NumberPicker picker, int oldVal, int newVal) {
                mEvent.setPriority(newVal);
            }
        });





        mDescription = view.findViewById(R.id.description);
        mDescription.setText(mEvent.getDescription());
        mDescription.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence s, int start, int count, int after) {

            }

            @Override
            public void onTextChanged(CharSequence s, int start, int before, int count) {
                mEvent.setDescription(s.toString());
            }

            @Override
            public void afterTextChanged(Editable s) {

            }
        });

        mReminder = view.findViewById(R.id.reminder);
        mReminder.setOnClickListener(new View.OnClickListener()
        {

            @Override
            public void onClick(View v) {
                calendar = Calendar.getInstance();
                int currHour = calendar.get(Calendar.HOUR_OF_DAY);
                int currMinute = calendar.get(Calendar.MINUTE);
                mTimePickerDialog = new TimePickerDialog(getActivity(), new TimePickerDialog.OnTimeSetListener()
                {
                    @Override
                    public void onTimeSet(TimePicker view, int hourOfDay, int minute)
                    {
                        mReminder.setText(String.valueOf(hourOfDay) + ":" + String.valueOf(minute));
                    }
                }, currHour, currMinute, false);

                mTimePickerDialog.show();
            }
        });

        mCreate = view.findViewById(R.id.create_button);
        mCreate.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                getActivity().finish();
            }
        });


        mDelete = view.findViewById(R.id.delete_button);
        mDelete.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Events.get(getActivity()).deleteEvent(mEvent);
                getActivity().finish();
            }
        });

        return view;
    }

    @Override
    public void onActivityResult(int requestCode, int resultCode, Intent data){
        if(resultCode != Activity.RESULT_OK){
            return;
        }
        if(requestCode == REQUEST_DATE){

        }
    }

    private void updateDate() {

        mDateField.setText(mEvent.getDate().toString());
        sendResult(Activity.RESULT_OK, mEvent.getDate());
    }

    @Override
    public void onPause()
    {
        super.onPause();

        Events.get(getActivity()).updateEvent(mEvent);
        //mCallBacks.onCrimeUpdated(mMemory);

    }

    private void sendResult(int resultCode, Date date)
    {
        if (getTargetFragment() == null)
        {
            return;
        }
        Intent i = new Intent();
        i.putExtra(EVENT, date);
        getTargetFragment().onActivityResult(getTargetRequestCode(), resultCode, i);

    }





}

