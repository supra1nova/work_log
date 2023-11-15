package com.cmw.kd.core.utils.validator;

import com.cmw.kd.core.utils.annotation.CustomBeforeOrPresent;
import lombok.extern.slf4j.Slf4j;
import org.apache.commons.lang3.StringUtils;

import javax.validation.ConstraintValidator;
import javax.validation.ConstraintValidatorContext;
import java.time.LocalDate;
import java.util.Objects;

@Slf4j
public class CustomBeforeOrPresentValidator  implements ConstraintValidator<CustomBeforeOrPresent, String> {

  @Override
  public boolean isValid(String dateInfo, ConstraintValidatorContext constraintValidatorContext) {
    if(!Objects.isNull(dateInfo) && StringUtils.isNotBlank(dateInfo)){
      LocalDate parsedDate = LocalDate.parse(dateInfo);
      LocalDate currDate = LocalDate.now();

      return !currDate.isBefore(parsedDate);
    }

    return true;
  }
}
