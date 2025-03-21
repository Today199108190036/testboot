package com.test.testboot.service.impl;

import ch.qos.logback.classic.Logger;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.test.testboot.entity.Student;
import com.test.testboot.service.StudentService;
import com.test.testboot.mapper.StudentMapper;
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

    @Override
    public List<Student> selectAll() {
        return studentMapper.selectAll();
    }

    @Override
    public List<Student> selectById(int id) {
        return studentMapper.selectById(id);
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
}




