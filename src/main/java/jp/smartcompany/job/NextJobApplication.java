package jp.smartcompany.job;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;

/**
 * 系统启动类
 * @author Xiao Wenpeng
 */
@SpringBootApplication(scanBasePackages = {
   "jp.smartcompany.boot",
   "jp.smartcompany.job",
   "jp.smartcompany.framework",
   "jp.smartcompany.controller",
   "jp.smartcompany.admin"
})
public class NextJobApplication {

    public static void main(String[] args) {
        SpringApplication.run(NextJobApplication.class, args);
    }

}
