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


}
