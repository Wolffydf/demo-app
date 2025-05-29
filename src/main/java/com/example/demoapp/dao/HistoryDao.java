package com.example.demoapp.dao;

import com.example.demoapp.entity.Collect;
import com.example.demoapp.entity.History;
import org.apache.ibatis.annotations.*;
import tk.mybatis.mapper.common.Mapper;

import java.util.List;

public interface HistoryDao extends Mapper<History> {
    @Select("SELECT h.*, s.name AS song_name FROM history h " +
            "LEFT JOIN song s ON h.song_id = s.id " +
            "WHERE h.user_id = #{user_id}")
    List<History> findByUserId(@Param("user_id") Integer userId);

    @Insert("INSERT INTO history (user_id, song_id, play_time) " +
            "VALUES (#{user_id}, #{song_id}, NOW())")
    int insertHistory(History history);

    @Delete("DELETE FROM history WHERE id = #{id}")
    int deleteHistory(@Param("id") Integer id);

}
