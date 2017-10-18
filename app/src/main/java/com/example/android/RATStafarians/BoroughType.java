package com.example.android.RATStafarians;

/**
 * Created by helen.wan on 10/16/2017.
 */

public enum BoroughType {
    MANHATTAN ("Manhattan"), STATENISLAND ("Staten Island"),
    QUEENS ("Queen"), BROOKLYN ("Brooklyn"), BRONX ("Bronx");

    private String name;

    BoroughType(String name) {
        this.name = name;
    }
    public String toString() {
        return name;
    }
}
