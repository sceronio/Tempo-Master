package com.example.skyler.guitarapp;
import com.google.gson.Gson;

public class DrumPlaybackItemModel {

    private String fileName;
    private String serializedRecording;
    private RecordedBeat recording;

    public DrumPlaybackItemModel() {
        this("", "");
    }

    public DrumPlaybackItemModel(String filename, String serializedRecording) {
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