package com.example.demoapp.service;

import com.example.demoapp.dao.PlaylistDao;
import com.example.demoapp.entity.Playlist;
import jakarta.annotation.Resource;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class PlaylistService {
    @Resource
    @Autowired
    PlaylistDao playlistDao;

    public List<Playlist> getAll(){

        return playlistDao.selectAll();
    }

    public List<Playlist> getPlaylistsByIds(List<Integer> ids) {
        return playlistDao.getPlaylistsByIds(ids);
    }
}
