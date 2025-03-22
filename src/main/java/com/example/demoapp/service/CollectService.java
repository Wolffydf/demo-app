package com.example.demoapp.service;

import com.example.demoapp.dao.CollectDao;
import com.example.demoapp.entity.Collect;
import jakarta.annotation.Resource;
import java.util.List;

public class CollectService {
    @Resource
    CollectDao collectDao;

    public List<Collect> getAll(){
        return collectDao.selectAll();
    }
}
