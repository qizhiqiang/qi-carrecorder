package com.qi.carrecord.model;

public class SettingModel {
    private int id;
    private int recording;
    private int split_video;
    private int start_video;
    private int usb_state;
    
    public int getId() {
        return id;
    }
    
    public void setId(int id) {
        id = id;
    }
    
    public int getSplit_video() {
        return split_video;
    }
    
    public void setSplit_video(int split_video) {
        split_video = split_video;
    }
    
    public int getRecording() {
        return recording;
    }
    
    public void setRecording(int recording) {
        recording = recording;
    }
    
    public int getStart_video() {
        return start_video;
    }
    
    public void setStart_video(int start_video) {
        start_video = start_video;
    }
    
    public int getUsb_state() {
        return usb_state;
    }
    
    public void setUsb_state(int usb_state) {
        usb_state = usb_state;
    }
}
