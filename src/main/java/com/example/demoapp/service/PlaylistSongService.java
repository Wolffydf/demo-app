package com.example.demoapp.service;

import com.example.demoapp.dao.PlaylistSongDao;
import com.example.demoapp.entity.Song;
import jakarta.annotation.Resource;
import org.springframework.stereotype.Service;

import java.util.Collections;
import java.util.List;
import java.util.stream.Collectors;

@Service
public class PlaylistSongService {

    @Resource
    PlaylistSongDao playlistSongDao;

    /**
     * 根据歌单id查询随机4首歌
     */
    public List<Song> getRandomSongsByPlaylistId(Integer playlistId) {
        List<Song> songs = playlistSongDao.getSongsByPlaylistId(playlistId);
        // 如果歌曲不足4首，返回全部；否则随机4首
        if (songs.size() <= 4) {
            return songs;
        }
        Collections.shuffle(songs);
        return songs.stream().limit(4).collect(Collectors.toList());
    }
}
