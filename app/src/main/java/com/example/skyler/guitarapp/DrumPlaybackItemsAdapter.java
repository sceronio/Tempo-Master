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

public class DrumPlaybackItemsAdapter extends ArrayAdapter<DrumPlaybackItemModel> {

    //if you delete item 0, it is removed from the screen, but if you touch item 0 again, android still
    // thinks the item at position 0 was the one that was deleted before

    ArrayList<DrumPlaybackItemModel> itemList;

    public DrumPlaybackItemsAdapter(Context context, ArrayList<DrumPlaybackItemModel> itemList) {
        super(context, 0, itemList);
        this.itemList = itemList;
    }

    @Override
    public View getView(int position, View convertView, ViewGroup parent) {

        // Get the data item for this position
        final DrumPlaybackItemModel drumPlaybackItemModel = getItem(position);
        final int finalPosition = position;

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
                    player.playNext(0, drumPlaybackItemModel.getRecording());
                }
            });

            viewHolder.deleteButton.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {

                    DrumPlaybackItemModel selectedItem = getItem(finalPosition);

                    //remove item from shared preferences
                    SharedPreferences settings = PreferenceManager.getDefaultSharedPreferences(getContext());
                    SharedPreferences.Editor editor = settings.edit();
                    editor.remove(selectedItem.getFileName());
                    editor.apply();

                    //remove item from adapterDrum
                    DrumPlaybackItemsAdapter.super.remove(selectedItem);

                    //notify the adapter that you have modified the dataset
                    DrumPlaybackItemsAdapter.super.notifyDataSetChanged();
                }
            });

            viewHolder.title = convertView.findViewById(R.id.filename);
            // Lookup view for data population
            TextView filename = convertView.findViewById(R.id.filename);
            // Populate the data into the template view using the data object
            filename.setText(drumPlaybackItemModel.getFileName());
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
