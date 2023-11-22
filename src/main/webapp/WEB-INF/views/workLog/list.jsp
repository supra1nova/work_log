<%--
  Created by IntelliJ IDEA.
  User: kwangdoo
  Date: 2023-09-04
  Time: 오후 6:33
  To change this template use File | Settings | File Templates.
--%>
<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<html>
<head>
  <title>Simple  list</title>
  <link rel="stylesheet" href="/css/style.css">
  <script src="/js/libs/jquery-3.7.0.min.js"></script>
  <script src="/js/common/common.js"></script>
</head>
<body>
<%@ include file="../_include/header.jsp" %>

<div class="container">
  <div>Work log list page</div>

  <form method="get" action="${pageContext.request.contextPath}/work-log/list">
    <table class="mb5">
      <colgroup>
        <col style="min-width: 150px; width: 150px">
        <col style="min-width: 250px; width: 250px">
        <col style="min-width: 150px; width: 150px">
        <col style="min-width: 250px; width: 250px">
      </colgroup>
      <tbody>
        <tr>
          <th>카테고리</th>
          <td>
            <select name="searchCategory">
              <option value="all" <c:if test="${searchInfo.searchCategory.equals('all')}">selected</c:if>>전체</option>
              <option value="title" <c:if test="${searchInfo.searchCategory.equals('title')}">selected</c:if>>제목</option>
              <option value="content" <c:if test="${searchInfo.searchCategory.equals('content')}">selected</c:if>>내용</option>
              <option value="id" <c:if test="${searchInfo.searchCategory.equals('id')}">selected</c:if>>작성자</option>
            </select>
          </td>
          <th>검색어</th>
          <td><input type="text" name="searchKeyword" placeholder="검색어를 입력해 주세요" value="${searchInfo.searchKeyword}"></td>
        </tr>
      </tbody>
    </table>

    <div class="mb10 tr">
      <button type="submit">제출</button>
      <button type="button" onclick="CommonUtils.formReset()">검색어 초기화</button>
    </div>

    <div class="mb5">
      <span>[ ${pagination.page} / ${pagination.totalPageCount == 0 ? 1 : pagination.totalPageCount} page ]</span>
      <span>
        <select name="listLimit" style="border: 1px solid black; width: 100px" onchange="$('form').submit()">
          <option value="10" <c:if test="${pagination.listLimit == 10}">selected</c:if> >10</option>
          <option value="20" <c:if test="${pagination.listLimit == 20}">selected</c:if> >20</option>
          <c:if test="${pagination.listLimit != 10 && pagination.listLimit != 20}">
            <option value="${pagination.listLimit}" <c:if test="${pagination.listLimit != 10 && pagination.listLimit != 20}">selected</c:if> >${pagination.listLimit}</option>
          </c:if>
        </select>
      </span>
    </div>
  </form>

  <table class="mb5 hover-table table-layout-fixed">
    <colgroup>
      <col style="min-width: 125px; width: 125px">
      <col style="min-width: 300px; width: 300px">
      <col style="min-width: 125px; width: 125px">
      <col style="min-width: 125px; width: 125px">
      <col style="min-width: 125px; width: 125px">
    </colgroup>
    <thead>
      <th>No.</th>
      <th>근무일</th>
      <th>작성자</th>
      <th>작성일시</th>
      <th>수정일시</th>
    </thead>
    <tbody>
      <c:forEach items="${list}" var="item">
        <c:if test="${item.active.equals('Y')}">
          <tr>
            <td class="tc">
              <a href="${pageContext.request.contextPath}/work-log/view?workLogSeq=${item.workLogSeq}">${item.workLogSeq}</a>
            </td><td class="tc">
              <a href="${pageContext.request.contextPath}/work-log/view?workLogSeq=${item.workLogSeq}">${item.workLogDate}</a>
            </td>
            <td class="tc"><a href="${pageContext.request.contextPath}/work-log/view?workLogSeq=${item.workLogSeq}">${item.regId}</a></td>
            <td class="tc"><a href="${pageContext.request.contextPath}/work-log/view?workLogSeq=${item.workLogSeq}">${item.regDatetime}</a></td>
            <td class="tc">
              <a href="${pageContext.request.contextPath}/work-log/view?workLogSeq=${item.workLogSeq}">
                <c:if test="${!empty item.updDatetime}">${item.updDatetime}</c:if>
                <c:if test="${empty item.updDatetime}">-</c:if>
              </a>
            </td>
          </tr>
        </c:if>
        <c:if test="${item.active.equals('N')}">
          <tr>
            <td colspan="5" class="tc">삭제된 글입니다</td>
          </tr>
        </c:if>
      </c:forEach>
      <c:if test="${empty list}">
        <tr><td colspan="5" style="text-align: center">게시글이 존재하지 않습니다</td></tr>
      </c:if>
    </tbody>
  </table>

  <div class="pagination mb10">
    <ul>
      <c:if test="${pagination.first}"><a href="${pagination.url}${pagination.urlParams}=1"><li>시작</li></a></c:if>
      <c:if test="${pagination.prev}"><a href="${pagination.url}${pagination.urlParams}=${pagination.page - pagination.blockSize}"><li>이전</li></a></c:if>

      <c:forEach begin="${pagination.blockStartPage}" end="${pagination.blockEndPage}" var="idx">
        <c:if test="${pagination.page != idx}"><li><a href="${pagination.url}${pagination.urlParams}=${idx}">${idx}</a></li></c:if>
        <c:if test="${pagination.page == idx}"><li style="color: deepskyblue"><b>${idx}</b></li></c:if>
      </c:forEach>
      <c:if test="${pagination.next}"><a href="${pagination.url}${pagination.urlParams}=${pagination.blockEndPage + 1}"><li>다음</li></a></c:if>
      <c:if test="${pagination.last}"><a href="${pagination.url}${pagination.urlParams}=${pagination.totalPageCount}"><li>끝</li></a></c:if>
    </ul>
  </div>

  <div class="mb150 tr">
    <button type="button" onclick="location.href='${pageContext.request.contextPath}/work-log/add'">새 글 작성</button>
    <button type="button" onclick="location.href='${pageContext.request.contextPath}/work-log-calendar'">일일 보고 리스트 보기</button>
  </div>

</div>
</body>
</html>
