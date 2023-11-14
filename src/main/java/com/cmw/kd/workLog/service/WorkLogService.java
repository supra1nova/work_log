package com.cmw.kd.workLog.service;

import com.cmw.kd.core.dto.SearchDto;
import com.cmw.kd.workLog.model.WorkLogDto;
import com.cmw.kd.workLog.model.WorkLogDto.WorkLogVo;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
@RequiredArgsConstructor
public class WorkLogService {
  private final WorkLogMapper workLogMapper;

  public int selectWorkLogListCount(SearchDto searchDto){
    return workLogMapper.selectWorkLogListCount(searchDto);
  }

  public List<WorkLogDto> selectWorkLogList(SearchDto searchDto){
    return workLogMapper.selectWorkLogList(searchDto);
  }

  public void insertWorkLog(WorkLogDto workLogDto){
    workLogDto.setMemberInfo();

    WorkLogVo workLogVo = workLogDto.toEntity();
    workLogMapper.insertWorkLog(workLogVo);

    workLogDto.setWorkLogSeq(workLogVo.getWorkLogSeq());
  }

  public WorkLogDto selectWorkLog(Integer workLogSeq){
    return workLogMapper.selectWorkLog(workLogSeq);
  }

  public void updateWorkLog(WorkLogDto workLogDto){
    workLogDto.setMemberInfo();
    workLogMapper.updateWorkLog(workLogDto.toEntity());
  }

  public void deleteWorkLog(WorkLogDto workLogDto){
    workLogMapper.deleteWorkLog(workLogDto.toEntity());
  }
}
