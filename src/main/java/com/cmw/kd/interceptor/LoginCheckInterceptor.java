package com.cmw.kd.interceptor;

import com.cmw.kd.core.utils.CommonUtils;
import org.springframework.context.annotation.Configuration;
import org.springframework.web.servlet.HandlerInterceptor;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;

@Configuration
public class LoginCheckInterceptor implements HandlerInterceptor {

  @Override
  public boolean preHandle(HttpServletRequest request, HttpServletResponse response, Object handler) {
    if (!CommonUtils.isLogin()){
      try {
        response.sendRedirect("/auth/login");
      } catch (IOException e) {
        throw new RuntimeException(e);
      }
      return false;
    }

    return true;
  }
}
