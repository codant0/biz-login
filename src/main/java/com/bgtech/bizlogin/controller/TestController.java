package com.bgtech.bizlogin.controller;

import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

/**
 * @author HuangJiefeng
 * @date 2020/9/19 0019 9:57
 */
@RequestMapping("/test")
@RestController
public class TestController {

    @RequestMapping("/test")
    public String test() {
        return "无限制访问";
    }
}
