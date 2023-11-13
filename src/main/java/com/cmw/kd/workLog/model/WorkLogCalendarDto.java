package com.cmw.kd.workLog.model;

import lombok.Builder;
import lombok.Getter;
import lombok.Setter;
import lombok.ToString;

@Getter
@Setter
@ToString
public class WorkLogCalendarDto {
  private String calDate;
  private String calCode;
  private String regDatetime;
  private String regId;
  private String updDatetime;
  private String updId;
  private String active;

  public WorkCalendarVo toEntity() {
    return WorkCalendarVo.builder()
      .calDate(calDate)
      .calCode(calCode)
      .regDatetime(regDatetime)
      .regId(regId)
      .updDatetime(updDatetime)
      .updId(updId)
      .active(active)
      .build();
  }

  @Getter
  @Builder
  @ToString
  public static class WorkCalendarVo {
    private String calDate;
    private String calCode;
    private String regDatetime;
    private String regId;
    private String updDatetime;
    private String updId;
    private String active;
  }
}
