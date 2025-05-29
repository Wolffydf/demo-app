package com.example.demoapp.controller;

import com.example.demoapp.entity.Collect;
import com.example.demoapp.entity.History;
import com.example.demoapp.service.CollectService;
import com.example.demoapp.service.HistoryService;
import jakarta.annotation.Resource;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@CrossOrigin(origins = "http://localhost:8080", allowedHeaders = "*", allowCredentials = "true")
@RestController
@RequestMapping("/history")
public class HistoryController {
    @Resource
    private HistoryService historyService;

    @GetMapping("/list")
    public List<History> getHistories(@RequestParam Integer userId) {
        List<History> histories = historyService.getUserHistory(userId);
        System.out.println(histories);
        return histories;
    }

    @PostMapping("/add")
    public boolean addHistory(@RequestBody History history) {
        return historyService.addHistory(history);
    }

    @DeleteMapping("/delete/{id}")
    public boolean deleteHistory(@PathVariable Integer id) {
        return historyService.removeHistory(id);
    }

}