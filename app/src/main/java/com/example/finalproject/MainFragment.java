package com.example.finalproject;

import android.content.Context;
import android.content.DialogInterface;
import android.os.Bundle;

import androidx.appcompat.app.AppCompatDelegate;
import androidx.fragment.app.Fragment;
import androidx.navigation.Navigation;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.CompoundButton;

import com.example.finalproject.databinding.FragmentMainBinding;
import com.google.android.material.dialog.MaterialAlertDialogBuilder;
import com.google.android.material.snackbar.Snackbar;
import com.google.android.material.switchmaterial.SwitchMaterial;
import com.google.firebase.auth.FirebaseAuth;

import java.util.ArrayList;

/**
 * A simple {@link Fragment} subclass.
 * Use the {@link MainFragment#newInstance} factory method to
 * create an instance of this fragment.
 */
public class MainFragment extends Fragment {

    private FirebaseAuth mAuth;

    // TODO: Rename parameter arguments, choose names that match
    // the fragment initialization parameters, e.g. ARG_ITEM_NUMBER
    private static final String ARG_PARAM1 = "param1";
    private static final String ARG_PARAM2 = "param2";

    // TODO: Rename and change types of parameters
    private String mParam1;
    private String mParam2;

    public MainFragment() {
        // Required empty public constructor
    }

    /**
     * Use this factory method to create a new instance of
     * this fragment using the provided parameters.
     *
     * @param param1 Parameter 1.
     * @param param2 Parameter 2.
     * @return A new instance of fragment MainFragment.
     */
    // TODO: Rename and change types and number of parameters
    public static MainFragment newInstance(String param1, String param2) {
        MainFragment fragment = new MainFragment();
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
        FragmentMainBinding binding = FragmentMainBinding.inflate(getLayoutInflater());

        View view = binding.getRoot();


        SwitchMaterial switchMaterial = (SwitchMaterial) binding.switchMaterial;
        switchMaterial.setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(CompoundButton buttonView, boolean isChecked) {
                if (isChecked){
                    AppCompatDelegate.setDefaultNightMode(AppCompatDelegate.MODE_NIGHT_YES);

                } else {
                    AppCompatDelegate.setDefaultNightMode(AppCompatDelegate.MODE_NIGHT_NO);
                }
            }
        });
//        Button regClient = (Button) binding.loginRegisterClientButton;
//        Button regBusiness = (Button) binding.loginRegisterBusinessButton;
        Button logIn = (Button) binding.loginLoginButton;
        Button signUp = (Button) binding.registerTextButton;

        mAuth = FirebaseAuth.getInstance();
        MainActivity main = (MainActivity) getActivity();

        String[] regTypes = {"Client", "Business"};
        int checkedItem[] = {-1};

        Context context = this.getContext();
        MaterialAlertDialogBuilder materialAlertDialogBuilder = new MaterialAlertDialogBuilder(context);


        signUp.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                materialAlertDialogBuilder.setTitle("Select Account Type")
                        .setPositiveButton("OK", new DialogInterface.OnClickListener() {
                            @Override
                            public void onClick(DialogInterface dialog, int which) {
                                if (checkedItem[0] == 0) {
                                    Navigation.findNavController(view).navigate(R.id.action_mainFragment_to_registerClientFragment);
                                } else if (checkedItem[0] == 1){
                                    Navigation.findNavController(view).navigate(R.id.action_mainFragment_to_registerBusinessFragment);
                                }
                                else if (checkedItem[0] == -1){
                                    Snackbar.make(getView(), "Invalid Selection...", Snackbar.LENGTH_LONG).show();
                                }

                            }
                        })
                        .setNegativeButton("Cancel", null)
                        .setSingleChoiceItems(regTypes, checkedItem[0], new DialogInterface.OnClickListener() {
                            @Override
                            public void onClick(DialogInterface dialog, int which) {
                                checkedItem[0] = which;
                            }
                        })
                        .show();
            }
        });





//        ///// Move to RegisterClientFragment when clicking create new client button \\\\\
//        regClient.setOnClickListener(new View.OnClickListener() {
//            @Override
//            public void onClick(View v) {
//
////                Navigation.findNavController(view).navigate(R.id.action_mainFragment_to_registerClientFragment);
//                materialAlertDialogBuilder.setTitle("Select Account Type")
//                        .setPositiveButton("Create", new DialogInterface.OnClickListener() {
//                            @Override
//                            public void onClick(DialogInterface dialog, int which) {
//                                if (which == 1) {
//                                    Navigation.findNavController(view).navigate(R.id.action_mainFragment_to_registerClientFragment);
//                                } else {
//                                    Navigation.findNavController(view).navigate(R.id.action_mainFragment_to_registerBusinessFragment);
//                                }
//
//                            }
//                        })
//                        .setNegativeButton("Cancel", null)
//                        .setSingleChoiceItems(regTypes, 0, null)
//                        .show();
//            }
//        });
//
//        ///// Move to RegisterBusinessFragment when clicking create new Business button \\\\\
//        regBusiness.setOnClickListener(new View.OnClickListener() {
//            @Override
//            public void onClick(View v) {
//                Navigation.findNavController(view).navigate(R.id.action_mainFragment_to_registerBusinessFragment);
//            }
//        });

        ///// Login Client \\\\\
        logIn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                //main.funcClientLogIn();
                if (binding.loginClientButton.isChecked()){
                    main.funcClientLogIn();
//                    main.readClientData();
                    //Navigation.findNavController(view).navigate(R.id.action_mainFragment_to_searchScreenFragment);
                } else if (binding.loginBusinessButton.isChecked()){
                    main.funcBusinessLogIn();
//                    main.readBusinessData();
                }
            }
        });


        ///// just to check the business mgmt fragment, delete it later \\\\\
//        Button tempButton = (Button) binding.tempButton;

//        tempButton.setOnClickListener(new View.OnClickListener() {
//            @Override
//            public void onClick(View v) {
//                Navigation.findNavController(view).navigate(R.id.action_mainFragment_to_businessWorkDaysHrsMgmt);
//
//            }
//        });


        //return inflater.inflate(R.layout.fragment_main, container, false);
        return binding.getRoot();
    }
}