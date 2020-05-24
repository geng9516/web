package jp.smartcompany.job.configuration;

import cn.hutool.db.ds.DSFactory;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.Import;

import javax.sql.DataSource;

/**
 * 系统公共配置
 * @author Xiao Wenpeng
 */
@Configuration
@Import(cn.hutool.extra.spring.SpringUtil.class)
@RequiredArgsConstructor(onConstructor = @__(@Autowired))
public class BeanConfiguration {

    @Bean
    public DataSource dataSource() {
        return DSFactory.get();
    }

}
