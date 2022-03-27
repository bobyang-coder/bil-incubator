package com.bil.account.configuration;

import org.springframework.context.annotation.Configuration;
import org.springframework.web.method.support.HandlerMethodArgumentResolver;
import org.springframework.web.servlet.config.annotation.WebMvcConfigurer;

import java.util.List;

@Configuration
public class WebMvcCustomConfig implements WebMvcConfigurer {

    @Override
    public void addArgumentResolvers(List<HandlerMethodArgumentResolver> resolvers) {
        if (resolvers.isEmpty()) {
            resolvers.add(0, new DecryptableArgumentResolver());
        } else {
            resolvers.set(0, new DecryptableArgumentResolver());
        }
    }
}
