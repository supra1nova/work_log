package com.cmw.kd.workLog.service;

import com.cmw.kd.core.commmonEnum.Role;
import com.cmw.kd.core.commonDto.CommonDto;
import com.cmw.kd.core.commonDto.SearchDto;
import com.cmw.kd.core.utils.CommonUtils;
import com.cmw.kd.file.service.FileService;
import com.cmw.kd.workLog.model.WorkLogDto;
import com.cmw.kd.workLog.model.WorkLogDto.WorkLogVo;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;

import java.io.IOException;
import java.util.List;

@Service
@RequiredArgsConstructor
public class WorkLogService {
  private final WorkLogMapper workLogMapper;
  private final FileService fileService;
  @Value("${custom.work-log-attach-dir-path}")
  private String attachDirPath;

  public int selectWorkLogListCount(SearchDto searchDto){
    return workLogMapper.selectWorkLogListCount(searchDto);
  }

  public List<WorkLogDto> selectWorkLogList(SearchDto searchDto){
    return workLogMapper.selectWorkLogList(searchDto);
  }

  public WorkLogDto selectWorkLog(WorkLogDto workLogDto){
    WorkLogDto resultDto = workLogMapper.selectWorkLog(workLogDto.toEntity());
    if(CommonUtils.getSession().getAttribute("loginMemberRole").equals(Role.STAFF.toString()) && !CommonUtils.getSession().getAttribute("loginId").equals(resultDto.getRegId())){
      resultDto = null;
    }
    return resultDto;
  }

  public boolean insertWorkLog(WorkLogDto workLogDto) throws IOException {
    workLogDto.setMemberInfo();

    WorkLogVo workLogVo = workLogDto.toEntity();
    int result = workLogMapper.insertWorkLog(workLogVo);

    if(result > 0){
      workLogDto.setWorkLogSeq(workLogVo.getWorkLogSeq());
      CommonDto commonDto = new CommonDto();
      commonDto.setMemberInfoAndFileList(workLogDto);
      fileService.uploadFiles(commonDto, attachDirPath);
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
