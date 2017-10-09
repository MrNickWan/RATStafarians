package com.example.android.firebasetest;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.LinearLayoutManager;
import android.widget.ListView;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.Query;
import com.google.firebase.database.ValueEventListener;
import java.util.ArrayList;
import java.util.List;

/**
 * This default activity for getting the list of RatReports should probably change it more
 * and place in the Controller folder as it is an activity
 */
public class MainActivity extends AppCompatActivity {

    private ListView ratReportsList; // ListView of RatReports
    private List<RatReport> list = new ArrayList<>(); // A list of the reports

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        // Don;t ask me what this following three lines do, I don't know
        LinearLayoutManager mManager = new LinearLayoutManager(this);
        mManager.setReverseLayout(true);
        mManager.setStackFromEnd(true);

        // Under activity_main.xml, this a ratListings ListView UI
        ratReportsList = findViewById(R.id.ratListings);

        /*
          Queries the 50 most recent reports and change the limitToLast to change
           how many reports you want. Also, you should set the database to the Firebase using
           the tool kit on the IDE toolbar under Tools, Firebase
          */
        final Query ratQuery = FirebaseDatabase.getInstance().getReference().
                child("ratData").orderByKey().limitToLast(50);

        // Basically listens whenever data changes on the Database and gives back a query
        ratQuery.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(DataSnapshot dataSnapshot) {
                list.clear(); // clears so that duplicates won't happen

                for (DataSnapshot reportSnap : dataSnapshot.getChildren()) {
                    RatReport report = reportSnap.getValue(RatReport.class);
                    list.add(report); // adds the report to the list
                }
                RatAdapter adapter = new RatAdapter(MainActivity.this, list);
                ratReportsList.setAdapter(adapter); // sets the ListView to display this
            }

            // Haven't thought of what we need for this yet, so this is just here and empty
            @Override
            public void onCancelled(DatabaseError databaseError) {

            }
        });

    }

}
