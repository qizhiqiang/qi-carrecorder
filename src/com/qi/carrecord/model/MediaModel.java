package com.qi.carrecord.model;

public class MediaModel {
	private String begintime;
    private String endtime;
    private int id;
    private String name;
    private String smallimage;
    private int type;
    private String video_address;
    private int video_length;
    
    public int getId() {
        return id;
    }
    
    public void setId(int id) {
        id = id;
    }
    
    public String getName() {
        return name;
    }
    
    public void setName(String name) {
        name = name;
    }
    
    public String getBegintime() {
        return begintime;
    }
    
    public void setBegintime(String begintime1) {
    	begintime = begintime1;
    }
    
    public String getEndtime() {
        return endtime;
    }
    
    public void setEndtime(String endtime1) {
        endtime = endtime1;
    }
    
    public int getVideo_length() {
        return video_length;
    }
    
    public void setVideo_length(int video_length) {
        video_length = video_length;
    }
    
    public String getVideo_address() {
        return video_address;
    }
    
    public void setVideo_address(String video_address) {
        video_address = video_address;
    }
    
    public int getType() {
        return type;
    }
    
    public void setType(int type) {
        type = type;
    }
    
    public String getSmallimage() {
        return smallimage;
    }
    
    public void setSmallimage(String smallimage) {
        smallimage = smallimage;
    }
}
