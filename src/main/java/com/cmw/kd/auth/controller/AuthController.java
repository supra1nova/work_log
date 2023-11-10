package com.cmw.kd.auth.controller;

import com.cmw.kd.auth.model.LoginDto;
import com.cmw.kd.auth.service.AuthService;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;

@Controller
@RequestMapping("/auth")
@RequiredArgsConstructor
public class AuthController {
  private final AuthService authService;

  @GetMapping("/login")
  public String getLoginPage() {
    return "auth/login";
  }

  @PostMapping("/login")
  public String getLoginPageProc(@ModelAttribute LoginDto loginDto) {
    boolean result = authService.processLogin(loginDto);

    if (result) {
      return "redirect:/";
    }

    return "redirect:/auth/login?error=unauthenticated";
  }

  @GetMapping("/logout")
  public String makeLogout() {
    authService.proceedLogout();
    return "redirect:/auth/login";
  }
}
