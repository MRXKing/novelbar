package com.novelbar.model;


import com.alibaba.fastjson.JSONObject;
import org.springframework.beans.factory.annotation.Autowired;
import com.novelbar.item.userItem;
import com.novelbar.dom.user;
import org.springframework.data.mongodb.core.query.Criteria;
import org.springframework.data.mongodb.core.query.Query;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;


public class registerModel extends baseModel{

    @Autowired
    private util util;

    private String msg;

    private Boolean stat;


    public void data(JSONObject json){

        userItem userItem = (userItem) util.classParse(json,userItem.class);
        SimpleDateFormat sdf = new SimpleDateFormat("yyyy-mm-dd");
        try {
            Date date = sdf.parse(userItem.getBirth());
            Calendar c = Calendar.getInstance();
            c.setTime(date);
            Calendar d = Calendar.getInstance();
            d.setTime(new Date());
            int month = c.get(Calendar.MONTH) == 0 ? 12:(c.get(Calendar.MONTH) == 12 ? c.get(Calendar.MONTH):c.get(Calendar.MONTH)+1);
            int month2 = d.get(Calendar.MONTH) == 0 ? 12:(d.get(Calendar.MONTH) == 12 ? d.get(Calendar.MONTH):d.get(Calendar.MONTH)+1);
            Query query = new Query(Criteria.where("username").is(userItem.getUsername()));
            if(!util.NotNull(userItem)){
                stat = false;
                msg = "参数不能为空";
            }else if(util.isNum(userItem.getUsername().substring(0,1))){
                stat = false;
                msg="账号开头不能是数字";
            }else if(userItem.getUsername().length() < 6 || userItem.getUsername().length() > 10){
                stat = false;
                msg="账号长度应为6-10";
            }else if(userItem.getPwd().length() < 6 || userItem.getPwd().length() > 16){
                stat = false;
                msg="密码长度应为6-16";
            }else if(userItem.getPwd().equals(userItem.getAgainpwd()) ==false){
                stat = false;
                msg="密码不一致";
            }else if(!util.isNum(userItem.getAge()) || Integer.parseInt(userItem.getAge()) > 100){
                stat = false;
                msg="请输入正确的年龄";
            }else if(c.get(Calendar.YEAR) > d.get(Calendar.YEAR)){
                stat = false;
                msg="日期不正确";
            }else if(c.get(Calendar.YEAR) == d.get(Calendar.YEAR) && month > month2){
                stat = false;
                msg="日期不正确";
            }else if(c.get(Calendar.YEAR) == d.get(Calendar.YEAR) && month == month2 && c.get(Calendar.DAY_OF_MONTH) > d.get(Calendar.DAY_OF_MONTH)){
                stat = false;
                msg="日期不正确";
            }else if(mongoTemplate.find(query,user.class).size() >= 1 ){
                stat = false;
                msg="该用户名已经注册";
            }else
            {
                stat = true;
                user user = (user)util.copyBean(new user(),userItem);
                mongoTemplate.insert(user);
                mongoTemplate.createCollection(user.getUsername());
            }
        } catch (ParseException e) {
            stat = false;
            msg = "参数不能为空";
            e.printStackTrace();
        }
    }

    public String getMsg() {
        return msg;
    }

    public Boolean getStat() {
        return stat;
    }

}
