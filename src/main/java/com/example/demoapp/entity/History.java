package com.example.demoapp.entity;

import javax.persistence.*;
import java.time.LocalDateTime;

@Table(name = "history")
public class History {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Integer id;

    @Column(name = "user_id")
    private Integer user_id;
    @Column(name = "song_id")
    private Integer song_id;

    @Column(name = "play_time")
    private LocalDateTime play_time;

    private String song_name;

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

    public LocalDateTime getPlay_time() {
        return play_time;
    }

    public void setPlay_time(LocalDateTime play_time) {
        this.play_time = play_time;
    }

    public String getSong_name() {
        return song_name;
    }

    public void setSong_name(String song_name) {
        this.song_name = song_name;
    }
}
