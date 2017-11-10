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

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

/**
 * The activity that displays the Google Map
 */
public class MapsActivity extends FragmentActivity implements OnMapReadyCallback {

    private GoogleMap mMap;
    static Model model;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        model = Model.get(); // Gets the model singleton.
        setContentView(R.layout.activity_maps);
        // Obtain the SupportMapFragment and get notified when the map is ready to be used.
        SupportMapFragment mapFragment = (SupportMapFragment) getSupportFragmentManager()
                .findFragmentById(R.id.map);
        mapFragment.getMapAsync(this);
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
        LatLng newYorkLatLng;

        // Adding the markers to the map
        int i = 0;
        while (i < 300 && i < model.list.size()) {
            LatLng reportLatLng = null;
            RatReport report = model.list.get(i++);
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
        newYorkLatLng = new LatLng(40.730610, -73.935242);
        mMap.moveCamera(CameraUpdateFactory.newLatLng(newYorkLatLng)); // moves the camera to New York
        mMap.moveCamera(CameraUpdateFactory.zoomTo(10)); // zooms the camera
    }
}
