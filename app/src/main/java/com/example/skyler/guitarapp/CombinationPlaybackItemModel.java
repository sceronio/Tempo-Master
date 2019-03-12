package com.example.skyler.guitarapp;

public class CombinationPlaybackItemModel {

    private RecordingPlaybackItemModel recording;
    private DrumPlaybackItemModel drumBeat;
    private String filename;

    public CombinationPlaybackItemModel(String filename, RecordingPlaybackItemModel recording, DrumPlaybackItemModel drumBeat) {
        this.filename = filename;
        this.recording = recording;
        this.drumBeat = drumBeat;
    }

    public RecordingPlaybackItemModel getRecording() {
        return recording;
    }

    public DrumPlaybackItemModel getDrumBeat() {
        return drumBeat;
    }

    public String getFilename() {
        return filename;
    }
}
