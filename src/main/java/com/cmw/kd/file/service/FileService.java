package com.cmw.kd.file.service;

import com.cmw.kd.core.commonDto.CommonDto;
import com.cmw.kd.file.model.FileDto;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.apache.commons.lang3.StringUtils;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.core.io.PathResource;
import org.springframework.core.io.Resource;
import org.springframework.http.*;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

import java.io.File;
import java.io.IOException;
import java.nio.charset.StandardCharsets;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.List;
import java.util.UUID;

@Slf4j
@Service
@RequiredArgsConstructor
public class FileService {
  @Value("${custom.file.upload-path}")
  private String customFileInternalUploadPath;

  private final FileMapper fileMapper;

  public void insertFile(FileDto fileDto) {
    fileMapper.insertFile(fileDto.toEntity());
  }

  public FileDto selectFile(FileDto fileDto) {
    return fileMapper.selectFile(fileDto.toEntity());
  }

  // TODO: valid checking for file extension
  // TODO: return get method url
  public String uploadFiles(CommonDto commonDto, String uploadDir) throws IOException {
    List<MultipartFile> multipartFileList = commonDto.getUploadFileList();

    if (!multipartFileList.isEmpty()) {
      for (MultipartFile multipartFile : multipartFileList) {
        if(StringUtils.isNotBlank(multipartFile.getOriginalFilename())){
          String dateStr = LocalDateTime.now().format(DateTimeFormatter.ofPattern("yyMMdd_HHmmss"));

          UUID uuid = UUID.randomUUID();

          FileDto fileDto = new FileDto();

          if(StringUtils.isNotBlank(multipartFile.getOriginalFilename())){
            int dotIdx = multipartFile.getOriginalFilename().indexOf(".");
            String extStr = multipartFile.getOriginalFilename().substring(dotIdx);
            fileDto.setFileExt(extStr);
          }

          String targetFileName = dateStr + "_" + uuid;

          String uploadDirectoryPathStr = Paths.get(customFileInternalUploadPath, uploadDir).toString();
          Files.createDirectories(Paths.get(uploadDirectoryPathStr));

          String uploadFilePathStr = Paths.get(uploadDirectoryPathStr, targetFileName).toString();
          multipartFile.transferTo(new File(uploadFilePathStr));

          fileDto.setFileSourceName(multipartFile.getOriginalFilename());
          fileDto.setFileTargetName(targetFileName);
          fileDto.setFileTargetPath(uploadDir);
          fileDto.setFileSize(multipartFile.getSize());
          fileDto.setRegId(commonDto.getRegId());

          insertFile(fileDto);

          return "/toast-ui-bbs/display-image/" + targetFileName;
        }
      }
    }

    return null;
  }

  public ResponseEntity<Resource> displayImage(FileDto fileDto, String uploadDir) throws IOException {
    FileDto fileDtoResult = selectFile(fileDto);

    Path targetFile = Paths.get(customFileInternalUploadPath,  uploadDir, fileDtoResult.getFileTargetName());
    Resource resource = new PathResource(targetFile);

    return ResponseEntity.ok()
      .contentType(MediaType.APPLICATION_OCTET_STREAM)
      .cacheControl(CacheControl.noCache())
      .header(HttpHeaders.CONTENT_DISPOSITION, ContentDisposition.inline().filename(fileDtoResult.getFileTargetName(), StandardCharsets.UTF_8).build().toString())
      .body(resource);
  }
}
