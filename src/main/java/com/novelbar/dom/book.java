package com.novelbar.dom;

import org.springframework.data.annotation.Id;
import org.springframework.data.mongodb.core.mapping.Field;

import java.beans.JavaBean;

@JavaBean
public class book {

    @Id
    private String id;

    @Field("book_chapter_titile")
    private String book_chapter_titile;

    @Field("book_chapter_content")
    private String book_chapter_content;

    public String getBook_chapter_titile() {
        return book_chapter_titile;
    }

    public String getId() {
        return id;
    }

    public String getBook_chapter_content() {
        return book_chapter_content;
    }

    public void setId(String id) {
        this.id = id;
    }

    public void setBook_chapter_titile(String book_chapter_titile) {
        this.book_chapter_titile = book_chapter_titile;
    }

    public void setBook_chapter_content(String book_chapter_content) {
        this.book_chapter_content = book_chapter_content;
    }
}
