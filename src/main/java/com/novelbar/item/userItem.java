package com.novelbar.item;


import java.beans.JavaBean;

@JavaBean
public class userItem {


    private String username =null;

    private String pwd =null;

    private String birth =null;

    private String sex =null;

    private String age =null;

    private String againpwd =null;

    public String getAgainpwd() {
        return againpwd;
    }

    public void setAgainpwd(String againpwd) {
        this.againpwd = againpwd;
    }

    public String getUsername() {
        return username;
    }

    public void setUsername(String username) {
        this.username = username;
    }

    public String getPwd() {
        return pwd;
    }

    public void setPwd(String pwd) {
        this.pwd = pwd;
    }

    public String getBirth() {
        return birth;
    }

    public void setBirth(String birth) {
        this.birth = birth;
    }

    public String getSex() {
        return sex;
    }

    public void setSex(String sex) {
        this.sex = sex;
    }

    public String getAge() {
        return age;
    }

    public void setAge(String age) {
        this.age = age;
    }
}
