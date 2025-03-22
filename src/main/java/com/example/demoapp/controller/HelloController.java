package com.example.demoapp.controller;

import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
public class HelloController {
//  http://localhost:8080/hello
//  http://localhost:8080/hello?nickname=zhangsan
    @RequestMapping("/hello")
    public String sayHello(String nickname) {
        return "你好" + nickname;
    }
}
