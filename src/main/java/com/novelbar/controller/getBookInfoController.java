package com.novelbar.controller;


import com.alibaba.fastjson.JSONObject;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;
import com.novelbar.model.getBookInfoModel;

import java.util.HashMap;
import java.util.List;

@RestController
@RequestMapping("/getBookInfo")
public class getBookInfoController extends result{

    @Autowired
    private getBookInfoModel getBookInfoModel;

    @GetMapping("/complete")
    public HashMap complete(@RequestParam(value = "currentPage") int currentPage){

        HashMap map = new HashMap();
        List list = getBookInfoModel.getCompleteBook(currentPage);
        map.put("msg",list);
        map.put("errcode",0);
        map.put("total",getBookInfoModel.getCompleteBookTotal());
        return map;
    }

    @GetMapping("/search")
    public HashMap search(@RequestParam(value = "searchContent") String searchContent , @RequestParam(value = "currentPage") int currentPage){
        HashMap map = new HashMap();
        List list = getBookInfoModel.getSearchBook(searchContent,currentPage);
        map.put("msg",list);
        map.put("errcode",0);
        map.put("total",getBookInfoModel.searchTotal(searchContent));
        return map;
    }

    @GetMapping("/allBook")
    public HashMap allBook(@RequestParam(value = "currentPage") int currentPage){
        HashMap map = new HashMap();
        List list = getBookInfoModel.getAllBook(currentPage);
        map.put("msg",list);
        map.put("errcode",0);
        map.put("total",getBookInfoModel.AllTotal());
        return map;
    }

    @GetMapping("/recommend")
    public List recommend(){

        return getBookInfoModel.getRecommendBook();
    }

    @PostMapping("/bookIndex")
    public HashMap bookIndex(@RequestBody JSONObject data){

            HashMap map2 = getBookInfoModel.bookIndex(data);
            List list2 = getBookInfoModel.bookList(data);
            int eRead = getBookInfoModel.existRead(data);
            if (map2.size() == 0 || list2.size() == 0 ){
                HashMap map1 = new HashMap();
                map1.put("detail","fail");
                HashMap res = err(404,map1);
                res.put("errcode",1);
                return res;
            }else{
                HashMap map = new HashMap();
                map.put("errcode",0);
                map.put("userInfo",map2);
                map.put("bookList",list2);
                map.put("msg","success");
                map.put("eRead",eRead);
                return map;
            }
    }

    @PostMapping("/goRead")
    public HashMap goRead(@RequestBody JSONObject data){
         if (getBookInfoModel.checkBookTitle(data) || getBookInfoModel.chapter_page(data).size() > 0){
             HashMap map = new HashMap();
             HashMap map2 = getBookInfoModel.chapter_page(data);
             map.put("errcode",0);
             map.put("msg","success");
             map.put("bookData",map2);
             return map;
         }else{
             HashMap map1 = new HashMap();
             map1.put("detail","fail");
             HashMap res = err(404,map1);
             res.put("errcode",1);
             return res;
         }
    }

    @PostMapping("/getChapterContent")
    public HashMap getChapterContent(@RequestBody JSONObject data){

        HashMap map2 = getBookInfoModel.chapter_content(data);
        HashMap map3 =  getBookInfoModel.bookIndex(data);
        if (getBookInfoModel.checkBookTitle(data) || map2.size() > 0){
            HashMap map = new HashMap();
            map.put("errcode",0);
            map.put("msg","success");
            map.put("bookData",map2);
            map.put("bookInfo",map3);
            return map;
        }else {
            HashMap map = new HashMap();
            map.put("detail","fail");
            HashMap res = err(404,map);
            res.put("errcode",1);
            return res;
        }
    }
}
