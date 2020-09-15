package jp.smartcompany.boot.configuration;

import cn.hutool.cache.CacheUtil;
import cn.hutool.cache.impl.LRUCache;
import cn.hutool.cache.impl.TimedCache;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.Import;

/**
 * 系统公共配置
 * @author Xiao Wenpeng
 */
@Configuration
@Import(cn.hutool.extra.spring.SpringUtil.class)
@RequiredArgsConstructor(onConstructor = @__(@Autowired))
public class BeanConfiguration {

    @Bean
    public LRUCache<Object,Object> scCache() {
        return CacheUtil.newLRUCache(4000);
    }

    @Bean
    public TimedCache<String,Object> timedCache() {
        TimedCache<String,Object> timedCache = CacheUtil.newTimedCache(1000 * 30 * 10);
        // 设置定时清理的间隔时间为2秒
        timedCache.schedulePrune(1000 * 2);
        return timedCache;
    }

}
