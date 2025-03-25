package com.test.testboot.kafkaservice;

import com.alibaba.fastjson.JSON;
import com.alibaba.fastjson.JSONObject;
import com.test.testboot.entity.Student;
import com.test.testboot.service.StudentService;
import com.test.testboot.service.impl.StudentServiceImpl;
import jakarta.annotation.Resource;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.kafka.annotation.KafkaListener;
import org.springframework.stereotype.Service;

/**
 * @author lzx
 */
@Service
public class ConsumerService {

    private static Logger logger = LoggerFactory.getLogger(ConsumerService.class);

    @Resource
    private StudentService studentService = new StudentServiceImpl();

    @KafkaListener(topics = "testtopic")
    public void consumeMessage(String message) {
        try {
            // 解析消息
            JSONObject jsonMessage = JSON.parseObject(message);
            String messageType = jsonMessage.getString("messageType");
            String data = jsonMessage.getString("data");

            // 根据消息类型处理数据
            switch (messageType) {
                case "JSON":
                    processJsonData(data);
                    break;
                case "AVRO":
                    processAvroData(data);
                    break;
                default:
                    System.err.println("Unknown message type: " + messageType);
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    private void processJsonData(String jsonData) {
        // 处理 JSON 数据
        logger.info(jsonData);
        Student student = JSON.parseObject(jsonData, Student.class);
        studentService.insertById(student);
    }

    private void processAvroData(String avroData) {
        // 处理 Avro 数据
        logger.info(avroData);
        JSONObject jsonMessage = JSON.parseObject(avroData);
        studentService.selectById(Integer.parseInt(jsonMessage.getString("id")));
    }
}