package com.example.android.RATStafarians;

import android.app.DatePickerDialog;
import android.app.Dialog;
import android.content.Intent;
import android.os.AsyncTask;
import android.os.Bundle;
import android.support.v4.app.DialogFragment;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.DatePicker;
import android.widget.TextView;
import android.widget.Toast;

import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.Query;
import com.google.firebase.database.ValueEventListener;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;
import java.util.Locale;


/**
 * The main activity the application runs after logging in. It prompts you to select dates
 * for the MapsActivity and loads the query using the confirm dates button. Also, allows you to
 * go view the list and go back to the login screen.
 */
public class MainActivity extends AppCompatActivity implements View.OnClickListener{

    private TextView startDate;
    private static Date startDateObj;
    private Button startButton;
    private TextView endDate;
    private static Date endDateObj;
    private Button endButton;
    private DatePickerFragment datePicker;
    private Button listButton;
    private Button mapButton;
    private Button graphButton;
    private SimpleDateFormat dateFormat;
    private Button confirmDates;
    private Button logout;
    static Model model;

    @Override
    protected void onCreate(Bundle savedInstanceData) {
        super.onCreate(savedInstanceData);
        setContentView(R.layout.activity_main);
        model = Model.get();
        model.list = new ArrayList<>();

        startDate = findViewById(R.id.beg_date_view);
        endDate = findViewById(R.id.end_date_view);
        listButton = findViewById(R.id.list_button);
        graphButton = findViewById(R.id.graph_button);
        mapButton = findViewById(R.id.map_button);
        confirmDates = findViewById(R.id.confirm_dates);

        datePicker = new DatePickerFragment();

        logout = findViewById(R.id.logout);
        startButton = findViewById(R.id.beg_date);
        endButton = findViewById(R.id.end_date);

        startButton.setOnClickListener(this);
        endButton.setOnClickListener(this);
        mapButton.setOnClickListener(this);
        listButton.setOnClickListener(this);
        graphButton.setOnClickListener(this);
        confirmDates.setOnClickListener(this);
        logout.setOnClickListener(this);

        dateFormat = new SimpleDateFormat("MM/dd/yyyy hh:mm:ss a",
                Locale.US);
    }

    /**
     * The onClick method directs and controls the activity functions for the different
     * buttons on the screen.
     * @param v
     */
    public void onClick(View v) {
        int id = v.getId();
        if (id == startButton.getId()) { // if user clicks start date button
            datePicker.setFlag(0); // sets the flag to start date
            datePicker.show(getSupportFragmentManager(), "datePicker");
        } else if (id == endButton.getId()) { // if user clicks end date button
            datePicker.setFlag(1); // sets the flag to end date
            datePicker.show(getSupportFragmentManager(), "datePicker");
        } else if (id == mapButton.getId()) { // if user clicks the map button
            Intent mapIntent = new Intent(MainActivity.this, MapsActivity.class);
            startActivity(mapIntent);
        } else if (id == graphButton.getId()) { // if user clicks the map button
            Intent graphIntent = new Intent(MainActivity.this, GraphActivity.class);
            startActivity(graphIntent);
        } else if (id == listButton.getId()) { // if user clicks list button
            Intent listIntent = new Intent(MainActivity.this, ListActivity.class);
            startActivity(listIntent);
        } else if (id == confirmDates.getId()) {
            queryForMap(); // calls queryForMap to query the rat report list into the list
            startDate.setText(dateFormat.format(startDateObj));
            endDate.setText(dateFormat.format(endDateObj));
        } else if (id == logout.getId()) {
            Intent returnInt = new Intent(MainActivity.this, LoginActivity.class);
            startActivity(returnInt);
        }
    }

    /**
     * Private method to query and load the list for MapsActivity
     */
    private void queryForMap() {

        final Query markerQuery = FirebaseDatabase.getInstance().getReference().child("pr")
                .child("ratData").limitToLast(5000);

        markerQuery.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(DataSnapshot dataSnapshot) {
                model.list.clear(); // clears so that duplicates won't happen

                for (DataSnapshot reportSnap : dataSnapshot.getChildren()) {
                    RatReport report = reportSnap.getValue(RatReport.class);
                    try {
                        String date = report.getCreatedDate(); // Gets the date
                        Date reportDate = dateFormat.parse(date); // Parse the date into Date obj
                        if (reportDate.after(startDateObj) && reportDate.before(endDateObj)) {
                            model.list.add(report); // if before end date and after start date add to list
                        }
                    } catch (ParseException e) {
                        e.printStackTrace();
                    }
                }
                confirmDates.setText("Finished Querying");
            }

            // Haven't thought of what we need for this yet, so this is just here and empty
            @Override
            public void onCancelled(DatabaseError databaseError) {

            }
        });
    }

    /**
     * @inheritDoc
     */
    public static class DatePickerFragment extends DialogFragment
            implements DatePickerDialog.OnDateSetListener {

        public static final int FLAG_START_DATE = 0;
        public static final int FLAG_END_DATE = 1;

        private int flag;

        @Override
        public Dialog onCreateDialog(Bundle savedInstanceState) {
            // Use the current date as the default date in the picker
            final Calendar c = Calendar.getInstance();
            int year = c.get(Calendar.YEAR);
            int month = c.get(Calendar.MONTH);
            int day = c.get(Calendar.DAY_OF_MONTH);

            // Create a new instance of DatePickerDialog and return it
            return new DatePickerDialog(getActivity(), this, year, month, day);
        }

        public void setFlag(int i) {
            flag = i;
        }

        @Override
        public void onDateSet(DatePicker view, int year, int month, int day) {
            // Do something with the date chosen by the user
            Calendar calendar = Calendar.getInstance();
            calendar.set(year,month,day); // sets the highlighted date to current time
            Date date =  calendar.getTime(); // gets the new selected date
            if (flag == FLAG_START_DATE) {
                startDateObj = date; // if start date then point startDateObj to this
            } else if (flag == FLAG_END_DATE) {
                endDateObj = date; // if end date then point endDateObj to this
            }
        }
    }

}
