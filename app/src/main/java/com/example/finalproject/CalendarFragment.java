package com.example.finalproject;

import android.annotation.SuppressLint;
import android.content.Context;
import android.content.DialogInterface;
import android.os.Bundle;

import androidx.annotation.NonNull;
import androidx.fragment.app.Fragment;
import androidx.navigation.Navigation;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.TextView;
import android.widget.Toast;

import com.example.finalproject.databinding.FragmentCalendarBinding;
import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.android.material.chip.Chip;
import com.google.android.material.chip.ChipGroup;
import com.google.android.material.datepicker.MaterialDatePicker;
import com.google.android.material.datepicker.MaterialPickerOnPositiveButtonClickListener;
import com.google.android.material.datepicker.MaterialStyledDatePickerDialog;
import com.google.android.material.dialog.MaterialAlertDialogBuilder;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

import java.util.ArrayList;


/**
 * A simple {@link Fragment} subclass.
 * Use the {@link CalendarFragment#newInstance} factory method to
 * create an instance of this fragment.
 */
public class CalendarFragment extends Fragment {

    private Button mPickDateButton;
    private TextView mShowSelectedDateText;
    public String chosenDate;
    private ChipGroup chipGroup;
    private Button newEventButton;
    private Button showFreeMeetingsHrsButton;
    private String chosenTime;
    private MainActivity mainActivity;
    private Event chosenEvent;
    private MaterialAlertDialogBuilder materialDialogBuilder;

    // TODO: Rename parameter arguments, choose names that match
    // the fragment initialization parameters, e.g. ARG_ITEM_NUMBER
    private static final String ARG_PARAM1 = "param1";
    private static final String ARG_PARAM2 = "param2";


    // TODO: Rename and change types of parameters
    private String mParam1;
    private String mParam2;

    public CalendarFragment() {
        // Required empty public constructor
    }

    /**
     * Use this factory method to create a new instance of
     * this fragment using the provided parameters.
     *
     * @param param1 Parameter 1.
     * @param param2 Parameter 2.
     * @return A new instance of fragment CalenderFragment.
     */
    // TODO: Rename and change types and number of parameters
    public static CalendarFragment newInstance(String param1, String param2) {
        CalendarFragment fragment = new CalendarFragment();
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
        // Inflate the layout for this fragment
        FragmentCalendarBinding binding = FragmentCalendarBinding.inflate(getLayoutInflater());

        mainActivity = (MainActivity) getActivity();

        mPickDateButton = (Button) binding.pickDateButton;
        mShowSelectedDateText = (TextView) binding.showSelectedDate;

        newEventButton = (Button) binding.newEventButton;
        showFreeMeetingsHrsButton = (Button) binding.showFreeMeetingsButton;

        chipGroup = (ChipGroup) binding.chipGroup1;

        Context context = this.getContext();
        MaterialDatePicker.Builder materialDateBuilder = MaterialDatePicker.Builder.datePicker();
        materialDialogBuilder = new MaterialAlertDialogBuilder(context);


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

                        mShowSelectedDateText.setText("Selected Date is : " + materialDatePicker.getHeaderText());
                        mShowSelectedDateText.setVisibility(View.VISIBLE);
                        chosenDate = materialDatePicker.getHeaderText();
                        showFreeMeetingsHrsButton.setVisibility(View.VISIBLE);
//                        Toast.makeText(getActivity(), chosenDate, Toast.LENGTH_SHORT).show();

                    }
                });

        showFreeMeetingsHrsButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                chipGroup.removeAllViews();
                newEventButton.setText("Create New Event");
                chosenTime = null;
                getBusFreeEventsByDate();
            }
        });


        newEventButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
//                Event chosenEvent = (getBusSelectedEvent());
                if (chosenTime != null){
                    getBusSelectedEvent();
                } else {
                    Toast.makeText(getActivity(), "Please select a valid time", Toast.LENGTH_SHORT).show();
                }

//                String userFirstName = mainActivity.user.getFirstName();
//                String userLastName = mainActivity.user.getLastName();
//                String userEmail = mainActivity.user.getEmail();
//                String userPhone = mainActivity.user.getPhone();
//
//                materialDialogBuilder.setTitle("New Event - " + chosenEvent.getName())
//                        .setIcon(R.drawable.ic_event)
//                        .setMessage("Event Details: " + chosenEvent.getDate() + ", " + chosenEvent.getTime())
////                        .setMessage("Client Details: " + userFirstName + " " + userLastName + ", " + userEmail + ", " + userPhone)
//                        .setNegativeButton("Cancel", null)
//                        //.setNeutralButton("ca", null)
////                        .setPositiveButton("Accept", null)
//                        .setPositiveButton("Accept", new DialogInterface.OnClickListener() {
//                            @Override
//                            public void onClick(DialogInterface dialog, int which) {
////                                name, date, time, data, isAvilable
////                                Toast.makeText(getActivity(), "!!!!!!!!!!", Toast.LENGTH_SHORT).show();
//                                chosenEvent.setData("Client Details: " + userFirstName + " " + userLastName + ", " + userEmail + ", " + userPhone);
//                                chosenEvent.setIsAvailable("0");
//                                saveNewEvent(chosenEvent);
//                            }
//                        })
//                        .show();



