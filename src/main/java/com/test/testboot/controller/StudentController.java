package com.test.testboot.controller;

import ch.qos.logback.classic.Logger;
import com.alibaba.fastjson.JSONObject;
import com.test.testboot.common.Result;
import com.test.testboot.entity.Student;
import com.test.testboot.kafkaservice.ProducerService;
import com.test.testboot.service.StudentService;
import com.test.testboot.service.impl.StudentServiceImpl;
import io.swagger.v3.oas.annotations.ExternalDocumentation;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.Parameter;
import io.swagger.v3.oas.annotations.media.Content;
import io.swagger.v3.oas.annotations.media.Schema;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.tags.Tag;
import jakarta.annotation.Resource;
import org.slf4j.LoggerFactory;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

@RestController
@RequestMapping("/student")
@Tag(name = "StudentControllerAPI", description = "学生控制器接口"
        , externalDocs = @ExternalDocumentation(description = "这是一个接口文档介绍"))
public class StudentController {
    protected static Logger logger= (Logger) LoggerFactory.getLogger(StudentController.class);

    @Resource
    private StudentService studentService = new StudentServiceImpl();

    @Resource
    private ProducerService producerService = new ProducerService();

    @Operation(
            summary = "查询所有学生信息", description = "查询所有学生信息，并返回响应结果信息",
            responses = {
                    @ApiResponse(
                            responseCode = "200",
                            description = "响应成功",
                            content = @Content(
                                    mediaType = "application/json",
                                    schema = @Schema(
                                            title = "Resul和Student组合模型",
                                            description = "返回实体，Result内data为Student模型",
                                            anyOf = {Result.class, Student.class}
                                    )
                            )
                    ),
                    @ApiResponse(
                            responseCode = "500",
                            description = "响应失败",
                            content = @Content(
                                    mediaType = "application/json",
                                    schema = @Schema(
                                            title = "Resul模型",
                                            description = "返回实体，Result内 data为空",
                                            implementation = Result.class
                                    )
                            )
                    )
            }
    )
    @GetMapping("/selectAll")
    public Result selectAll(){
        List<Student> list = studentService.selectAll();
        logger.info(list.toString());
        return Result.success(list);
    }

    @Operation(
            summary = "根据Id查询学生信息", description = "根据ID查询学生信息，并返回响应结果信息",
            parameters = {
                    @Parameter(name = "id", description = "学生ID", required = true, example = "1")
            },
            responses = {
                    @ApiResponse(
                            responseCode = "200",
                            description = "响应成功",
                            content = @Content(
                                    mediaType = "application/json",
                                    schema = @Schema(
                                            title = "Resul和StudentVO组合模型",
                                            description = "返回实体，AjaxResult内data为StudentVO模型",
                                            anyOf = {Result.class, Student.class}
                                    )
                            )
                    ),
                    @ApiResponse(
                            responseCode = "500",
                            description = "响应失败",
                            content = @Content(
                                    mediaType = "application/json",
                                    schema = @Schema(
                                            title = "Resul模型",
                                            description = "返回实体，Result内 data为空",
                                            implementation = Result.class
                                    )
                            )
                    )
            }
    )
    @GetMapping("/selectById/{id}")
    public Result selectById(@PathVariable(value = "id") int id){
        List<Student> list = studentService.selectById(id);
        logger.info(list.toString());
        return Result.success(list);
    }

    @Operation(
            summary = "根据Id查询redis学生信息", description = "根据ID查询redis学生信息，并返回响应结果信息",
            parameters = {
                    @Parameter(name = "id", description = "学生ID", required = true, example = "1")
            },
            responses = {
                    @ApiResponse(
                            responseCode = "200",
                            description = "响应成功",
                            content = @Content(
                                    mediaType = "application/json",
                                    schema = @Schema(
                                            title = "Resul和StudentVO组合模型",
                                            description = "返回实体，Result内data为Student模型",
                                            anyOf = {Result.class, Student.class}
                                    )
                            )
                    ),
                    @ApiResponse(
                            responseCode = "500",
                            description = "响应失败",
                            content = @Content(
                                    mediaType = "application/json",
                                    schema = @Schema(
                                            title = "Resul模型",
                                            description = "返回实体，Result内 data为空",
                                            implementation = Result.class
                                    )
                            )
                    )
            }
    )
    @GetMapping("/findById/{id}")
    public Result findById(@PathVariable(value = "id") int id){
        List<Student> list = studentService.findById(id);
        if(list.size() == 0){
            return Result.success(null);
        } else {
            logger.info(list.toString());
            return Result.success(list);
        }
    }

