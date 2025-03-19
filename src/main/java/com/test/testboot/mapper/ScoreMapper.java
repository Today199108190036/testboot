package com.test.testboot.mapper;

import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import com.test.testboot.entity.Score;

import java.util.List;

/**
* @author zhaoxin
* @description 针对表【score】的数据库操作Mapper
* @createDate 2025-03-19 15:50:56
* @Entity generator.entity.Score
*/
public interface ScoreMapper extends BaseMapper<Score> {
    List<Score> selectAll();
}




