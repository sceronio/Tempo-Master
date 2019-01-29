package com.example.skyler.guitarapp;

public class PlaybackItem {

    private String fileName = "temp";

    public PlaybackItem(String filename) {
        this.fileName = filename;
    }

    public String getFileName() {
        return fileName;
    }

    public void setFileName(String fileName) {
        this.fileName = fileName;
    }
}
