package com.cmw.kd.auth.service;

import com.cmw.kd.auth.model.LoginDto;
import com.cmw.kd.core.utils.CommonUtils;
import com.cmw.kd.member.model.MemberDto;
import com.cmw.kd.member.service.MemberService;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import javax.servlet.http.HttpSession;
import java.util.Objects;

@Slf4j
@Service
@RequiredArgsConstructor
public class AuthService {
  private final MemberService memberService;
  private final PasswordEncoder passwordEncoder;

  public boolean processLogin(LoginDto loginDto) {
    MemberDto memberInfo = memberService.selectMemberById(loginDto.getLoginId());
    if (Objects.isNull(memberInfo)) {
      log.warn("Member not found");
      return false;
    }

    if (!isMatchPassword(loginDto.getLoginPassword(), memberInfo.getMemberPassword())) {
      log.warn("Password incorrect");
      return false;
    }

    HttpSession session = CommonUtils.getSession();
    session.setAttribute("loginId", memberInfo.getMemberId());
    session.setAttribute("loginMemberName", memberInfo.getMemberName());
    session.setAttribute("loginMemberRole", memberInfo.getRole().toString());

    return true;
  }

  public void proceedLogout() {
    CommonUtils.getSession().invalidate();
  }

  private boolean isMatchPassword(String loginPassword, String memberPassword) {
    log.warn("loginPassword : {}", loginPassword);
    log.warn("memberPassword : {}", memberPassword);
    return passwordEncoder.matches(loginPassword, memberPassword);
  }
}
