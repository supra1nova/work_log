package com.cmw.kd.workLog.service;

import com.cmw.kd.workLog.model.WorkLogCalendarDto;
import org.apache.ibatis.annotations.Mapper;

import java.util.List;

import static com.cmw.kd.workLog.model.WorkLogCalendarDto.*;

@Mapper
public interface WorkLogCalendarMapper {
  List<WorkLogCalendarDto> selectWorkLogCalendarList(WorkCalendarVo workCalendarVo);

  void insertWorkLogCalendar(WorkCalendarVo workCalendarVo);
}
