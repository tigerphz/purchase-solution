package com.zixun.purchase;

import com.zixun.purchase.utils.security.SnowflakeIdWorker;
import com.zixun.purchase.web.configs.SnowflakeIdProperty;
import org.mybatis.spring.annotation.MapperScan;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.context.properties.EnableConfigurationProperties;
import org.springframework.cache.annotation.EnableCaching;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.ComponentScan;
import org.springframework.context.annotation.Scope;
import org.springframework.transaction.annotation.EnableTransactionManagement;

/**
 * @description:
 * @author: tiger
 * @date: 18-1-10 下午10:16
 * @version: V1.0
 * @modified by:
 */
@SpringBootApplication
@EnableTransactionManagement
@MapperScan("com.zixun.purchase.persist.daomapper")
@EnableConfigurationProperties(SnowflakeIdProperty.class)
public class PurchaseWebApplication {

    public static void main(String[] args) {
        SpringApplication.run(PurchaseWebApplication.class, args);
    }

    @Autowired
    private SnowflakeIdProperty snowflakeIdProperty;

    @Bean
    @Scope("singleton")
    public SnowflakeIdWorker getSnowflakeIdWorker() {
        return new SnowflakeIdWorker(snowflakeIdProperty.getWorkerId(), snowflakeIdProperty.getDatacenterId());
    }
}
