package com.cmw.kd.core.commonDto;

import com.cmw.kd.core.utils.CommonUtils;
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
public class CommonDto {
  private Integer seq;
  @NotBlank(message = "제목은 필수 입력 사항입니다")
  private String title;
  @NotBlank(message = "본문 내용은 필수 입력 사항입니다")
  private String content;
  private Integer grp;
  private Integer ord;
  private Integer dep;
  private String regId;
  private String regName;
  private String updId;
  private String updName;
  private String regDatetime;
  private String updDatetime;
  private String topFixed;
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

  public CommonVo toEntity() {
    return CommonVo.builder()
      .seq(seq)
      .title(title)
      .content(content)
      .grp(grp)
      .ord(ord)
      .dep(dep)
      .regId(regId)
      .updId(updId)
      .regDatetime(regDatetime)
      .updDatetime(updDatetime)
      .topFixed(topFixed)
      .active(active)
      .build();
  }

  @Getter
  @Builder
  @ToString
  public static class CommonVo {
    private Integer seq;
    private String title;
    private String content;
    private Integer grp;
    private Integer ord;
    private Integer dep;
    private String regId;
    private String updId;
    private String regDatetime;
    private String updDatetime;
    private String topFixed;
    private String active;
  }
}
