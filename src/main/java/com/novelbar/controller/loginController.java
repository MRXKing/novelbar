package com.novelbar.controller;


import com.alibaba.fastjson.JSONObject;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;
import com.novelbar.model.loginModel;

import java.util.HashMap;

@RestController
@RequestMapping("/login")  //登陆
public class loginController extends  result{

    @Autowired
    private loginModel loginModel;

    @PostMapping("")
    public HashMap login(@RequestBody JSONObject jsonObject){

        loginModel.login(jsonObject);
        HashMap map = new HashMap();
        try {
            Thread.sleep(0);
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
        if (loginModel.getStat()){
            map.put("token",loginModel.getToken());
            HashMap res = ok(map);
            res.put("success","登陆成功");
            res.put("errcode",0);
            return res;

        }else {
            map.put("detail",loginModel.getMsg());
            HashMap res = err(404,map);
            res.put("errcode",1);
            return res;

        }

    }

    @GetMapping("/logout")
    public void logout(@RequestParam(value = "token") String token){

        loginModel.logout(token);

    }
}
