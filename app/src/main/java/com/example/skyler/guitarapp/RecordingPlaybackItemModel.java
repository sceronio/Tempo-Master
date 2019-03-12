package com.example.skyler.guitarapp;

import java.io.File;

public class RecordingPlaybackItemModel {

    private String filename;
    private File fileDescriptor;
    private boolean checked = false;

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
