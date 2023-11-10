package com.cmw.kd.auth.model;

import lombok.Getter;
import lombok.Setter;
import lombok.ToString;

@Getter
@Setter
@ToString
public class LoginDto {
  private String loginId;
  private String loginPassword;
}
