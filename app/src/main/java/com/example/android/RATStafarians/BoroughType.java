package com.example.android.RATStafarians;

/**
 * This is an enum class for boroughs
 */

public enum BoroughType {
    MANHATTAN ("Manhattan"), STATENISLAND ("Staten Island"),
    QUEENS ("Queen"), BROOKLYN ("Brooklyn"), BRONX ("Bronx");

    private final String name;

    BoroughType(String name) {
        this.name = name;
    }
    public String toString() {
        return name;
    }
}
