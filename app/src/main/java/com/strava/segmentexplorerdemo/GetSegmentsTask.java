package com.strava.segmentexplorerdemo;

import android.os.AsyncTask;
import android.util.Log;

import com.google.gson.FieldNamingPolicy;
import com.google.gson.Gson;
import com.google.gson.GsonBuilder;

import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.net.URL;
import java.util.Arrays;
import java.util.List;

import javax.net.ssl.HttpsURLConnection;

/**
 * An asynchronous task to retrieve Segments from the API. This is run on a background thread so
 * that network I/O does not block user interactions and make the app appear to "freeze".
 */
public class GetSegmentsTask extends AsyncTask<Void, Void, List<Segment>> {
    private static final String TAG = GetSegmentsTask.class.getCanonicalName();

    private static final Gson GSON = new GsonBuilder()
            .setFieldNamingPolicy(FieldNamingPolicy.LOWER_CASE_WITH_UNDERSCORES)
            .create();
    private static final String SEGMENT_EXPORE_URL = "https://app.strava.com/api/v3/segments/explore?bounds=42.371357,-71.121899,42.381357,-71.110899&activity_type=cycling";

    private SegmentsReceivedListener mSegmentsReceivedListener;

    public GetSegmentsTask(SegmentsReceivedListener listener) {
        mSegmentsReceivedListener = listener;
    }

    /**
     * This method is called in the background, so that it doesn't block the UI thread.
     * @return A list of segments returned by the Strava Segment Explore API.
     */
    @Override
    protected List<Segment> doInBackground(Void... params) {
        Segment[] apiResult = null;
        try {
            URL api = new URL(SEGMENT_EXPORE_URL);
            HttpsURLConnection connection = (HttpsURLConnection) api.openConnection();
            connection.setRequestMethod("GET");
            connection.setRequestProperty("Authorization", "access_token 12e3c07bd976c6904087a70094ccb6b905e3d13e");

            // Wait for the result and check if successful. A 20x response code indicates success.
            if (connection.getResponseCode() / 100 == 2) {
                InputStream response = connection.getInputStream();
                InputStreamReader reader = new InputStreamReader(response);
                StringBuilder builder = new StringBuilder();
                char[] buffer = new char[1024];
                int charsRead;
                while ((charsRead = reader.read(buffer)) >= 0) {
                    builder.append(buffer, 0, charsRead);
                }
                apiResult = GSON.fromJson(builder.toString(), SegmentExploreResult.class).segments;
            }

        } catch (IOException e) {
            Log.e(TAG, "", e);
        }

        return apiResult != null ? Arrays.asList(apiResult) : null;
    }

    /**
     * This method is called once doInBackground() finishes.
     * @param segments The list of segments to display on the UI.
     */
    @Override
    protected void onPostExecute(List<Segment> segments) {
        mSegmentsReceivedListener.onSegmentsReceived(segments);
    }

    private static final class SegmentExploreResult {
        Segment[] segments;
    }
}
