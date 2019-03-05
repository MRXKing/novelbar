package com.novelbar.dom;


import org.springframework.data.annotation.Id;
import org.springframework.data.mongodb.core.mapping.Document;
import org.springframework.data.mongodb.core.mapping.Field;

import java.beans.JavaBean;

@Document(collection="bookinfo")
@JavaBean
public class bookinfo {

    @Id
    private String id;

    @Field("book_name")
    private String book_name;

    @Field("book_type")
    private String book_type;

    @Field("book_stat")
    private String book_stat;

    @Field("book_author")
    private String book_author;

    @Field("book_fontNum")
    private String book_fontNum;

    public String getId() {
        return id;
    }

    public String getBook_name() {
        return book_name;
    }

    public String getBook_type() {
        return book_type;
    }

    public String getBook_stat() {
        return book_stat;
    }

    public String getBook_author() {
        return book_author;
    }

    public String getBook_fontNum() {
        return book_fontNum;
    }

    public void setId(String id) {
        this.id = id;
    }

    public void setBook_name(String book_name) {
        this.book_name = book_name;
    }

    public void setBook_type(String book_type) {
        this.book_type = book_type;
    }

    public void setBook_stat(String book_stat) {
        this.book_stat = book_stat;
    }

    public void setBook_author(String book_author) {
        this.book_author = book_author;
    }

    public void setBook_fontNum(String book_fontNum) {
        this.book_fontNum = book_fontNum;
    }
}
