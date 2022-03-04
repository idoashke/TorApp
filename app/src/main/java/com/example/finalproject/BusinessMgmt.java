package com.example.finalproject;

import android.annotation.SuppressLint;
import android.os.Bundle;

import androidx.annotation.NonNull;
import androidx.fragment.app.Fragment;
import androidx.navigation.Navigation;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.AutoCompleteTextView;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import com.example.finalproject.databinding.FragmentBusinessMgmtBinding;
import com.google.android.material.card.MaterialCardView;
import com.google.android.material.datepicker.MaterialDatePicker;
import com.google.android.material.datepicker.MaterialPickerOnPositiveButtonClickListener;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

import java.util.ArrayList;

/**
 * A simple {@link Fragment} subclass.
 * Use the {@link BusinessMgmt#newInstance} factory method to
 * create an instance of this fragment.
 */
public class BusinessMgmt extends Fragment {

    private String chosenDate;
    MaterialCardView materialCardView;


    // TODO: Rename parameter arguments, choose names that match
    // the fragment initialization parameters, e.g. ARG_ITEM_NUMBER
    private static final String ARG_PARAM1 = "param1";
    private static final String ARG_PARAM2 = "param2";

    // TODO: Rename and change types of parameters
    private String mParam1;
    private String mParam2;

    public BusinessMgmt() {
        // Required empty public constructor
    }

    /**
     * Use this factory method to create a new instance of
     * this fragment using the provided parameters.
     *
     * @param param1 Parameter 1.
     * @param param2 Parameter 2.
     * @return A new instance of fragment BusinessMgmt.
     */
    // TODO: Rename and change types and number of parameters
    public static BusinessMgmt newInstance(String param1, String param2) {
        BusinessMgmt fragment = new BusinessMgmt();
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

        FragmentBusinessMgmtBinding binding = FragmentBusinessMgmtBinding.inflate(getLayoutInflater());
        // Inflate the layout for this fragment

        MainActivity mainActivity = (MainActivity) getActivity();

        Button mgmtWrkHrs = (Button) binding.mgmtWorkingHours;

        mgmtWrkHrs.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                Navigation.findNavController(getView()).navigate(R.id.action_businessMgmt_to_businessWorkDaysHrsMgmt);


            }
        });


        materialCardView = (MaterialCardView) binding.cardview;


//        AutoCompleteTextView dropdown = (AutoCompleteTextView) binding.meetingsDropdown;
//        ArrayList<String> meetings = new ArrayList<String>();
//        ArrayAdapter<String> adapter2 = new ArrayAdapter<String>(this.getActivity(), android.R.layout.simple_list_item_1, meetings);
//        dropdown.setAdapter(adapter2);
//
//        dropdown.setOnClickListener(new View.OnClickListener() {
//            @Override
//            public void onClick(View v) {
//                for (Event event : mainActivity.business.events){
////                    meetings.add(event.getDate().toString());
//                    adapter2.add(event.getDate());
//
//                }
//            }
//        });




//        String input = ((EditText) dropdown).getText().toString();


        Button mPickDateButton = (Button) binding.pickDateButton;
        TextView mShowSelectedDateText = (TextView) binding.showSelectedDate;
        Button showButton = (Button) binding.showButton;

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

                        mShowSelectedDateText.setText("Selected Date is : " + materialDatePicker.getHeaderText());
                        mShowSelectedDateText.setVisibility(View.VISIBLE);
                        chosenDate = materialDatePicker.getHeaderText();
                        showButton.setVisibility(View.VISIBLE);
                    }
                });

        showButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                materialCardView.removeAllViews();
                readMeetingsDB();
            }
        });




        //return inflater.inflate(R.layout.fragment_business_mgmt, container, false);
        return binding.getRoot();
    }


public void readMeetingsDB() {
    MainActivity mainActivity = (MainActivity) getActivity();
    String str = mainActivity.business.busProfiler.getEmail();
    str = str.substring(0, str.indexOf("@"));
    FirebaseDatabase database = FirebaseDatabase.getInstance();
    DatabaseReference myRef = database.getReference("Business").child(str).child("Events").child(chosenDate);
    myRef.addValueEventListener(new ValueEventListener() {
        @Override
        public void onDataChange(@NonNull DataSnapshot snapshot) {
            for (DataSnapshot dataSnapshot : snapshot.getChildren()) {
                Event value = dataSnapshot.getValue(Event.class);
                String name = value.getName();
                String date = value.getDate();
                String time = value.getTime();
                String data = value.getData();
                String isAvailable = value.getIsAvailable();
                addNewMeeting(date, time, data);
            }
//            TextView textView = new TextView(getContext());
//            textView.setText("Meeting information: " + date + ", " + time + ", " + data );
//            Event event = new Event(name, date, time, data, isAvailable);
        }

        @Override
        public void onCancelled(@NonNull DatabaseError error) {

        }
    });

}

public void addNewMeeting(String date, String time, String data) {
    TextView textView = new TextView(this.getContext());
    textView.setText("Meeting information: " + date + ", " + time + ", " + data + "\n" );
    materialCardView.addView(textView);
}

}