package com.mms.reporting.service.controller;

import com.mms.reporting.service.helper.ApiResponse;
import org.springframework.web.bind.annotation.GetMapping;

public class HelloWorldController {
    ApiResponse<String> apiResponse = new ApiResponse<>();
    @GetMapping("/hello")
    public String seyHello(){
        apiResponse.setMessage("Hello World");
//        apiResponse.setStatus(200);
        return apiResponse.toString();
    }
}
