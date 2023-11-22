package com.cmw.kd.core.utils;

import com.cmw.kd.core.commmonEnum.Role;
import com.cmw.kd.core.commonDto.ResponseDto;
import lombok.extern.slf4j.Slf4j;
import org.apache.commons.lang3.StringUtils;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.BindingResult;
import org.springframework.validation.FieldError;
import org.springframework.web.context.request.RequestContextHolder;
import org.springframework.web.context.request.ServletRequestAttributes;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.Map;
import java.util.concurrent.ConcurrentHashMap;

@Slf4j
public class CommonUtils {

  public static HttpServletRequest getRequest() {
    ServletRequestAttributes attr = (ServletRequestAttributes) RequestContextHolder.currentRequestAttributes();
    return attr.getRequest();
  }

  public static HttpServletResponse getResponse() {
    ServletRequestAttributes attr = (ServletRequestAttributes) RequestContextHolder.currentRequestAttributes();
    return attr.getResponse();
  }

  public static HttpSession getSession() {
    return getRequest().getSession();
  }

  public static String convertNRtoBrSpToNbsp(String content) {
    return content.replaceAll("\r\n|\r|\n", "<br>").replaceAll(" ", "&nbsp;");
  }

  public static boolean isLogin() {
    return StringUtils.isNotBlank((String) getSession().getAttribute("loginId"));
  }

  public static boolean isManager() {
    String loginMemberRole = (String) getSession().getAttribute("loginMemberRole");
    return StringUtils.isNotBlank(loginMemberRole) && loginMemberRole.equals(Role.MANAGER.toString());
  }

  public static String convertDate(String dateStr){
    //  method에 들어온 parameter의 형식 정의
    DateTimeFormatter formatter = DateTimeFormatter.ofPattern("yyyy-MM-dd HH:mm:ss");
    // 정해진 형식으로 LocalDatetime 객체 을 만들어줌
    LocalDateTime date = LocalDateTime.parse(dateStr, formatter);
    // LocalDatetime 객체로부터 날짜/일시 정보를 가져와 지정된 DateTimeFormatter의 패턴으로 변경한 뒤 string으로 리턴
    return date.format(DateTimeFormatter.ofPattern("yy-MM-dd"));
  }

  public static ResponseEntity<ResponseDto<?>> errorResponseEntityBuilder(BindingResult errors, boolean result, String description, String callback) {
    final String prefix = "invalid_";
    Map<String, String> errorMap = new ConcurrentHashMap<>();
    for(FieldError err: errors.getFieldErrors()){
      errorMap.put(String.format(prefix + "%s", err.getField()), err.getDefaultMessage());
    }
    return ResponseEntity.status(HttpStatus.NOT_ACCEPTABLE).body(ResponseDto.builder().result(result).description(description).invalidMessage(errorMap).callback(callback).build());
  }

}
