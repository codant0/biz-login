package com.bgtech.bizlogin;

import com.bgtech.bizlogin.db.mapper.UserMapper;
import com.bgtech.bizlogin.db.module.LoginUser;
import com.bgtech.bizlogin.service.UserService;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit4.SpringRunner;


/**
 * @author HuangJiefeng
 * @date 2020/9/18 0018 11:43
 */
@SpringBootTest
@RunWith(SpringRunner.class)
public class UserTest {

    @Autowired
    private UserService userService;

    @Test
    public void getUserByName() {
        LoginUser zhangsan = userService.getUserByName("xiaoming");
        System.out.println(zhangsan);
    }

    @Test
    public void getUserByPhone() {
        LoginUser user = userService.getUserByPhone("15501918879");
        System.out.println(user);
    }

    @Test
    public void register() {
        LoginUser user = new LoginUser();
        user.setUsername("xm");
        user.setPassword("123456");
        userService.register(user);
    }
}
