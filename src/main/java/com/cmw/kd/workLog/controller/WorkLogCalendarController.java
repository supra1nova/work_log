package com.cmw.kd.workLog.controller;

import com.cmw.kd.workLog.model.WorkLogCalendarDto;
import com.cmw.kd.workLog.service.WorkLogCalendarService;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.RequestMapping;

import java.util.List;

@Slf4j
@Controller
@RequestMapping("/work-log-calendar")
@RequiredArgsConstructor
public class WorkLogCalendarController {
  private final WorkLogCalendarService workLogCalendarService;

  @GetMapping({"", "/", "/list"})
  public String getWorkLogCalendarList(@ModelAttribute WorkLogCalendarDto workLogCalendarDto, Model model) {
    List<WorkLogCalendarDto> workLogCalendarDtoList = workLogCalendarService.selectWorkLogCalendarList(workLogCalendarDto);
    model.addAttribute("list", workLogCalendarDtoList);
    return "workLog/calendar-list";
  }

//  @Scheduled(cron = "")
//  public void addWorkLogCalendar() {
//  //   TODO: 사용자 임의 접근 못하도록 막아야
//  }
  @GetMapping("/add")
  public String addWorkLogCalendar() {
    workLogCalendarService.procedureInsertWorkLogCalendar();
    return "redirect:/work-log-calendar";
  }
}
