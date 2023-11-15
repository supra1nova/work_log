package com.cmw.kd.core.commonDto;

import lombok.Getter;
import lombok.Setter;
import lombok.ToString;
import lombok.experimental.SuperBuilder;

@Getter
@Setter
@ToString
public class RegUpdDateDto {
  private String regId;
  private String updId;
  private String regDatetime;
  private String updDatetime;

  public RegUpDateVo toEntity() {
    return RegUpDateVo.builder()
      .regId(regId)
      .updId(updId)
      .regDatetime(regDatetime)
      .updDatetime(updDatetime)
      .build();
  }

  @Getter
  @SuperBuilder
  @ToString
  public static class RegUpDateVo {
    private String regId;
    private String updId;
    private String regDatetime;
    private String updDatetime;
  }
}
