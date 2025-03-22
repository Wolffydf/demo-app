package com.example.demoapp.controller;

import com.example.demoapp.service.AudioService;
import org.springframework.core.io.Resource;
import org.springframework.core.io.UrlResource;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.beans.factory.annotation.Autowired;

import java.net.MalformedURLException;
import java.nio.file.Path;
import java.nio.file.Paths;

@RestController
@RequestMapping("/audio")
public class AudioController {

    @Autowired
    private AudioService audioService;

    @GetMapping("/hello")
    public String hello() {
        return "你好";
    }

    @GetMapping("/{id}")
    public ResponseEntity<Resource> getAudio(@PathVariable Integer id) throws MalformedURLException {
        // 查询数据库获取音频文件的 URL
        String filePath = audioService.getAudioUrlById(id);
        if (filePath == null || filePath.isEmpty()) {
            return ResponseEntity.notFound().build();
        }

        // 读取音频文件
        Path path = Paths.get(filePath);
        Resource resource = new UrlResource(path.toUri());

        // 确定文件 MIME 类型
        String contentType = "audio/mpeg"; // 默认 MP3
        if (filePath.endsWith(".wav")) {
            contentType = "audio/wav";
        }

        return ResponseEntity.ok()
                .contentType(MediaType.parseMediaType(contentType))
                .body(resource);
    }
}
