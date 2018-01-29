package com.zixun.purchase.web.controllers;

import com.alibaba.fastjson.JSON;
import com.zixun.purchase.PurchaseWebApplication;
import com.zixun.purchase.core.shiro.AuthRealm;
import com.zixun.purchase.model.vo.UserVO;
import com.zixun.purchase.utils.security.SnowflakeIdWorker;
import org.apache.shiro.SecurityUtils;
import org.apache.shiro.crypto.hash.Md5Hash;
import org.apache.shiro.mgt.SecurityManager;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.http.MediaType;
import org.springframework.test.context.junit4.SpringRunner;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.request.MockMvcRequestBuilders;
import org.springframework.test.web.servlet.result.MockMvcResultHandlers;
import org.springframework.test.web.servlet.result.MockMvcResultMatchers;
import org.springframework.test.web.servlet.setup.MockMvcBuilders;
import org.springframework.web.context.WebApplicationContext;

import java.util.UUID;

/**
 * @description:
 * @author: tiger
 * @date: 18-1-19 下午2:25
 * @version: V1.0
 * @modified by:
 */
@RunWith(SpringRunner.class)
@SpringBootTest(classes = PurchaseWebApplication.class)
@AutoConfigureMockMvc
public class UserControllerTest {
    private MockMvc mvc;

    @Autowired
    private WebApplicationContext wac;

    @Autowired
    private SecurityManager securityManager;

    @Autowired
    private SnowflakeIdWorker snowflakeIdWorker;

    @Before
    public void setup() {
        mvc = MockMvcBuilders.webAppContextSetup(wac).build();
        SecurityUtils.setSecurityManager(securityManager);
    }

    @Test
    public void addUser() throws Exception {
        UserVO user = new UserVO();

        user.setUsername("admin");
        user.setNickname("系统管理员");
        user.setPassword("123123");

        long id = snowflakeIdWorker.nextId();
        String salt = UUID.randomUUID().toString();
        String md5pwd = new Md5Hash("123123", salt, AuthRealm.HASHITERATIONS).toString();

        mvc.perform(MockMvcRequestBuilders.post("/api/users/add")
                .contentType(MediaType.APPLICATION_JSON)
                .content(JSON.toJSONString(user)))
                .andExpect(MockMvcResultMatchers.status().isOk())
                .andDo(MockMvcResultHandlers.print())
                .andReturn();
    }
}
