package com.example.android.RATStafarians;

import android.app.*;
import android.app.ListActivity;
import android.content.Intent;
import android.icu.text.DateFormat;
import android.icu.text.SimpleDateFormat;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Spinner;
import android.widget.TextView;

import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.Query;
import com.google.firebase.database.ValueEventListener;

import java.util.Calendar;
import java.util.Date;


/**
 * Created by helen.wan on 10/16/2017.
 */

public class AddRatReportActivity extends AppCompatActivity{
    private Spinner locType;
    private TextView zipcode;
    private Button continueButton;
    private TextView address;
    private TextView city;
    private Spinner borough1;
    private Button submitButton;
    private Activity act;
    RatReport report = new RatReport();

    private RatReport newReport = new RatReport();



    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        act = this;
        setContentView(R.layout.add_rat_report_1);
        locType = (Spinner) findViewById(R.id.locType);

        locType.setAdapter(new ArrayAdapter<LocationType>
                (this, android.R.layout.simple_spinner_dropdown_item, LocationType.values()));
        zipcode = (EditText) findViewById(R.id.zipCode);
        continueButton = (Button) findViewById(R.id.continueButton);
        //Create button to continue to next page when user has submitted data
        continueButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                //Place all values entered by user into newReport
                newReport.setLocationType(locType.getSelectedItem().toString());
                String zipCode = zipcode.toString();
                zipCode = zipCode.replaceAll("\\s+","");
                newReport.setIncidentZip(zipCode);
                //Set up fields for next page for user to enter information
                setContentView(R.layout.add_rat_report_2);
                address = (EditText) findViewById(R.id.address);
                city = (EditText) findViewById(R.id.city);
                borough1 = (Spinner) findViewById(R.id.borough);
                borough1.setAdapter(new ArrayAdapter<BoroughType>
                        (act, android.R.layout.simple_spinner_dropdown_item, BoroughType.values()));
                submitButton = findViewById(R.id.submit);
                submitButton.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View view) {
                        newReport.setIncidentAddress(address.toString());
                        newReport.setCity(city.toString());
                        newReport.setBorough(borough1.getSelectedItem().toString());
                        Date date = Calendar.getInstance().getTime();

                        final Query ratQuery = FirebaseDatabase.getInstance().getReference().child("pr").
                                child("ratData").orderByKey().limitToLast(1);

                        ratQuery.addListenerForSingleValueEvent(new ValueEventListener() {
                            @Override
                            public void onDataChange(DataSnapshot dataSnapshot) {
                                report = dataSnapshot.getValue(RatReport.class);
                            }
                            @Override
                            public void onCancelled(DatabaseError databaseError) {
                                // ...
                            }
                        });
                        System.out.println(report.getUniqueKey() + " " + "ROFLCOPTER");
                        newReport.setUniqueKey("" + (report.getUniqueKey() + 1));
                        DatabaseReference mDatabase = FirebaseDatabase.getInstance().getReference();

                        mDatabase.child("qa").child("ratData").child(newReport.getUniqueKey()).setValue(newReport);

                        finish();
                    }
                });
            }
        });
    }
}
