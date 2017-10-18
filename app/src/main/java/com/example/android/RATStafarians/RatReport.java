package com.example.android.RATStafarians;

import android.os.Bundle;

/**
 * Created by robert on 10/7/17.
 *
 * Basically, this is a model class for putting in RatReports and exists as an information holder
 */

public class RatReport {
    private String uniqueKey;
    private String createdDate;
    private String locationType;
    private String incidentZip;
    private String incidentAddress;
    private String city;
    private String borough;
    private String latitude;
    private String longitude;


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
    public RatReport(String key, String date,
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
    public RatReport() {
        this("420691337", "04/20/69", "your basement", "42069", "butthole",
                "straightouttacompton", "comptonbro", "69.420", "69.69");
    }

    public String getUniqueKey() {
        return uniqueKey;
    }

    public void setUniqueKey(String uniqueKey) {
        this.uniqueKey = uniqueKey;
    }

    public String getCreatedDate() {

        return createdDate;
    }

    public void setCreatedDate(String createdDate) {
        this.createdDate = createdDate;
    }

    public String getLocationType() {
        return locationType;
    }

    public void setLocationType(String locationType) {
        this.locationType = locationType;
    }

    public String getIncidentAddress() {
        return incidentAddress;
    }

    public void setIncidentAddress(String incidentAddress) {
        this.incidentAddress = incidentAddress;
    }

    public String getIncidentZip() {

        return incidentZip;
    }

    public void setIncidentZip(String incidentZip) {
        this.incidentZip = incidentZip;
    }

    public String getCity() {
        return city;
    }

    public void setCity(String city) {
        this.city = city;
    }

    public String getBorough() {
        return borough;
    }

    public void setBorough(String borough) {
        this.borough = borough;
    }

    public String getLatitude() {
        return latitude;
    }

    public void setLatitude(String latitude) {
        this.latitude = latitude;
    }

    public String getLongitude() {
        return longitude;
    }

    public void setLongitude(String longitude) {
        this.longitude = longitude;
    }
}
