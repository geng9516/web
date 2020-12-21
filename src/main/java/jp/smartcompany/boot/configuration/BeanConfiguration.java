package jp.smartcompany.boot.configuration;

import cn.hutool.cache.CacheUtil;
import cn.hutool.cache.impl.LRUCache;
import cn.hutool.cache.impl.TimedCache;
import com.fasterxml.jackson.annotation.JsonInclude;
import com.fasterxml.jackson.databind.DeserializationFeature;
import com.fasterxml.jackson.databind.MapperFeature;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.databind.SerializationFeature;
import com.fasterxml.jackson.databind.module.SimpleModule;
import com.fasterxml.jackson.databind.ser.std.ToStringSerializer;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.Primary;
import org.springframework.core.annotation.Order;

import java.text.SimpleDateFormat;
import java.util.TimeZone;

/**
 * 系统公共配置
 * @author Xiao Wenpeng
 */
@Configuration
@Order(1)
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

    @Bean
    @Primary
    public ObjectMapper objectMapper(){
        ObjectMapper objectMapper = new ObjectMapper();
        objectMapper.configure(DeserializationFeature.FAIL_ON_UNKNOWN_PROPERTIES, false);
        objectMapper.configure(SerializationFeature.FAIL_ON_EMPTY_BEANS,false);
        objectMapper.configure(SerializationFeature.WRITE_DATES_AS_TIMESTAMPS,false);
        objectMapper.configure(MapperFeature.ACCEPT_CASE_INSENSITIVE_PROPERTIES,true);
        objectMapper.setDefaultPropertyInclusion(JsonInclude.Include.ALWAYS);
        objectMapper.setTimeZone(TimeZone.getTimeZone("Asia/Tokyo"));
        objectMapper.setDateFormat(new SimpleDateFormat("yyyy/MM/dd HH:mm:ss"));
        objectMapper.setSerializationInclusion(JsonInclude.Include.NON_NULL);
        SimpleModule simpleModule = new SimpleModule();
        simpleModule.addSerializer(Long.class, ToStringSerializer.instance);
        simpleModule.addSerializer(Long.TYPE, ToStringSerializer.instance);
        objectMapper.registerModule(simpleModule);

        return objectMapper;
    }

}
