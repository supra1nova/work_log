package com.cmw.kd.workLog.controller;

import com.cmw.kd.core.dto.CommonDto;
import com.cmw.kd.core.dto.ResponseDto;
import com.cmw.kd.core.dto.SearchDto;
import com.cmw.kd.file.service.FileService;
import com.cmw.kd.workLog.model.WorkLogDto;
import com.cmw.kd.workLog.service.WorkLogService;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.apache.commons.lang3.StringUtils;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.validation.FieldError;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;
import java.util.List;
import java.util.Map;
import java.util.concurrent.ConcurrentHashMap;

@Slf4j
@Controller
@RequestMapping("/work-log")
@RequiredArgsConstructor
public class WorkLogController {
  private final FileService fileService;
  private final WorkLogService workLogService;

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
    model.addAttribute("info", workLogDto);
    return "workLog/view";
  }

  @GetMapping("/view-by-cal-date")
  public String getWorkLogByCalDate(@ModelAttribute WorkLogDto workLogDto, Model model){
    WorkLogDto result = workLogService.selectWorkLogByCalDate(workLogDto);
    model.addAttribute("info", result);
    return "workLog/view";
  }

  @GetMapping("/add")
  public String addWorkLog(@ModelAttribute WorkLogDto workLogDto, Model model){
    workLogDto.setMemberInfo();

    model.addAttribute("info", workLogDto);
    return "workLog/add";
  }

  @PostMapping("/add")
  @ResponseBody
  public ResponseDto<?> addWorkLogProc(@Valid @ModelAttribute WorkLogDto workLogDto, BindingResult errors){
//  public ResponseDto<?> addWorkLogProc(@ModelAttribute WorkLogDto workLogDto){
    boolean result = false;
    String description = "게시물 등록에 실패했습니다";

    if(errors.hasErrors()){
      final String prefix = "invalid_";

      Map<String, String> errorMap = new ConcurrentHashMap<>();
      for(FieldError error : errors.getFieldErrors()){
        errorMap.put(String.format(prefix + "%s", error.getField()), error.getDefaultMessage());
      }
      return ResponseDto.builder().result(result).description(description).invalidMessage(errorMap).build();
    }

    try {
      workLogService.insertWorkLog(workLogDto);
      result = true;
    } catch (Exception e) {
      log.error(e.getMessage());
      description = e.getMessage();
    }

    if (result) {
      description = "게시물이 성공적으로 등록되었습니다";
    }

//    return ResponseDto.builder().result(result).description(description).callback("location.href='/work-log/view?workLogSeq=" + workLogDto.getWorkLogSeq() + "'").build();
//    return ResponseDto.builder().result(result).description(description).callback("location.href='/work-log/view-by-cal-date?workLogDate=" + workLogDto.getWorkLogDate() + "'").build();
    return ResponseDto.builder().result(result).description(description).callback("location.href='/work-log/view-by-cal-date?workLogDate=" + workLogDto.getWorkLogDate() + "&regId=" + workLogDto.getRegId() + "'").build();
  }

  @GetMapping("/update")
  public String updateWorkLog(Integer workLogSeq, Model model){
    WorkLogDto workLogDto = workLogService.selectWorkLog(workLogSeq);
    model.addAttribute("info", workLogDto);
    return "workLog/update";
  }

  @GetMapping("/update-by-cal-date")
  public String updateWorkLogByCalDate(@ModelAttribute WorkLogDto workLogDto, Model model){
    WorkLogDto result = workLogService.selectWorkLogByCalDate(workLogDto);
    model.addAttribute("info", result);
    return "workLog/update";
  }

  @PostMapping("/update")
  @ResponseBody
  public ResponseDto<?> updateWorkLogProc(@Valid @ModelAttribute WorkLogDto workLogDto, BindingResult errors){
    boolean result = false;
    String description = "게시물 등록에 실패했습니다";

    if(errors.hasErrors()){
      final String prefix = "invalid_";

      Map<String, String> errorMap = new ConcurrentHashMap<>();
      for(FieldError error : errors.getFieldErrors()){
        errorMap.put(String.format(prefix + "%s", error.getField()), error.getDefaultMessage());
      }
      return ResponseDto.builder().result(result).description(description).invalidMessage(errorMap).build();
    }

    try {
      workLogService.updateWorkLog(workLogDto);
      result = true;
    } catch (Exception e) {
      log.error(e.getMessage());
      description = e.getMessage();
    }

    if (result) {
      description = "게시물이 성공적으로 등록되었습니다";
    }

    return ResponseDto.builder().result(result).description(description).callback("location.href='/work-log/view?workLogSeq=" + workLogDto.getWorkLogSeq() + "'").build();
  }

  @PostMapping("/delete")
  @ResponseBody
  public ResponseDto<?> deleteWorkLogProc(@RequestBody WorkLogDto workLogDto){
    boolean result = false;
    String description = "게시물 등록에 실패했습니다";

    try {
      workLogService.deleteWorkLog(workLogDto);
      result = true;
    } catch (Exception e) {
      log.error(e.getMessage());
      description = e.getMessage();
    }

    if (result) {
      description = "게시물이 성공적으로 삭제되었습니다";
    }

    return ResponseDto.builder().result(result).description(description).callback("location.href='/work-log-calendar/list'").build();
  }

  @PostMapping("/delete-by-cal-date")
  @ResponseBody
  public ResponseDto<?> deleteWorkLogProcByCalDate(@RequestBody WorkLogDto workLogDto){
    boolean result = false;
    String description = "게시물 등록에 실패했습니다";

    try {
      workLogService.deleteWorkLogByCalDate(workLogDto);
      result = true;
    } catch (Exception e) {
      log.error(e.getMessage());
      description = e.getMessage();
    }

    if (result) {
      description = "게시물이 성공적으로 삭제되었습니다";
    }

    return ResponseDto.builder().result(result).description(description).callback("location.href='/work-log-calendar/list'").build();
  }

  @PostMapping("/add-image")
  @ResponseBody
  public ResponseDto<?> addImageProc(@ModelAttribute CommonDto commonDto) {
    boolean result = false;
    String description = "이미지 등록에 실패했습니다";
    String fileUrl = null;

    try {
      fileUrl = fileService.uploadFiles(commonDto, "content-image");
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
}
