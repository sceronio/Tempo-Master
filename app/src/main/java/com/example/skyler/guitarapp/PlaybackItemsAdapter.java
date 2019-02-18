package com.example.skyler.guitarapp;

import android.content.Context;
import android.content.SharedPreferences;
import android.preference.PreferenceManager;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.TextView;

import java.util.ArrayList;

public class PlaybackItemsAdapter extends ArrayAdapter<PlaybackItem> {

    public PlaybackItemsAdapter(Context context, ArrayList<PlaybackItem> item) {
        super(context, 0, item);
    }

    @Override
    public View getView(int position, View convertView, ViewGroup parent) {

        // Get the data item for this position
        final PlaybackItem playbackItemModel = getItem(position);

        ViewHolder mainViewHolder;

        // Check if an existing view is being reused, otherwise inflate the view
        if (convertView == null) {
            convertView = LayoutInflater.from(getContext()).inflate(R.layout.playback_item, parent, false);
            ViewHolder viewHolder = new ViewHolder();
            viewHolder.playButton = convertView.findViewById(R.id.playback_item_play);
            viewHolder.deleteButton = convertView.findViewById(R.id.playback_item_delete);

            viewHolder.playButton.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    Player player = new Player(getContext());
                    player.playNext(0, playbackItemModel.getRecording());
                }
            });

            viewHolder.deleteButton.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    //remove item from shared preferences
                    SharedPreferences settings = PreferenceManager.getDefaultSharedPreferences(getContext());
                    SharedPreferences.Editor editor = settings.edit();
                    editor.remove(playbackItemModel.getFileName());
                    editor.apply();
                    //remove item from adapter
                    PlaybackItemsAdapter.super.remove(playbackItemModel);
                    PlaybackItemsAdapter.super.notifyDataSetChanged();
                }
            });

            viewHolder.title = convertView.findViewById(R.id.filename);
            // Lookup view for data population
            TextView filename = convertView.findViewById(R.id.filename);
            // Populate the data into the template view using the data object
            filename.setText(playbackItemModel.getFileName());
            convertView.setTag(viewHolder);
        }
        else {
            mainViewHolder = (ViewHolder)convertView.getTag();
            mainViewHolder.title.setText(getItem(position).getFileName());
        }

        // Return the completed view to render on screen
        return convertView;
    }

    public class ViewHolder {
        Button playButton;
        Button deleteButton;
        TextView title;
    }
}
