package com.example.demoapp.service;

import cn.hutool.core.util.ObjectUtil;
import com.example.demoapp.common.ResultCode;
import com.example.demoapp.dao.ConsumerDao;
import com.example.demoapp.entity.Consumer;
import com.example.demoapp.exception.CustomException;
import jakarta.annotation.Resource;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class ConsumerService {

    @Resource
    ConsumerDao consumerDao;

    public List<Consumer> getAll(){
        return consumerDao.selectAll();
    }

    public Consumer consumerRegister(Consumer consumer) {
        // 插入数据库前做一下用户名唯一性校验，
        String username = consumer.getUsername();
        // 先拿到用户输入的用户名
        Consumer dbConsumer = consumerDao.findByUsername(username);
        if (ObjectUtil.isNotEmpty(dbConsumer)){
            throw new CustomException(ResultCode.USER_EXIST_ERROR);
        }
        consumerDao.insertSelective(consumer);
        return consumer;
    }

    public Consumer consumerLogin(Consumer consumer) {

//        1.拿到用户传来的用户名，先到数据库查看这个用户名是否存在,如果不存在抛出异常：“用户不存在”
        Consumer dbUser = consumerDao.findByUsername(consumer.getUsername());
        if (ObjectUtil.isEmpty(dbUser)){
            throw new CustomException(ResultCode.USER_ACCOUNT_ERROR);
        }
//        2.如果用户存在，则那用户输入的密码跟数据库找到的用户的密码比对，密码输入正确，允许登录
        String password = consumer.getPassword(); //用户输入的密码
        String dbPassword = dbUser.getPassword(); // 数据库找到用户的密码

        if (!password.equalsIgnoreCase(dbPassword)){
            throw new CustomException(ResultCode.USER_ACCOUNT_ERROR);
        }

        return dbUser;

    }


    public Consumer findById(Integer id) {
        return consumerDao.findById(id);
    }
    public Consumer findByUsername(String username) {
        return consumerDao.findByUsername(username);
    }

    public boolean updateUserInfo(Consumer consumer) {
        return consumerDao.updateUserInfo(consumer) > 0;
    }

}