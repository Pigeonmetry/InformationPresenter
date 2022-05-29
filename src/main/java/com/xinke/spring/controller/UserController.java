package com.xinke.spring.controller;

import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RequestMapping()
@RestController
public class UserController implements Datebase{
    @Override
    @GetMapping("login")
    public String login(int id, String password) {

        return null;
    }
}
