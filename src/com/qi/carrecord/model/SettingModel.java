package com.qi.carrecord.model;

public class SettingModel {
    private int id;
    private int recording;
    private int split_video;
    private int start_video;
    private int usb_state;
    private int resolution_state;
    
    public int getId() {
        return id;
    }
    
    public void setId(int id1) {
        id = id1;
    }
    
    public int getSplit_video() {
        return split_video;
    }
    
    public void setSplit_video(int split_video1) {
        split_video = split_video1;
    }
    
    public int getRecording() {
        return recording;
    }
    
    public void setRecording(int recording1) {
        recording = recording1;
    }
    
    public int getStart_video() {
        return start_video;
    }
    
    public void setStart_video(int start_video1) {
        start_video = start_video1;
    }
    
    public int getUsb_state() {
        return usb_state;
    }
    
    public void setUsb_state(int usb_state1) {
        usb_state = usb_state1;
    }
    
    public int getResolution_state() {
        return resolution_state;
    }
    
    public void setResolution_state(int resolution_state1) {
    	resolution_state = resolution_state1;
    }    
}
