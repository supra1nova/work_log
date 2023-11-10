package com.cmw.kd.member.controller;

import com.cmw.kd.core.dto.SearchDto;
import com.cmw.kd.core.utils.CommonUtils;
import com.cmw.kd.member.model.MemberDto;
import com.cmw.kd.member.service.MemberService;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;

import java.util.Objects;

@Controller
@RequestMapping("/member")
@RequiredArgsConstructor
public class MemberController {
  private final MemberService memberService;

  @GetMapping({"", "/", "/list"})
  public String getMemberList(@ModelAttribute("searchInfo") SearchDto searchDto, Model model) {
    int listCount = memberService.selectMemberListCount(searchDto);
    searchDto.setListCount(listCount);

    model.addAttribute("list", memberService.selectMemberList(searchDto));
    model.addAttribute("pagination", searchDto.getPagination());

    return "member/list";
  }

  @GetMapping("/add")
  public String addMember() {
    return "member/add";
  }

  @PostMapping("/add")
  public String addMemberProc(@ModelAttribute MemberDto memberDto, Model model) {
    if(memberService.insertMember(memberDto)){
      return "redirect:/member/view/" + memberDto.getMemberId();
    }

    model.addAttribute("info", memberDto);
    return "redirect:/member/add";
  }

  @GetMapping("/view/{memberId}")
  public String addMember(@PathVariable String memberId, Model model) {
    MemberDto memberDto = memberService.selectMemberById(memberId);
    if(Objects.isNull(memberDto)){
      return "error/noContent";
    }

    model.addAttribute("info", memberDto);
    return "member/view";
  }

  @GetMapping("/update/{memberId}")
  public String updateMember(@PathVariable String memberId, Model model) {
    model.addAttribute("info", memberService.selectMemberById(memberId));
    return "member/update";
  }

  @PostMapping("/update")
  public String updateMemberProc(@ModelAttribute MemberDto memberDto) {
    memberService.updateMember(memberDto);
    return "redirect:/member/view/" + memberDto.getMemberId();
  }

  @PostMapping("/delete")
  public String deleteMember(@ModelAttribute MemberDto memberDto) {
    memberService.deleteMember(memberDto);
    return "redirect:/member/list";
  }

  @GetMapping("/update-password")
  public String updateMemberPassword(Model model) {
    MemberDto memberDto = memberService.selectMemberById((String) CommonUtils.getSession().getAttribute("loginId"));
    model.addAttribute("info", memberDto);
    return "member/password";
  }

  @PostMapping("/update-password")
  public String updateMemberPasswordProc(@ModelAttribute MemberDto memberDto) {
    boolean result = memberService.updateMemberPassword(memberDto);
    if (result) {
      return "redirect:/member/view/" + memberDto.getMemberId();
    }
    return "redirect:/member/update-password";
  }
}
