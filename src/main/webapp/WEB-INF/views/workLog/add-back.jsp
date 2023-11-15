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
  <title>Simple bbs view</title>
  <link rel="stylesheet" href="/css/style.css">
  <script src="/js/libs/jquery-3.7.0.min.js"></script>
</head>
<body>
<jsp:include page="../_include/header.jsp"/>

<div class="container">
  <div>Simple bbs add page</div>

  <form method="post">
    <table class="mb5">
      <colgroup>
        <col style="min-width: 100px">
        <col style="min-width: 500px">
      </colgroup>
      <tbody>
      <tr>
        <th>제목</th>
        <td colspan="3"><input type="text" name="bbsTitle" placeholder="제목을 입력해 주세요."></td>
      </tr>
      <tr>
        <th>내용</th>
        <td colspan="3" style="height: 300px; vertical-align: top">
          <textarea name="bbsContent" placeholder="내용을 입력해 주세요."></textarea>
        </td>
      </tr>
      </tbody>
    </table>

    <div class="tr">
      <button>저장</button>
      <button type="button" onclick="location.href='${pageContext.request.contextPath}/simple-bbs/list'">취소</button>
    </div>
  </form>
</div>
</body>
</html>
