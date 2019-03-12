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

import java.io.FileDescriptor;
import java.util.ArrayList;
//TODO: what I was doing: making an adapter to create views for each combined item to be displayed
// in the drum playback fragment. 

public class CombinationPlaybackItemsAdapter extends ArrayAdapter<CombinationPlaybackItemModel> {

    ArrayList<CombinationPlaybackItemModel> itemList;

    public CombinationPlaybackItemsAdapter(Context context, ArrayList<CombinationPlaybackItemModel> itemList) {
        super(context, 0, itemList);
        this.itemList = itemList;
    }

    @Override
    public View getView(int position, View convertView, ViewGroup parent) {

        // Get the data item for this position
        final CombinationPlaybackItemModel CombinationPlaybackItemModel = getItem(position);
        final int finalPosition = position;

        ViewHolder mainViewHolder;

        // Check if an existing view is being reused, otherwise inflate the view
        if (convertView == null) {
            convertView = LayoutInflater.from(getContext()).inflate(R.layout.playback_item, parent, false);
            ViewHolder viewHolder = new ViewHolder();
            viewHolder.playButton = convertView.findViewById(R.id.playback_item_play);
            viewHolder.deleteButton = convertView.findViewById(R.id.playback_item_delete);

            //play drum beat and audio together
            viewHolder.playButton.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    //set up
                    Player player = new Player(getContext());

                    Thread recordingPlayer = new Thread() {
                        @Override
                        public void run() {
                            try {
                                FileDescriptor fileDescriptor = getContext()
                                        .openFileInput(CombinationPlaybackItemModel.getRecording().getFileName()).getFD();
                                MediaPlayer player = new MediaPlayer();
                                player.setDataSource(fileDescriptor);
                                player.prepare();
                                player.start();

                            } catch (Exception e) {
                                e.printStackTrace();
                            }
                        }
                    };

                    //play
                    player.playNext(0, CombinationPlaybackItemModel.getDrumBeat().getRecording());
                    recordingPlayer.start();

                }
            });

            viewHolder.deleteButton.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {

                    /*CombinationPlaybackItemModel selectedItem = getItem(finalPosition);

                    //remove item from shared preferences
                    SharedPreferences settings = PreferenceManager.getDefaultSharedPreferences(getContext());
                    SharedPreferences.Editor editor = settings.edit();
                    editor.remove(selectedItem.getFileName());
                    editor.apply();

                    //remove item from adapterDrum
                    CombinationPlaybackItemsAdapter.super.remove(selectedItem);

                    //notify the adapter that you have modified the dataset
                    CombinationPlaybackItemsAdapter.super.notifyDataSetChanged();*/
                }
            });

            viewHolder.title = convertView.findViewById(R.id.filename);
            // Lookup view for data population
            TextView filename = convertView.findViewById(R.id.filename);
            // Populate the data into the template view using the data object
            filename.setText(CombinationPlaybackItemModel.getFilename());
            convertView.setTag(viewHolder);
        }
        else {
            mainViewHolder = (ViewHolder)convertView.getTag();
            mainViewHolder.title.setText(getItem(position).getFilename());
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
