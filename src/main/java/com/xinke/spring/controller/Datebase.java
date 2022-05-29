package com.xinke.spring.controller;

import org.springframework.stereotype.Component;

@Component
public interface Datebase {
    public String login(int id,String password);
}
