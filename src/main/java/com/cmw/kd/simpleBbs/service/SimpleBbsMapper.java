package com.cmw.kd.simpleBbs.service;

import com.cmw.kd.core.commonDto.CommonDto;
import com.cmw.kd.core.commonDto.CommonDto.CommonVo;
import com.cmw.kd.core.commonDto.SearchDto;
import org.apache.ibatis.annotations.Mapper;

import java.util.List;

@Mapper
public interface SimpleBbsMapper {
    Integer selectSimpleBbsListCount(SearchDto searchDto);

    List<CommonDto> selectSimpleBbsList(SearchDto searchDto);

    CommonDto selectSimpleBbs(CommonVo commonVo);

    void insertSimpleBbs(CommonVo commonVo);

    int updateSimpleBbs(CommonVo commonVo);

    int deleteSimpleBbs(CommonVo commonVo);
}
