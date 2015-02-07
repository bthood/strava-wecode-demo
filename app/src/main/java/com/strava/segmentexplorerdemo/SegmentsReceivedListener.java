package com.strava.segmentexplorerdemo;

import java.util.List;

/**
 * Describes a class that is to be notified when Segment data becomes available from the API.
 */
public interface SegmentsReceivedListener {

    /**
     * Called when Segment data is received from the API.
     * @param segments The Segments sent by the API.
     */
    void onSegmentsReceived(List<Segment> segments);
}
