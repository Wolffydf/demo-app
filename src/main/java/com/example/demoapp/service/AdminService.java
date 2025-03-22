package com.example.demoapp.service;

import cn.hutool.core.util.ObjectUtil;
import com.example.demoapp.common.ResultCode;
import com.example.demoapp.dao.AdminDao;
import com.example.demoapp.entity.Admin;
import com.example.demoapp.exception.CustomException;
import jakarta.annotation.Resource;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class AdminService {

    @Resource
    AdminDao adminDao;

    public List<Admin> getAll() {
        return adminDao.selectAll();
    }

    public Admin adminLogin(Admin admin) {

//        1.拿到用户传来的用户名，先到数据库查看这个用户名是否存在,如果不存在抛出异常：“用户不存在”
        Admin dbAdmin = adminDao.findByUsername(admin.getUsername());
        if (ObjectUtil.isEmpty(dbAdmin)){
            throw new CustomException(ResultCode.USER_ACCOUNT_ERROR);
        }
//        2.如果用户存在，则那用户输入的密码跟数据库找到的用户的密码比对，密码输入正确，允许登录
        String password = admin.getPassword(); //用户输入的密码
        String dbPassword = dbAdmin.getPassword(); // 数据库找到用户的密码

        if (!password.equalsIgnoreCase(dbPassword)){
            throw new CustomException(ResultCode.USER_ACCOUNT_ERROR);
        }

        return dbAdmin;

    }

    public Admin findById(Integer id) {

        return adminDao.findById(id);
    }
}
