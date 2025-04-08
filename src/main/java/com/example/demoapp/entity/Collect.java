package com.example.demoapp.entity;

import javax.persistence.*;
import java.time.LocalDateTime;

@Table(name = "collect")
public class Collect {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Integer id;

    @Column(name = "user_id")
    private Integer user_id;
    @Column(name = "song_id")
    private Integer song_id;

//    收藏类型(1歌曲0歌单)
    @Column(name = "type")
    private Integer type;
    @Column(name = "playlist_id")
    private Integer playlist_id;
    @Column(name = "create_time")
    private LocalDateTime create_time;

    private String song_name;
    private String playlist_name;

    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }

    public Integer getUser_id() {
        return user_id;
    }

    public void setUser_id(Integer user_id) {
        this.user_id = user_id;
    }

    public Integer getSong_id() {
        return song_id;
    }

    public void setSong_id(Integer song_id) {
        this.song_id = song_id;
    }

    public Integer getType() {
        return type;
    }

    public void setType(Integer type) {
        this.type = type;
    }

    public Integer getPlaylist_id() {
        return playlist_id;
    }

    public void setPlaylist_id(Integer playlist_id) {
        this.playlist_id = playlist_id;
    }

    public LocalDateTime getCreate_time() {
        return create_time;
    }

    public void setCreate_time(LocalDateTime create_time) {
        this.create_time = create_time;
    }

    public String getSong_name() {
        return song_name;
    }

    public void setSong_name(String song_name) {
        this.song_name = song_name;
    }

    public String getPlaylist_name() {
        return playlist_name;
    }

    public void setPlaylist_name(String playlist_name) {
        this.playlist_name = playlist_name;
    }
}
