package com.cmw.kd.workLog.service;

import com.cmw.kd.core.dto.CommonDto;
import com.cmw.kd.core.dto.SearchDto;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.List;

import static com.cmw.kd.core.dto.CommonDto.*;

@Service
@RequiredArgsConstructor
public class WorkLogService {
  private final WorkLogMapper workLogMapper;

  public int selectWorkLogListCount(SearchDto searchDto){
    return workLogMapper.selectWorkLogListCount(searchDto);
  }

  public List<CommonDto> selectWorkLogList(SearchDto searchDto){
    return workLogMapper.selectWorkLogList(searchDto);
  }

  public void insertWorkLog(CommonDto commonDto){
    commonDto.setMemberInfo();

    CommonVo commonVo = commonDto.toEntity();
    workLogMapper.insertWorkLog(commonVo);

    commonDto.setSeq(commonVo.getSeq());
  }

  public CommonDto selectWorkLog(Integer seq){
    return workLogMapper.selectWorkLog(seq);
  }

  public void updateWorkLog(CommonDto commonDto){
    commonDto.setMemberInfo();
    workLogMapper.updateWorkLog(commonDto.toEntity());
  }

  public void deleteWorkLog(CommonDto commonDto){
    workLogMapper.deleteWorkLog(commonDto.toEntity());
  }
}
