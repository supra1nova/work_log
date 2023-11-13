package com.cmw.kd.core.utils.validator;

import com.cmw.kd.core.utils.annotation.MultipartFileList;
import lombok.extern.slf4j.Slf4j;
import org.apache.commons.lang3.StringUtils;
import org.springframework.web.multipart.MultipartFile;

import javax.validation.ConstraintValidator;
import javax.validation.ConstraintValidatorContext;
import java.util.List;
import java.util.Objects;

@Slf4j
public class MultipartFileValidator implements ConstraintValidator<MultipartFileList, List<MultipartFile>> {

  @Override
  public boolean isValid(List<MultipartFile> multipartFileList, ConstraintValidatorContext constraintValidatorContext) {
    if(!Objects.isNull(multipartFileList) && !multipartFileList.isEmpty()){
      int listSize = multipartFileList.size();

      for(MultipartFile mtpFile : multipartFileList){
        if(listSize > 1 && StringUtils.isBlank(mtpFile.getOriginalFilename()) || listSize > 5) {
          return false;
        }
      }
    }

    return true;
  }
}
