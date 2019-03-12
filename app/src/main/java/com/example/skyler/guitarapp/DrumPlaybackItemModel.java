package com.example.skyler.guitarapp;
import com.google.gson.Gson;

public class DrumPlaybackItemModel {

    private String fileName;
    private RecordedBeat recording;
    private boolean checked = false;

    public DrumPlaybackItemModel(String filename, String serializedRecording) {
        Gson gson = new Gson();

        this.fileName = filename;
        this.recording = gson.fromJson(serializedRecording, RecordedBeat.class);
    }

    public String getFileName() {
        return fileName;
    }

    public RecordedBeat getRecording() {
        return recording;
    }

    public boolean isChecked() {
        return checked;
    }

    public void setChecked(boolean checked) {
        this.checked = checked;
    }

    public void toggleChecked() {
        if(checked == false) {
            checked = true;
        }
        else {
            checked = false;
        }
    }
}
