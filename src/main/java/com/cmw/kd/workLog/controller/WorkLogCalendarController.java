package com.cmw.kd.workLog.controller;

import com.cmw.kd.core.utils.CommonUtils;
import com.cmw.kd.workLog.model.WorkLogCalendarDto;
import com.cmw.kd.workLog.service.WorkLogCalendarService;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;

import java.util.List;

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

    model.addAttribute("calendarList", calendarDtoList);
    model.addAttribute("list", workLogCalendarDtoList);
    model.addAttribute("role", CommonUtils.getSession().getAttribute("loginMemberRole").toString());

    return calendarDtoList.isEmpty() ? "redirect:/" : "workLog/calendar-list";
  }

  @GetMapping("/add/{calMonth}")
  public String addWorkLogCalendar(@PathVariable String calMonth) {
    boolean result = workLogCalendarService.manualInsertWorkLogCalendar(calMonth);
    String returnStr = result ? "/work-log-calendar?calMonth=" + calMonth : "/";
    return "redirect:" + returnStr;
  }
}
