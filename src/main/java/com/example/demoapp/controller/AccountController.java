package com.example.demoapp.controller;

import com.example.demoapp.common.Result;
import com.example.demoapp.common.config.JwtTokenUtils;
import com.example.demoapp.entity.Account;
import com.example.demoapp.entity.Admin;
import com.example.demoapp.entity.Consumer;
import com.example.demoapp.service.AdminService;
import com.example.demoapp.service.ConsumerService;
import jakarta.annotation.Resource;
import org.springframework.beans.BeanUtils;
import org.springframework.web.bind.annotation.*;

import java.util.HashMap;
import java.util.Map;

@CrossOrigin(origins = "http://localhost:8080", allowedHeaders = "*", allowCredentials = "true")
@RestController
@RequestMapping("/account")
public class AccountController {
    @Resource
    AdminService adminService;

    @Resource
    ConsumerService consumerService;
    
    /**
     * 注册接口
     */

    @PostMapping("/register")
    public Result accountRegister(@RequestBody Account account){
        Integer role = account.getRole();
        Account login = new Account(); // 定义一个Account类的login,用于返回给前端
        if ( 0 == role){ //这里只有用户注册 如果有其他角色也需要注册，也是同样的逻辑
            Consumer consumer = new Consumer();
            BeanUtils.copyProperties(account,consumer);
            login = consumerService.consumerRegister(consumer);
        }
        return Result.success(login);
    }


    /**
     * 用户登录接口
     */

    @PostMapping("/login")
    public Result accountLogin(@RequestBody Account account){
        Integer role = account.getRole();
        Account login = new Account(); // 定义一个Account类的login,用于返回给前端
        if ( 1 == role){
            Admin admin = new Admin(); //创建一个管理员类admin
            BeanUtils.copyProperties(account,admin); //把父类的属性拷贝到admin
            login = adminService.adminLogin(admin); //调用服务层的登录逻辑
        }
        if ( 0 == role){
            Consumer consumer = new Consumer();
            BeanUtils.copyProperties(account,consumer);
            login = consumerService.consumerLogin(consumer);
        }

        //        生成token
        String token = JwtTokenUtils.genToken(login.getId() + "-" + login.getRole(), login.getPassword());
        //        创建一个键值对map集合，把token和user塞进去，返回给前端
        Map<String, Object> map = new HashMap<>();
        map.put("token", token);
        map.put("user", login);
        return Result.success(map); //装好数据后，把map结合返回给前端s
//        return Result.success(login);
    }

}
