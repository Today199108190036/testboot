package com.test.testboot.service.impl;

import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.test.testboot.entity.Score;
import com.test.testboot.service.ScoreService;
import com.test.testboot.mapper.ScoreMapper;
import jakarta.annotation.Resource;
import org.springframework.stereotype.Service;

import java.util.List;


/**
* @author zhaoxin
* @description 针对表【score】的数据库操作Service实现
* @createDate 2025-03-19 15:50:56
*/
@Service
public class ScoreServiceImpl extends ServiceImpl<ScoreMapper, Score>
    implements ScoreService{
    @Resource
    private ScoreMapper scoreMapper;

    public List<Score> selectAll() {
        List<Score> list = scoreMapper.selectAll();
        return list;
    }

}




