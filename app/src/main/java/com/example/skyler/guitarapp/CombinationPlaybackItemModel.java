package com.example.skyler.guitarapp;

public class CombinationPlaybackItemModel {

    private RecordingPlaybackItemModel recording;
    private DrumPlaybackItemModel drumBeat;

    public CombinationPlaybackItemModel(RecordingPlaybackItemModel recording, DrumPlaybackItemModel drumBeat) {
        this.recording = recording;
        this.drumBeat = drumBeat;
    }

    public RecordingPlaybackItemModel getRecording() {
        return recording;
    }

    public DrumPlaybackItemModel getDrumBeat() {
        return drumBeat;
    }
}
