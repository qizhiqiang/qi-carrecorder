package com.qi.carrecord.dao;

import com.qi.carrecord.model.SettingModel;

public interface SettingDao {
    
    public abstract int add(SettingModel p1);
    
    
    public abstract SettingModel findAll();
    
    
    public abstract int update(SettingModel p1);
    
}
