package com.cmw.kd.file.service;

import com.cmw.kd.file.model.FileDto;
import org.apache.ibatis.annotations.Mapper;

@Mapper
public interface FileMapper {

  void insertFile(FileDto.FileVo fileVo);

  FileDto selectFile(FileDto.FileVo fileVo);
}
