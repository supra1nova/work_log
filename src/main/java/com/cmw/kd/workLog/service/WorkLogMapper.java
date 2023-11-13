package com.cmw.kd.workLog.service;

import com.cmw.kd.core.dto.CommonDto;
import com.cmw.kd.core.dto.CommonDto.CommonVo;
import com.cmw.kd.core.dto.SearchDto;
import org.apache.ibatis.annotations.Mapper;

import java.util.List;

@Mapper
public interface WorkLogMapper {
  int selectWorkLogListCount(SearchDto searchDto);

  List<CommonDto> selectWorkLogList(SearchDto searchDto);

  void insertWorkLog(CommonVo commonVo);

  CommonDto selectWorkLog(Integer seq);

  void updateWorkLog(CommonVo commonVo);

  void deleteWorkLog(CommonVo commonVo);
}
