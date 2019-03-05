package com.novelbar.dom;

import org.springframework.data.annotation.Id;
import org.springframework.data.mongodb.core.mapping.Field;

//用户看过的书
public class userRead {
    @Id
    private String Id;

    @Field("book_name")
    private String book_name;

    @Field("book_chapter")
    private int book_chapter;

    public String getId() {
        return Id;
    }

    public void setId(String id) {
        Id = id;
    }

    public String getBook_name() {
        return book_name;
    }

    public void setBook_name(String book_name) {
        this.book_name = book_name;
    }

    public int getBook_chapter() {
        return book_chapter;
    }

    public void setBook_chapter(int book_chapter) {
        this.book_chapter = book_chapter;
    }
}
