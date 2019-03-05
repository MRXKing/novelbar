package com.novelbar.model;


import com.alibaba.fastjson.JSONObject;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.mongodb.core.query.Criteria;
import org.springframework.data.mongodb.core.query.Query;
import com.novelbar.dom.bookinfo;
import com.novelbar.dom.book;
import com.novelbar.dom.user;
import com.novelbar.dom.userRead;
import com.novelbar.item.bookIndex;
import org.springframework.data.mongodb.core.query.Update;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.regex.Pattern;

public class getBookInfoModel extends  baseModel{

    @Autowired
    private util util;
    //获取完本书籍
    public List getCompleteBook(int currentPage){
        List list = new ArrayList();
        Query query = new Query(Criteria.where("book_stat").is("已完结"));
        int pageSize = 15;
        query.skip((currentPage-1)*pageSize);
        query.limit(pageSize);
        List list2 = mongoTemplate.find(query,bookinfo.class);
        for (int i = 0 ; i < list2.size() ; i++){
            HashMap map = new HashMap();
            bookinfo bookinfo = (bookinfo) list2.get(i);
            map.put("book_name",bookinfo.getBook_name());
            map.put("book_type",bookinfo.getBook_type());
            map.put("book_author",bookinfo.getBook_author());
            map.put("book_stat",bookinfo.getBook_stat());
            map.put("book_fontNum",bookinfo.getBook_fontNum());
            list.add(map);
        }
        return list;

    }
    //获取所有完本书籍总数
    public int getCompleteBookTotal(){
        Query query = new Query(Criteria.where("book_stat").is("已完结"));
        List list = mongoTemplate.find(query,bookinfo.class);
        return list.size();
    }
    //获取所有书的总数
    public int AllTotal(){
        List list = mongoTemplate.findAll(bookinfo.class);
        return list.size();
    }
    //获取所有书籍
    public List getAllBook(int currentPage){
        List list = new ArrayList();
        Query query = new Query();
        int pageSize = 5;
        query.skip((currentPage-1)*pageSize);
        query.limit(pageSize);
        List list2 = mongoTemplate.find(query,bookinfo.class);
        for (int i = 0 ; i < list2.size() ; i++){
            HashMap map = new HashMap();
            bookinfo bookinfo = (bookinfo) list2.get(i);
            map.put("book_name",bookinfo.getBook_name());
            map.put("book_type",bookinfo.getBook_type());
            map.put("book_author",bookinfo.getBook_author());
            map.put("book_stat",bookinfo.getBook_stat());
            map.put("book_fontNum",bookinfo.getBook_fontNum());
            list.add(map);
        }
        return list;
    }
    //查询
    public List getSearchBook(String searchContent,int currentPage){
        Pattern pattern = Pattern.compile("^.*"+searchContent+".*$", Pattern.CASE_INSENSITIVE);
        Query query = new Query(Criteria.where("book_name").regex(pattern));
        int pageSize = 5;
        query.skip((currentPage-1)*pageSize);
        query.limit(pageSize);
        List list = mongoTemplate.find(query,bookinfo.class);
        return list;
    }
    //查询出来的书的总数
    public int searchTotal(String searchContent){
        Pattern pattern = Pattern.compile("^.*"+searchContent+".*$", Pattern.CASE_INSENSITIVE);
        Query query = new Query(Criteria.where("book_name").regex(pattern));
        List list = mongoTemplate.find(query,bookinfo.class);
        return list.size();
    }
    //获取推荐书籍
    public List getRecommendBook(){
        List list = new ArrayList();
        Query query = new Query();
        int pageSize = 15;
        long start = Math.round(Math.random()*this.AllTotal());
        query.skip(start);
        query.limit(pageSize);
        List list2 = mongoTemplate.find(query,bookinfo.class);
        for (int i = 0 ; i < list2.size() ; i++){
            HashMap map = new HashMap();
            bookinfo bookinfo = (bookinfo) list2.get(i);
            map.put("book_name",bookinfo.getBook_name());
            map.put("book_type",bookinfo.getBook_type());
            map.put("book_author",bookinfo.getBook_author());
            map.put("book_stat",bookinfo.getBook_stat());
            map.put("book_fontNum",bookinfo.getBook_fontNum());
            list.add(map);
        }
        return list;
    }

    //获取该书籍基本信息
    public HashMap bookIndex(JSONObject data){

        bookIndex bookIndex = (bookIndex)util.classParse(data,bookIndex.class);
        Query query = new Query(Criteria.where("book_name").is(bookIndex.getBookTitle()));
        List list = mongoTemplate.find(query,bookinfo.class);
        if (list.size() != 0) {
            HashMap map = new HashMap();
            bookinfo bookinfo = (bookinfo) list.get(0);
            map.put("book_name", bookinfo.getBook_name());
            map.put("book_type", bookinfo.getBook_type());
            map.put("book_author", bookinfo.getBook_author());
            map.put("book_stat", bookinfo.getBook_stat());
            map.put("book_fontNum", bookinfo.getBook_fontNum());
            return map;
        }else {
            HashMap map = new HashMap();
            return map;
        }
    }

