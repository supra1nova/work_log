package com.cmw.kd.config;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.provisioning.InMemoryUserDetailsManager;
import org.springframework.security.web.SecurityFilterChain;

@Configuration
public class SecurityConfig {

  @Bean
  protected SecurityFilterChain httpSecurity(HttpSecurity http) throws Exception {
    http
      .formLogin().disable();

    http
      .csrf().disable()
      .headers().frameOptions().sameOrigin();

    http
      .authorizeRequests()
      .anyRequest()
      .permitAll();

    return http.build();
  }

  // TODO: member service 및 userDetail 내용 연결
  @Bean
  public UserDetailsService userDetailsService(){
    return new InMemoryUserDetailsManager();
  }
}
