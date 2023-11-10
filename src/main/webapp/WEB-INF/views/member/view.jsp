<%--
  Created by IntelliJ IDEA.
  User: kwangdoo
  Date: 2023-09-08
  Time: 오후 4:50
  To change this template use File | Settings | File Templates.
--%>
<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<html>
<head>
  <title>Member view</title>
  <link rel="stylesheet" href="/css/style.css">
  <script src="/js/libs/jquery-3.7.0.min.js"></script>
  <script src="/js/common/common.js"></script>
</head>
<body>
<jsp:include page="../_include/header.jsp"/>

<div class="container">
  <div>Member view page</div>

  <table class="mb5" style="width: 80%; margin-left: auto; margin-right: auto">
    <colgroup>
      <col style="min-width: 200px">
      <col style="min-width: 400px">
    </colgroup>
    <tbody>
      <tr>
        <th>아이디</th>
        <td class="tc">${info.memberId}</td>
      </tr>
      <tr>
        <th>이름</th>
        <td class="tc">${info.memberName}</td>
      </tr>
      <tr>
        <th>이메일</th>
        <td class="tc">${info.memberEmail}</td>
      </tr>
      <tr>
        <th>등록일시</th>
        <td class="tc">${info.memberRegDatetime}</td>
      </tr>
      <tr>
        <th>수정일시</th>
        <td class="tc">${empty info.memberUpdDatetime ? "-" : info.memberUpdDatetime}</td>
      </tr>
    </tbody>
  </table>

  <form method="post" action="/member/delete" hidden>
    <input type="text" name="memberSeq" value="${info.memberSeq}" hidden readonly>
  </form>

  <div class="tr mb150" style="width: 80%; margin-left: auto; margin-right: auto">
    <%-- TODO: 향후 본인 정보 수정 제한 및 account 메뉴에서 처리되도록 수정 필요 --%>
    <button type="button" onclick="location.href='/member/update/${memberId}'">수정</button>
    <button type="button" onclick="$('form').submit()">삭제</button>
    <button type="button" onclick="location.href='/member/list'">목록</button>
  </div>
</div>
</body>
</html>
