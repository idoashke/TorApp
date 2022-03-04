package com.example.finalproject;

import android.os.Bundle;

import androidx.appcompat.widget.MenuPopupWindow;
import androidx.fragment.app.Fragment;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.AutoCompleteTextView;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Spinner;
import android.widget.Toast;


import com.example.finalproject.databinding.FragmentBusinessRegisterBinding;
import com.google.android.material.textfield.TextInputEditText;
import com.google.android.material.textfield.TextInputLayout;
import com.google.firebase.auth.FirebaseAuth;

/**
 * A simple {@link Fragment} subclass.
 * Use the {@link RegisterBusinessFragment#newInstance} factory method to
 * create an instance of this fragment.
 */
public class RegisterBusinessFragment extends Fragment {

    private FirebaseAuth mAuth;

    // TODO: Rename parameter arguments, choose names that match
    // the fragment initialization parameters, e.g. ARG_ITEM_NUMBER
    private static final String ARG_PARAM1 = "param1";
    private static final String ARG_PARAM2 = "param2";

    // TODO: Rename and change types of parameters
    private String mParam1;
    private String mParam2;

    public RegisterBusinessFragment() {
        // Required empty public constructor
    }

    /**
     * Use this factory method to create a new instance of
     * this fragment using the provided parameters.
     *
     * @param param1 Parameter 1.
     * @param param2 Parameter 2.
     * @return A new instance of fragment fragment_business_register.
     */
    // TODO: Rename and change types and number of parameters
    public static RegisterBusinessFragment newInstance(String param1, String param2) {
        RegisterBusinessFragment fragment = new RegisterBusinessFragment();
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
        FragmentBusinessRegisterBinding binding = FragmentBusinessRegisterBinding.inflate(getLayoutInflater());


        // Get a reference to the AutoCompleteTextView in the layout
        AutoCompleteTextView textView = (AutoCompleteTextView) binding.BusinessRegisterCityAc;
// Get the string array
        String[] cities = getResources().getStringArray(R.array.cities_array);
// Create the adapter and set it to the AutoCompleteTextView
        ArrayAdapter<String> adapter = new ArrayAdapter<String>(this.getActivity(), android.R.layout.simple_list_item_1, cities);
        textView.setAdapter(adapter);


        String[] categories = getResources().getStringArray(R.array.categories_array);

        AutoCompleteTextView dropdown = (AutoCompleteTextView) binding.BusinessRegisterCategoryDropdown;
        ArrayAdapter<String> adapter2 = new ArrayAdapter<String>(this.getActivity(), android.R.layout.simple_list_item_1, categories);
        dropdown.setAdapter(adapter2);





        mAuth = FirebaseAuth.getInstance();
        MainActivity main = (MainActivity) getActivity();

        Button regBusButton = (Button) binding.BusinessRegisterRegisterBusinessButton;
        TextInputLayout emailInput = (TextInputLayout) binding.BusinessRegisterEmailText;
        TextInputLayout passInput = (TextInputLayout) binding.BusinessRegisterPasswordText;

        String email = emailInput.toString().trim();
        String password = passInput.toString().trim();


        regBusButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (email.isEmpty() || password.isEmpty())
                    Toast.makeText(getActivity(), "Please fill missing fields!", Toast.LENGTH_SHORT).show();
                else {
                    main.funcBusinessReg();
//                    main.addBusinessData();
                }

            }
        });






        //return inflater.inflate(R.layout.fragment_business_register, container, false);
        return binding.getRoot();
    }
}