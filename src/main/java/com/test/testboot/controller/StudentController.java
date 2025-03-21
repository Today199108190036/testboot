package com.test.testboot.controller;

import ch.qos.logback.classic.Logger;
import com.test.testboot.common.Result;
import com.test.testboot.entity.Student;
import com.test.testboot.service.StudentService;
import com.test.testboot.service.impl.StudentServiceImpl;
import jakarta.annotation.Resource;
import org.slf4j.LoggerFactory;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/student")
public class StudentController {
    protected static Logger logger= (Logger) LoggerFactory.getLogger(StudentController.class);

    @Resource
    private StudentService studentService = new StudentServiceImpl();

    @GetMapping("/selectAll")
    public Result selectAll(){
        List<Student> list = studentService.selectAll();
        logger.info(list.toString());
        return Result.success(list);
    }

    @GetMapping("/selectById")
    public Result selectById(int id){
        List<Student> list = studentService.selectById(id);
        logger.info(list.toString());
        return Result.success(list);
    }

    @DeleteMapping("/deleteById")
    public Result deleteById(int id){
        if(studentService.deleteById(id)){
            return Result.success(true);
        }
        return Result.success(false);
    }

    @PostMapping("/updateById")
    public Result updateById(int id, String address){
        if(studentService.updateById(id, address)){
            return Result.success(true);
        }
        return Result.success(false);
    }

    @PostMapping("/updateByIdAll")
    public Result updateByIdAll(int id, String name, String sex, int birth, String department, String address){
        if(studentService.updateByIdAll(id, name, sex, birth, department, address)){
            return Result.success(true);
        }
        return Result.success(false);
    }
//
//    @PutMapping("/insertById")
//    public Result insertById(Score score){
//        if(scoreService.insertById(score)){
//            return Result.success(true);
//        }
//        return Result.success(false);
//    }
}
