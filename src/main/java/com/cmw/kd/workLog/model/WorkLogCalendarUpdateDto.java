package com.cmw.kd.workLog.model;

import lombok.Builder;
import lombok.Getter;
import lombok.Setter;
import lombok.ToString;

import javax.validation.constraints.*;
import java.time.LocalDate;

@Getter
@Setter
@ToString
public class WorkLogCalendarUpdateDto {
  @NotNull
  @PastOrPresent(message = "현재 또는 과거의 날짜만 선택 가능합니다")
  private LocalDate calDate;
  @NotNull
  @Pattern(regexp = "\\d{4}-\\d{2}", message = "연도 및 월 형식이 맞지 않습니다")
  private String calMonth;
  @NotNull
  @Pattern(regexp = "[YN]", message = "활성/비활성 코드 형식이 맞지 않습니다")
  private String active;

  public WorkLogCalendarUpdateVo toEntity() {
    return WorkLogCalendarUpdateVo.builder()
      .calDate(calDate.toString())
      .calMonth(calMonth)
      .active(active)
      .build();
  }

  @Getter
  @Builder
  @ToString
  public static class WorkLogCalendarUpdateVo {
    private String calDate;
    private String calMonth;
    private String active;
  }

}
