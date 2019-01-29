package com.example.skyler.guitarapp;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.TextView;

import java.util.ArrayList;

public class PlaybackItemsAdapter extends ArrayAdapter<PlaybackItemModel> {

    public PlaybackItemsAdapter(Context context, ArrayList<PlaybackItemModel> item) {
        super(context, 0, item);
    }

    @Override
    public View getView(int position, View convertView, ViewGroup parent) {
        // Get the data item for this position
        PlaybackItemModel playbackItemModel = getItem(position);

        // Check if an existing view is being reused, otherwise inflate the view
        if (convertView == null) {
            convertView = LayoutInflater.from(getContext()).inflate(R.layout.playback_item, parent, false);
        }

        // Lookup view for data population
        TextView filename = (TextView) convertView.findViewById(R.id.filename);
        // Populate the data into the template view using the data object
        filename.setText(playbackItemModel.getFileName());

        // Return the completed view to render on screen
        return convertView;
    }
}