    //获取书籍目录
    public List bookList(JSONObject data){
        Query query = new Query();
        bookIndex bookIndex = (bookIndex)util.classParse(data,bookIndex.class);
        List list  = new ArrayList();
        List list2 = mongoTemplate.find(query,book.class,bookIndex.getBookTitle());
        if (list2.size() != 0){
            for (int i = 0 ; i < list2.size() ; i++)
            {
                HashMap map = new HashMap();
                book book = (book) list2.get(i);
                map.put("book_chapter_titile",book.getBook_chapter_titile());
                list.add(map);
            }
        }
        return list;
    }

    //获取读者该书的阅读情况
    public int existRead(JSONObject data){
        bookIndex bookIndex = (bookIndex)util.classParse(data,bookIndex.class);
        if (util.getTokenData(bookIndex.getToken()) != null){
            user user = (user)util.classParse(util.getTokenData(bookIndex.getToken()),user.class);
            Query query = new Query(Criteria.where("book_name").is(bookIndex.getBookTitle()));
            List list = mongoTemplate.find(query,userRead.class,user.getUsername());
            if (list.size() == 0){
                return 0;
            }else {
                userRead userReads = (userRead)list.get(0);
                return userReads.getBook_chapter();
            }
        }else {
            return 0;
        }
    }

    //检测书是否存在
    public Boolean checkBookTitle(JSONObject data){
        bookIndex bookIndex = (bookIndex)util.classParse(data,bookIndex.class);
        return mongoTemplate.collectionExists(bookIndex.getBookTitle());
    }

    //获取书籍总章节数
    public  int chapte_end(String bookName) {
        List list2 = mongoTemplate.find(new Query(),book.class,bookName);
        return list2.size();
    }

    //根据读者阅读情况获取书籍当前阅读章节数,并获取总章节数
    public HashMap chapter_page(JSONObject data){
        bookIndex bookIndex = (bookIndex)util.classParse(data,bookIndex.class);
        HashMap map = new HashMap();
        if (util.getTokenData(bookIndex.getToken()) != null){
            user user = (user)util.classParse(util.getTokenData(bookIndex.getToken()),user.class);
            Query query = new Query(Criteria.where("book_name").is(bookIndex.getBookTitle()));
            List list = mongoTemplate.find(query,userRead.class,user.getUsername());
            if (list.size() == 0) {  //没看过书时的情况
                userRead userRead = new userRead();
                userRead.setBook_chapter(1);
                userRead.setBook_name(bookIndex.getBookTitle());
                mongoTemplate.insert(userRead,user.getUsername());
                map.put("book_chapter",1);
                return map;
            }else {
                userRead userRead = (userRead) list.get(0);
                map.put("book_chapter",userRead.getBook_chapter());
                return map;
            }
        }else {
            return map;
        }
    }

    //获取章节内容
    public HashMap chapter_content(JSONObject data){
        bookIndex bookIndex = (bookIndex)util.classParse(data,bookIndex.class);
        HashMap map = new HashMap();
        if (util.getTokenData(bookIndex.getToken()) != null) {
            user user = (user)util.classParse(util.getTokenData(bookIndex.getToken()),user.class);
            List list = mongoTemplate.find(new Query(Criteria.where("book_name").is(bookIndex.getBookTitle())),userRead.class,user.getUsername());
            int chapter = Integer.parseInt(bookIndex.getChapter());
            if (list.size() > 0 ){
                userRead userRead = (userRead)list.get(0);
                if (chapter > userRead.getBook_chapter()){
                    mongoTemplate.updateMulti(new Query(Criteria.where("book_name").is(bookIndex.getBookTitle())),new Update().set("book_chapter",chapter),user.getUsername());
                }
            }else {
                userRead userRead = new userRead();
                userRead.setBook_chapter(chapter);
                userRead.setBook_name(bookIndex.getBookTitle());
                mongoTemplate.insert(userRead,user.getUsername());
            }
            Query query = new Query();
            query.skip(chapter-1);
            query.limit(1);
            List list2 = mongoTemplate.find(query,book.class,bookIndex.getBookTitle());
            book book = (book)list2.get(0);
            map.put("book_chapter_titile",book.getBook_chapter_titile());
            map.put("chapter_end",chapte_end(bookIndex.getBookTitle()));
            map.put("book_chapter_content",book.getBook_chapter_content());
            return map;
        }else {
            return map;
        }
    }

}
