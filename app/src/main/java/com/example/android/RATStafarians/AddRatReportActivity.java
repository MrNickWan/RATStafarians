package com.example.android.RATStafarians;

import android.app.*;
import android.location.Address;
import android.location.Geocoder;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Spinner;
import android.widget.TextView;
import android.widget.Toast;

import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;

import java.io.IOException;
import java.util.Calendar;
import java.util.Date;
import java.util.List;


public class AddRatReportActivity extends AppCompatActivity{
    private Spinner locType;
    private TextView zipCode;
    private Button continueButton;
    private TextView address;
    private TextView city;
    private Spinner borough1;
    private Button submitButton;
    private Activity act;
    String zipCodeStr = null;

    private RatReport newReport = new RatReport();

    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        act = this;
        setContentView(R.layout.add_rat_report);
        locType = findViewById(R.id.locType);

        Geocoder geocoder = new Geocoder(this);
        locType.setAdapter(new ArrayAdapter<>
                (this, android.R.layout.simple_spinner_dropdown_item, LocationType.values()));
        zipCode = findViewById(R.id.zipCode);

        //Place all values entered by user into newReport
        newReport.setLocationType(locType.getSelectedItem().toString());

        //Set up fields for next page for user to enter information3
        address = findViewById(R.id.address);
        city = findViewById(R.id.city);
        borough1 = findViewById(R.id.borough);
        borough1.setAdapter(new ArrayAdapter<>(act, android.R.layout.
                simple_spinner_dropdown_item, BoroughType.values()));

        submitButton = findViewById(R.id.submit);
        submitButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                zipCodeStr = zipCode.getText().toString();
                newReport.setIncidentZip(zipCodeStr);
                setGeoLocation(zipCodeStr);
                newReport.setIncidentAddress(address.getText().toString());
                newReport.setCity(city.getText().toString());
                newReport.setBorough(borough1.getSelectedItem().toString());
                Date date = Calendar.getInstance().getTime();
                Integer uniqueKey =  newReport.getUniqueKeyCounter() + 1;
                newReport.setUniqueKeyCounter(newReport.getUniqueKeyCounter() + 1);
                newReport.setUniqueKey(uniqueKey.toString());
                newReport.setCreatedDate(date.toString());
                DatabaseReference mDatabase = FirebaseDatabase.getInstance().getReference();

                mDatabase.child("qa").child("ratData").child(newReport.getUniqueKey()).setValue(newReport);

                finish();
            }
        });

    }

    protected void setGeoLocation(String zipCode) {
        try {
            Geocoder geocoder = new Geocoder(this);
            List<Address> addresses = geocoder.getFromLocationName(zipCode, 1);
            if (addresses != null && !addresses.isEmpty()) {
                Address address = addresses.get(0);
                // Use the address as needed
                newReport.setLatitude("" + address.getLatitude());
                newReport.setLongitude("" + address.getLongitude());
            } else {
                // Display appropriate message when Geocoder services are not available
                Toast.makeText(this, "Unable to geocode zipcode, so a default value is assigned",
                        Toast.LENGTH_LONG).show();
            }
        } catch (IOException e) {
            String errorMessage = "No wifi connection, so a default value is assigned";
            Toast.makeText(this, errorMessage, Toast.LENGTH_LONG).show();
        }
    }
}
