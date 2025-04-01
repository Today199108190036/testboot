package com.test.testboot.controller;

import ch.qos.logback.classic.Logger;
import jakarta.annotation.Resource;
import com.test.testboot.service.OllamaSpringAIService;
import com.test.testboot.service.impl.OllamaSpringAIServiceImpl;
import org.slf4j.LoggerFactory;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/sringai")
public class OllamaSpringAIController {

    protected static Logger logger= (Logger) LoggerFactory.getLogger(ScoreController.class);

    @Resource
    private OllamaSpringAIService ollamaSpringAIService = new OllamaSpringAIServiceImpl();

    @PostMapping("/select")
    public String select(String str){
        return ollamaSpringAIService.select(str);
    }
}
