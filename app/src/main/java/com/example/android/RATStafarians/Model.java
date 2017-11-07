package com.example.android.RATStafarians;

import com.google.firebase.database.Query;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by robert on 11/6/17.
 */

public class Model {
    protected ArrayList<RatReport> list;
    protected Query query;
    private static Model model = new Model();
    public static Model get() { return model;}
}
