package com.novelbar.controller;


import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.ArrayList;
import java.util.List;
import com.novelbar.model.hpBookModel;
import org.springframework.web.filter.CorsFilter;

@RestController
@RequestMapping("/getHpBook")  //首页书籍
//@CrossOrigin(origins="*",methods ={RequestMethod.GET,RequestMethod.POST})
public class hpBookController extends result{

    @Autowired
    private hpBookModel hpBookModel;

    @Autowired
    private CorsFilter corsFilter;

    @GetMapping("")
    public List<?> info(){
        List list = new ArrayList();
        List list1 = hpBookModel.gethpBook("武侠");
        List list2 = hpBookModel.gethpBook("玄幻");
        list.add(list1);
        list.add(list2);
        return list;
    }
}
