package com.cmw.kd.simpleBbs.service;

import com.cmw.kd.core.commonDto.CommonDto;
import com.cmw.kd.core.commonDto.CommonDto.CommonVo;
import com.cmw.kd.core.commonDto.SearchDto;
import com.cmw.kd.file.service.FileService;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;

import java.io.IOException;
import java.util.List;

@Slf4j
@Service
@RequiredArgsConstructor
public class SimpleBbsService {
    private final SimpleBbsMapper simpleBbsMapper;
    private final FileService fileService;

    public Integer selectSimpleBbsListCount(SearchDto searchDto) {
        return simpleBbsMapper.selectSimpleBbsListCount(searchDto);
    }

    public List<CommonDto> selectSimpleBbsList(SearchDto searchDto) {
        return simpleBbsMapper.selectSimpleBbsList(searchDto);
    }

    public CommonDto selectSimpleBbs(CommonDto commonDto) {
        return simpleBbsMapper.selectSimpleBbs(commonDto.toEntity());
    }

    public void insertSimpleBbs(CommonDto commonDto) {
        CommonVo commonVo = commonDto.toEntity();
        simpleBbsMapper.insertSimpleBbs(commonVo);
        commonDto.setSeq(commonVo.getSeq());
    }

    public boolean updateSimpleBbs(CommonDto commonDto){
        // 1. 글 업데이트
        boolean result = simpleBbsMapper.updateSimpleBbs(commonDto.toEntity()) > 0;
//        // TODO: 2. 추가/삭제 파일 확인
//        if (result) {
//            // TODO: 2-1. 추가/삭제 파일 존재시 파일 추가 또는 삭제 진행
//            fileService.processUpdateAddDeleteFile(commonDto);
//        }
        return result;
    }

    public boolean deleteSimpleBbs(CommonDto commonDto) {
        return simpleBbsMapper.deleteSimpleBbs(commonDto.toEntity()) > 0;
    }

    // form submit 형식으로 파일 받을 경우
    public void processInsertBbsAndUploadFileList(CommonDto commonDto) throws IOException {
        insertSimpleBbs(commonDto);
        if (commonDto.getSeq() != null) {
            fileService.uploadFiles(commonDto, "attach");
        }
    }

    // 이미 input 으로 파일 업로드를 진행한 뒤 submit 으로 target file name list 를 가져온 경우
    public void processInsertBbsAndTargetFileNameList(CommonDto commonDto) throws IOException {
        insertSimpleBbs(commonDto);
        if (commonDto.getSeq() != null) {
            // TODO: 1) 파일들 저장 위치 temp 에서 attach 폴더로 변경 또는 복사, 2) target file name list 에 해당하는 파일들의 bbs seq, 저장 경로 업데이트
        }
    }

}
