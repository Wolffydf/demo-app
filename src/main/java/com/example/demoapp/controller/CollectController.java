package com.example.demoapp.controller;

import com.example.demoapp.common.Result;
import com.example.demoapp.entity.Collect;
import com.example.demoapp.service.AdminService;
import com.example.demoapp.service.CollectService;
import jakarta.annotation.Resource;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@CrossOrigin(origins = "http://localhost:8080", allowedHeaders = "*", allowCredentials = "true")
@RestController
@RequestMapping("/collect")
public class CollectController {
    @Resource
    private CollectService collectService;

    @GetMapping("/list")
    public List<Collect> getCollects(@RequestParam Integer userId) {
        return collectService.getUserCollects(userId);
    }

    @PostMapping("/add")
    public boolean addCollect(@RequestBody Collect collect) {
        return collectService.addCollect(collect);
    }

    @DeleteMapping("/delete/{id}")
    public boolean deleteCollect(@PathVariable Integer id) {
        return collectService.removeCollect(id);
    }

}
