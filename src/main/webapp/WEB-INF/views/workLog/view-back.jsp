<%--
  Created by IntelliJ IDEA.
  User: kwangdoo
  Date: 2023-09-04
  Time: 오후 6:34
  To change this template use File | Settings | File Templates.
--%>
<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<html>
<head>
  <title>Simple  view</title>
  <link rel="stylesheet" href="/css/style.css">
  <script src="/js/libs/jquery-3.7.0.min.js"></script>
  <script src="/js/common/common.js"></script>
</head>
<body>
<jsp:include page="../_include/header.jsp"/>

<div class="container">
  <div>Simple  view page</div>

  <table class="mb5">
    <colgroup>
      <col style="min-width: 100px">
      <col style="min-width: 150px">
      <col style="min-width: 100px">
      <col style="min-width: 150px">
    </colgroup>
    <tbody>
      <tr>
        <th>제목</th>
        <td colspan="3">${info.title}</td>
      </tr>
      <tr>
        <th>작성자</th>
        <td class="tc">${info.regId}</td>
        <th>작성일시</th>
        <td class="tc">${info.regDatetime}</td>
      </tr>
      <c:if test="${!empty info.updDatetime}">
        <tr>
          <th>수정자</th>
          <td class="tc">${info.updId}</td>
          <th>수정일시</th>
          <td class="tc">${info.updDatetime}</td>
        </tr>
      </c:if>
      <tr>
        <th>내용</th>
<%--        <td colspan="3" style="height: 310px; vertical-align: top">${info.content}</td>--%>
        <td colspan="3" style="height: 310px; vertical-align: top"></td>
      </tr>
    </tbody>
  </table>
  <div>
    ${info.content}
  </div>

  <form method="post" action="${pageContext.request.contextPath}/simple-/delete" hidden>
    <input type="text" name="Seq" value="${seq}" hidden readonly>
  </form>

  <div class="tr mb150" >
    <%-- TODO: 본인 글에 답글 달기 제한 --%>
    <button type="button" onclick="location.href='${pageContext.request.contextPath}/simple-/reply/${seq}'">답글작성</button>
    <button type="button" onclick="location.href='${pageContext.request.contextPath}/simple-/update/${seq}'">수정</button>
    <button type="button" onclick="$('form').submit()">삭제</button>
    <button type="button" onclick="location.href='${pageContext.request.contextPath}/simple-/list'">목록</button>
  </div>
</div>
</body>
</html>
