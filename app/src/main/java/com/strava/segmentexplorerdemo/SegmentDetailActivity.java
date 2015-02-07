package com.strava.segmentexplorerdemo;

import android.os.Bundle;
import android.support.v7.app.ActionBarActivity;
import android.widget.TextView;

public class SegmentDetailActivity extends ActionBarActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_segment_detail);

        TextView textView = (TextView) findViewById(R.id.textView2);
        textView.setText("hello world!");

        if (getIntent().hasExtra("segment")) {
            Segment segment = (Segment) getIntent().getSerializableExtra("segment");
            textView.setText(segment.getName());
        }
    }

}
