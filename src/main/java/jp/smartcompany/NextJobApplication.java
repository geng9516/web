package jp.smartcompany;

import jp.smartcompany.boot.util.SpringUtil;
import jp.smartcompany.job.modules.core.service.IMastSystemService;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.context.event.ApplicationReadyEvent;
import org.springframework.context.ApplicationListener;

/**
 * 系统启动类
 * @author Xiao Wenpeng
 */
@SpringBootApplication
public class NextJobApplication {

    public static void main(String[] args) {
        SpringApplication springApplication = new SpringApplication(NextJobApplication.class);
        // 预加载系统公共配置到缓存中
        springApplication.addListeners((ApplicationListener<ApplicationReadyEvent>) event -> {
            IMastSystemService systemService = SpringUtil.getBean(IMastSystemService.class);
            systemService.getTableInfo();
            systemService.selectSearchRangeInfo();
            systemService.getByLang("ja");
        });
        springApplication.run(args);
    }

}
