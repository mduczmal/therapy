package com.mduczmal.therapy.config;

import com.mduczmal.therapy.ad.AdFactory;
import com.mduczmal.therapy.ad.TherapyAdFactory;
import com.mduczmal.therapy.cookies.Cookies;
import com.mduczmal.therapy.cookies.Observer;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.web.context.annotation.SessionScope;
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

    @Bean
    @SessionScope
    Cookies cookiesProvider(Observer observer) {
        Cookies cookies = new Cookies();
        cookies.attach(observer);
        return cookies;
    }
}
