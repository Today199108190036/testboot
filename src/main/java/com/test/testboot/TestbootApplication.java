package com.test.testboot;

import com.test.testboot.service.DateService;
import org.mybatis.spring.annotation.MapperScan;
import org.springframework.ai.tool.ToolCallbackProvider;
import org.springframework.ai.tool.method.MethodToolCallbackProvider;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.annotation.Bean;

@SpringBootApplication
@MapperScan("com.test.testboot.mapper")
public class TestbootApplication {

    public static void main(String[] args) {
        SpringApplication.run(TestbootApplication.class, args);
    }

    @Bean
    public ToolCallbackProvider addressDateTools(DateService dateService) {
        return MethodToolCallbackProvider.builder()
                .toolObjects(dateService).build();
    }

}
