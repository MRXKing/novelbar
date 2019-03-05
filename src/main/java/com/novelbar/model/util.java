package com.novelbar.model;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.springframework.util.StringUtils;

import java.beans.IntrospectionException;
import java.beans.PropertyDescriptor;
import java.io.IOException;
import java.lang.reflect.Field;
import java.lang.reflect.InvocationTargetException;
import java.lang.reflect.Method;
import java.util.UUID;

public class util extends baseModel{

    //随机token
    public String getToken(){
        String token = UUID.randomUUID().toString();
        return token;
    }

    //json转class
    public Object classParse(Object json , Class arg){
        try {
            ObjectMapper mapper = new ObjectMapper();
            Object o = mapper.readValue(json.toString(), arg);
            return o;
        }catch (IOException e){
            e.printStackTrace();
        }
        return "";
    }

    //类转json
    public String jsonParse(Object arg){
            ObjectMapper mapper = new ObjectMapper();
        try {
            String json = mapper.writeValueAsString(arg);
            return json;
        } catch (JsonProcessingException e) {
            e.printStackTrace();
        }
        return "";
    }

    //实体类不为空
    public Boolean NotNull(Object beanObject){
        Field[] fields = beanObject.getClass().getDeclaredFields();
        Class clazz = beanObject.getClass();

        for(Field field : fields){
            try {
                PropertyDescriptor pd = new PropertyDescriptor(field.getName(),clazz);
                Method getMethod = pd.getReadMethod();
                    try {

                      if (StringUtils.isEmpty(getMethod.invoke(beanObject))){
                          return false;
                      }
                    } catch (IllegalAccessException e) {
                        e.printStackTrace();
                    } catch (InvocationTargetException e) {
                        e.printStackTrace();
                    }
            } catch (IntrospectionException e) {
                e.printStackTrace();
            }
        }
        return true;
    }
    //arg2的值 复制给 arg
    public Object copyBean(Object arg,Object arg2){
        Field[] fields = arg.getClass().getDeclaredFields();
        Class clazz = arg.getClass();
        Class clazz2 = arg2.getClass();
        for(Field field : fields){
            try {
                if (field.getName().equals("id") == false){
                    PropertyDescriptor pd = new PropertyDescriptor(field.getName(),clazz);
                    PropertyDescriptor pd2 = new PropertyDescriptor(field.getName(),clazz2);
                    Method getMethod2 = pd2.getReadMethod();
                    Method setMethod = pd.getWriteMethod();
                    try {

                        setMethod.invoke(arg,getMethod2.invoke(arg2));

                    } catch (IllegalAccessException e) {
                        e.printStackTrace();
                    } catch (InvocationTargetException e) {
                        e.printStackTrace();
                    }
                }
            } catch (IntrospectionException e) {
                e.printStackTrace();
            }
        }
        return arg;
    }

    //判断是否为数字
    public  boolean isNum(String str){
        return str.matches("^[-+]?(([0-9]+)([.]([0-9]+))?|([.]([0-9]+))?)$");
    }

    //确认token
    public boolean checkToken(String token){
        Object object  = redisTemplate.opsForValue().get(token);
        if (object == null){
            return false;
        }else{
            return true;
        }
    }

    //获取token的值
    public Object getTokenData(String token){
        Object object = redisTemplate.opsForValue().get(token);
        return object;
    }

}
