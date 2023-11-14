package com.cmw.kd.workLog.model;

import com.cmw.kd.core.utils.CommonUtils;
import com.cmw.kd.core.utils.annotation.CustomBeforeOrPresent;
import com.cmw.kd.core.utils.annotation.MultipartFileList;
import lombok.Builder;
import lombok.Getter;
import lombok.Setter;
import lombok.ToString;
import org.springframework.web.multipart.MultipartFile;

import javax.validation.constraints.NotBlank;
import java.util.List;

@Getter
@Setter
@ToString
public class WorkLogDto {
  private Integer workLogSeq;
  @CustomBeforeOrPresent(message = "근무일 정보는 누락될 수 없습니다")
  private String workLogDate;
  @NotBlank(message = "본문 내용은 필수 입력 사항입니다")
  private String workLogContent;
  private String regId;
  private String regName;
  private String updId;
  private String updName;
  private String regDatetime;
  private String updDatetime;
  private String active;
  private String savedFileList;
  @MultipartFileList(message = "첨부된 파일을 등록할 수 없습니다")
  private List<MultipartFile> uploadFileList;
  private List<String> removeFileList;

  public void setMemberInfo() {
    String loginId = (String) CommonUtils.getSession().getAttribute("loginId");
    String loginMemberName = (String) CommonUtils.getSession().getAttribute("loginMemberName");
    regId = loginId;
    updId = loginId;
    regName = loginMemberName;
    updName = loginMemberName;
  }

  public WorkLogVo toEntity() {
    return WorkLogVo.builder()
      .workLogSeq(workLogSeq)
      .workLogDate(workLogDate)
      .workLogContent(workLogContent)
      .regId(regId)
      .updId(updId)
      .regDatetime(regDatetime)
      .updDatetime(updDatetime)
      .active(active)
      .build();
  }

  @Getter
  @Builder
  @ToString
  public static class WorkLogVo {
    private Integer workLogSeq;
    private String workLogDate;
    private String workLogContent;
    private String regId;
    private String updId;
    private String regDatetime;
    private String updDatetime;
    private String active;
  }
}
