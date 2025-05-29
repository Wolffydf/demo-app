
package com.example.demoapp.service;

import com.example.demoapp.dao.SongDao;
import com.example.demoapp.entity.Song;
import jakarta.annotation.Resource;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class SongService {
    @Resource
    SongDao songDao;
    public List<Song> getAll(){
        return songDao.selectAll();
    }
}