package com.example.demoapp.controller;

import com.example.demoapp.common.Result;
import com.example.demoapp.dao.PlaylistDao;
import com.example.demoapp.entity.Playlist;
import com.example.demoapp.service.PlaylistService;
import jakarta.annotation.Resource;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@CrossOrigin(origins = "http://localhost:8080", allowedHeaders = "*", allowCredentials = "true")
@RestController
@RequestMapping("/playlist")
public class PlaylistController {

    @Resource
    PlaylistService playlistService;
    @Autowired
    private PlaylistDao playlistDao;
    @GetMapping("/alldata")
    public Result getData() {
        return Result.success(playlistService.getAll());
    }

    @GetMapping("/search")
    public Result search(String name) {
        return Result.success(playlistDao.searchPlaylistByName(name));
    }

    @PostMapping("/get_playlists")
    public List<Playlist> getPlaylistsByIds(@RequestBody List<Integer> ids) {
        return playlistService.getPlaylistsByIds(ids);
    }

}