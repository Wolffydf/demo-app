package com.example.demoapp.dao;

import com.example.demoapp.entity.Song;
import org.apache.ibatis.annotations.Insert;
import org.apache.ibatis.annotations.Options;
import org.apache.ibatis.annotations.Param;
import org.apache.ibatis.annotations.Select;
import org.springframework.stereotype.Repository;
import tk.mybatis.mapper.common.Mapper;

import java.util.List;

@Repository

public interface SongDao extends Mapper<Song> {
    // 模糊查询
    @Select("SELECT * FROM song WHERE name LIKE CONCAT('%', #{name}, '%')")
    List<Song> searchSongsByName(@Param("name") String name);

    // 新增插入方法
    @Insert("INSERT INTO song (name, singer_id, introduction, create_time, " +
            "update_time, pic, lyric, url, genre, singer_name) " +
            "VALUES (#{name}, #{singer_id}, #{introduction}, #{create_time}, " +
            "#{update_time}, #{pic}, #{lyric}, #{url}, #{genre}, #{singer_name})")
    @Options(useGeneratedKeys = true, keyProperty = "id", keyColumn = "id")
    int insertSong(Song song);
}

