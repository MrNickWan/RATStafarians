package com.example.android.RATStafarians;

import android.app.ProgressDialog;
import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;

import com.github.mikephil.charting.data.Entry;
import com.google.firebase.auth.FirebaseAuth;

import java.util.HashMap;
import java.util.List;

import java.io.IOException;
import java.util.ArrayList;
import android.widget.Toast;
import com.github.mikephil.charting.charts.LineChart;
import com.github.mikephil.charting.data.LineDataSet;
import com.github.mikephil.charting.data.LineData;
import java.lang.Integer.*;

/**
 * Created by jacklafiandra on 11/4/17.
 */

public class GraphActivity extends AppCompatActivity {
    private List<RatReport> list;
    private LineChart chart;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_graph);

        chart = (LineChart) findViewById(R.id.chart);

        Bundle bundle = getIntent().getExtras();
        try {
            list = (ArrayList<RatReport>) bundle.getSerializable("listQuery");
        } catch (ClassCastException e) {
            Toast.makeText(this, "Empty list for makrkers", Toast.LENGTH_LONG)
                    .show();
        }

        List<Entry> entries = new ArrayList<Entry>();
        int month;
        int date;
        HashMap<Integer, Integer> hasher = new HashMap<Integer, Integer>();
        
        for (RatReport x : list) {

            // turn your data into Entry objects
            date = Integer.parseInt(x.getCreatedDate().substring(6,10) + x.getCreatedDate().substring(0,2)); //month
            if(hasher.get(date) == null){
                hasher.put(date,0);
            }

            hasher.put(date,hasher.get(date) + 1);
        }

        for (int data : hasher.keySet()) {

            // turn your data into Entry objects
            entries.add(new Entry(data, hasher.get(data)));
        }

        LineDataSet dataSet = new LineDataSet(entries, "X:Month/Year Y:Rat Reports in that Month/Year");

        LineData lineData = new LineData(dataSet);
        chart.setData(lineData);
        chart.invalidate();

    }

}
