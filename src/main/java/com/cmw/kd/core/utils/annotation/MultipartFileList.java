package com.cmw.kd.core.utils.annotation;

import com.cmw.kd.core.utils.validator.MultipartFileValidator;

import javax.validation.Constraint;
import javax.validation.Payload;
import java.lang.annotation.Documented;
import java.lang.annotation.Retention;
import java.lang.annotation.Target;

import static java.lang.annotation.ElementType.FIELD;
import static java.lang.annotation.RetentionPolicy.RUNTIME;

@Target({FIELD})  // 해당 어노테이션을 FIELD 에만 선언 가능하도록 설정
@Retention(RUNTIME) // 해당 어노테이션 유효시간을 RUNTIME 으로 설정
@Constraint(validatedBy = MultipartFileValidator.class) // 해당 어노테이션의 유효성 검증을 임의로 생성한 MultipartFileValidator.class 로 진행함을 설정
@Documented // JavaDoc 생성시 Annotation 정보 생성
public @interface MultipartFileList {

  String message() default "Invalid MulipartFileList";  // 유효하지 않을 경우 반환할 메세지

  Class<?>[] groups() default {}; // 유효성 검증이 진행될 그룹

  Class<? extends Payload>[] payload() default {};  // 유효성 검증 시 전달할 메타 정보
}
