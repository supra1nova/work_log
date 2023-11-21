package com.cmw.kd.file.service;

import com.cmw.kd.core.commonDto.CommonDto;
import com.cmw.kd.file.model.FileDto;
import com.cmw.kd.file.model.FileDto.FileVo;
import org.apache.ibatis.annotations.Mapper;

import java.util.List;

@Mapper
public interface FileMapper {

  List<FileDto> selectFileList(CommonDto commonDto);

  int insertFile(FileVo fileVo);

  FileDto selectFile(FileVo fileVo);

  void deleteFile(FileVo fileVo);
}
