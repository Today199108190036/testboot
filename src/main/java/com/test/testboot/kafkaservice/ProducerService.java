package com.test.testboot.kafkaservice;

import ch.qos.logback.classic.Logger;
import jakarta.annotation.Resource;
import org.slf4j.LoggerFactory;
import org.springframework.kafka.core.KafkaTemplate;
import org.springframework.kafka.support.SendResult;
import org.springframework.stereotype.Service;

import java.util.concurrent.CompletableFuture;
import java.util.concurrent.ExecutionException;

@Service
public class ProducerService {

    public static Logger logger= (Logger) LoggerFactory.getLogger(ProducerService.class);

    @Resource
    private KafkaTemplate<String, String> kafkaTemplate;

    public boolean selectKafkaById(String topic, String message) {
        CompletableFuture<SendResult<String, String>> future = kafkaTemplate.send(topic, message);

        try {
            // 阻塞等待发送结果
            SendResult<String, String> result = future.get();
            logger.info(result.getProducerRecord().value());
            return true; // 发送成功返回 true
        } catch (InterruptedException | ExecutionException e) {
            e.printStackTrace();
            return false; // 发送失败返回 false
        }
    }

    public boolean insterKafkaStudent(String topic, String message) {
        CompletableFuture<SendResult<String, String>> future = kafkaTemplate.send(topic, message);

        try {
            // 阻塞等待发送结果
            SendResult<String, String> result = future.get();
            logger.info(result.getProducerRecord().value());
            return true; // 发送成功返回 true
        } catch (InterruptedException | ExecutionException e) {
            e.printStackTrace();
            return false; // 发送失败返回 false
        }
    }
}