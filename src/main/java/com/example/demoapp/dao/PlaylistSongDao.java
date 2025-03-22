package com.example.demoapp.dao;

import com.example.demoapp.entity.Song;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Param;
import org.apache.ibatis.annotations.Select;

import java.util.List;

@Mapper
public interface PlaylistSongDao{

    @Select("SELECT * FROM playlist_song ps " +
            "JOIN song s ON ps.song_id = s.id " +
            "WHERE ps.playlist_id = #{playlistId}")
    List<Song> getSongsByPlaylistId(@Param("playlistId") Integer playlistId);
}
