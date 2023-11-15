package com.cmw.kd.core.commonDto;

import com.cmw.kd.core.utils.CommonUtils;
import lombok.Getter;
import lombok.Setter;
import lombok.ToString;
import org.apache.commons.lang3.StringUtils;

import javax.servlet.http.HttpServletRequest;

@Getter
@Setter
@ToString
public class Pagination {
  private Integer page = 1;
  private int listCount = 0;
  private int totalPageCount = 1;
  private int listLimit = 10;
  private int blockSize = 10;
  private int blockStartPage = 0;
  private int blockEndPage = 0;

  private boolean first = false;
  private boolean last = false;
  private boolean prev = false;
  private boolean next = false;

  private String url;
  private String urlParams;

  private int rowNum = 0;

  public Pagination() {
    init(CommonUtils.getRequest());
  }

  public Pagination(HttpServletRequest request) {
    init(request);
  }

  public void init(HttpServletRequest request) {
    setUrl(request.getRequestURI(), request.getQueryString());
  }

  public void setUrl(String url, String urlParams) {
    this.url = url;
    setUrlParams(urlParams);
  }

  public void setUrlParams(String urlParams) {
    String urlRegex = "([&])?page=[0-9]*";
    String urlParamsWithoutPage = StringUtils.isNotBlank(urlParams) ? urlParams.replaceAll(urlRegex, "") : null;
    this.urlParams = StringUtils.isNotBlank(urlParamsWithoutPage) ? "?" + urlParamsWithoutPage + "&amp;page" : "?page";
  }

  public void calcPagination() {
    this.rowNum = Math.max((page - 1) * listLimit, 0);

    totalPageCount = (int) Math.ceil(listCount / (double) listLimit);
    blockStartPage = ((page - 1) / blockSize) * blockSize + 1;
    blockEndPage = Math.min(((page - 1) / blockSize) * blockSize + blockSize, Math.max(totalPageCount, 1));

    if (totalPageCount > 0) {
      first = page > 1;
      last = page < totalPageCount;
      prev = page > blockSize;
      next = blockEndPage < totalPageCount;
    }
  }
}
