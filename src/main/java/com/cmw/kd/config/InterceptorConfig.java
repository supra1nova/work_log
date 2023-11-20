package com.cmw.kd.config;

import com.cmw.kd.interceptor.LoginCheckInterceptor;
import com.cmw.kd.interceptor.ManagerCheckInterceptor;
import lombok.RequiredArgsConstructor;
import org.springframework.context.annotation.Configuration;
import org.springframework.web.servlet.config.annotation.InterceptorRegistry;
import org.springframework.web.servlet.config.annotation.WebMvcConfigurer;

@Configuration
@RequiredArgsConstructor
public class InterceptorConfig implements WebMvcConfigurer {
  private final LoginCheckInterceptor loginCheckInterceptor;
  private final ManagerCheckInterceptor managerCheckInterceptor;

  @Override
  public void addInterceptors(InterceptorRegistry registry) {
    registry.addInterceptor(loginCheckInterceptor)
      .addPathPatterns("/**")
      .excludePathPatterns("/css/**", "/js/**")
      .excludePathPatterns("/auth/login", "/auth/logout", "/member/dummy");

    registry.addInterceptor(managerCheckInterceptor)
      .addPathPatterns("/work-log-calendar/add", "/work-log-calendar/update");
  }
}
