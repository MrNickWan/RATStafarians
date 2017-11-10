package com.example.android.RATStafarians;

import android.app.*;
import android.location.Address;
import android.location.Geocoder;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.Spinner;
import android.widget.TextView;
import android.widget.Toast;

import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;

import java.io.IOException;
import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;
import java.util.List;
import java.util.Locale;

/**
 * The activity class is for creating and adding rat reports to the Firebase database. It also
 * makes sure the rat report persist in the database.
 */
public class AddRatReportActivity extends AppCompatActivity{
    private TextView zipCode;
    private TextView address;
    private TextView city;
    private Spinner borough1;
    private String zipCodeStr = null;

    private RatReport newReport = new RatReport();

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        Activity act = this;
        setContentView(R.layout.add_rat_report);
        Spinner locType = findViewById(R.id.locType);

        Geocoder geocoder = new Geocoder(this);
        locType.setAdapter(new ArrayAdapter<>
                (this, android.R.layout.simple_spinner_dropdown_item, LocationType.values()));
        zipCode = findViewById(R.id.zipCode);

        //Place all values entered by user into newReport
        newReport.setLocationType(locType.getSelectedItem().toString());

        //Set up fields for user to enter information
        address = findViewById(R.id.address);
        city = findViewById(R.id.city);

        borough1 = findViewById(R.id.borough);

        //Set borough adapter for the enum values of the borough
        borough1.setAdapter(new ArrayAdapter<>(act, android.R.layout.
                simple_spinner_dropdown_item, BoroughType.values()));

        Button submitButton = findViewById(R.id.submit);
        Button cancelButton = findViewById(R.id.cancel);

        submitButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                SimpleDateFormat sdf = new SimpleDateFormat("MM/dd/yyyy hh:mm:ss a",
                        Locale.US);

                zipCodeStr = zipCode.getText().toString();
                newReport.setIncidentZip(zipCodeStr);
                String longLat = address.getText().toString() + zipCodeStr;
                setGeoLocation(longLat);
                newReport.setIncidentAddress(address.getText().toString());
                newReport.setCity(city.getText().toString());
                newReport.setBorough(borough1.getSelectedItem().toString());
                Date date = Calendar.getInstance().getTime();
                String dateStr = sdf.format(date);
                Integer uniqueKey =  newReport.getUniqueKeyCounter() + 1;
                newReport.setUniqueKeyCounter(newReport.getUniqueKeyCounter() + 1);
                newReport.setUniqueKey(uniqueKey.toString());
                newReport.setCreatedDate(dateStr);
                DatabaseReference mDatabase = FirebaseDatabase.getInstance().getReference();

                //Puts the new rat report into the database
                mDatabase.child("qa").child("ratData").child(newReport.getUniqueKey()).setValue(newReport);

                finish();
            }
        });

        cancelButton.setOnClickListener(new View.OnClickListener() {

            @Override
            public void onClick(View view) {
                finish();
                }
            }
        );


    }

    /**
     * The method takes in the zip code the user types in and sets the longitude and latitude, and
     * if the zip code is not valid, it sets up to the default values
     * @param geoLocation the zip code input for the method
     */
    private void setGeoLocation(String geoLocation) {
        try {
            Geocoder geocoder = new Geocoder(this);
            List<Address> addresses = geocoder.getFromLocationName(geoLocation, 1);
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
