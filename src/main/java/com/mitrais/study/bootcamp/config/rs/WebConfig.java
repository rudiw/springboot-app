package com.mitrais.study.bootcamp.config.rs;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.boot.autoconfigure.web.servlet.WebMvcAutoConfiguration;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.http.converter.json.MappingJackson2HttpMessageConverter;
import org.springframework.web.servlet.config.annotation.EnableWebMvc;
import org.springframework.web.servlet.config.annotation.WebMvcConfigurationSupport;

/**
 * @author Rudi_W144
 */
@Configuration
@EnableWebMvc
public class WebConfig extends WebMvcConfigurationSupport {

    private static final Logger log = LoggerFactory.getLogger(WebConfig.class);

    @Bean
    public MappingJackson2HttpMessageConverter mappingJackson2HttpMessageConverter() {
        log.debug("Creating MappingJackson2HttpMessageConverter...");
        final MappingJackson2HttpMessageConverter jackson2 = new MappingJackson2HttpMessageConverter();
        jackson2.setObjectMapper(JsonUtils.mapper);
        //	jackson2.getSupportedMediaTypes().add(MediaType.parseMediaType("application/ld+json"));
        jackson2.setSupportedMediaTypes(ImmutableList.of(
                MediaType.APPLICATION_JSON,
                MediaTypes.HAL_JSON,
                MediaType.parseMediaType("application/ld+json")));
        return jackson2;
    }
}
