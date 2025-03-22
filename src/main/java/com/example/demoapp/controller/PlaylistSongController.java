package com.example.demoapp.controller;

import com.example.demoapp.entity.Song;
import com.example.demoapp.service.PlaylistSongService;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import jakarta.annotation.Resource;
import java.util.List;

@RestController
public class PlaylistSongController {

    @Resource
    private PlaylistSongService playlistSongService;

    @GetMapping("/playlist/randomSongs")
    public List<Song> getRandomSongs(@RequestParam Integer playlistId) {
        return playlistSongService.getRandomSongsByPlaylistId(playlistId);
    }
}