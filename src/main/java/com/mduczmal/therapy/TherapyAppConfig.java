package com.mduczmal.therapy;

import com.mduczmal.therapy.ad.AdFactory;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

@Configuration
public class TherapyAppConfig {
    @Bean
    AdFactory adFactory() { return new TherapyAdFactory(); }
}
