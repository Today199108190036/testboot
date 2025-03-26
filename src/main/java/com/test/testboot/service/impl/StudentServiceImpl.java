package com.test.testboot.service.impl;

import ch.qos.logback.classic.Logger;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.fasterxml.jackson.core.type.TypeReference;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.test.testboot.entity.Student;
import com.test.testboot.service.StudentService;
import com.test.testboot.mapper.StudentMapper;
import com.test.testboot.utils.RedisUtil;
import jakarta.annotation.Resource;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Service;

import java.util.List;

/**
* @author zhaoxin
* @description 针对表【student】的数据库操作Service实现
* @createDate 2025-03-20 19:59:35
*/
@Service
public class StudentServiceImpl extends ServiceImpl<StudentMapper, Student>
    implements StudentService{
    public static Logger logger= (Logger) LoggerFactory.getLogger(ScoreServiceImpl.class);

    @Resource
    private StudentMapper studentMapper;

    @Resource
    private RedisUtil redisUtil;

    @Override
    public List<Student> selectAll() {
        return studentMapper.selectAll();
    }

    @Override
    public List<Student> selectById(int id) {
        return studentMapper.selectById(id);
    }

    @Override
    public List<Student> findById(int id) {
        String key = "student:id:" + id;
        //判断key是否在缓存中存在
        boolean isExist = redisUtil.hasKey(key);
        if(isExist){
            //如果在缓存中存在，直接获取并返回
            return getStudentListFromRedis(key);
        } else {
            // 不存在缓存，先从数据库中获取，再保存至 Redis，最后返回用户
            List<Student> list = studentMapper.selectById(id);
            logger.info(list.toString());
            if (list != null) {
                // 将数据放入缓存
                setStudentListToRedis(key, list);
            }
            return list;
        }
    }

    @Override
    public boolean deleteById(int id) {
        boolean flag = false;
        try {
            studentMapper.deleteById(id);
            flag = true;
        } catch (Exception e) {
            throw new RuntimeException(e);
        }
        return flag;
    }

    @Override
    public boolean updateById(int id, String address) {
        boolean flag = false;
        try {
            studentMapper.updateById(id, address);
            flag = true;
        } catch (Exception e) {
            throw new RuntimeException(e);
        }
        return flag;
    }

    @Override
    public boolean updateByIdAll(int id, String name, String sex, int birth, String department, String address) {
        boolean flag = false;
        try {
            studentMapper.updateByIdAll(id, name, sex, birth, department, address);
            flag = true;
        } catch (Exception e) {
            throw new RuntimeException(e);
        }
        return flag;
    }

    @Override
    public boolean insertById(Student student) {
        boolean flag = false;
        try {
            studentMapper.insertById(student);
            flag = true;
        } catch (Exception e) {
            throw new RuntimeException(e);
        }
        return flag;
    }

    public List<Student> getStudentListFromRedis(String key) {
        try {
            String json = (String) redisUtil.get(key);
            if (json != null) {
                ObjectMapper objectMapper = new ObjectMapper();
                return objectMapper.readValue(json, new TypeReference<List<Student>>() {});
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
        return null;
    }

    public void setStudentListToRedis(String key, List<Student> studentList) {
        try {
            ObjectMapper objectMapper = new ObjectMapper();
            String json = objectMapper.writeValueAsString(studentList);
            redisUtil.set(key, json);
        } catch (Exception e) {
            e.printStackTrace();
        }
    }
}




