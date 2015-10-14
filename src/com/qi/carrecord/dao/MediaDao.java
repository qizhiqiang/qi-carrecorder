package com.qi.carrecord.dao;

import java.util.List;

import com.qi.carrecord.model.MediaModel;

public interface MediaDao {
    
    public abstract int add(MediaModel p1);
    
    
    public abstract int delete(int p1);
    
    
    public abstract List findAll();
    
    
    public abstract MediaModel findById(int p1);
    
    
    public abstract MediaModel findByName(String p1);
    
    
    public abstract List findByTem(int p1, int p2);
    
    
    public abstract int update(MediaModel p1);
    
    
    public abstract int updateState(int p1, int p2);
    
}

