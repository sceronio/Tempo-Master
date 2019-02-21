package com.example.skyler.guitarapp;

import java.io.File;

public class RecordingPlaybackItemModel {

    private String filename;
    private File fileDescriptor;

    public RecordingPlaybackItemModel(String filename, File fileDescriptor) {
        this.filename = filename;
        this.fileDescriptor = fileDescriptor;
    }

    public String getFileName() {
        return filename;
    }

    public File getFileDescriptor() {
        return fileDescriptor;
    }
}
