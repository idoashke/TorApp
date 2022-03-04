package com.example.finalproject;

import android.os.Bundle;

import androidx.fragment.app.Fragment;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import com.example.finalproject.databinding.FragmentClientRegisterBinding;
import com.example.finalproject.databinding.FragmentClientRegisterBinding;
import com.google.android.material.textfield.TextInputLayout;
import com.google.firebase.auth.FirebaseAuth;

/**
 * A simple {@link Fragment} subclass.
 * Use the {@link RegisterClientFragment#newInstance} factory method to
 * create an instance of this fragment.
 */
public class RegisterClientFragment extends Fragment {

    private FirebaseAuth mAuth;

    // TODO: Rename parameter arguments, choose names that match
    // the fragment initialization parameters, e.g. ARG_ITEM_NUMBER
    private static final String ARG_PARAM1 = "param1";
    private static final String ARG_PARAM2 = "param2";

    // TODO: Rename and change types of parameters
    private String mParam1;
    private String mParam2;

    public RegisterClientFragment() {
        // Required empty public constructor
    }

    /**
     * Use this factory method to create a new instance of
     * this fragment using the provided parameters.
     *
     * @param param1 Parameter 1.
     * @param param2 Parameter 2.
     * @return A new instance of fragment RegisterFragment.
     */
    // TODO: Rename and change types and number of parameters
    public static RegisterClientFragment newInstance(String param1, String param2) {
        RegisterClientFragment fragment = new RegisterClientFragment();
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
        FragmentClientRegisterBinding binding = FragmentClientRegisterBinding.inflate(getLayoutInflater());

        mAuth = FirebaseAuth.getInstance();
        MainActivity main = (MainActivity) getActivity();

        Button regClientButton = (Button) binding.clientRegisterRegisterClientButton;
//        EditText emailInput = (EditText) binding.clientRegisterEmailInput;
//        EditText passInput = (EditText) binding.clientRegisterPasswordInput;
        TextInputLayout emailInput = (TextInputLayout) binding.clientRegisterEmailText;
        TextInputLayout passInput = (TextInputLayout) binding.clientRegisterPasswordText;

        String email = emailInput.toString().trim();
        String password = passInput.toString().trim();


        regClientButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
//                String email = emailInput.getText().toString().trim();
//                String password = passInput.getText().toString().trim();
                if (email.isEmpty() || password.isEmpty())
                    Toast.makeText(getActivity(), "Please fill missing fields!", Toast.LENGTH_SHORT).show();
                else {
                    main.funcClientReg();
//                    main.addClientData();
                }

            }
        });

        //return inflater.inflate(R.layout.fragment_register, container, false);
        return binding.getRoot();
    }

//c
}