package com.michaelloi.user.config;

import com.michaelloi.common.configs.BaseSecurityConfig;
import org.springframework.context.annotation.ComponentScan;
import org.springframework.context.annotation.Configuration;
import org.springframework.core.Ordered;
import org.springframework.core.annotation.Order;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;

@Order(Ordered.HIGHEST_PRECEDENCE)
@Configuration
@ComponentScan(basePackages = {"com.michaelloi.common"})
public class SecurityConfig extends BaseSecurityConfig {
    @Override
    protected void configure(HttpSecurity http) throws Exception{
        http.headers().contentSecurityPolicy("script-src 'self'");
        http.antMatcher("/**")
            .authorizeRequests()
            .antMatchers("/**").permitAll();
        super.configure(http);
    }
}
