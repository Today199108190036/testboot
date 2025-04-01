package com.test.testboot.service.impl;

import com.test.testboot.service.OllamaSpringAIService;
import jakarta.annotation.Resource;
import org.springframework.ai.ollama.OllamaChatModel;
import org.springframework.stereotype.Service;

@Service
public class OllamaSpringAIServiceImpl implements OllamaSpringAIService {

    @Resource
    private OllamaChatModel ollamaChatModel;

    @Override
    public String select(String message) {
        String prompt = """
                你是一个精通中文和英文的翻译大师。如果我给你英文就翻译成中文，给你中文就翻译成英文。
                """;
//        String message = """
//                Difficult times show us who our true friends are.
//                """;
        return ollamaChatModel.call(prompt + ":" + message);
    }
}
