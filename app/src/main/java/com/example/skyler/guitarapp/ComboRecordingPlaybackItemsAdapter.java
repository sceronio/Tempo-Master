package com.example.skyler.guitarapp;

import android.content.Context;
import android.media.MediaPlayer;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.CheckBox;
import android.widget.TextView;

import java.io.FileDescriptor;
import java.util.ArrayList;

public class ComboRecordingPlaybackItemsAdapter extends ArrayAdapter<RecordingPlaybackItemModel> {

    public ComboRecordingPlaybackItemsAdapter(Context context, ArrayList<RecordingPlaybackItemModel> item) {
        super(context, 0, item);
    }

    @Override
    public View getView(int position, View convertView, ViewGroup parent) {

        // Get the data item for this position
        final RecordingPlaybackItemModel recordingPlaybackItemModel = getItem(position);
        final int finalPosition = position;

        ComboRecordingPlaybackItemsAdapter.ViewHolder mainViewHolder;

        // Check if an existing view is being reused, otherwise inflate the view
        if (convertView == null) {
            convertView = LayoutInflater.from(getContext()).inflate(R.layout.combination_item, parent, false);
            ComboRecordingPlaybackItemsAdapter.ViewHolder viewHolder = new ComboRecordingPlaybackItemsAdapter.ViewHolder();
            viewHolder.playButton = convertView.findViewById(R.id.combo_play_button);

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

            viewHolder.checkBox = convertView.findViewById(R.id.selectBox);
            viewHolder.checkBox.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    //Toast.makeText(getContext(), "CHECKBOX TOUCHED", Toast.LENGTH_LONG).show();
                    RecordingPlaybackItemModel item = getItem(finalPosition);
                    item.toggleChecked();
                }
            });

            viewHolder.title = convertView.findViewById(R.id.combo_filename);
            // Lookup view for data population
            TextView filename = convertView.findViewById(R.id.combo_filename);
            // Populate the data into the template view using the data object
            filename.setText(recordingPlaybackItemModel.getFileName());
            convertView.setTag(viewHolder);
        }
        else {
            mainViewHolder = (ComboRecordingPlaybackItemsAdapter.ViewHolder)convertView.getTag();
            mainViewHolder.title.setText(getItem(position).getFileName());
        }

        // Return the completed view to render on screen
        return convertView;
    }

    public class ViewHolder {
        Button playButton;
        TextView title;
        CheckBox checkBox;
    }
}
