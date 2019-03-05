package com.novelbar.controller;


import com.alibaba.fastjson.JSONObject;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;
import com.novelbar.model.registerModel;

import java.util.HashMap;

@RestController
@RequestMapping("/register") //注册
public class registerController extends result{

    @Autowired
    private registerModel registerModel;

    @PostMapping("")
    public HashMap register(@RequestBody JSONObject jsonObject){

        registerModel.data(jsonObject);
        HashMap map = new HashMap();
        try {
            Thread.sleep(0);
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
        if (registerModel.getStat()){

            map.put("success","注册成功");
            map.put("errcode",0);
            return map;
        }else {
            map.put("detail",registerModel.getMsg());
            HashMap res = err(404,map);
            res.put("errcode",1);
            return res;
        }
    }
}
