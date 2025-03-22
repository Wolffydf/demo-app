package com.example.demoapp.dao;

import com.example.demoapp.entity.Admin;
import com.example.demoapp.entity.Song;
import org.apache.ibatis.annotations.Select;
import org.springframework.stereotype.Repository;
import tk.mybatis.mapper.common.Mapper;

@Repository
public interface AudioDao extends Mapper<Song> {

    @Select("SELECT url FROM song WHERE id = #{id}")
    String findUrlById(Integer id);
}
