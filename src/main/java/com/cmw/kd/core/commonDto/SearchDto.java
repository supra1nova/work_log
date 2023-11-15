package com.cmw.kd.core.commonDto;

import lombok.Getter;
import lombok.Setter;
import lombok.ToString;

@Getter
@Setter
@ToString(callSuper = true)
public class SearchDto extends PageDto{
  private String searchCategory;
  private String searchKeyword;
  private String sortColumn;
  private boolean sortAscending;
}
