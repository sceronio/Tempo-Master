package com.example.skyler.guitarapp;
import com.google.gson.Gson;

public class PlaybackItemModel {

    private String fileName;
    private String serializedRecording;
    private RecordedBeat recording;


    public PlaybackItemModel(String filename, String serializedRecording) {
        Gson gson = new Gson();

        this.fileName = filename;
        this.serializedRecording = serializedRecording;
        this.recording = gson.fromJson(serializedRecording, RecordedBeat.class);
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
