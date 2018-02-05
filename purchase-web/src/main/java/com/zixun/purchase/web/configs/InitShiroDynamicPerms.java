package com.zixun.purchase.web.configs;

import com.zixun.purchase.core.shiro.DynamicPermsManager;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.ApplicationArguments;
import org.springframework.boot.ApplicationRunner;
import org.springframework.context.annotation.Configuration;

/**
 * @description: spring boot加载完容器后，但还没有执行程序自定义的方法时，也可以回调指定的方法
 * 完成shiro权限控制
 * @author: tiger
 * @date: 18-1-21 下午6:33
 * @version: V1.0
 * @modified by:
 */
@Configuration
public class InitShiroDynamicPerms implements ApplicationRunner {
    @Autowired
    private DynamicPermsManager dynamicPermsManager;

    @Override
    public void run(ApplicationArguments applicationArguments) throws Exception {
        dynamicPermsManager.updatePermission();
    }
}
