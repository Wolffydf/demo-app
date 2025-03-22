package com.example.demoapp.controller;

import com.example.demoapp.common.Result;
import com.example.demoapp.dao.ConsumerDao;
import com.example.demoapp.entity.Consumer;
import com.example.demoapp.service.ConsumerService;
import jakarta.annotation.Resource;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.MediaType;
import org.springframework.web.bind.annotation.*;

@CrossOrigin(origins = "http://localhost:8080", allowedHeaders = "*", allowCredentials = "true")
@RestController
@RequestMapping("/consumer")
public class ConsumerController {
    @Resource
    ConsumerService consumerService;
    @Autowired
    private ConsumerDao consumerDao;


    //    查询所有数据
    @GetMapping("/alldata")
    public Result getData(){

        return Result.success(consumerService.getAll());

    }
    // 显式声明consumes类型
    @PostMapping(value = "/update_user_info",
            consumes = MediaType.APPLICATION_JSON_VALUE)
    public Result updateUserInfo(@RequestBody Consumer consumer) {
        return Result.success(consumerDao.updateUserInfo(consumer));
    }
//    @GetMapping("/user_info")
//    public Result getUserInfo(@RequestBody Consumer consumer) {
//        Integer userId = consumer.getId();
//        Consumer dbConsumer = consumerDao.findConsumerById(userId);
//        if (consumer != null) {
//            return Result.success(dbConsumer);
//        } else {
//            return Result.error("用户信息不存在");
//        }
//    }


}