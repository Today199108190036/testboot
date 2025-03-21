package com.test.testboot.mapper;

import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import com.test.testboot.entity.Student;

import java.util.List;

/**
* @author zhaoxin
* @description 针对表【student】的数据库操作Mapper
* @createDate 2025-03-20 19:59:35
* @Entity generator.entity.Student
*/
public interface StudentMapper extends BaseMapper<Student> {
    List<Student> selectAll();

    List<Student> selectById(int id);

    boolean deleteById(int id);

    boolean updateById(int id, String address);

    boolean updateByIdAll(int id, String name, String sex, int birth, String department, String address);
}




