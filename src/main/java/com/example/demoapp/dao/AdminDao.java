package com.example.demoapp.dao;

import com.example.demoapp.entity.Admin;
import org.apache.ibatis.annotations.Param;
import org.apache.ibatis.annotations.Select;
import org.springframework.stereotype.Repository;
import tk.mybatis.mapper.common.Mapper;

@Repository
public interface AdminDao extends Mapper<Admin> {

    @Select("select * from admin where `username` = #{username}")
    Admin findByUsername(@Param("username") String username);

    @Select("select * from admin where `id` = #{id}")
    Admin findById(@Param("id")Integer id);
}