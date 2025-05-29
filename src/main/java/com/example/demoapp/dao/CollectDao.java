package com.example.demoapp.dao;

import com.example.demoapp.entity.Collect;
import org.apache.ibatis.annotations.*;
import tk.mybatis.mapper.common.Mapper;

import java.util.List;

public interface CollectDao extends Mapper<Collect> {
    @Select("SELECT c.*, s.name AS song_name, p.title AS playlist_name FROM collect c " +
            "LEFT JOIN song s ON c.song_id = s.id " +
            "LEFT JOIN playlist p ON c.playlist_id = p.id " +
            "WHERE c.user_id = #{user_id}")
    List<Collect> findByUserId(@Param("user_id") Integer userId);

    @Insert("INSERT INTO collect (user_id, type, song_id, playlist_id, create_time) " +
            "VALUES (#{user_id}, #{type}, #{song_id}, #{playlist_id}, NOW())")
    int insertCollect(Collect collect);

    @Delete("DELETE FROM collect WHERE id = #{id}")
    int deleteCollect(@Param("id") Integer id);

}
