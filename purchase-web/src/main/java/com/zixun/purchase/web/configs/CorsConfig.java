package com.zixun.purchase.web.configs;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.context.annotation.Configuration;
import org.springframework.web.servlet.config.annotation.CorsRegistry;
import org.springframework.web.servlet.config.annotation.WebMvcConfigurerAdapter;

/**
 * @description: 注册跨域访问配置
 * @author: tiger
 * @date: 18-1-21 下午8:08
 * @version: V1.0
 * @modified by:
 */
@Configuration
public class CorsConfig extends WebMvcConfigurerAdapter {
    Logger logger = LoggerFactory.getLogger(CorsConfig.class);

    @Override
    public void addCorsMappings(CorsRegistry registry) {
        logger.info("注册跨域访问配置");
        registry.addMapping("/api/**")
                .allowedOrigins("*")
                .allowCredentials(true)
                .allowedMethods("GET", "POST", "DELETE", "PUT")
                .maxAge(3600);
    }
}
