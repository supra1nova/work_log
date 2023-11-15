package com.cmw.kd.core.commonDto;

import lombok.Getter;
import lombok.Setter;
import lombok.ToString;
import lombok.extern.slf4j.Slf4j;
import org.apache.commons.lang3.StringUtils;

import java.util.Objects;

@Slf4j
@Getter
@Setter
@ToString
public class PageDto {
  private Pagination pagination = new Pagination();

  public void setPage(Integer page) {
    pagination.setPage(Objects.isNull(page) ? 1 : page);
  }

  public void setPage(String pageNo) {
    int page = 1;
    if (StringUtils.isNotBlank(pageNo)) {
      try {
        page = Integer.parseInt(pageNo);
      } catch (NumberFormatException e) {
        log.warn("page NumberFormatException");
      }
    }
    pagination.setPage(page);
  }

  public void setListCount(int listCount) {
    pagination.setListCount(listCount);
  }

  public void setListLimit(Integer listLimit) {
    pagination.setListLimit(Objects.isNull(listLimit) ? 10 : listLimit);
  }

  public void setListLimit(String listLimitNo) {
    int listLimit = 1;
    if (StringUtils.isNotBlank(listLimitNo)) {
      try {
        listLimit = Integer.parseInt(listLimitNo);
      } catch (NumberFormatException e) {
        log.warn("page NumberFormatException");
      }
    }
    pagination.setListLimit(listLimit);
  }

  public Pagination getPagination() {
    pagination.calcPagination();
    return pagination;
  }

}
