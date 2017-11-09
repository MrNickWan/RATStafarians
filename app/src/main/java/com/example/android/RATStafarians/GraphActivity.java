package com.example.android.RATStafarians;

import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import com.github.mikephil.charting.charts.LineChart;
import com.github.mikephil.charting.components.AxisBase;
import com.github.mikephil.charting.components.Description;
import com.github.mikephil.charting.components.XAxis;
import com.github.mikephil.charting.data.LineData;
import com.github.mikephil.charting.data.LineDataSet;
import com.github.mikephil.charting.data.Entry;
import com.github.mikephil.charting.formatter.IAxisValueFormatter;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Collections;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.ArrayList;
import java.util.Locale;

/**
 * A class to handle to graphing activities.
 */

public class GraphActivity extends AppCompatActivity {
    static Model model; // Singleton of model
    private LineChart chart; // The chart
    SimpleDateFormat dateFormat; // Date formatter for the x axis and handling

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_graph);
        dateFormat = new SimpleDateFormat("yyyy/MM", Locale.US);

        model = Model.get();
        chart = findViewById(R.id.chart);

        List<Entry> entries = new ArrayList<>();
        HashMap<Date, Integer> hasher = new HashMap<>();

        for (RatReport x : model.list) {

            // Putting in dates as the hashed keys.
            Date date = new Date(); //month
            try {
                date = dateFormat.parse(x.getCreatedDate().substring(6,10) +
                        "/" + x.getCreatedDate().substring(0,2));
            } catch (ParseException e) {
                e.printStackTrace();
            }
            // if date doesn't exist in hasher yet, put in zero for value of that date.
            if(hasher.get(date) == null){
                hasher.put(date,0);
            }

            hasher.put(date,hasher.get(date) + 1); // Increments the counter for each month.
        }
        ArrayList<Date> xLabelList = new ArrayList<>(hasher.keySet());
        Collections.sort(xLabelList); // Sorts the dates into an order
        for (Date data : xLabelList) {
            // turn your data into Entry objects of float values to make sure graph in continuous.
            entries.add(new Entry(new Long(data.getTime()).floatValue(), hasher.get(data)));
        }

        LineDataSet dataSet = new LineDataSet(entries, "X:Month/Year Y:Rat Reports in that Month/Year");

        LineData lineData = new LineData(dataSet);
        chart.setData(lineData);
        chart.invalidate();
        Description desc = new Description();
        desc.setText("The Number of Reports Per Month in the Date Range.");
        chart.setDescription(desc);
        XAxis xAxis = chart.getXAxis();
        xAxis.setValueFormatter(new DateValueFormatter());

    }

    /**
     * The Formatter for the x axis of the chart
     */
    public class DateValueFormatter implements IAxisValueFormatter {

        @Override
        public String getFormattedValue(float value, AxisBase axis) {
            // Returns the float value into a date format of yyyy/MM
            return dateFormat.format(new Date(new Float(value).longValue()));
        }
        // ...
    }

}
