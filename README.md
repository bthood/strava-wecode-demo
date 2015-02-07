# Strava Android App for WECode workshop

This is the sample app for Strava's WECode workshop. It is a slimmed down version of the Segment Explore
feature in the main app. It's a great Android learning tool, as it teaches concepts of Activity, View,
passing data between Activities, layouts, network calls, and more.

## Structure

* **SegmentListActivity** - loads and displays a list of Segments using Strava's Segment Explore API.
* **SegmentDetailActivity** - shows more detailed information about an individual Segment.
* **GetSegmentsTask** - retrieves Segment data and updates the UI when received.
