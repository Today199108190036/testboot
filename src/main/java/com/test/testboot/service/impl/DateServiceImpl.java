package com.test.testboot.service.impl;


import com.test.testboot.service.DateService;
import com.test.testboot.utils.AddressRequest;
import com.test.testboot.utils.DateResponse;
import org.springframework.ai.tool.annotation.Tool;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;

@Service
public class DateServiceImpl implements DateService {
    @Override
    @Tool(description = "获取指定地点的当前时间")
    public DateResponse getAddressDate(AddressRequest request) {
        String result = String.format("%s的当前时间是%s",
                request.getAddress(),
                LocalDateTime.now().format(DateTimeFormatter.ofPattern("yyyy-MM-dd HH:mm:ss")));
        return new DateResponse(result);
    }
}
