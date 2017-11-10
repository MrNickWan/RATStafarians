package com.example.android.RATStafarians;

import java.util.ArrayList;

/**
 * A Model class to adhere to the singleton design principle.
 */
public class Model {
    protected ArrayList<RatReport> list;
    private static Model model = new Model();
    public static Model get() { return model;}
}
