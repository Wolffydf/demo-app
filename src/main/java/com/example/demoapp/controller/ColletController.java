package com.example.demoapp.controller;

import com.example.demoapp.common.Result;
import com.example.demoapp.service.AdminService;
import jakarta.annotation.Resource;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import javax.persistence.GeneratedValue;

@CrossOrigin
@RestController
@RequestMapping("/collect")
public class ColletController {
    @Resource
    AdminService adminService;
    //    查询所有数据
    @GetMapping("/alldata")
    public Result getData(){

        return Result.success(adminService.getAll());

    }
}
