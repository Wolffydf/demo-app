package com.example.demoapp.dao;

import com.example.demoapp.entity.Playlist;
import com.example.demoapp.entity.Song;
import org.apache.ibatis.annotations.Param;
import org.apache.ibatis.annotations.Select;
import tk.mybatis.mapper.common.Mapper;

import java.util.List;


public interface PlaylistDao extends Mapper<Playlist> {
    // 模糊查询
    @Select("SELECT * FROM playlist WHERE title LIKE CONCAT('%', #{title}, '%')")
    List<Playlist> searchPlaylistByName(@Param("title") String title);

    @Select({
            "<script>",
            "SELECT * FROM playlist WHERE id IN",
            "<foreach collection='ids' item='id' open='(' separator=',' close=')'>",
            "#{id}",
            "</foreach>",
            "</script>"})
    List<Playlist> getPlaylistsByIds(@Param("ids") List<Integer> ids);

}
