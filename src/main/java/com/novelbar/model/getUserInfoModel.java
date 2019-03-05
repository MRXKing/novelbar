package com.novelbar.model;


import com.alibaba.fastjson.JSONObject;
import org.springframework.beans.factory.annotation.Autowired;
import com.novelbar.dom.user;
import com.novelbar.dom.userRead;
import com.novelbar.dom.bookinfo;
import org.springframework.data.mongodb.core.query.Criteria;
import org.springframework.data.mongodb.core.query.Query;

import java.beans.JavaBean;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

@JavaBean
public class getUserInfoModel extends baseModel {

    @Autowired
    private util util;

    private Boolean stat;

    private String msg;

    public String getUserInfo(String token){

      if (!util.checkToken(token)){
          stat = false;
          return "请先登陆";
      }
      else{
          stat = true;
          user user = (user)util.classParse(util.getTokenData(token),user.class);
          return user.getUsername();
      }
    }

    public Boolean getStat() {
        return stat;
    }


    public String getMsg() {
        return msg;
    }

    public int userReadBookTotal(String token){
        user user =(user)util.classParse(util.getTokenData(token), user.class);
        int bookCount = mongoTemplate.find(new Query(),userRead.class,user.getUsername()).size();
        return bookCount;
    }

    public String getUUID(String token) {
        user user =(user)util.classParse(util.getTokenData(token), user.class);
        return user.getId();
    }

    public HashMap UserInfo(String token){
        user user =(user)util.classParse(util.getTokenData(token), user.class);
        HashMap map = new HashMap();
        map.put("sex",user.getSex());
        map.put("age",user.getAge());
        map.put("username",user.getUsername());
        map.put("birth",user.getBirth());
        return map;
    }

    public List userReadBook(String token){
        user user =(user)util.classParse(util.getTokenData(token), user.class);
        List list = new ArrayList();
        List list2 = mongoTemplate.find(new Query(),userRead.class,user.getUsername());
        for(int i = 0 ; i< list2.size();i++){
            userRead userRead = (userRead)list2.get(i);
            HashMap map = new HashMap();
            map.put("chapter",userRead.getBook_chapter());
            List list3 =mongoTemplate.find(new Query(Criteria.where("book_name").is(userRead.getBook_name())),bookinfo.class);
            if (list3.size() > 0){
                bookinfo bi = (bookinfo) list3.get(0);
                map.put("book_name",bi.getBook_name());
                map.put("book_author",bi.getBook_author());
                map.put("book_stat",bi.getBook_stat());
                map.put("book_type",bi.getBook_type());
            }else {
                map.put("book_name","获取信息失败");
                map.put("book_author","获取信息失败");
                map.put("book_stat","获取信息失败");
                map.put("book_type","获取信息失败");
            }

            list.add(map);
        }
        return list;
    }
}
