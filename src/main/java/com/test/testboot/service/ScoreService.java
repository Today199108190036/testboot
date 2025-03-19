package com.test.testboot.service;

import com.baomidou.mybatisplus.extension.service.IService;
import com.test.testboot.entity.Score;

import java.util.List;

/**
* @author zhaoxin
* @description 针对表【score】的数据库操作Service
* @createDate 2025-03-19 15:50:56
*/
public interface ScoreService extends IService<Score> {
    List<Score> selectAll();
}
