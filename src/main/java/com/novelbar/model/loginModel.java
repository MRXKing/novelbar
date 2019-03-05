package com.novelbar.model;

import com.novelbar.dom.user;
import com.novelbar.model.util;

import com.alibaba.fastjson.JSONObject;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Sort;
import org.springframework.data.mongodb.core.query.Criteria;
import org.springframework.data.mongodb.core.query.Field;
import org.springframework.data.mongodb.core.query.Query;

import java.util.List;
import java.util.concurrent.TimeUnit;


public class loginModel extends  baseModel{

    @Autowired
    private util util;

    private String msg;

    private Boolean stat;

    private String token;

    private Object username;

    public void login(JSONObject json){

           user user = (user)util.classParse(json,user.class);
           Criteria criteria = new Criteria();
           criteria.and("username").is(user.getUsername());
           criteria.and("pwd").is(user.getPwd());
           Query query = new Query(criteria);
//           query.fields().include("username");  //限制显示字段,其他字段不会消失而是变为空。
//           query.fields().include("pwd");
//        query.with(new Sort(new Sort.Order(Sort.Direction.ASC,"id")));  //按id升序排序
           //query.limit(pagesize)限定返回多少行记录
           if (user.getUsername().equals("")|| user.getPwd().equals("")){
               stat = false;
               msg = "账号密码不能为空";
           }else if(util.isNum(user.getUsername().substring(0,1))){
               stat = false;
               msg="账号开头不能是数字";
           }else {

               List list = mongoTemplate.find(query,user.class);
               if (list.size() > 0)
               {
                   stat = true;
                   token = util.getToken();
                   Object data = util.jsonParse(list.get(0));
                   username = ((user)list.get(0)).getUsername();
                   redisTemplate.opsForValue().set(token,data);
                   redisTemplate.expire(token,7200, TimeUnit.SECONDS);
                   msg="登陆成功";
               }else {
                   stat = false;
                   msg = "账号或密码错误";
               }
           }

    }

    public void logout(String token){

        if (redisTemplate.opsForValue().get(token) != null){
            redisTemplate.delete(token);
        }

    }

    public String getMsg() {
        return msg;
    }

    public Object getUsername() {
        return username;
    }

    public Boolean getStat() {
        return stat;
    }

    public String getToken() {
        return token;
    }
}
