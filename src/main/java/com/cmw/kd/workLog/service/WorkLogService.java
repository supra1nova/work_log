package com.cmw.kd.workLog.service;

import com.cmw.kd.core.commmonEnum.Role;
import com.cmw.kd.core.commonDto.SearchDto;
import com.cmw.kd.core.utils.CommonUtils;
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

  public WorkLogDto selectWorkLog(Integer workLogSeq){
    WorkLogDto workLogDto = workLogMapper.selectWorkLog(workLogSeq);
    if(CommonUtils.getSession().getAttribute("loginMemberRole").equals(Role.STAFF.toString()) && !CommonUtils.getSession().getAttribute("loginId").equals(workLogDto.getRegId())){
      workLogDto = null;
    }
    return workLogDto;
  }

  public boolean insertWorkLog(WorkLogDto workLogDto){
    workLogDto.setMemberInfo();

    WorkLogVo workLogVo = workLogDto.toEntity();
    int result = workLogMapper.insertWorkLog(workLogVo);

    if(result > 0){
      workLogDto.setWorkLogSeq(workLogVo.getWorkLogSeq());
      return true;
    }
    return false;
  }

  public boolean updateWorkLog(WorkLogDto workLogDto){
    workLogDto.setMemberInfo();
    return workLogMapper.updateWorkLog(workLogDto.toEntity()) > 0;
  }

  public boolean deleteWorkLog(WorkLogDto workLogDto){
    return workLogMapper.deleteWorkLog(workLogDto.toEntity()) > 0;
  }

}
