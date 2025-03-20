package com.test.testboot.controller;

import ch.qos.logback.classic.Logger;
import com.test.testboot.common.Result;
import com.test.testboot.entity.Score;
import com.test.testboot.service.ScoreService;
import com.test.testboot.service.impl.ScoreServiceImpl;
import jakarta.annotation.Resource;
import org.slf4j.LoggerFactory;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/score")
public class ScoreController {
    protected static Logger logger= (Logger) LoggerFactory.getLogger(ScoreController.class);

    @Resource
    private ScoreService scoreService = new ScoreServiceImpl();

    @GetMapping("/selectAll")
    public Result selectAll(){
        List<Score> list = scoreService.selectAll();
        logger.info(list.toString());
        return Result.success(list);
    }

    @GetMapping("/selectById")
    public Result selectById(int id){
        List<Score> list = scoreService.selectById(id);
        logger.info(list.toString());
        return Result.success(list);
    }

    @DeleteMapping("/deleteById")
    public Result deleteById(int id){
        if(scoreService.deleteById(id)){
            return Result.success(true);
        }
        return Result.success(false);
    }

    @PostMapping("/updateById")
    public Result updateById(int id, int grade){
        if(scoreService.updateById(id, grade)){
            return Result.success(true);
        }
        return Result.success(false);
    }

    @PostMapping("/updateByIdAll")
    public Result updateByIdAll(int id, int stuId, String cName, int grade){
        if(scoreService.updateByIdAll(id, stuId, cName, grade)){
            return Result.success(true);
        }
        return Result.success(false);
    }

    @PutMapping("/insertById")
    public Result insertById(Score score){
        if(scoreService.insertById(score)){
            return Result.success(true);
        }
        return Result.success(false);
    }
}
