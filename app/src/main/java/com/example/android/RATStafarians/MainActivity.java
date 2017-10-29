package com.example.android.RATStafarians;

import android.app.DatePickerDialog;
import android.app.Dialog;
import android.content.Intent;
import android.os.Bundle;
import android.support.v4.app.DialogFragment;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.Button;
import android.widget.DatePicker;
import android.widget.TextView;

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
import java.util.List;
import java.util.Locale;


/**
 * Created by robert on 10/28/17.
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
    private SimpleDateFormat dateFormat;
    private ArrayList<RatReport> list;
    private Button confirmDates;

    @Override
    protected void onCreate(Bundle savedInstanceData) {
        super.onCreate(savedInstanceData);
        setContentView(R.layout.activity_main);
        list = new ArrayList<>();

        startDate = findViewById(R.id.beg_date_view);
        endDate = findViewById(R.id.end_date_view);
        listButton = findViewById(R.id.list_button);
        mapButton = findViewById(R.id.map_button);
        confirmDates = findViewById(R.id.confirm_dates);

        datePicker = new DatePickerFragment();

        startButton = findViewById(R.id.beg_date);
        endButton = findViewById(R.id.end_date);

        startButton.setOnClickListener(this);
        endButton.setOnClickListener(this);
        mapButton.setOnClickListener(this);
        listButton.setOnClickListener(this);
        confirmDates.setOnClickListener(this);

        dateFormat = new SimpleDateFormat("MM/dd/yyyy hh:mm:ss a",
                Locale.US);
    }

    public void onClick(View v) {
        int id = v.getId();
        if (id == startButton.getId()) {
            datePicker.setFlag(0);
            datePicker.show(getSupportFragmentManager(), "datePicker");
        } else if (id == endButton.getId()) {
            datePicker.setFlag(1);
            datePicker.show(getSupportFragmentManager(), "datePicker");
        } else if (id == mapButton.getId()) {
            Intent mapIntent = new Intent(MainActivity.this, MapsActivity.class);
            mapIntent.putExtra("listQuery", list);
            startActivity(mapIntent);
        } else if (id == listButton.getId()) {
            Intent listIntent = new Intent(MainActivity.this, ListActivity.class);
            startActivity(listIntent);
        } else if (id == confirmDates.getId()) {
            queryForMap();
            startDate.setText(dateFormat.format(startDateObj));
            endDate.setText(dateFormat.format(endDateObj));
        }
    }

    private void queryForMap() {

        final Query markerQuery = FirebaseDatabase.getInstance().getReference().child("qa")
                .child("ratData");

        markerQuery.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(DataSnapshot dataSnapshot) {
                list.clear(); // clears so that duplicates won't happen

                for (DataSnapshot reportSnap : dataSnapshot.getChildren()) {
                    RatReport report = reportSnap.getValue(RatReport.class);
                    try {
                        String date = report.getCreatedDate();
                        Date reportDate = dateFormat.parse(date);
                        if (reportDate.after(startDateObj) && reportDate.before(endDateObj)) {
                            list.add(report);
                        }
                    } catch (ParseException e) {
                        e.printStackTrace();
                    }
                }

            }

            // Haven't thought of what we need for this yet, so this is just here and empty
            @Override
            public void onCancelled(DatabaseError databaseError) {

            }
        });
    }

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

        public void onDateSet(DatePicker view, int year, int month, int day) {
            // Do something with the date chosen by the user
            Calendar calendar = Calendar.getInstance();
            calendar.set(year,month,day);
            Date date =  calendar.getTime();
            if (flag == FLAG_START_DATE) {
                startDateObj = date;
            } else if (flag == FLAG_END_DATE) {
                endDateObj = date;
            }
        }
    }


}
