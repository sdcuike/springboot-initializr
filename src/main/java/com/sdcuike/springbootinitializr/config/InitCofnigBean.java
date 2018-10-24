package com.sdcuike.springbootinitializr.config;

import com.sdcuike.springbootinitializr.util.GitLabApi;
import com.sdcuike.springbootinitializr.util.ProjectGenerator;
import com.sdcuike.springbootinitializr.util.TemplateRenderer;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

/**
 * 工具类初始化
 *
 * @author sdcuike
 * @date 2018/10/23
 * @since 2018/10/23
 */
@Configuration
public class InitCofnigBean {

    @Bean
    public GitLabApi gitLabApi() {
        return new GitLabApi();
    }

    @Bean
    public TemplateRenderer templateRenderer() {
        return new TemplateRenderer();
    }

    @Bean
    public ProjectGenerator projectGenerator(TemplateRenderer templateRenderer) {
        return new ProjectGenerator(templateRenderer(), gitLabApi());
    }

}
