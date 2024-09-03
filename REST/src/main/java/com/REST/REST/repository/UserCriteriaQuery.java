package com.REST.REST.repository;

import com.REST.REST.entity.Users;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.mongodb.core.query.Criteria;
import org.springframework.data.mongodb.core.query.Query;
import org.springframework.data.mongodb.core.MongoTemplate;
import org.springframework.stereotype.Component;
import org.springframework.stereotype.Repository;

import java.util.List;

@Component
public class UserCriteriaQuery {

    @Autowired
    private MongoTemplate mongoTemplate;
    public List<Users> getAllValidEmailUsers(){
        String emailRegex = "^[a-zA-Z0-9._%+-]+@[a-zA-Z0-9.-]+\\.[a-zA-Z]{2,}$";
        Query query = new Query();
        query.addCriteria(Criteria.where("email").regex(emailRegex));
        query.addCriteria(Criteria.where("roles").in("USERS","ADMIN"));
        return mongoTemplate.find(query, Users.class);
    }
}
