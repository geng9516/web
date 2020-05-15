package jp.smartcompany.job.configuration;

import cn.hutool.db.ds.DSFactory;
import cn.hutool.extra.spring.SpringUtil;
import jp.smartcompany.job.common.Constant;
import jp.smartcompany.job.modules.core.util.PsSession;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.Import;

import javax.servlet.http.HttpSession;
import javax.sql.DataSource;

/**
 * 系统公共配置
 * @author Xiao Wenpeng
 */
@Configuration
@Import(cn.hutool.extra.spring.SpringUtil.class)
@RequiredArgsConstructor(onConstructor = @__(@Autowired))
public class BeanConfiguration {

    private final HttpSession httpSession;

    @Bean
    public DataSource dataSource() {
        return DSFactory.get();
    }

    @Bean
    public PsSession getSession() {
        return (PsSession)httpSession.getAttribute(Constant.PS_SESSION);
    }

}
