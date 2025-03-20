package com.test.testboot.service.impl;

import ch.qos.logback.classic.Logger;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.test.testboot.entity.Score;
import com.test.testboot.service.ScoreService;
import com.test.testboot.mapper.ScoreMapper;
import jakarta.annotation.Resource;
import org.slf4j.LoggerFactory;
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
    protected static Logger logger= (Logger) LoggerFactory.getLogger(ScoreServiceImpl.class);

    @Resource
    private ScoreMapper scoreMapper;

    @Override
    public List<Score> selectAll() {
        return scoreMapper.selectAll();
    }

    @Override
    public List<Score> selectById(int id) {
        return scoreMapper.selectById(id);
    }

    @Override
    public boolean deleteById(int id) {
        //用于判断删除语句是否执行成功
        boolean flag = false;
        try {
            scoreMapper.deleteById(id);
            flag = true;
        } catch (Exception e) {
            throw new RuntimeException(e);
        }
        return flag;
    }

    @Override
    public boolean updateById(int id, int grade) {
        //用于判断更新语句是否执行成功
        boolean flag = false;
        try {
            scoreMapper.updateById(id, grade);
            flag = true;
        } catch (Exception e) {
            throw new RuntimeException(e);
        }
        return flag;
    }

    @Override
    public boolean updateByIdAll(int id, int stuId, String cName, int grade) {
        //用于判断更新语句是否执行成功
        boolean flag = false;
        try {
            scoreMapper.updateByIdAll(id, stuId, cName, grade);
            flag = true;
        } catch (Exception e) {
            throw new RuntimeException(e);
        }
        return flag;
    }

    @Override
    public boolean insertById(Score score) {
        //用于判断插入语句是否执行成功
        boolean flag = false;
        try {
            scoreMapper.insertById(score);
            flag = true;
        } catch (Exception e) {
            throw new RuntimeException(e);
        }
        return flag;
    }
}




