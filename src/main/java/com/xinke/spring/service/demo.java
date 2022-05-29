package com.xinke.spring.service;

import org.springframework.boot.SpringBootVersion;
import org.springframework.stereotype.Component;

@Component
public class demo {
    public static void main(String[] args) {
        System.out.println("hello");
        String version = SpringBootVersion.getVersion();
        System.out.println(version);

    }
}
