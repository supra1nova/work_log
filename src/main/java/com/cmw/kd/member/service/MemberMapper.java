package com.cmw.kd.member.service;

import com.cmw.kd.core.commonDto.SearchDto;
import com.cmw.kd.member.model.MemberDto;
import org.apache.ibatis.annotations.Mapper;

import java.util.List;

import static com.cmw.kd.member.model.MemberDto.MemberVo;

@Mapper
public interface MemberMapper {

  List<MemberDto> selectMemberList(SearchDto searchDto);

  int selectMemberListCount(SearchDto searchDto);

  void insertMember(MemberVo memberVo);

  MemberDto selectMemberById(String memberId);

  void updateMember(MemberVo memberVo);

  void deleteMember(MemberVo memberVo);

  void updateMemberPassword(MemberVo memberVo);
}
