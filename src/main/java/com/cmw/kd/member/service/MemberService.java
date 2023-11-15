package com.cmw.kd.member.service;

import com.cmw.kd.core.commonDto.SearchDto;
import com.cmw.kd.core.utils.CommonUtils;
import com.cmw.kd.member.model.MemberDto;
import lombok.RequiredArgsConstructor;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import java.util.List;

import static com.cmw.kd.member.model.MemberDto.MemberVo;

@Service
@RequiredArgsConstructor
public class MemberService {
  private final MemberMapper memberMapper;
  private final PasswordEncoder passwordEncoder;

  public int selectMemberListCount(SearchDto searchDto) {
    return memberMapper.selectMemberListCount(searchDto);
  }

  public List<MemberDto> selectMemberList(SearchDto searchDto) {
    return memberMapper.selectMemberList(searchDto);
  }

  public boolean insertMember(MemberDto memberDto) {
    boolean result = checkMemberConfirmPassword(memberDto);

    if(result) {
      // reg id 및 upd id 세팅, password encode 진행
      setMemberInfo(memberDto);
      memberDto.encodePassword(passwordEncoder);
      MemberVo memberVo = memberDto.toEntity();
      memberMapper.insertMember(memberVo);
      // dto 내 seq 등록
      memberDto.setMemberSeq(memberVo.getMemberSeq());
      return true;
    }
    return false;
  }

  public MemberDto selectMemberById(String memberId) {
    return memberMapper.selectMemberById(memberId);
  }

  public void updateMember(MemberDto memberDto) {
    memberMapper.updateMember(memberDto.toEntity());
  }

  public void deleteMember(MemberDto memberDto) {
    memberMapper.deleteMember(memberDto.toEntity());
  }

  public boolean updateMemberPassword(MemberDto memberDto) {
    MemberDto memberDbDto = selectMemberById((String) CommonUtils.getSession().getAttribute("loginId"));

    boolean isEqual = checkMemberConfirmPassword(memberDto);
    boolean isMatch = passwordEncoder.matches(memberDto.getMemberPassword(), memberDbDto.getMemberPassword());

    if (isEqual && isMatch) {
      memberDto.encodePassword(passwordEncoder);
      memberMapper.updateMemberPassword(memberDto.toEntity());
      return true;
    }

    return false;
  }

  public void setMemberInfo(MemberDto memberDto) {
    String loginId = (String) CommonUtils.getSession().getAttribute("loginId");
    memberDto.setMemberRegId(loginId);
    memberDto.setMemberUpdId(loginId);
  }

  private boolean checkMemberConfirmPassword(MemberDto memberDto) {
    return memberDto.getMemberPassword().equals(memberDto.getMemberConfPassword());
  }
}
