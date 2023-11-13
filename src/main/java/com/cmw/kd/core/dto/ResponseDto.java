package com.cmw.kd.core.dto;

import com.fasterxml.jackson.annotation.JsonInclude;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

import java.util.Map;

// JsonInclude 설정을 통해 data 필드가 null인 경우 해당 필드는 응답에서 제외 후 리턴
@JsonInclude(JsonInclude.Include.NON_NULL)
@Getter
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class ResponseDto<T> {
  private boolean result;
  private String description;
  private T data;
  private Pagination pagination;
  private String callback;
  private Map<String, String> invalidMessage;
}
