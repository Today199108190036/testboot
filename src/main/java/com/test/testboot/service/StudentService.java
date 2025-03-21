package com.test.testboot.service;

import com.test.testboot.entity.Student;
import com.baomidou.mybatisplus.extension.service.IService;

import java.util.List;

/**
* @author zhaoxin
* @description 针对表【student】的数据库操作Service
* @createDate 2025-03-20 19:59:35
*/
public interface StudentService extends IService<Student> {
    List<Student> selectAll();

    List<Student> selectById(int id);

    boolean deleteById(int id);

    boolean updateById(int id, String address);

    boolean updateByIdAll(int id, String name, String sex, int birth, String department, String address);

    boolean insertById(Student student);
}
