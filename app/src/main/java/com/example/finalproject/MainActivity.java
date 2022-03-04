package com.example.finalproject;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.app.AppCompatDelegate;
import androidx.navigation.Navigation;

import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.ArrayAdapter;
import android.widget.AutoCompleteTextView;
import android.widget.Button;
import android.widget.CompoundButton;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import com.example.finalproject.databinding.ActivityMainBinding;
import com.google.android.gms.common.api.Api;
import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.android.material.snackbar.Snackbar;
import com.google.android.material.switchmaterial.SwitchMaterial;
import com.google.android.material.textfield.TextInputEditText;
import com.google.android.material.textfield.TextInputLayout;
import com.google.firebase.auth.AuthResult;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.database.ChildEventListener;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

import java.lang.reflect.Array;
import java.util.ArrayList;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class MainActivity extends AppCompatActivity {

    private FirebaseAuth mAuth;
    Business business;
    User user;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        ActivityMainBinding binding = ActivityMainBinding.inflate(getLayoutInflater());
        mAuth = FirebaseAuth.getInstance();
        this.setTitle("TorApp");

//        SwitchMaterial switchMaterial = (SwitchMaterial) findViewById(R.id.switchMaterial);
//        switchMaterial.setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener() {
//            @Override
//            public void onCheckedChanged(CompoundButton buttonView, boolean isChecked) {
//                if (isChecked){
//                    AppCompatDelegate.setDefaultNightMode(AppCompatDelegate.MODE_NIGHT_YES);
//
//                } else {
//                    AppCompatDelegate.setDefaultNightMode(AppCompatDelegate.MODE_NIGHT_NO);
//                }
//            }
//        });

    }

    ///// Register Client - Firebase Authentication \\\\\
    public void funcClientReg(){

        String email = ((EditText) findViewById(R.id.client_register_email_input)).getText().toString();
        String password = ((EditText) findViewById(R.id.client_register_password_input)).getText().toString();

        mAuth.createUserWithEmailAndPassword(email, password)
                .addOnCompleteListener(this, new OnCompleteListener<AuthResult>() {
                    @Override
                    public void onComplete(@NonNull Task<AuthResult> task) {
                        if (task.isSuccessful()) {
                            addClientData();
                            Toast.makeText(MainActivity.this, "Registration Successfully!", Toast.LENGTH_SHORT).show();
                            Navigation.findNavController(getCurrentFocus()).navigate(R.id.action_registerClientFragment_to_mainFragment);

                        } else {
                            Toast.makeText(MainActivity.this, "Registration Failed!", Toast.LENGTH_SHORT).show();
                        }
                    }
                });

    }

    ///// Login Client - Firebase Authentication \\\\\
    public void funcClientLogIn() {

        String email = ((EditText) findViewById(R.id.login_email_input)).getText().toString();
        String password = ((EditText) findViewById(R.id.login_password_input)).getText().toString();

        mAuth.signInWithEmailAndPassword(email, password)
                .addOnCompleteListener(this, new OnCompleteListener<AuthResult>() {
                    @Override
                    public void onComplete(@NonNull Task<AuthResult> task) {
                        if (task.isSuccessful()) {
                            Toast.makeText(MainActivity.this, "log in Successfully!", Toast.LENGTH_SHORT).show();
                            readClientData();
                            Navigation.findNavController(getCurrentFocus()).navigate(R.id.action_mainFragment_to_searchScreenFragment);

                        } else {
                            Toast.makeText(MainActivity.this, "log in failed!", Toast.LENGTH_SHORT).show();

                        }
                    }
                });
    }

    ///// Register Business - Firebase Authentication \\\\\
    public void funcBusinessReg(){

        String email = ((EditText) findViewById(R.id.Business_register_email_input)).getText().toString();
        String password = ((EditText) findViewById(R.id.Business_register_password_input)).getText().toString();

        mAuth.createUserWithEmailAndPassword(email, password)
                .addOnCompleteListener(this, new OnCompleteListener<AuthResult>() {
                    @Override
                    public void onComplete(@NonNull Task<AuthResult> task) {
                        if (task.isSuccessful()) {
                            addBusinessData();
                            Toast.makeText(MainActivity.this, "Registration Successfully!", Toast.LENGTH_SHORT).show();
                            Navigation.findNavController(getCurrentFocus()).navigate(R.id.action_registerBusinessFragment_to_mainFragment);

                        } else {
                            Toast.makeText(MainActivity.this, "Registration Failed!", Toast.LENGTH_SHORT).show();
                        }
                    }
                });

    }

    ///// Login Business - Firebase Authentication \\\\\
    public void funcBusinessLogIn() {

        String email = ((EditText) findViewById(R.id.login_email_input)).getText().toString();
        String password = ((EditText) findViewById(R.id.login_password_input)).getText().toString();

        mAuth.signInWithEmailAndPassword(email, password)
                .addOnCompleteListener(this, new OnCompleteListener<AuthResult>() {
                    @Override
                    public void onComplete(@NonNull Task<AuthResult> task) {
                        if (task.isSuccessful()) {
                            Toast.makeText(MainActivity.this, "log in Successfully!", Toast.LENGTH_SHORT).show();
                            readBusinessData();
                            Navigation.findNavController(getCurrentFocus()).navigate(R.id.action_mainFragment_to_businessMgmt);

                        } else {
                            Toast.makeText(MainActivity.this, "log in failed!", Toast.LENGTH_SHORT).show();

                        }
                    }
                });
    }

    ///// Add New Client Data to Firebase - Firebase Realtime Database \\\\\
    public void addClientData(){
//        String userName = ((EditText) findViewById(R.id.client_register_username_input)).getText().toString();
        String email = ((EditText) findViewById(R.id.client_register_email_input)).getText().toString();
        String firstName = ((EditText) findViewById(R.id.client_register_firstname_input)).getText().toString();
        String lastName = ((EditText) findViewById(R.id.client_register_lastname_input)).getText().toString();
        String phone = ((EditText) findViewById(R.id.client_register_phone_input)).getText().toString();

        user = new User(email, firstName, lastName, phone);
        String str = email;
        str = str.substring(0, str.indexOf("@"));
        // Write a message to the database
        FirebaseDatabase database = FirebaseDatabase.getInstance();
        DatabaseReference myRef = database.getReference("Clients").child(str);

        myRef.setValue(user);
    }

    ///// Add New Business Data to Firebase - Firebase Realtime Database \\\\\
    public void addBusinessData(){
//        String userName = ((EditText) findViewById(R.id.Business_register_userNameame_input)).getText().toString();
        String busName = ((EditText) findViewById(R.id.Business_register_name_input)).getText().toString();
        String email = ((EditText) findViewById(R.id.Business_register_email_input)).getText().toString();
        String category = ((EditText) findViewById(R.id.Business_register_category_dropdown)).getText().toString();
        String city = ((EditText) findViewById(R.id.Business_register_city_ac)).getText().toString();
        String address = ((EditText) findViewById(R.id.Business_register_address_input)).getText().toString();
        String postalCode = ((EditText) findViewById(R.id.Business_register_postalcode_input)).getText().toString();
        String phone = ((EditText) findViewById(R.id.Business_register_phone_input)).getText().toString();
//        ArrayList<Event> events = new ArrayList<Event>();

        BusinessProfiler busProfiler = new BusinessProfiler(busName, email, category, city, address, postalCode, phone);

        String str = email;
        str = str.substring(0, str.indexOf("@"));
        FirebaseDatabase database = FirebaseDatabase.getInstance();

        DatabaseReference busProfilerRef = database.getReference("Business").child(str).child("BusinessProfiler");
        busProfilerRef.setValue(busProfiler);


        DatabaseReference businessLiteRef = database.getReference("BusinessLite").child(busName);
        businessLiteRef.child("busName").setValue(busName);
        businessLiteRef.child("emailName").setValue(str);
        businessLiteRef.child("category").setValue(category);


    }

    ///// Read Existing Client Data from Firebase - Firebase Realtime Database \\\\\
    public void readClientData(){
        // Read from the database
        String str = ((EditText) findViewById(R.id.login_email_input)).getText().toString();
        String temp = str.substring(0, str.indexOf("@"));
        FirebaseDatabase database = FirebaseDatabase.getInstance();
        DatabaseReference myRef = database.getReference("Clients").child(temp);


        myRef.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(DataSnapshot dataSnapshot) {
                // This method is called once with the initial value and again
                // whenever data at this location is updated.
                User value = dataSnapshot.getValue(User.class);
//                String userName = value.getUserName();
                String email = value.getEmail();
                String firstName = value.getFirstName();
                String lastName = value.getLastName();
                String phone = value.getPhone();
                user = new User(email, firstName, lastName, phone);
                Toast.makeText(MainActivity.this, "Welcome " +temp+"! ☺", Toast.LENGTH_SHORT).show();

            }

            @Override
            public void onCancelled(DatabaseError error) {
                // Failed to read value
//                Log.w(TAG, "Failed to read value.", error.toException());
                Toast.makeText(MainActivity.this, "Database Error!", Toast.LENGTH_SHORT).show();
            }
        });
    }

    ///// Read Existing Business Data from Firebase - Firebase Realtime Database \\\\\
    public void readBusinessData(){
        // Read from the database
        String str = ((EditText) findViewById(R.id.login_email_input)).getText().toString();
        String temp = str.substring(0, str.indexOf("@"));
        FirebaseDatabase database = FirebaseDatabase.getInstance();
        DatabaseReference myRef = database.getReference("Business").child(temp);
        DatabaseReference busProfilerRef = database.getReference("Business").child(temp).child("BusinessProfiler");
        DatabaseReference busEventsRef = database.getReference("Business").child(temp).child("Events");

        business = new Business();
        Toast.makeText(MainActivity.this, "Welcome " +temp+ "! ☺", Toast.LENGTH_SHORT).show();

        busProfilerRef.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot dataSnapshot) {
                BusinessProfiler busProValue = dataSnapshot.getValue(BusinessProfiler.class);
                String busName = busProValue.getBusName();
                String email = busProValue.getEmail();
                String category = busProValue.getCategory();
                String city = busProValue.getCity();
                String address = busProValue.getAddress();
                String postalCode = busProValue.getPostalCode();
                String phone = busProValue.getPhone();

                business.busProfiler = new BusinessProfiler(busName, email, category, city, address, postalCode, phone);

            }

            @Override
            public void onCancelled(@NonNull DatabaseError error) {
                Toast.makeText(MainActivity.this, "Database Error!", Toast.LENGTH_SHORT).show();
            }
        });

        busEventsRef.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot dataSnapshot) {
                for(DataSnapshot sn : dataSnapshot.getChildren()) {
                    String str = sn.getKey().toString();
                    DatabaseReference newRef = busEventsRef.child(str);
//                    Toast.makeText(MainActivity.this, "str: " + str, Toast.LENGTH_SHORT).show();

                    newRef.addValueEventListener(new ValueEventListener() {
                        @Override
                        public void onDataChange(@NonNull DataSnapshot snapshot) {
                            for (DataSnapshot shot : snapshot.getChildren()) {
                                Event value = shot.getValue(Event.class);
                                String name = value.getName();
                                String date = value.getDate();
                                String time = value.getTime();
                                String data = value.getData();
                                String isAvailable = value.getIsAvailable();
                                Event event = new Event(name, date, time, data, isAvailable);
                                business.events.add(event);
//                                Toast.makeText(MainActivity.this, "EVENT: " + business.events.get(0).getTime(), Toast.LENGTH_SHORT).show();
//                                Toast.makeText(MainActivity.this, "NAME: " + event.getName(), Toast.LENGTH_SHORT).show();
                            }

                        }

                        @Override
                        public void onCancelled(@NonNull DatabaseError error) {

                        }
                    });

                }
            }

            @Override
            public void onCancelled(@NonNull DatabaseError error) {
                Toast.makeText(MainActivity.this, "Database Error!", Toast.LENGTH_SHORT).show();
            }
        });

    }


    ///// Add New Events List Data to Business Firebase - Firebase Realtime Database \\\\\

    public void addEventsDataToBusiness(ArrayList<Event> events){
        String str = this.business.busProfiler.getEmail();
        str = str.substring(0, str.indexOf("@"));
        FirebaseDatabase database = FirebaseDatabase.getInstance();
        DatabaseReference myRef = database.getReference("Business").child(str).child("Events");
//        Snackbar.make(view, "Invalid Selection...", Snackbar.LENGTH_LONG).show();
        for (Event event : events){
            DatabaseReference myEventRef = myRef.child(event.getDate()).child(event.getTime());
            myEventRef.setValue(event);
        }
        Toast.makeText(MainActivity.this, "Events added successfully!", Toast.LENGTH_SHORT).show();
        Navigation.findNavController(getCurrentFocus()).navigate(R.id.action_businessWorkDaysHrsMgmt_to_businessMgmt);

    }

        public void readBusinessProfilerData(String input){
            FirebaseDatabase database = FirebaseDatabase.getInstance();
            DatabaseReference myRef = database.getReference("Business").child(input).child("BusinessProfiler");
            business = new Business();

            myRef.addValueEventListener(new ValueEventListener() {
                @Override
                public void onDataChange(@NonNull DataSnapshot snapshot) {
                    BusinessProfiler busProValue = snapshot.getValue(BusinessProfiler.class);
                    String busName = busProValue.getBusName();
                    String email = busProValue.getEmail();
                    String category = busProValue.getCategory();
                    String city = busProValue.getCity();
                    String address = busProValue.getAddress();
                    String postalCode = busProValue.getPostalCode();
                    String phone = busProValue.getPhone();

                    business.busProfiler = new BusinessProfiler(busName, email, category, city, address, postalCode, phone);
                }

                @Override
                public void onCancelled(@NonNull DatabaseError error) {
                    Toast.makeText(MainActivity.this, "Failed Reading Business Data!", Toast.LENGTH_SHORT).show();

                }
            });

    }


}
