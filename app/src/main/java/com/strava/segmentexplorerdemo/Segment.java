package com.strava.segmentexplorerdemo;

import android.support.v4.util.Pair;

import java.io.Serializable;

/**
 * Represents the data returned by the Strava Segment Explore API.
 */
public class Segment implements Serializable {

    private long id;
    private String name;
    private float avgGrade;
    private float[] startLatlng;
    private float[] endLatlng;
    private float distance; // in meters
    private float elevDifference; // in meters
    private String points; // returns a polyline in Google polyline format

    public long getId() {
        return id;
    }

    public String getName() {
        return name;
    }

    public float getAvgGrade() {
        return avgGrade;
    }

    public Pair<Float, Float> getStartLatlng() {
        return Pair.create(startLatlng[0], startLatlng[1]);
    }

    public Pair<Float, Float> getEndLatlng() {
        return Pair.create(endLatlng[0], endLatlng[1]);
    }

    public float getDistanceMeters() {
        return distance;
    }

    public float getElevationDifferenceMeters() {
        return elevDifference;
    }

    public String getPoints() {
        return points;
    }
}
