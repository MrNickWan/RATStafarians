package com.example.android.RATStafarians;

import android.app.Activity;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;
import android.widget.ArrayAdapter;

import java.util.List;

/**
 * Created by Robert
 *
 * Basically, this class is another Model class for adapting the data and use it for the listview
 */

public class RatAdapter extends ArrayAdapter<RatReport> {
    private Activity context;
    List<RatReport> reports;

    /**
     * The constructor for the RatAdapter class
     * @param context the context within which the RatAdapter is created
     * @param reports the list of rat reports
     */
    public RatAdapter(Activity context, List<RatReport> reports) {
        super(context, R.layout.item_desc, reports);
        this.context = context;
        this.reports = reports;
    }


    /**
     * {@inheritDoc}
     */
    @Override
    public View getView(int position, View convertView, ViewGroup parent) {
        LayoutInflater inflater = context.getLayoutInflater();
        View listViewItem = inflater.inflate(R.layout.item_desc, null, true);

        // Should look at the res.layout.item_desc.xml for reference to these UIs
        TextView reportDate = listViewItem.findViewById(R.id.date);
        TextView reportDesc = listViewItem.findViewById(R.id.desc);

        RatReport ratReport = reports.get(position); // Gets the RatReport
        reportDate.setText(ratReport.getCreatedDate()); // This line and the next sets the TextViews
        reportDesc.setText(ratReport.getIncidentAddress() + ", " + ratReport.getCity());

        return listViewItem; // Returns the item
    }
}