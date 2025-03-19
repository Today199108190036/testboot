package com.test.testboot.controller;

import ch.qos.logback.classic.Logger;
import com.test.testboot.common.Result;
import com.test.testboot.entity.Score;
import com.test.testboot.service.ScoreService;
import com.test.testboot.service.impl.ScoreServiceImpl;
import jakarta.annotation.Resource;
import org.slf4j.LoggerFactory;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

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
}
