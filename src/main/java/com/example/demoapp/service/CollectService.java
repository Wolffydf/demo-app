package com.example.demoapp.service;

import com.example.demoapp.dao.CollectDao;
import com.example.demoapp.entity.Collect;
import jakarta.annotation.Resource;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class CollectService {
    @Resource
    CollectDao collectDao;

    public List<Collect> getUserCollects(Integer userId) {
        return collectDao.findByUserId(userId);
    }

    public boolean addCollect(Collect collect) {
        return collectDao.insertCollect(collect) > 0;
    }

    public boolean removeCollect(Integer id) {
        return collectDao.deleteCollect(id) > 0;
    }
}
