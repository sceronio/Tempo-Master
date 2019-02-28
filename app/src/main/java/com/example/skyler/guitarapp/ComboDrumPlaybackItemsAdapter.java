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

public class ComboDrumPlaybackItemsAdapter extends ArrayAdapter<DrumPlaybackItemModel> {

    ArrayList<DrumPlaybackItemModel> itemList;

    public ComboDrumPlaybackItemsAdapter(Context context, ArrayList<DrumPlaybackItemModel> itemList) {
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
            convertView = LayoutInflater.from(getContext()).inflate(R.layout.combination_item, parent, false);
            ViewHolder viewHolder = new ViewHolder();
            viewHolder.playButton = convertView.findViewById(R.id.combo_play_button);

            viewHolder.playButton.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    Player player = new Player(getContext());
                    player.playNext(0, drumPlaybackItemModel.getRecording());
                }
            });

            viewHolder.title = convertView.findViewById(R.id.combo_filename);
            // Lookup view for data population
            TextView filename = convertView.findViewById(R.id.combo_filename);
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
        TextView title;
    }
}
