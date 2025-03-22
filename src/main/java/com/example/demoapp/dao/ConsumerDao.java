package com.example.demoapp.dao;

import com.example.demoapp.entity.Consumer;
import com.example.demoapp.entity.Song;
import org.apache.ibatis.annotations.*;
import org.springframework.stereotype.Repository;
import tk.mybatis.mapper.common.Mapper;

@Repository
public interface ConsumerDao extends Mapper<Consumer> {
    //    根据用户名查询，查询跟参数相同的用户名 返回一个用户
    @Select("select * from consumer where `username` = #{username}")
    Consumer findByUsername(@Param("username") String username);

    @Select("select * from consumer where `id` = #{id}")
    Consumer findById(@Param("id")Integer id);

    @Update("UPDATE consumer SET username=#{username}, password=#{password}, sex=#{sex}, tel=#{tel}, email=#{email}, birth=#{birth}, introduction=#{introduction}, location=#{location}, avatar=#{avatar}, update_time=#{update_time} WHERE id=#{id}")
    int updateUserInfo(Consumer consumer);

    @Select("select * from consumer where `id` = #{id}")
    Consumer findConsumerById(Integer id);
}
