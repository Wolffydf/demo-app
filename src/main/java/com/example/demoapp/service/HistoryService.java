package com.example.demoapp.service;

import com.example.demoapp.dao.CollectDao;
import com.example.demoapp.dao.HistoryDao;
import com.example.demoapp.entity.Collect;
import com.example.demoapp.entity.History;
import jakarta.annotation.Resource;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class HistoryService {
    @Resource
    HistoryDao historyDao;

    public List<History> getUserHistory(Integer userId) {
        return historyDao.findByUserId(userId);
    }

    public boolean addHistory(History history) {
        return historyDao.insertHistory(history) > 0;
    }

    public boolean removeHistory(Integer id) {
        return historyDao.deleteHistory(id) > 0;
    }
}
