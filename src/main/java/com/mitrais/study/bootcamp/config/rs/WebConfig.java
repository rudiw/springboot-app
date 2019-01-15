package com.mitrais.study.bootcamp.config.rs;

import com.google.common.collect.ImmutableList;
import org.springframework.boot.web.servlet.FilterRegistrationBean;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.web.filter.HiddenHttpMethodFilter;

/**
 * @author Rudi_W144
 */
@Configuration
public class WebConfig {

    @Bean
    public FilterRegistrationBean<HiddenHttpMethodFilter> hiddenHttpMethodFilter() {
        final HiddenHttpMethodFilter filter = new HiddenHttpMethodFilter();
        final FilterRegistrationBean registration = new FilterRegistrationBean<>(filter);
        registration.setUrlPatterns(ImmutableList.of("/*"));

        return registration;
    }

}
