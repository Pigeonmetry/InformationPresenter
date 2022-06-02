package com.xinke.spring.bean;

import com.baomidou.mybatisplus.annotation.TableName;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.springframework.stereotype.Component;

import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;

/**
 * @author Administrator
 */
@Data
@AllArgsConstructor
@NoArgsConstructor
@TableName("tab_user")
@Entity
public class User{
    @Id
    @GeneratedValue
    private String email;

    private String password;

    private String username;

    private String sex;

    private String phone;

    private String height;

    private String skills;

    private String education;

    private String school;

    private String address;

    private String text;

}
