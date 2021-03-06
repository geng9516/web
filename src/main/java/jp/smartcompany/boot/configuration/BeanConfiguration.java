package jp.smartcompany.boot.configuration;

import cn.hutool.cache.CacheUtil;
import cn.hutool.cache.impl.LRUCache;
import cn.hutool.cache.impl.TimedCache;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.core.Ordered;
import org.springframework.core.annotation.Order;

/**
 * 系统公共配置
 * @author Xiao Wenpeng
 */
@Configuration
@Order(Ordered.HIGHEST_PRECEDENCE)
public class BeanConfiguration {

    @Bean
    public LRUCache<Object,Object> scCache() {
        return CacheUtil.newLRUCache(4000);
    }

    @Bean
    public TimedCache<String,Object> timedCache() {
        // 数据缓存过期时间默认为10分钟
        TimedCache<String,Object> timedCache = CacheUtil.newTimedCache(1000 * 60 * 10);
        // 设置定时清理的间隔时间为2秒
        timedCache.schedulePrune(1000 * 2);
        return timedCache;
    }

}
