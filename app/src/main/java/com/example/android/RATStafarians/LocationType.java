package com.example.android.RATStafarians;



public enum LocationType {
    FAMILYDWELLING ("Family Dwelling"), FAMILYAPTBUILDING ("Family Apt. Building"),
    FAMILYMIXEDUSEBUILDING ("Family Mixed Use Building"),
    COMMERCIALBUILDING ("Commercial Building"),
    VACANTLOT("Vacant Lot"),
    CONSTRUCTIONSITE ("Construction Site"),
    HOSPITAL ("Hospital"), CATCHBASINSEWER ("Catch Basin/Sewer");

    private String name;

    LocationType(String name) {
        this.name = name;
    }
    public String toString() {
        return name;
    }
}
