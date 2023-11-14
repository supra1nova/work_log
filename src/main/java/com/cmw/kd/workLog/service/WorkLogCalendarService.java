package com.cmw.kd.workLog.service;

import com.cmw.kd.core.utils.CommonUtils;
import com.cmw.kd.workLog.model.WorkLogCalendarDto;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.apache.commons.lang3.StringUtils;
import org.springframework.stereotype.Service;

import java.time.LocalDate;
import java.time.format.TextStyle;
import java.util.List;
import java.util.Locale;
import java.util.Objects;

@Slf4j
@Service
@RequiredArgsConstructor
public class WorkLogCalendarService {
  private final WorkLogCalendarMapper workLogCalendarMapper;

  public List<WorkLogCalendarDto> selectWorkLogCalendarList(WorkLogCalendarDto workLogCalendarDto) {
    // calendar list 가져옴
    if(Objects.isNull(workLogCalendarDto) || StringUtils.isBlank(workLogCalendarDto.getCalMonth())){
      workLogCalendarDto = new WorkLogCalendarDto();
      workLogCalendarDto.setCalMonth(LocalDate.now().withDayOfMonth(1).toString().substring(0, 7));
    }

    workLogCalendarDto.setRegId(CommonUtils.getSession().getAttribute("loginId").toString());

    List<WorkLogCalendarDto> workLogCalendarDtoList = workLogCalendarMapper.selectWorkLogCalendarList(workLogCalendarDto.toEntity());
    workLogCalendarDtoList.forEach(dto -> dto.setCalDayName(LocalDate.parse(dto.getCalDate()).getDayOfWeek().getDisplayName(TextStyle.SHORT, Locale.KOREAN)));

    return workLogCalendarDtoList;
  }

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
      workLogCalendarDto.setCalMonth(saveDate.toString().substring(0, 7));

      String active = saveDate.getDayOfWeek().getValue() < 6 ? "Y" : "N";
      workLogCalendarDto.setActive(active);

      insertWorkLogCalendar(workLogCalendarDto);
    }
  }
}
