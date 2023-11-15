<%--
  Created by IntelliJ IDEA.
  User: kwangdoo
  Date: 2023-09-08
  Time: 오후 12:31
  To change this template use File | Settings | File Templates.
--%>
<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<html>
<head>
  <title>Member list</title>
  <link rel="stylesheet" href="/css/style.css">
  <style>
    ul {
      list-style: none;
      display: flex;
      justify-content: space-around;
      width: 800px;
      margin: 0 auto;
    }
    li {
      width: 50px;
      padding: 0 5px 0;
      text-align: center;
    }
  </style>
  <script src="/js/libs/jquery-3.7.0.min.js"></script>
  <script src="/js/common/common.js"></script>
</head>
<body>
<jsp:include page="../_include/header.jsp"/>

<div class="container">
  <div>Member list page</div>

  <form method="get">
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
              <option value="all" <c:if test="${searchInfo.searchCategory eq 'all'}">selected</c:if> >전체</option>
              <option value="id" <c:if test="${searchInfo.searchCategory eq 'id'}">selected</c:if> >아이디</option>
              <option value="name" <c:if test="${searchInfo.searchCategory eq 'name'}">selected</c:if> >이름</option>
              <option value="email" <c:if test="${searchInfo.searchCategory eq 'email'}">selected</c:if> >이메일</option>
            </select>
          </td>
          <th>키워드</th>
          <td><input type="text" name="searchKeyword" placeholder="검색어를 입력해 주세요" value="${searchInfo.searchKeyword}"></td>
        </tr>
      </tbody>
    </table>

    <div class="tr mb10">
      <button>제출</button>
      <button type="button" onclick="CommonUtils.formReset()">검색어 초기화</button>
    </div>

    <div class="mb5">
      <span>[ ${pagination.page} / ${pagination.totalPageCount eq 0 ? 1 : pagination.totalPageCount} page ]</span>
      <span>
        <select name="listLimit" style="border: 1px solid black; width: 100px" onchange="$('form').submit()">
          <option <c:if test="${pagination.listLimit eq 10}">selected</c:if> value="10">10</option>
          <option <c:if test="${pagination.listLimit eq 20}">selected</c:if> value="20">20</option>
          <c:if test="${!(pagination.listLimit eq 10) && !(pagination.listLimit eq 20)}">
            <option selected value="${pagination.listLimit}">${pagination.listLimit}</option>
          </c:if>
        </select>
      </span>
    </div>
  </form>

  <table class="mb5 hover-table table-layout-fixed">
    <colgroup>
      <col style="min-width: 50px; width: 50px">
      <col style="min-width: 125px; width: 125px">
      <col style="min-width: 125px; width: 125px">
      <col style="min-width: 125px; width: 125px">
      <col style="min-width: 200px; width: 200px">
      <col style="min-width: 150px; width: 150px">
      <col style="min-width: 150px; width: 150px">
    </colgroup>
    <thead>
      <tr>
        <th>No</th>
        <th>아이디</th>
        <th>이름</th>
        <th>권한</th>
        <th>이메일</th>
        <th>등록일시</th>
        <th>수정일시</th>
      </tr>
    </thead>
    <tbody>
      <c:forEach items="${list}" var="item">
        <c:if test="${item.active.equals('Y')}">
          <tr>
            <td class="tc"><a href="/member/view/${item.memberId}">${item.memberSeq}</a></td>
            <td class="tc ellipsis-no-wrap"><a href="/member/view/${item.memberId}">${item.memberId}</a></td>
            <td class="tc ellipsis-no-wrap"><a href="/member/view/${item.memberId}">${item.memberName}</a></td>
            <td class="tc ellipsis-no-wrap"><a href="/member/view/${item.memberId}">${item.role}</a></td>
            <td class="tc ellipsis-no-wrap"><a href="/member/view/${item.memberId}">${item.memberEmail}</a></td>
            <td class="tc"><a href="/member/view/${item.memberId}">${item.memberRegDatetime}</a></td>
            <td class="tc"><a href="/member/view/${item.memberId}">${empty item.memberUpdDatetime ? "-" : item.memberUpdDatetime}</a></td>
          </tr>
        </c:if>
        <c:if test="${item.active.equals('N')}">
          <tr>
            <td class="tc" colspan="6">삭제된 회원입니다</td>
          </tr>
        </c:if>
      </c:forEach>
      <c:if test="${empty list}">
        <tr><td colspan="6" style="text-align: center">회원 정보가 존재하지 않습니다</td></tr>
      </c:if>
    </tbody>
  </table>

  <div class="pagination mb10">
    <ul>
      <c:if test="${pagination.first}">
        <li><a href="${pagination.url}${pagination.urlParams}=1">시작</a></li>
      </c:if>
      <c:if test="${pagination.prev}">
        <li><a href="${pagination.url}${pagination.urlParams}=${pagination.blockStartPage - pagination.blockSize}">이전</a></li>
      </c:if>
      <c:forEach begin="${pagination.blockStartPage}" end="${pagination.blockEndPage}" var="idx">
        <c:if test="${pagination.page != idx}"><li><a href="${pagination.url}${pagination.urlParams}=${idx}">${idx}</a></li></c:if>
        <c:if test="${pagination.page == idx}"><li style="color: #f08080">${idx}</li></c:if>
      </c:forEach>
      <c:if test="${pagination.next}">
        <li><a href="${pagination.url}${pagination.urlParams}=${pagination.blockEndPage + 1}">다음</a></li>
      </c:if>
      <c:if test="${pagination.last}">
        <li><a href="${pagination.url}${pagination.urlParams}=${pagination.totalPageCount}">끝</a></li>
      </c:if>
    </ul>
  </div>

  <div class="tr mb150">
    <button type="button" onclick="location.href='/member/add'">신규 회원 추가</button>
  </div>
</div>
</body>
</html>
