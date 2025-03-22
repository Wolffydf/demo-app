
package com.example.demoapp.service;

import com.example.demoapp.dao.AudioDao;
import com.example.demoapp.dao.SongDao;
import com.example.demoapp.entity.Song;
import jakarta.annotation.Resource;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class AudioService {

    @Resource
    AudioDao audioDao;

    public String getAudioUrlById(Integer id) {
        return audioDao.findUrlById(id);
    }

}