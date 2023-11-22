package com.cmw.kd.workLog.service;

import com.cmw.kd.core.commonDto.SearchDto;
import com.cmw.kd.workLog.model.WorkLogDto;
import com.cmw.kd.workLog.model.WorkLogDto.WorkLogVo;
import org.apache.ibatis.annotations.Mapper;

import java.util.List;

@Mapper
public interface WorkLogMapper {

  int selectWorkLogListCount(SearchDto searchDto);

  List<WorkLogDto> selectWorkLogList(SearchDto searchDto);

  WorkLogDto selectWorkLog(WorkLogVo workLogVo);

  int insertWorkLog(WorkLogVo commonVo);

  int updateWorkLog(WorkLogVo commonVo);

  int deleteWorkLog(WorkLogVo commonVo);

}
