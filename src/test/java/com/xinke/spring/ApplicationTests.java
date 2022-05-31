package com.xinke.spring;

import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.jdbc.core.JdbcTemplate;

import java.util.List;
import java.util.Map;

@SpringBootTest
class ApplicationTests {
    @Autowired(required = false)
    JdbcTemplate jdbcTemplate;
    @Test
    void contextLoads() {
        List<Map<String, Object>> list = jdbcTemplate.queryForList("select * from tab_user  ");
        if(list.indexOf("email")!=-1){
        System.out.println(list.get(list.indexOf("email")));
        }
    }

}
