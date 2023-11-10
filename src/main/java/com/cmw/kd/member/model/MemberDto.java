package com.cmw.kd.member.model;

import lombok.Builder;
import lombok.Getter;
import lombok.Setter;
import lombok.ToString;
import org.springframework.security.crypto.password.PasswordEncoder;

@Getter
@Setter
@ToString
public class MemberDto {
  private Integer memberSeq;
  private String memberId;
  private String memberPassword;
  private String memberConfPassword;
  private String memberNewPassword;
  private String memberName;
  private String memberEmail;
  private String memberRegId;
  private String memberUpdId;
  private String memberRegDatetime;
  private String memberUpdDatetime;
  private String active;

  public void encodePassword(PasswordEncoder passwordEncoder) {
    memberPassword = passwordEncoder.encode(memberPassword);
  }

  public MemberVo toEntity() {
    return MemberVo.builder()
      .memberSeq(memberSeq)
      .memberId(memberId)
      .memberPassword(memberPassword)
      .memberName(memberName)
      .memberEmail(memberEmail)
      .memberRegId(memberRegId)
      .memberUpdId(memberUpdId)
      .memberRegDatetime(memberRegDatetime)
      .memberUpdDatetime(memberUpdDatetime)
      .active(active)
      .build();
  }

  @Getter
  @Builder
  @ToString
  public static class MemberVo {
    private Integer memberSeq;
    private String memberId;
    private String memberPassword;
    private String memberName;
    private String memberEmail;
    private String memberRegId;
    private String memberUpdId;
    private String memberRegDatetime;
    private String memberUpdDatetime;
    private String active;
  }
}
