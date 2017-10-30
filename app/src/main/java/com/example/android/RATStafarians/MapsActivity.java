package com.example.android.RATStafarians;

import android.location.Address;
import android.location.Geocoder;
import android.support.v4.app.FragmentActivity;
import android.os.Bundle;
import android.widget.Toast;

import com.google.android.gms.maps.CameraUpdateFactory;
import com.google.android.gms.maps.GoogleMap;
import com.google.android.gms.maps.OnMapReadyCallback;
import com.google.android.gms.maps.SupportMapFragment;
import com.google.android.gms.maps.model.LatLng;
import com.google.android.gms.maps.model.MarkerOptions;
import com.google.firebase.FirebaseApp;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.Query;
import com.google.firebase.database.ValueEventListener;

import java.io.IOException;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;
import java.util.GregorianCalendar;
import java.util.List;
import java.util.Locale;

/**
 * The activity that displays the Google Map
 */
public class MapsActivity extends FragmentActivity implements OnMapReadyCallback {

    private GoogleMap mMap;
    private List<RatReport> list;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_maps);
        // Obtain the SupportMapFragment and get notified when the map is ready to be used.
        SupportMapFragment mapFragment = (SupportMapFragment) getSupportFragmentManager()
                .findFragmentById(R.id.map);
        mapFragment.getMapAsync(this);

        Bundle bundle = getIntent().getExtras();
        try {
            list = (ArrayList<RatReport>) bundle.getSerializable("listQuery");
        } catch (ClassCastException e) {
            Toast.makeText(this, "Empty list for makrkers", Toast.LENGTH_LONG)
                    .show();
        }
    }


    /**
     * Manipulates the map once available.
     * This callback is triggered when the map is ready to be used.
     * This is where we can add markers or lines, add listeners or move the camera. In this case,
     * we just add a marker near Sydney, Australia.
     * If Google Play services is not installed on the device, the user will be prompted to install
     * it inside the SupportMapFragment. This method will only be triggered once the user has
     * installed Google Play services and returned to the app.
     */
    @Override
    public void onMapReady(GoogleMap googleMap) {
        mMap = googleMap;
        Geocoder geocoder = new Geocoder(this);
        LatLng newYorkLatLng = new LatLng(0,0); // adds a LatLng for New York

        // Adding the markers to the map
        for (RatReport report : list) {
            LatLng reportLatLng = null;

            // If Latitude and Longitude are empty string, then use the address for LatLng
            if (report.getLatitude().length() == 0 ||
                    report.getLongitude().length() == 0) {
                try {
                    // Gets location using address and zip
                    String location = report.getIncidentAddress() + report.getIncidentZip();
                    List<Address> addresses = geocoder.getFromLocationName(location, 1);
                    reportLatLng = new LatLng(
                            addresses.get(0).getLatitude(),
                            addresses.get(0).getLongitude()
                    );
                } catch (IOException e) {
                    Toast.makeText(this, "Tossing this one out", Toast.LENGTH_LONG)
                        .show();
                }
            } else {
                // Parse the Longitude and Latitude for LatLng
                reportLatLng = new LatLng(
                        Double.parseDouble(report.getLatitude()),
                        Double.parseDouble(report.getLongitude())
                );
            }
            // Adds the marker for map
            if (reportLatLng != null) {
                mMap.addMarker(new MarkerOptions().position(reportLatLng).
                        title(report.getUniqueKey()));
            }
        }
        try {
            List<Address> addressesNY = geocoder.getFromLocationName("New York", 1);
            newYorkLatLng = new LatLng(addressesNY.get(0).getLatitude(),
                    addressesNY.get(0).getLongitude());
        } catch (IOException e) {
            Toast.makeText(this, "I am showing (0,0) instead of New York",
                    Toast.LENGTH_LONG).show();
        }
        mMap.moveCamera(CameraUpdateFactory.newLatLng(newYorkLatLng)); // moves the camera to New York
        mMap.moveCamera(CameraUpdateFactory.zoomTo(10)); // zooms the camera
    }
}