//                materialDialogBuilder.setTitle("New Event")
//                        .setMessage("Date: " + chosenDate)
//                        .setNegativeButton("Cancel", null)
//                        //.setNeutralButton("ca", null)
//                        .setPositiveButton("Accept", null)
//                        .show();
            }
        });


        chipGroup.setOnCheckedChangeListener(new ChipGroup.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(ChipGroup group, int checkedId) {
//                Toast.makeText(getActivity(), "onCheckedChanged:", Toast.LENGTH_SHORT).show();
                Chip chip = (Chip) group.findViewById(checkedId);
                chosenTime = chip.getText().toString();
                newEventButton.setText("Schedule a new meeting for "+chosenTime);
            }
        });





        //return inflater.inflate(R.layout.fragment_calender, container, false);
        return binding.getRoot();
    }

    public void getBusSelectedEvent() {
        String userName = mainActivity.business.busProfiler.getEmail();
        userName = userName.substring(0, userName.indexOf("@"));
        FirebaseDatabase database = FirebaseDatabase.getInstance();
        DatabaseReference myRef = database.getReference("Business").child(userName).child("Events").child(chosenDate).child(chosenTime);


        myRef.addListenerForSingleValueEvent(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot snapshot) {
                chosenEvent = snapshot.getValue(Event.class);
//                return event;
                createMaterialDialog();
            }

            @Override
            public void onCancelled(@NonNull DatabaseError error) {

            }
        });
    }

    public void getBusFreeEventsByDate(){
//        MainActivity mainActivity = (MainActivity) getActivity();
        String userName = mainActivity.business.busProfiler.getEmail();
        userName = userName.substring(0, userName.indexOf("@"));
        FirebaseDatabase database = FirebaseDatabase.getInstance();
        DatabaseReference myRef = database.getReference("Business").child(userName).child("Events").child(chosenDate);
//        Toast.makeText(getActivity(), "chosenDate:" + chosenDate, Toast.LENGTH_SHORT).show();
        myRef.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot snapshot) {
//                Toast.makeText(getActivity(), "Event:" + snapshot.getValue(Event.class).getName(), Toast.LENGTH_SHORT).show();
                for (DataSnapshot dataSnapshot : snapshot.getChildren()) {
                    Event value = dataSnapshot.getValue(Event.class);
                    String name = value.getName();
                    String date = value.getDate();
                    String time = value.getTime();
                    String data = value.getData();
                    String isAvailable = value.getIsAvailable();
                    Event event = new Event(name, date, time, data, isAvailable);
//                    Event event = dataSnapshot.getValue(Event.class);
//                    Toast.makeText(getActivity(), "Event:" + event.getName(), Toast.LENGTH_SHORT).show();
//                    Toast.makeText(getActivity(), "Time:" + event.getTime(), Toast.LENGTH_SHORT).show();
                    mainActivity.business.events.add(event);
//                    addNewChip(event.getTime());

//                    Toast.makeText(getActivity(), "getIsAvailable: ---> " + event.getIsAvailable(), Toast.LENGTH_SHORT).show();


                    if (event.getIsAvailable().equals("1")) {
                        addNewChip(event.getTime());
                    }
                }
//                for (Event event : mainActivity.business.events){
//                    addNewChip(event.getTime());
//                }
            }

            @Override
            public void onCancelled(@NonNull DatabaseError error) {

            }
        });
        newEventButton.setVisibility(View.VISIBLE);
    }

    public void addNewChip(String chipName) {
        Chip chip = new Chip(this.getContext());
        chip.setText(chipName);
        chip.setClickable(true);
        chip.setCheckable(true);
        chip.setChecked(false);
        chip.setChipBackgroundColorResource(R.color.chip_color);
        chipGroup.addView(chip);
    }

    public void saveNewEvent(Event chosenEvent){
        String userName = mainActivity.business.busProfiler.getEmail();
        userName = userName.substring(0, userName.indexOf("@"));
        FirebaseDatabase database = FirebaseDatabase.getInstance();
        DatabaseReference myRef = database.getReference("Business").child(userName).child("Events").child(chosenEvent.getDate()).child(chosenEvent.getTime());
        myRef.setValue(chosenEvent);
        Toast.makeText(getActivity(), "The meeting has been successfully scheduled!", Toast.LENGTH_SHORT).show();
        Navigation.findNavController(getView()).navigate(R.id.action_calendarFragment_to_searchScreenFragment);

    }

    public void createMaterialDialog(){
        String userFirstName = mainActivity.user.getFirstName();
        String userLastName = mainActivity.user.getLastName();
        String userEmail = mainActivity.user.getEmail();
        String userPhone = mainActivity.user.getPhone();

        materialDialogBuilder.setTitle("New Event - " + chosenEvent.getName())
                .setIcon(R.drawable.ic_event)
                .setMessage("Event Details: " + chosenEvent.getDate() + ", " + chosenEvent.getTime()
                + "\n" +"Client Details: " + userFirstName + " " + userLastName + ", " + userEmail + ", " + userPhone)
                .setNegativeButton("Cancel", null)
                //.setNeutralButton("ca", null)
                .setPositiveButton("Accept", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int which) {
//                                name, date, time, data, isAvilable
//                                Toast.makeText(getActivity(), "!!!!!!!!!!", Toast.LENGTH_SHORT).show();
                        chosenEvent.setData("Client Details: " + userFirstName + " " + userLastName + ", " + userEmail + ", " + userPhone);
                        chosenEvent.setIsAvailable("0");
                        saveNewEvent(chosenEvent);
                    }
                })
                .show();
    }





}