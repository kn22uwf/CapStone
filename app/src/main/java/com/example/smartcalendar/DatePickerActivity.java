package com.example.smartcalendar;


import android.support.v4.app.Fragment;


public class DatePickerActivity extends SingleFragmentActivity {



    @Override
    public Fragment createFragment() {

        return new DatePickerFragment();

    }

}
