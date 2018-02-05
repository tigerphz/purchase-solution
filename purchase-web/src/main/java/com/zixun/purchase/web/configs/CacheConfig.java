package com.zixun.purchase.web.configs;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.cache.annotation.EnableCaching;
import org.springframework.cache.ehcache.EhCacheCacheManager;
import org.springframework.cache.ehcache.EhCacheManagerFactoryBean;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.core.io.ClassPathResource;

/**
 * @description: proxyTargetClass=true表示：当需要代理的类是一个接口或者是一个动态生成的代理类时使用JdkProxy代理；
 * 而当要代理的类是一个具体类时，使用cglib来代理。假如不设置该属性，则默认使用JdkProxy代理，
 * 而JdkProxy能够代理的类必须实现接口，因此如果想要一个没实现接口的类被代理，
 * 就必须设置proxyTargetClass = true来使用cglib完成代理。
 * @author: tiger
 * @date: 18-2-1 下午4:58
 * @version: V1.0
 * @modified by:
 */
@Configuration
@EnableCaching(proxyTargetClass = true)
public class CacheConfig {
    /**
     * ehcache 主要的管理器
     *
     * @param bean
     * @return
     */
    @Bean
    @Autowired
    public EhCacheCacheManager ehCacheCacheManager(EhCacheManagerFactoryBean bean) {
        return new EhCacheCacheManager(bean.getObject());
    }

    /**
     * 据shared与否的设置,
     * Spring分别通过CacheManager.create()
     * 或new CacheManager()方式来创建一个ehcache基地.
     * 也说是说通过这个来设置cache的基地是这里的Spring独用,还是跟别的(如hibernate的Ehcache共享)
     *
     * @return
     */
    @Bean
    public EhCacheManagerFactoryBean ehCacheManagerFactoryBean() {
        EhCacheManagerFactoryBean cacheManagerFactoryBean = new EhCacheManagerFactoryBean();
        cacheManagerFactoryBean.setConfigLocation(new ClassPathResource("ehcache.xml"));
        cacheManagerFactoryBean.setShared(true);
        return cacheManagerFactoryBean;
    }
}
