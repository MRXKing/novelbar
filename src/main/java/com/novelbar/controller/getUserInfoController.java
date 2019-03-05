package com.novelbar.controller;



import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;
import com.novelbar.model.getUserInfoModel;

import java.util.HashMap;
import java.util.List;

@RestController
@RequestMapping("/getUserInfo") //获取用户信息
public class getUserInfoController extends result{

    @Autowired
    private  getUserInfoModel getUserInfoModel;

    @GetMapping("/username")
    public HashMap username(@RequestParam(value = "token") String token){

        HashMap map = new HashMap();
        String str = getUserInfoModel.getUserInfo(token);
        if (getUserInfoModel.getStat()){
            map.put("username",str);
            HashMap res = ok(map);
            res.put("errcode",0);
            return res;
        }else {
            map.put("error",str);
            HashMap res = err(404,map);
            res.put("errcode",1);
            return res;
        }

    }

    @GetMapping("/userInfo")
    public HashMap userINfo(@RequestParam(value = "token") String token){
        HashMap map = new HashMap();
        map.put("userInfo",getUserInfoModel.UserInfo(token));
        map.put("userReadBook",getUserInfoModel.userReadBookTotal(token));
        map.put("userId",getUserInfoModel.getUUID(token));
        return map;
    }

    @GetMapping("/userReadBook")
    public List userReadBook(@RequestParam(value = "token") String token){
        List list = getUserInfoModel.userReadBook(token);
        return list;
    }
}
