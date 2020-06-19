package jp.smartcompany.boot.configuration;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.thymeleaf.templateresolver.FileTemplateResolver;
import org.thymeleaf.templateresolver.ITemplateResolver;

/**
 * @author Xiao Wenpeng
 */
@Configuration
public class ThymeleafConfiguration {

    @Bean
    public ITemplateResolver defaultTemplateResolver() {
        FileTemplateResolver resolver = new FileTemplateResolver();
        resolver.setSuffix(".html");
        resolver.setPrefix("src/main/resources/templates/");
        resolver.setCacheable(false);
        return resolver;
    }

}
