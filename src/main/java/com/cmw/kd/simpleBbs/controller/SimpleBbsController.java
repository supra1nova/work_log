package com.cmw.kd.simpleBbs.controller;

import com.cmw.kd.core.commonDto.CommonDto;
import com.cmw.kd.core.commonDto.SearchDto;
import com.cmw.kd.simpleBbs.service.SimpleBbsService;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@Controller
@RequiredArgsConstructor
@RequestMapping("/bbs")
public class SimpleBbsController {
    private final SimpleBbsService simpleBbsService;

    @GetMapping({"", "/", "/list"})
    private String getSimpleBbsList(@ModelAttribute("info") SearchDto searchDto, Model model) {
        Integer listCount = simpleBbsService.selectSimpleBbsListCount(searchDto);
        searchDto.setListCount(listCount);

        List<CommonDto> commonDtoList = simpleBbsService.selectSimpleBbsList(searchDto);
        model.addAttribute("list", commonDtoList);
        model.addAttribute("pagination", searchDto.getPagination());

        return "simpleBbs/list";
    }

    @GetMapping("/view")
    private String getSimpleBbs(@ModelAttribute CommonDto commonDto, Model model) {
        model.addAttribute("info", simpleBbsService.selectSimpleBbs(commonDto));
        return "simpleBbs/view";
    }

    @GetMapping("/add")
    public String addSimpleBbs(@ModelAttribute CommonDto commonDto) {
        return "simpleBbs/add";
    }

    @PostMapping("/add")
    public String addSimpleBbsProc1(@ModelAttribute CommonDto commonDto) {
        // TODO: form 방식으로 파일 받아서 처리
        return "redirect:/bbs/view?seq=" + commonDto.getSeq();
    }

    @PostMapping("/add-api1")
    public @ResponseBody String addSimpleBbsProc2(@ModelAttribute CommonDto commonDto) {
        // TODO: ajax 방식 but form 파일을 받아서 처리, save 버튼 누를 경우 파일을 같이 받아 저장
        return "redirect:/bbs/view?seq=" + commonDto.getSeq();
    }

    @PostMapping("/add-api2")
    public @ResponseBody String addSimpleBbsProc3(@RequestBody CommonDto commonDto) {
        // TODO: ajax 방식으로 파일 받아서 처리, 파일을 input 에 넣을 때 ajax로 업로드, save 버튼 누르면 해당 파일들의 uuid 를 받아 temp 에서 attach로 경로 이동시키고 temp 에서는 inactive 처리
        return "redirect:/bbs/view?seq=" + commonDto.getSeq();
    }

    @GetMapping("/update")
    public String updateSimpleBbs(@ModelAttribute CommonDto commonDto, Model model) {
        model.addAttribute("info", simpleBbsService.selectSimpleBbs(commonDto));
        return "simpleBbs/update";
    }

    @PostMapping("/delete")
    public String deleteSimpleBbs(@ModelAttribute CommonDto commonDto) {
        simpleBbsService.deleteSimpleBbs(commonDto);
        return "redirect:/list";
    }
}