    @Operation(
            summary = "根据Id删除学生信息", description = "根据ID删除学生信息，并返回响应结果信息",
            parameters = {
                    @Parameter(name = "id", description = "学生ID", required = true, example = "1")
            },
            responses = {
                    @ApiResponse(
                            responseCode = "200",
                            description = "响应成功",
                            content = @Content(
                                    mediaType = "application/json",
                                    schema = @Schema(
                                            title = "状态码",
                                            description = "返回实体，Result内data为空",
                                            anyOf = {Result.class, Student.class}
                                    )
                            )
                    ),
                    @ApiResponse(
                            responseCode = "500",
                            description = "响应失败",
                            content = @Content(
                                    mediaType = "application/json",
                                    schema = @Schema(
                                            title = "Resul模型",
                                            description = "返回实体，Result内 data为空",
                                            implementation = Result.class
                                    )
                            )
                    )
            }
    )
    @DeleteMapping("/deleteById/{id}")
    public Result deleteById(@PathVariable(value = "id") int id){
        if(studentService.deleteById(id)){
            return Result.success(true);
        }
        return Result.success(false);
    }

    @Operation(
            summary = "根据Id更新学生地址", description = "根据ID更新学生地址，并返回响应结果信息",
            parameters = {
                    @Parameter(name = "id", description = "学生ID", required = true, example = "1"),
                    @Parameter(name = "address", description = "地址", required = true, example = "四川省成都市")
            },
            responses = {
                    @ApiResponse(
                            responseCode = "200",
                            description = "响应成功",
                            content = @Content(
                                    mediaType = "application/json",
                                    schema = @Schema(
                                            title = "状态码",
                                            description = "返回实体，Result内data为空",
                                            anyOf = {Result.class, Student.class}
                                    )
                            )
                    ),
                    @ApiResponse(
                            responseCode = "500",
                            description = "响应失败",
                            content = @Content(
                                    mediaType = "application/json",
                                    schema = @Schema(
                                            title = "Resul模型",
                                            description = "返回实体，Result内 data为空",
                                            implementation = Result.class
                                    )
                            )
                    )
            }
    )
    @PostMapping("/updateById/{id}")
    public Result updateById(@PathVariable(value = "id") int id, String address){
        if(studentService.updateById(id, address)){
            return Result.success(true);
        }
        return Result.success(false);
    }

    @Operation(
            summary = "根据Id更新学生所有信息", description = "根据ID更新学生所有信息，并返回响应结果信息",
            responses = {
                    @ApiResponse(
                            responseCode = "200",
                            description = "响应成功",
                            content = @Content(
                                    mediaType = "application/json",
                                    schema = @Schema(
                                            title = "状态码",
                                            description = "返回实体，Result内data为空",
                                            anyOf = {Result.class, Student.class}
                                    )
                            )
                    ),
                    @ApiResponse(
                            responseCode = "500",
                            description = "响应失败",
                            content = @Content(
                                    mediaType = "application/json",
                                    schema = @Schema(
                                            title = "Resul模型",
                                            description = "返回实体，Result内 data为空",
                                            implementation = Result.class
                                    )
                            )
                    )
            }
    )
    @PostMapping("/updateByIdAll")
    public Result updateByIdAll(@RequestBody Student student){
        if(studentService.updateByIdAll(student)){
            return Result.success(true);
        }
        return Result.success(false);
    }

    @Operation(
            summary = "插入学生信息", description = "插入学生信息，并返回响应结果信息",
            responses = {
                    @ApiResponse(
                            responseCode = "200",
                            description = "响应成功",
                            content = @Content(
                                    mediaType = "application/json",
                                    schema = @Schema(
                                            title = "状态码",
                                            description = "返回实体，Result内data为空",
                                            anyOf = {Result.class, Student.class}
                                    )
                            )
                    ),
                    @ApiResponse(
                            responseCode = "500",
                            description = "响应失败",
                            content = @Content(
                                    mediaType = "application/json",
                                    schema = @Schema(
                                            title = "Resul模型",
                                            description = "返回实体，Result内 data为空",
                                            implementation = Result.class
                                    )
                            )
                    )
            }
    )
    @PutMapping("/insertById")
    public Result insertById(@RequestBody Student student){
        if(studentService.insertById(student)){
            return Result.success(true);
        }
        return Result.success(false);
    }

    @GetMapping("/selectKafkaById/{id}")
    public Result selectKafkaById(@PathVariable(value = "id") int id){
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
    public Result insterKafkaStudent(@RequestBody Student student){
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
