package com.example.finalproject;

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
import android.widget.SearchView;
import android.widget.Spinner;
import android.widget.Toast;

import com.example.finalproject.databinding.FragmentSearchScreenBinding;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

import java.lang.reflect.Array;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.Map;
import java.util.stream.Collectors;

/**
 * A simple {@link Fragment} subclass.
 * Use the {@link SearchScreenFragment#newInstance} factory method to
 * create an instance of this fragment.
 */
public class SearchScreenFragment extends Fragment {
    ArrayAdapter<String> adapter2;
    Map<String, String> busList;
    AutoCompleteTextView textView;
    String busInputName;
    String busInputCategory;



    // TODO: Rename parameter arguments, choose names that match
    // the fragment initialization parameters, e.g. ARG_ITEM_NUMBER
    private static final String ARG_PARAM1 = "param1";
    private static final String ARG_PARAM2 = "param2";

    // TODO: Rename and change types of parameters
    private String mParam1;
    private String mParam2;

    public SearchScreenFragment() {
        // Required empty public constructor
    }

    /**
     * Use this factory method to create a new instance of
     * this fragment using the provided parameters.
     *
     * @param param1 Parameter 1.
     * @param param2 Parameter 2.
     * @return A new instance of fragment SearchScreenFragment.
     */
    // TODO: Rename and change types and number of parameters
    public static SearchScreenFragment newInstance(String param1, String param2) {
        SearchScreenFragment fragment = new SearchScreenFragment();
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
        FragmentSearchScreenBinding binding = FragmentSearchScreenBinding.inflate(getLayoutInflater());

        View view = binding.getRoot();
        MainActivity mainActivity = (MainActivity) getActivity();

//        Spinner dropdown = (Spinner) binding.searchBusinessCategorySpinner;
//        String[] categories = getResources().getStringArray(R.array.categories_array);
//        ArrayAdapter<String> adapter = new ArrayAdapter<String>(this.getActivity(), android.R.layout.simple_list_item_1, categories);
//        dropdown.setAdapter(adapter);


        String[] categories = getResources().getStringArray(R.array.categories_array);
        AutoCompleteTextView dropdown = (AutoCompleteTextView) binding.SearchScreenCategoryDropdown;
        ArrayAdapter<String> adapter2 = new ArrayAdapter<String>(this.getActivity(), android.R.layout.simple_list_item_1, categories);
        dropdown.setAdapter(adapter2);


        Button searchButton = (Button) binding.searchBusinessButton;
//        Spinner spinner = (Spinner) binding.searchBusinessCategorySpinner;


        busList = new HashMap<String,String>();
        textView = (AutoCompleteTextView) binding.searchBusinessAc;
         readAllBusinessLiteData();


        searchButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                busInputName = ((EditText) binding.searchBusinessAc).getText().toString();
//                busInputCategory = spinner.getSelectedItem().toString();
                busInputCategory = ((EditText) binding.SearchScreenCategoryDropdown).getText().toString();

                if (busList.keySet().contains(busInputName)) {

                    mainActivity.readBusinessProfilerData(busList.get(busInputName).toString());

                    Toast.makeText(getActivity(), "Welcome to " + busInputName + " page!", Toast.LENGTH_SHORT).show();
                    Navigation.findNavController(view).navigate(R.id.action_searchScreenFragment_to_calendarFragment);

//                    readAllBusinessData(busInputName, busInputCategory);
                } else{
                    Toast.makeText(getActivity(), "The business wasn't found", Toast.LENGTH_SHORT).show();
                }
            }
        });

        //return inflater.inflate(R.layout.fragment_search_screen, container, false);
        return binding.getRoot();

    }



    public void readAllBusinessLiteData(){
        FirebaseDatabase database = FirebaseDatabase.getInstance();
        DatabaseReference businessLiteRef = database.getReference("BusinessLite");

        businessLiteRef.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot snapshot) {
                for (DataSnapshot dataSnapshot : snapshot.getChildren()){
                    String busName = dataSnapshot.child("busName").getValue(String.class).toString();
                    String userName = dataSnapshot.child("emailName").getValue(String.class).toString();
                    busList.put(busName, userName);
                }
                ArrayList<String> busNamesList = new ArrayList<String>(busList.keySet());
                adapter2 = new ArrayAdapter<String>(getActivity(), android.R.layout.simple_list_item_1, busNamesList);
                textView.setAdapter(adapter2);
            }

            @Override
            public void onCancelled(@NonNull DatabaseError error) {
                Toast.makeText(getActivity(), "Database Error!", Toast.LENGTH_SHORT).show();
            }
        });

    }



//    public void readAllBusinessData(String name, String category){
//        FirebaseDatabase database = FirebaseDatabase.getInstance();
//        DatabaseReference businessLiteRef = database.getReference("BusinessLite").child(name);
//        businessLiteRef.addListenerForSingleValueEvent(new ValueEventListener() {
//            @Override
//            public void onDataChange(@NonNull DataSnapshot snapshot) {
//                if (snapshot.exists()) {
//                    snapshot.child("busName").getValue(String.class);
//                    snapshot.child("busName").getValue(String.class);
//                }
//                else {
//                    Toast.makeText(getActivity(), "No business found", Toast.LENGTH_SHORT).show();
//                }
//
//            }
//
//            @Override
//            public void onCancelled(@NonNull DatabaseError error) {
//
//            }
//        });
//
//        }

//    }

}