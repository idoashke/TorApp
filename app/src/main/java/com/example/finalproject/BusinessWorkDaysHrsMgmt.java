package com.example.finalproject;

import android.annotation.SuppressLint;
import android.content.Context;
import android.os.Bundle;

import androidx.fragment.app.Fragment;

import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.AutoCompleteTextView;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import com.example.finalproject.databinding.FragmentBusinessWorkDaysHrsMgmtBinding;
import com.google.android.material.chip.Chip;
import com.google.android.material.chip.ChipGroup;
import com.google.android.material.datepicker.MaterialDatePicker;
import com.google.android.material.datepicker.MaterialPickerOnPositiveButtonClickListener;
import com.google.android.material.dialog.MaterialAlertDialogBuilder;
import com.google.android.material.timepicker.MaterialTimePicker;
import com.google.android.material.timepicker.TimeFormat;

import java.util.ArrayList;

/**
 * A simple {@link Fragment} subclass.
 * Use the {@link BusinessWorkDaysHrsMgmt#newInstance} factory method to
 * create an instance of this fragment.
 */
public class BusinessWorkDaysHrsMgmt extends Fragment {

    private Button mPickStartTimeButton;
    private TextView mShowSelectedStartTimeText;
    private Button mPickEndTimeButton;
    private TextView mShowSelectedEndTimeText;
    private Button mPickDateButton;
    private TextView mShowSelectedDateText;

    String startTime, endTime, chosenDate;
    ChipGroup chipGroup;
    int startHour, startMinute, endHour, endMinute;


    // TODO: Rename parameter arguments, choose names that match
    // the fragment initialization parameters, e.g. ARG_ITEM_NUMBER
    private static final String ARG_PARAM1 = "param1";
    private static final String ARG_PARAM2 = "param2";

    // TODO: Rename and change types of parameters
    private String mParam1;
    private String mParam2;

    public BusinessWorkDaysHrsMgmt() {
        // Required empty public constructor
    }

    /**
     * Use this factory method to create a new instance of
     * this fragment using the provided parameters.
     *
     * @param param1 Parameter 1.
     * @param param2 Parameter 2.
     * @return A new instance of fragment BusinessWorkDaysHrsMgmt.
     */
    // TODO: Rename and change types and number of parameters
    public static BusinessWorkDaysHrsMgmt newInstance(String param1, String param2) {
        BusinessWorkDaysHrsMgmt fragment = new BusinessWorkDaysHrsMgmt();
        Bundle args = new Bundle();
        args.putString(ARG_PARAM1, param1);
        args.putString(ARG_PARAM2, param2);
        fragment.setArguments(args);
        return fragment;
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        if (getArguments() != null) {
            mParam1 = getArguments().getString(ARG_PARAM1);
            mParam2 = getArguments().getString(ARG_PARAM2);
        }
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        FragmentBusinessWorkDaysHrsMgmtBinding binding = FragmentBusinessWorkDaysHrsMgmtBinding.inflate(getLayoutInflater());
        // Inflate the layout for this fragment

        String[] appointmentDuration = getResources().getStringArray(R.array.appointment_duration_array);
        AutoCompleteTextView dropdown = (AutoCompleteTextView) binding.BusinessWdhmgmtHourspickerDropdown;
        ArrayAdapter<String> adapter2 = new ArrayAdapter<String>(this.getActivity(), android.R.layout.simple_list_item_1, appointmentDuration);
        dropdown.setAdapter(adapter2);


        ////////// Date Picker \\\\\\\\\\
        mPickDateButton = binding.pickDateButton;
        mShowSelectedDateText = binding.showSelectedDate;

        MaterialDatePicker.Builder materialDateBuilder = MaterialDatePicker.Builder.datePicker();
        materialDateBuilder.setTitleText("SELECT A DATE");
        final MaterialDatePicker materialDatePicker = materialDateBuilder.build();

        mPickDateButton.setOnClickListener(
                new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {
                        materialDatePicker.show(getParentFragmentManager(), "MATERIAL_DATE_PICKER");
                    }
                });

        materialDatePicker.addOnPositiveButtonClickListener(
                new MaterialPickerOnPositiveButtonClickListener() {
                    @SuppressLint("SetTextI18n")
                    @Override
                    public void onPositiveButtonClick(Object selection) {

                        mShowSelectedDateText.setText("Selected Date: " + materialDatePicker.getHeaderText());
                        chosenDate = materialDatePicker.getHeaderText();
//                        Toast.makeText(getActivity(), chosenDate, Toast.LENGTH_SHORT).show();
                    }
                });



        ////////// Time Picker \\\\\\\\\\
        mPickStartTimeButton = binding.BusinessWdhmgmtStartTimePickerButton;
        mShowSelectedStartTimeText = binding.showSelectedStartTime;

        mPickEndTimeButton = binding.BusinessWdhmgmtEndTimePickerButton;
        mShowSelectedEndTimeText = binding.showSelectedEndTime;


        MaterialTimePicker materialStartTimePicker = new MaterialTimePicker.Builder()
                .setTimeFormat(TimeFormat.CLOCK_24H)
                .setHour(00)
                .setMinute(00)
                .setTitleText("Select Start Time")
                .build();

        MaterialTimePicker materialEndTimePicker = new MaterialTimePicker.Builder()
                .setTimeFormat(TimeFormat.CLOCK_24H)
                .setHour(00)
                .setMinute(00)
                .setTitleText("Select End Time")
                .build();

        mPickStartTimeButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                materialStartTimePicker.show(getParentFragmentManager(), "START TIME PICKER");
            }
        });


        materialStartTimePicker.addOnPositiveButtonClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
//                int hour = materialStartTimePicker.getHour();
//                int minute = materialStartTimePicker.getMinute();
                startHour = materialStartTimePicker.getHour();
                startMinute = materialStartTimePicker.getMinute();
                startTime = String.format("%02d:%02d", startHour, startMinute);
                mShowSelectedStartTimeText.setText("Start Time: " + startTime);
            }
        });

        mPickEndTimeButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                materialEndTimePicker.show(getParentFragmentManager(), "END TIME PICKER");
            }
        });


        materialEndTimePicker.addOnPositiveButtonClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
//                int hour = materialEndTimePicker.getHour();
//                int minute = materialEndTimePicker.getMinute();
                endHour = materialEndTimePicker.getHour();
                endMinute = materialEndTimePicker.getMinute();
                endTime = String.format("%02d:%02d", endHour, endMinute);
                mShowSelectedEndTimeText.setText("End Time: " + endTime);
            }
        });





        ///////////////////////////////


        Button goButton = (Button) binding.BusinessWdhmgmtGoButton;
        Button saveButton = (Button) binding.BusinessWdhmgmtSaveButton;
//        ChipGroup chipGroup = (ChipGroup) binding.chipGroup1;
        chipGroup = (ChipGroup) binding.chipGroup1;


        goButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
//                addNewChip();
                String appointDurationInput = ((EditText) binding.BusinessWdhmgmtHourspickerDropdown).getText().toString();
//                String a = mShowSelectedStartTimeText.getText().toString();
//                Boolean a = appointDurationInput.isEmpty();
//                Toast.makeText(getActivity(), a , Toast.LENGTH_SHORT).show();
                if (!mShowSelectedStartTimeText.getText().toString().equals("Select Start Time") &&
                        !mShowSelectedEndTimeText.getText().toString().equals("Select End Time") &&
                        !appointDurationInput.isEmpty() &&
                        chosenDate != null) {
                    int appointDuration = getAppointDuration(appointDurationInput);
                    timeCalc(appointDuration);
                    saveButton.setVisibility(v.VISIBLE);
                } else {
                    Toast.makeText(getActivity(), "Please fill all the fields!", Toast.LENGTH_SHORT).show();
                }
//                int appointDuration = getAppointDuration(appointDurationInput);
//                timeCalc(appointDuration);
            }
        });

        saveButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                ArrayList<Event> events = new ArrayList<Event>();
                int chipsCount = chipGroup.getChildCount();
                for (int i=0;i<chipsCount;i++){
//                    Chip chip = new Chip(chipGroup.getContext())
                    Chip chip = (Chip) chipGroup.getChildAt(i);
//                    Toast.makeText(getActivity(), "Chip time : " + chip.getText(), Toast.LENGTH_SHORT).show();
//                    Chip chip = (Chip) chipGroup.getChildAt(i);
                    if (chip.isChecked()){
                        String time = chip.getText().toString();
//                        Toast.makeText(getActivity(), "Chip time : " + time, Toast.LENGTH_SHORT).show();
                        events.add(createNewEventWithTime(chosenDate, time));
                    }
                }
                MainActivity main = (MainActivity) getActivity();
                main.addEventsDataToBusiness(events);
            }
        });


//        return inflater.inflate(R.layout.fragment_business_work_days_hrs_mgmt, container, false);
        return binding.getRoot();
    }


    public void addNewChip(String chipName) {
        Chip chip = new Chip(this.getContext());
        chip.setText(chipName);
        chip.setCheckable(true);
        chip.setChecked(true);
        chip.setChipBackgroundColorResource(R.color.chip_color);
        chipGroup.addView(chip);

    }

    public void timeCalc(int appointDurationInt){

        String finalTime;
        int i = startHour;
        int j = startMinute;
        // startTime = 08:00  ,  endTime = 16:00  ,  appointDurationInt = 40
        // startHour = 08 , startMinute = 00 , endHour = 16 , endMinute = 00
        while (i < endHour){
            while (j < 60){

                finalTime = String.format("%02d:%02d", i, j);
                addNewChip(finalTime);
//                appoint.add(finalTime);
                j += appointDurationInt;
            }
            if (j > 60){
                j = j % 60;
            } else if (j == 60){
                j = 00;
            }
            i += 1;
        }
        if (j == 00){
            finalTime = String.format("%02d:%02d", i, j);
            addNewChip(finalTime);
        }

    }


    public int getAppointDuration(String appointDurationString){
        switch(appointDurationString) {
            case "10 minutes":
                return 10;
//                break;
            case "20 minutes":
                return 20;
//                break;
            case "30 minutes":
                return 30;
//                break;
            case "40 minutes":
                return 40;
//                break;
            case "50 minutes":
                return 50;
//                break;
            case "60 minutes":
                return 60;
//                break;
            default:
                return 30;
        }

    }

    public Event createNewEventWithTime(String date, String time){
        Event event = new Event(date, time);
        MainActivity main = (MainActivity) getActivity();
        event.setName("Business name: " + main.business.busProfiler.getBusName());

//        Toast.makeText(getActivity(), event.toString(), Toast.LENGTH_SHORT).show();
        return event;

    }

}