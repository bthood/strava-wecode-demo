package com.strava.segmentexplorerdemo;

import android.os.Bundle;
import android.support.v7.app.ActionBarActivity;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.ListView;
import android.widget.TextView;
import android.widget.Toast;

import java.util.List;

public class SegmentListActivity extends ActionBarActivity implements SegmentsReceivedListener {

    private GetSegmentsTask mGetSegmentsTask;

    private SegmentListAdapter mSegmentListAdapter;

    /** This code is executed once when the screen is first loaded. */
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        // Set the main layout for this screen.
        setContentView(R.layout.segment_list);

        // Set the title of the action bar above the main content view.
        getSupportActionBar().setTitle(R.string.segment_list_title);

        // Fetch data from the Strava Segments API.
        mGetSegmentsTask = new GetSegmentsTask(this);
        mGetSegmentsTask.execute();

        // Set up the list view that will display the segments.
        mSegmentListAdapter = new SegmentListAdapter();
        ListView segmentListView = (ListView) findViewById(R.id.segment_list);
        segmentListView.setAdapter(mSegmentListAdapter);
    }

    /** This code is executed whenever the user navigates back to the screen. */
    @Override
    protected void onResume() {
        super.onResume();
    }

    /** Executed whenever the user navigates away from the screen. */
    @Override
    protected void onPause() {
        super.onPause();
    }

    /** Executed when the system needs to kill this screen to reclaim memory. */
    @Override
    protected void onDestroy() {
        super.onDestroy();
        mGetSegmentsTask.cancel(true);
    }

    /** Called when segments are received by the API. */
    @Override
    public void onSegmentsReceived(List<Segment> segments) {
        if (segments == null) { // There was an error!
            Toast.makeText(this, R.string.segment_list_error, Toast.LENGTH_SHORT).show();
        } else {
            // First, make sure to clear out any old segments that aren't a part of this data set.
            mSegmentListAdapter.clear();
            // Next, add all of the new segments.
            mSegmentListAdapter.addAll(segments);
            // Finally, notify any attached views to redraw because the data set has changed.
            mSegmentListAdapter.notifyDataSetChanged();

            // Hide the "loading" state.
            TextView loadingText = (TextView) findViewById(R.id.segment_list_loading_state);
            loadingText.setVisibility(View.GONE);
        }
    }

    /**
     * An adapter class acts as the bridge between the data (a list of Segments) and the view
     * responsible for displaying it. Whenever adapter's notifyDataSetChanged() method is called, it
     * makes the associated ListView re-draw the list to account for updates in the data.
     */
    private final class SegmentListAdapter extends ArrayAdapter<Segment> {

        public SegmentListAdapter() {
            super(SegmentListActivity.this, 0);
        }

        @Override
        public View getView(int position, View convertView, ViewGroup parent) {
            // As an optimization, Android will "reuse" the views representing list items. This
            // permits the system to display a list of thousands of items without any performance
            // penalty.
            // If convertView is non-null, it means that it currently is displaying another Segment,
            // but was scrolled out of view. We need to reset any data fields associated with the
            // old Segment and/or replace them with data for the current Segment.
            View segmentView;
            if (convertView != null) {
                segmentView = convertView;
            } else {
                // We are not reusing an old view, so create a new one.
                segmentView = getLayoutInflater().inflate(R.layout.segment_list_item, parent, false);
            }

            // Get the Segment that we are currently trying to display.
            Segment segment = getItem(position);

            // Now attach this Segment's data to the view.
            TextView segmentName = (TextView) segmentView.findViewById(R.id.segment_list_item_name);
            segmentName.setText(segment.getName());

            // TODO: fill in the rest of the data!

            return segmentView;
        }
    }
}
