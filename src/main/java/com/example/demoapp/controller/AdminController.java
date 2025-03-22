package com.example.demoapp.controller;

import com.example.demoapp.common.Result;
import com.example.demoapp.entity.Admin;
import com.example.demoapp.entity.Consumer;
import com.example.demoapp.service.AdminService;
import jakarta.annotation.Resource;
import org.springframework.web.bind.annotation.*;

import java.util.List;
@CrossOrigin(origins = "http://localhost:8080", allowedHeaders = "*", allowCredentials = "true")
@RestController
@RequestMapping("/admin")
public class AdminController {
    @Resource
    AdminService adminService;

    //    查询所有数据
    @GetMapping("/alldata")
    public Result getData(){

        return Result.success(adminService.getAll());

    }


}