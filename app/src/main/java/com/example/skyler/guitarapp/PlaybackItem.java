package com.example.skyler.guitarapp;

interface PlaybackItem {
    String filename = "";

    public String getFileName();

    public RecordedBeat getRecording();
}
