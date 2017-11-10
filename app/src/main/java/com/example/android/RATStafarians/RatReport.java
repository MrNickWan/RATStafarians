package com.example.android.RATStafarians;

import java.io.Serializable;

/**
 * Created by robert on 10/7/17.
 *
 * Basically, this is a model class for putting in RatReports and exists as an information holder
 */

class RatReport implements Serializable{
    private String uniqueKey;
    private String createdDate;
    private String locationType;
    private String incidentZip;
    private String incidentAddress;
    private String city;
    private String borough;
    private String latitude;
    private String longitude;
    private static int uniqueKeyCounter;


    /**
     * The constructor for the RatReports class
     *
     * @param key the unique key given when creating reports
     * @param date the creation date for the report
     * @param location the location where the incident occurred
     * @param zipCode the zipcode of the incident
     * @param address the incident address
     * @param city the city where the incident occurred
     * @param lat the latitude of the incident place
     * @param longitude the longitude of the incident place
     */
    private RatReport(String key, String date,
                      String location, String zipCode,
                      String address, String city,
                      String neighborhood, String lat, String longitude) {
        this.uniqueKey = key;
        this.createdDate = date;
        this.locationType = location;
        this.incidentZip = zipCode;
        this.incidentAddress = address;
        this.city = city;
        this.borough = neighborhood;
        this.latitude = lat;
        this.longitude = longitude;
    }

    /**
     * The default constructor of the RatReports class
     */
    RatReport() {
        this("420691337", "04/20/69", "your basement", "42069", "butthole",
                "straightouttacompton", "comptonbro", "69.420", "69.69");
    }

    String getUniqueKey() {
        return uniqueKey;
    }

    void setUniqueKey(String uniqueKey) {
        this.uniqueKey = uniqueKey;
    }

    String getCreatedDate() {

        return createdDate;
    }

    void setCreatedDate(String createdDate) {
        this.createdDate = createdDate;
    }

    String getLocationType() {
        return locationType;
    }

    void setLocationType(String locationType) {
        this.locationType = locationType;
    }

    String getIncidentAddress() {
        return incidentAddress;
    }

    void setIncidentAddress(String incidentAddress) {
        this.incidentAddress = incidentAddress;
    }

    String getIncidentZip() {

        return incidentZip;
    }

    void setIncidentZip(String incidentZip) {
        this.incidentZip = incidentZip;
    }

    String getCity() {
        return city;
    }

    void setCity(String city) {
        this.city = city;
    }

    public String getBorough() {
        return borough;
    }

    void setBorough(String borough) {
        this.borough = borough;
    }

    String getLatitude() {
        return latitude;
    }

    void setLatitude(String latitude) {
        this.latitude = latitude;
    }

    String getLongitude() {
        return longitude;
    }

    void setLongitude(String longitude) {
        this.longitude = longitude;
    }

    int getUniqueKeyCounter() {
        return uniqueKeyCounter;
    }

    void setUniqueKeyCounter(int uniqueKey) {
        uniqueKeyCounter = uniqueKey;
    }
}
