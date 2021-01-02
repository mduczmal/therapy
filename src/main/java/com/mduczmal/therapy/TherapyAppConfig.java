package com.mduczmal.therapy;

import com.mduczmal.therapy.ad.AdFactory;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.web.servlet.config.annotation.ResourceHandlerRegistry;
import org.springframework.web.servlet.config.annotation.WebMvcConfigurer;

@Configuration
public class TherapyAppConfig implements WebMvcConfigurer {

    @Override
    public void addResourceHandlers(ResourceHandlerRegistry registry) {
        registry.addResourceHandler("/images/**").addResourceLocations("file:"+System.getenv("FILES") + "/images/");
    }

    @Bean
    AdFactory adFactory() { return new TherapyAdFactory(); }
}
