package com.cmw.kd.workLog.controller;

import com.cmw.kd.core.commonDto.ResponseDto;
import com.cmw.kd.core.utils.CommonUtils;
import com.cmw.kd.workLog.model.WorkLogCalendarDto;
import com.cmw.kd.workLog.model.WorkLogCalendarUpdateDto;
import com.cmw.kd.workLog.service.WorkLogCalendarService;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.apache.commons.lang3.StringUtils;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.validation.FieldError;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;
import java.util.List;
import java.util.Map;
import java.util.concurrent.ConcurrentHashMap;

@Slf4j
@Controller
@RequestMapping("/work-log-calendar")
@RequiredArgsConstructor
public class WorkLogCalendarController {
  private final WorkLogCalendarService workLogCalendarService;

  @GetMapping({"", "/"})
  public String getWorkLogCalendarList(@ModelAttribute WorkLogCalendarDto workLogCalendarDto, Model model) {
    List<WorkLogCalendarDto> calendarDtoList = workLogCalendarService.selectWorkLogCalendarList(workLogCalendarDto);
    List<WorkLogCalendarDto> workLogCalendarDtoList = workLogCalendarService.selectWorkLogAndCalendarList(workLogCalendarDto);
    String prevMonthValue = workLogCalendarService.selectPrevWorkLogCalendarList(workLogCalendarDto);
    String nextMonthValue = workLogCalendarService.selectNextWorkLogCalendarList(workLogCalendarDto);

    model.addAttribute("calendarList", calendarDtoList);
    model.addAttribute("list", workLogCalendarDtoList);
    model.addAttribute("role", CommonUtils.getSession().getAttribute("loginMemberRole").toString());

    model.addAttribute("prevMonth", StringUtils.isNotBlank(prevMonthValue));
    model.addAttribute("prevMonthValue", prevMonthValue);
    model.addAttribute("nextMonth", StringUtils.isNotBlank(nextMonthValue));
    model.addAttribute("nextMonthValue", nextMonthValue);

    return calendarDtoList.isEmpty() ? "redirect:/" : "workLog/calendar-list";
  }

  @GetMapping("/add/{calMonth}")
  public String addWorkLogCalendar(@PathVariable String calMonth) {
    boolean result = workLogCalendarService.manualInsertWorkLogCalendar(calMonth);
    String returnStr = result ? "/work-log-calendar?calMonth=" + calMonth : "/";
    return "redirect:" + returnStr;
  }

  @PostMapping("/update")
  @ResponseBody
  public ResponseEntity<ResponseDto<?>> updateWorkLogCalendar(@Valid @RequestBody WorkLogCalendarUpdateDto workLogCalendarUpdateDto, BindingResult errors) {
    boolean result = false;
    String description = "작업에 실패했습니다";
    String callback = "location.href='/work-log-calendar?calMonth=" + workLogCalendarUpdateDto.getCalMonth() + "'";

    if (errors.hasErrors()) {
      final String prefix = "invalid_";
      Map<String, String> errorMap = new ConcurrentHashMap<>();
      for(FieldError err: errors.getFieldErrors()){
        errorMap.put(String.format(prefix + "%s", err.getField()), err.getDefaultMessage());
      }
      return ResponseEntity.status(HttpStatus.NOT_ACCEPTABLE).body(ResponseDto.builder().result(result).description(description).invalidMessage(errorMap).callback(callback).build());
    }

    try {
      result = workLogCalendarService.updateWorkLogCalendar(workLogCalendarUpdateDto);
    } catch (RuntimeException e) {
      description = e.getMessage();
    }

    if (result) {
      description = "작업에 성공했습니다";
      return ResponseEntity.ok(ResponseDto.builder().result(result).description(description).callback(callback).build());
//      return ResponseEntity.accepted().body(ResponseDto.builder().result(result).description(description).callback(callback).build());
    }

    return ResponseEntity.internalServerError().body(ResponseDto.builder().result(result).description(description).callback(callback).build());
//    return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body(ResponseDto.builder().result(result).description(description).callback(callback).build());
  }
}
