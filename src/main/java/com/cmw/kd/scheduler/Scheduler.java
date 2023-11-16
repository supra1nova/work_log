package com.cmw.kd.scheduler;

import com.cmw.kd.workLog.service.WorkLogCalendarService;
import lombok.RequiredArgsConstructor;
import org.springframework.scheduling.annotation.Async;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Service;

import java.time.LocalDate;

@Service
@RequiredArgsConstructor
public class Scheduler {
  private final WorkLogCalendarService workLogCalendarService;

  @Async
  @Scheduled(cron = "0 5 0 1 * *")
  public void addWorkLogCalendarByScheduler() {
    workLogCalendarService.procedureInsertWorkLogCalendar(LocalDate.now());
  }
}
