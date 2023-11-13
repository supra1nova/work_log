package com.cmw.kd.file.model;

import com.cmw.kd.core.dto.RegUpdDateDto;
import lombok.Getter;
import lombok.Setter;
import lombok.ToString;
import lombok.experimental.SuperBuilder;

@Getter
@Setter
@ToString
public class FileDto extends RegUpdDateDto {
  private Integer fileSeq;
  private Integer bbsSeq;
  private String fileSourceName;
  private String fileTargetName;
  private String fileTargetPath;
  private String fileExt;
  private Long fileSize;
  private char active;

  @Override
  public FileVo toEntity() {
    return FileVo.builder()
      .fileSeq(fileSeq)
      .bbsSeq(bbsSeq)
      .fileSourceName(fileSourceName)
      .fileTargetName(fileTargetName)
      .fileTargetPath(fileTargetPath)
      .fileExt(fileExt)
      .fileSize(fileSize)
      .active(active)
      .regId(getRegId())
      .updId(getUpdId())
      .regDatetime(getRegDatetime())
      .updDatetime(getUpdDatetime())
      .build();
  }

  @Getter
  @SuperBuilder
  @ToString
  public static class FileVo extends RegUpDateVo{
    private Integer fileSeq;
    private Integer bbsSeq;
    private String fileSourceName;
    private String fileTargetName;
    private String fileTargetPath;
    private String fileExt;
    private Long fileSize;
    private char active;
  }
}
