package com.bgtech.bizlogin.controller;

import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

/**
 * @author HuangJiefeng
 * @date 2020/9/21 0021 10:50
 */
@RestController
@RequestMapping("/user")
public class UserController {

    @PostMapping("/index")
    public String index() {
        return "index";
    }
}
