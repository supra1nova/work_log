package com.cmw.kd.workLog.controller;

import com.cmw.kd.core.dto.CommonDto;
import com.cmw.kd.core.dto.ResponseDto;
import com.cmw.kd.core.dto.SearchDto;
import com.cmw.kd.file.service.FileService;
import com.cmw.kd.workLog.service.WorkLogService;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.apache.commons.lang3.StringUtils;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;
import java.util.List;

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

    List<CommonDto> commonDtoList = workLogService.selectWorkLogList(searchDto);

    model.addAttribute("list", commonDtoList);
    model.addAttribute("pagination", searchDto.getPagination());

    return "workLog/list";
  }

  @GetMapping("/view")
  public String getWorkLog(Integer seq, Model model){
    CommonDto commonDto = workLogService.selectWorkLog(seq);
    model.addAttribute("info", commonDto);
    return "workLog/view";
  }

  @GetMapping("/add")
  public String addWorkLog(Model model){
    CommonDto commonDto = new CommonDto();
    commonDto.setMemberInfo();

    model.addAttribute("info", commonDto);
    return "workLog/add";
  }

  @PostMapping("/add")
  @ResponseBody
  public ResponseDto<?> addWorkLogProc(@Valid @ModelAttribute CommonDto commonDto){
//  public ResponseDto<?> addWorkLogProc(@ModelAttribute CommonDto commonDto){
    boolean result = false;
    String description = "게시물 등록에 실패했습니다";

    try {
      workLogService.insertWorkLog(commonDto);
      result = true;
    } catch (Exception e) {
      log.error(e.getMessage());
      description = e.getMessage();
    }

    if (result) {
      description = "게시물이 성공적으로 등록되었습니다";
    }

    return ResponseDto.builder().result(result).description(description).callback("location.href='/work-log/view?seq=" + commonDto.getSeq() + "'").build();
  }

  @GetMapping("/update")
  public String updateWorkLog(Integer seq, Model model){
    CommonDto commonDto = workLogService.selectWorkLog(seq);
    model.addAttribute("info", commonDto);
    return "workLog/update";
  }

  @PostMapping("/update")
  @ResponseBody
  public ResponseDto<?> updateWorkLogProc(@Valid @ModelAttribute CommonDto commonDto){
    boolean result = false;
    String description = "게시물 등록에 실패했습니다";

    try {
      workLogService.updateWorkLog(commonDto);
      result = true;
    } catch (Exception e) {
      log.error(e.getMessage());
      description = e.getMessage();
    }

    if (result) {
      description = "게시물이 성공적으로 등록되었습니다";
    }

    return ResponseDto.builder().result(result).description(description).callback("location.href='/work-log/view?seq=" + commonDto.getSeq() + "'").build();
  }

  @PostMapping("/delete")
  @ResponseBody
  public ResponseDto<?> deleteWorkLogProc(@RequestBody CommonDto commonDto){
    boolean result = false;
    String description = "게시물 등록에 실패했습니다";

    try {
      workLogService.deleteWorkLog(commonDto);
      result = true;
    } catch (Exception e) {
      log.error(e.getMessage());
      description = e.getMessage();
    }

    if (result) {
      description = "게시물이 성공적으로 삭제되었습니다";
    }

    return ResponseDto.builder().result(result).description(description).callback("location.href='/work-log'").build();
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
