package com.test.testboot.controller;

import ch.qos.logback.classic.Logger;
import com.alibaba.fastjson.JSONObject;
import com.test.testboot.common.Result;
import com.test.testboot.entity.Student;
import com.test.testboot.kafkaservice.ProducerService;
import com.test.testboot.service.StudentService;
import com.test.testboot.service.impl.StudentServiceImpl;
import jakarta.annotation.Resource;
import org.slf4j.LoggerFactory;
import org.springframework.web.bind.annotation.*;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

@RestController
@RequestMapping("/student")
public class StudentController {
    protected static Logger logger= (Logger) LoggerFactory.getLogger(StudentController.class);

    @Resource
    private StudentService studentService = new StudentServiceImpl();

    @Resource
    private ProducerService producerService = new ProducerService();

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

    @GetMapping("/findById")
    public Result findById(int id){
        List<Student> list = studentService.findById(id);
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

    @PutMapping("/insertById")
    public Result insertById(Student student){
        if(studentService.insertById(student)){
            return Result.success(true);
        }
        return Result.success(false);
    }

    @GetMapping("/selectKafkaById")
    public Result selectKafkaById(int id){
        Map<String, Object> map = new HashMap<>();
        map.put("messageType", "AVRO");
        Map<String, Object> studentmap = new HashMap<>();
        studentmap.put("id", id);
        map.put("data", studentmap);
        String message = JSONObject.toJSONString(map);
        logger.info(message);
        if(producerService.selectKafkaById("testtopic", message)){
            return Result.success(true);
        }
        return Result.success(false);
    }

    @PutMapping("/insterKafkaStudent")
    public Result insterKafkaStudent(Student student){
        Map<String, Object> map = new HashMap<>();
        map.put("messageType", "JSON");
        Map<String, Object> studentmap = new HashMap<>();
        studentmap.put("id", student.getId());
        studentmap.put("name", student.getName());
        studentmap.put("sex", student.getSex());
        studentmap.put("birth", student.getBirth());
        studentmap.put("department", student.getDepartment());
        studentmap.put("address", student.getAddress());
        map.put("data", studentmap);
        String message = JSONObject.toJSONString(map);
        logger.info(message);
        if(producerService.insterKafkaStudent("testtopic", message)){
            return Result.success(true);
        }
        return Result.success(false);
    }

}
