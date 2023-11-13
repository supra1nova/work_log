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
<jsp:include page="../_include/header.jsp"/>

<div class="container">
  <div>Work log list page</div>

  <c:forEach items="${list}" var="item">
    <%-- TODO: WorkLog 정보 가져와서 각 날짜에 해당하는 데이터 있는지 여부 확인 후 분기 -> 없어서 작성 또는 있으므로 수정/삭제 --%>
    <div style="border: 1px solid darkgrey">${item.calDate}</div>
  </c:forEach>
<%--  <div class="mb150 tr">--%>
<%--    <button type="button" onclick="location.href='${pageContext.request.contextPath}/work-log/add'">새 글 작성</button>--%>
<%--  </div>--%>

</div>
</body>
</html>
