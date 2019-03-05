package com.novelbar.model;

import com.novelbar.dom.bookinfo;
import org.springframework.data.mongodb.core.query.Criteria;
import org.springframework.data.mongodb.core.query.Query;
import java.util.List;

public class hpBookModel extends baseModel{


    public List<?> gethpBook(String type){

            Query query = new Query(Criteria.where("book_type").is(type));
            query.limit(10);
            List<bookinfo> list = mongoTemplate.find(query,bookinfo.class);
            return list;
    }
}
