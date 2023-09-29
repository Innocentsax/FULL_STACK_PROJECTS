package com.decagon.OakLandv1be.utils;

import java.util.HashMap;

public class ApiQuery {

    private HashMap<String, Object> queryMap;

    public ApiQuery(){

        this.queryMap = new HashMap<>();
    }

    public void putParams(String key, Object value){
        this.queryMap.put(key, value);
    }

    public HashMap<String, Object> getParams(){
        return this.queryMap;
    }
}
