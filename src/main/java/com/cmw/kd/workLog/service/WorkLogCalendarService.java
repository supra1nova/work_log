package com.cmw.kd.workLog.service;

import com.cmw.kd.workLog.model.WorkLogCalendarDto;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.apache.commons.lang3.StringUtils;
import org.springframework.stereotype.Service;

import java.time.LocalDate;
import java.util.List;
import java.util.Objects;

@Slf4j
@Service
@RequiredArgsConstructor
public class WorkLogCalendarService {
  private final WorkLogCalendarMapper workLogCalendarMapper;

  // TODO: calendar list 가져옴
  public List<WorkLogCalendarDto> selectWorkLogCalendarList(WorkLogCalendarDto workLogCalendarDto) {
    if(Objects.isNull(workLogCalendarDto) || StringUtils.isBlank(workLogCalendarDto.getCalCode())){
      workLogCalendarDto = new WorkLogCalendarDto();
      workLogCalendarDto.setCalCode(LocalDate.now().withDayOfMonth(1).toString().substring(0, 7));
    }
    return workLogCalendarMapper.selectWorkLogCalendarList(workLogCalendarDto.toEntity());
  }

  // TODO:

  // TODO: work log list 가져옴


  public void insertWorkLogCalendar(WorkLogCalendarDto workLogCalendarDto) {
    workLogCalendarMapper.insertWorkLogCalendar(workLogCalendarDto.toEntity());
  }

  public void procedureInsertWorkLogCalendar(){
    LocalDate firstDate = LocalDate.now().withDayOfMonth(1);
    int daySize = firstDate.lengthOfMonth();

    for (int i = 0; i < daySize; i++) {
      LocalDate saveDate = firstDate.plusDays(i);

      WorkLogCalendarDto workLogCalendarDto = new WorkLogCalendarDto();
      workLogCalendarDto.setCalDate(saveDate.toString());
      workLogCalendarDto.setCalCode(saveDate.toString().substring(0, 7));

      insertWorkLogCalendar(workLogCalendarDto);
    }
  }
}
