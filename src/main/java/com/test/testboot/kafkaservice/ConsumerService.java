package com.test.testboot.kafkaservice;

import com.alibaba.fastjson.JSON;
import com.alibaba.fastjson.JSONArray;
import com.alibaba.fastjson.JSONObject;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.test.testboot.entity.Student;
import com.test.testboot.service.StudentService;
import com.test.testboot.service.impl.StudentServiceImpl;
import com.test.testboot.utils.RedisUtil;
import jakarta.annotation.Resource;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.kafka.annotation.KafkaListener;
import org.springframework.stereotype.Service;
import top.javatool.canal.client.context.CanalContext;
import top.javatool.canal.client.model.CanalModel;

/**
 * @author lzx
 */
@Service
public class ConsumerService {

    private static Logger logger = LoggerFactory.getLogger(ConsumerService.class);

    @Resource
    private StudentService studentService = new StudentServiceImpl();

    @Resource
    private RedisUtil redisUtil;

    @KafkaListener(topics = "testtopic")
    public void consumeMessage(String message) {
        try {
            // 解析消息
            JSONObject jsonMessage = JSON.parseObject(message);
            if(jsonMessage.getString("messageType") != null){
                String messageType = jsonMessage.getString("messageType");
                String data = jsonMessage.getString("data");
                logger.info(data);
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
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    @KafkaListener(topics = "testtopic2")
    public void canalconsumeMessage(String message) {
        try {
            CanalModel model = CanalContext.getModel();
            logger.info("CanalModel: " + model);
            // 解析消息
            JSONObject jsonMessage = JSON.parseObject(message);
            String messageType = jsonMessage.getString("messageType");
            String data = jsonMessage.getString("data");
            // 打印日志
            logger.info("canalconsumeMessage: " + jsonMessage.toJSONString());
            // 解析 data 字符串为 JSON 数组
            JSONArray dataArray = JSON.parseArray(data);
            if (dataArray != null && !dataArray.isEmpty()) {
                // 获取第一个对象
                JSONObject studentObject = dataArray.getJSONObject(0);
                // 提取 id 的值
                String id = studentObject.getString("id");
                // 将数据存储到 Redis
                redisUtil.set("student:id" + id, data);
            } else {
                logger.warn("data 数组为空或无效");
            }
        } catch (Exception e) {
            logger.error("处理消息时发生错误: ", e);
        }
    }

    private void processAvroData(String avroData) {
        // 处理 Avro 数据
        logger.info(avroData);
        JSONObject jsonMessage = JSON.parseObject(avroData);
        studentService.selectById(Integer.parseInt(jsonMessage.getString("id")));
    }

    private void processJsonData(String jsonData) {
        // 处理 JSON 数据
        logger.info(jsonData);
        Student student = JSON.parseObject(jsonData, Student.class);
        studentService.insertById(student);
    }

}