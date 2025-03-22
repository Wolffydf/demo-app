package com.example.demoapp.controller;

import com.example.demoapp.common.Result;
import com.example.demoapp.dao.SongDao;
import com.example.demoapp.entity.Song;
import com.example.demoapp.service.SongService;
import jakarta.annotation.Resource;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import java.util.List;

@CrossOrigin(origins = "http://localhost:8080", allowedHeaders = "*", allowCredentials = "true")
@RestController
@RequestMapping("/song")
public class SongController {

    @Resource
    SongService songService;
    @Autowired
    private SongDao songDao;
    @GetMapping("/alldata")
    public Result getData() {
        return Result.success(songService.getAll());
    }

    @GetMapping("/search")
    public Result search(String name) {
        return Result.success(songDao.searchSongsByName(name));
    }

    @PostMapping("/upload_song")
    public Result uploadSong(@RequestBody Song song) {
        return Result.success(songDao.insertSong(song));
    }


}