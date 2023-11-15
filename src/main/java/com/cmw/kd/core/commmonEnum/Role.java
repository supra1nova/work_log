package com.cmw.kd.core.commmonEnum;

import lombok.*;

@Getter
@AllArgsConstructor
public enum Role {
  STAFF("사원"),
  MANAGER("팀장"),
  ;

  private final String desc;
}
