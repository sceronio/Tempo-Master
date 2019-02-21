package com.example.skyler.guitarapp;

import android.content.Context;
import android.content.SharedPreferences;
import android.media.MediaPlayer;
import android.preference.PreferenceManager;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.TextView;

import java.io.File;
import java.io.FileDescriptor;
import java.io.FileInputStream;
import java.util.ArrayList;

public class RecordingPlaybackItemsAdapter extends ArrayAdapter<RecordingPlaybackItemModel> {

    public RecordingPlaybackItemsAdapter(Context context, ArrayList<RecordingPlaybackItemModel> item) {
        super(context, 0, item);
    }

    @Override
    public View getView(int position, View convertView, ViewGroup parent) {

        // Get the data item for this position
        final RecordingPlaybackItemModel recordingPlaybackItemModel = getItem(position);

        RecordingPlaybackItemsAdapter.ViewHolder mainViewHolder;

        // Check if an existing view is being reused, otherwise inflate the view
        if (convertView == null) {
            convertView = LayoutInflater.from(getContext()).inflate(R.layout.playback_item, parent, false);
            RecordingPlaybackItemsAdapter.ViewHolder viewHolder = new RecordingPlaybackItemsAdapter.ViewHolder();
            viewHolder.playButton = convertView.findViewById(R.id.playback_item_play);
            viewHolder.deleteButton = convertView.findViewById(R.id.playback_item_delete);

            viewHolder.playButton.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {

                    new Thread() {
                        @Override
                        public void run() {
                            try {
                                FileDescriptor fileDescriptor = getContext()
                                 .openFileInput(recordingPlaybackItemModel.getFileName()).getFD();
                                MediaPlayer player = new MediaPlayer();
                                player.setDataSource(fileDescriptor);
                                player.prepare();
                                player.start();

                            } catch (Exception e) {
                                e.printStackTrace();
                            }
                        }
                    }.start();
                }
            });

            viewHolder.deleteButton.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    //remove file from system
                    recordingPlaybackItemModel.getFileDescriptor().delete();
                    //remove item from adapterDrum
                    RecordingPlaybackItemsAdapter.super.remove(recordingPlaybackItemModel);
                    RecordingPlaybackItemsAdapter.super.notifyDataSetChanged();
                }
            });

            viewHolder.title = convertView.findViewById(R.id.filename);
            // Lookup view for data population
            TextView filename = convertView.findViewById(R.id.filename);
            // Populate the data into the template view using the data object
            filename.setText(recordingPlaybackItemModel.getFileName());
            convertView.setTag(viewHolder);
        }
        else {
            mainViewHolder = (RecordingPlaybackItemsAdapter.ViewHolder)convertView.getTag();
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
