package com.xinke.spring.mapper;

import com.xinke.spring.bean.User;
import org.apache.ibatis.annotations.Insert;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Param;
import org.apache.ibatis.annotations.Select;
import org.springframework.boot.autoconfigure.EnableAutoConfiguration;

import org.springframework.stereotype.Component;
import org.springframework.stereotype.Repository;


/**
 * @author Administrator
 */
@Repository
@Mapper
public interface UserMapper{
    /**
     * 登录
     * @param email
     * @param password
     * @return
     */
    @Select("select email,password from tab_user where email=#{email} and password=#{password}")
    User login(String email,String password);
    /**
     * 注册
     * @param email
     * @param password
     * @return
     */
    @Insert("insert into tab_user(email,password) values(#{email},#{password})")
    int saveUser(@Param("email") String email,@Param("password") String password);


}
