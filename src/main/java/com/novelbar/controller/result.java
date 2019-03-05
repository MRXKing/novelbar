package com.novelbar.controller;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import com.fasterxml.jackson.annotation.JsonInclude;

import java.util.HashMap;

@JsonIgnoreProperties(ignoreUnknown = true)
@JsonInclude(JsonInclude.Include.NON_NULL)
public class result extends  baseController{

    public HashMap ok(HashMap msg){
        response.setStatus(200);
        response.setHeader("Content-Type", "application/json");
        HashMap map = new HashMap();
        map.put("msg",msg);
        return map;
    }

    public HashMap err(int statusCode,HashMap msg){
        response.setStatus(statusCode);
        response.setHeader("Content-Type", "application/json");
        HashMap map = new HashMap();
        map.put("msg",msg);
        return map;
    }
}
