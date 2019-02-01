package com.example.skyler.guitarapp;
import com.google.gson.Gson;

public class PlaybackItemModel {

    private String fileName;
    private String serializedRecording;
    private RecordedBeat recording;
    private int listIndex;


    public PlaybackItemModel(String filename, String serializedRecording, int listIndex) {
        Gson gson = new Gson();

        this.fileName = filename;
        this.serializedRecording = serializedRecording;
        this.recording = gson.fromJson(serializedRecording, RecordedBeat.class);
        this.listIndex = listIndex;
    }

    public String getFileName() {
        return fileName;
    }

    public RecordedBeat getRecording() {
        return recording;
    }

    public String getSerializedRecording() {
        return serializedRecording;
    }
}
