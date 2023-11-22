package com.cmw.kd.workLog.controller;

import com.cmw.kd.core.commonDto.CommonDto;
import com.cmw.kd.core.commonDto.ResponseDto;
import com.cmw.kd.core.commonDto.SearchDto;
import com.cmw.kd.core.utils.CommonUtils;
import com.cmw.kd.file.model.FileDto;
import com.cmw.kd.file.service.FileService;
import com.cmw.kd.workLog.model.WorkLogDto;
import com.cmw.kd.workLog.service.WorkLogService;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.apache.commons.lang3.StringUtils;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.core.io.Resource;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.validation.FieldError;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;
import java.io.IOException;
import java.util.List;
import java.util.Map;
import java.util.Objects;
import java.util.concurrent.ConcurrentHashMap;

@Slf4j
@Controller
@RequestMapping("/work-log")
@RequiredArgsConstructor
public class WorkLogController {
  private final FileService fileService;
  private final WorkLogService workLogService;

  @Value("${custom.work-log-attach-dir-path}")
  private String uploadFileDir;

  @GetMapping({"", "/", "/list"})
  public String getWorkLogList(@ModelAttribute SearchDto searchDto, Model model){
    int listCount = workLogService.selectWorkLogListCount(searchDto);
    searchDto.setListCount(listCount);

    List<WorkLogDto> workLogDtoList = workLogService.selectWorkLogList(searchDto);

    model.addAttribute("list", workLogDtoList);
    model.addAttribute("pagination", searchDto.getPagination());

    return "workLog/list";
  }

  @GetMapping("/view")
  public String getWorkLog(Integer workLogSeq, Model model){
    WorkLogDto workLogDto = workLogService.selectWorkLog(workLogSeq);
    if(Objects.isNull(workLogDto)){
      return "redirect:/work-log-calendar";
    }
    model.addAttribute("info", workLogDto);
    model.addAttribute("memberId", CommonUtils.getSession().getAttribute("loginId").toString());
    model.addAttribute("fileList", fileService.selectFileList(workLogDto));

    return "workLog/view";
  }

  @GetMapping("/add")
  public String addWorkLog(@ModelAttribute WorkLogDto workLogDto, Model model){
    workLogDto.setMemberInfo();

    model.addAttribute("info", workLogDto);
    return "workLog/add";
  }

  @PostMapping("/add")
  public @ResponseBody ResponseEntity<ResponseDto<?>> addWorkLogProc(@Valid @ModelAttribute WorkLogDto workLogDto, BindingResult errors){
    boolean result = false;
    String description = "게시물 등록에 실패했습니다";
    String callback = null;

    if (errors.hasErrors()) {
      return CommonUtils.errorResponseEntityBuilder(errors, result, description, callback);
    }

    try {
      result = workLogService.insertWorkLog(workLogDto);
    } catch (Exception e) {
      log.error(e.getMessage());
      description = e.getMessage();
    }

    if (result) {
      description = "게시물이 성공적으로 등록되었습니다";
      callback = "location.href='/work-log/view?workLogSeq=" + workLogDto.getWorkLogSeq() + "'";
      return ResponseEntity.ok(ResponseDto.builder().result(result).description(description).callback(callback).build());
    }

    return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body(ResponseDto.builder().result(result).description(description).callback(callback).build());
  }

  @GetMapping("/update")
  public String updateWorkLog(Integer workLogSeq, Model model){
    WorkLogDto workLogDto = workLogService.selectWorkLog(workLogSeq);
    model.addAttribute("info", workLogDto);
    return "workLog/update";
  }

  @PostMapping("/update")
  public @ResponseBody ResponseEntity<ResponseDto<?>> updateWorkLogProc(@Valid @ModelAttribute WorkLogDto workLogDto, BindingResult errors){
    boolean result = false;
    String description = "게시물 등록에 실패했습니다";
    String callback = null;

    if (errors.hasErrors()) {
      return CommonUtils.errorResponseEntityBuilder(errors, result, description, callback);
    }

    try {
      result = workLogService.updateWorkLog(workLogDto);
    } catch (Exception e) {
      log.error(e.getMessage());
      description = e.getMessage();
    }

    if (result) {
      description = "게시물이 성공적으로 등록되었습니다";
      callback = "location.href='/work-log/view?workLogSeq=" + workLogDto.getWorkLogSeq() + "'";
      return ResponseEntity.ok(ResponseDto.builder().result(result).description(description).callback(callback).build());
    }

    return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body(ResponseDto.builder().result(result).description(description).callback(callback).build());
  }

  @PostMapping("/delete")
  public @ResponseBody ResponseDto<?> deleteWorkLogProc(@RequestBody WorkLogDto workLogDto){
    boolean result = false;
    String description = "게시물 등록에 실패했습니다";
    String callback = null;

    try {
      result = workLogService.deleteWorkLog(workLogDto);
    } catch (Exception e) {
      log.error(e.getMessage());
      description = e.getMessage();
    }

    if (result) {
      description = "게시물이 성공적으로 삭제되었습니다";
      callback = "location.href='/work-log-calendar'";
    }

    return ResponseDto.builder().result(result).description(description).callback(callback).build();
  }

  @PostMapping("/add-image")
  public @ResponseBody ResponseDto<?> addImageProc(@ModelAttribute CommonDto commonDto) {
    boolean result = false;
    String description = "이미지 등록에 실패했습니다";
    String fileUrl = null;

    try {
//      fileUrl = fileService.uploadFiles(commonDto, "content-image");
    } catch (Exception e) {
      log.error(e.getMessage());
      description = e.getMessage();
    }

    if (StringUtils.isNotBlank(fileUrl)) {
      result = true;
      description = "이미지가 성공적으로 등록되었습니다";
    }

    return ResponseDto.builder().result(result).description(description).data(fileUrl).build();
  }

  @GetMapping("/download/{bbsSeq}/{fileTargetName}")
  public ResponseEntity<Resource> downloadFormFileBbs(@ModelAttribute FileDto fileDto) throws IOException {
    return fileService.downloadFile2(fileDto, uploadFileDir);
  }

}
