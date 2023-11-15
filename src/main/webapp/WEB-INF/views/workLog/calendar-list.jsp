<%--
  Created by IntelliJ IDEA.
  User: kwangdoo
  Date: 2023-09-04
  Time: 오후 6:33
  To change this template use File | Settings | File Templates.
--%>
<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@ taglib prefix="fmt" uri="http://java.sun.com/jsp/jstl/fmt" %>
<html>
<head>
  <title>Simple  list</title>
  <link rel="stylesheet" href="/css/style.css">
  <style>
    .day-article:hover{
      background-color: #f5dddd;
    }
  </style>
  <script src="/js/libs/jquery-3.7.0.min.js"></script>
  <script src="/js/common/common.js"></script>
  <script>
    const deleteArticle = () => {
      const workLogDate = $(event.target).eq(0).data('workLogDate');

      const beforeSend = () => {
        if(!confirm("현재 글을 삭제 할까요?")){
          return false;
        }
      }
      const ajaxOptions = {
        url: '/work-log/delete-by-cal-date',
        data: JSON.stringify({workLogDate: workLogDate}),
        contentType: 'application/json',
        dataType: 'json',
        beforeSend: beforeSend,
        success: function(data) {
          if(data.result) {
            alert(data.description);
            new Function(data.callback)();
          } else {
            alert(data.description);
          }
        },
        error: function(data) {
          alert(data.description);
        }
      }

      $.post(ajaxOptions)
    }
  </script>
</head>
<body>
<jsp:include page="../_include/header.jsp"/>

<div class="container">
  <div>Work log list page</div>

  <div style="width: 100%; margin: 0 auto; vertical-align: center; line-height: 40px;">
    <div style="margin: 0.8em auto; text-align: center;">
      <span style="font-size: 45px; font-weight: bolder; margin: 0 50px; cursor: pointer"><</span>
      <span style="font-size: 45px; font-weight: bolder">11월(static)</span>
      <span style="font-size: 45px; font-weight: bolder; margin: 0 50px; cursor: pointer">></span>
    </div>
    <div>
      <c:set var="today" value="<%=new java.util.Date()%>" />
      <c:forEach items="${list}" var="item">
        <%-- TODO: WorkLog 정보 가져와서 각 날짜에 해당하는 데이터 있는지 여부 확인 후 분기 -> 없어서 작성 또는 있으므로 수정/삭제 --%>
        <c:if test="${!empty item.contentActive}">
          <a href="/work-log/view-by-cal-date?workLogDate=${item.calDate}">
        </c:if>
          <div style="display: flex; justify-content: left; margin-bottom: 10px;">
            <div class="day-article" style="width: 82%; border: 1px solid darkgrey; min-width: 60%;">
              <span style="padding-left: 20px; <c:if test="${item.calDayName.equals('토') || item.calDayName.equals('일')}">color: red</c:if>" >
  <%--              <c:if test="${!empty item.contentActive}">--%>
  <%--                <a href="/work-log/view-by-cal-date?workLogDate=${item.calDate}">--%>
  <%--              </c:if>--%>
                  ${item.calDate} ${item.calDayName}
  <%--              <c:if test="${!empty item.contentActive}">--%>
  <%--                </a>--%>
  <%--              </c:if>--%>
              </span>
            </div>
            <div style="width: 220px; margin: auto 0">
              <%-- TODO: 관리자 계정의 경우 휴일로 전환 버튼 보이기 --%>
              <c:if test="${item.active.equals('Y')}">
                <fmt:formatDate var="currDate" value="${today}" pattern="yyyy-MM-dd" />
                <c:if test="${empty item.contentActive && currDate >= item.calDate}">
                  <button type="button" onclick="location.href='/work-log/add?workLogDate=${item.calDate}'">등록</button>
                </c:if>
                <c:if test="${!empty item.contentActive && !item.contentActive.equals('N')}">
                  <button type="button" onclick="location.href='/work-log/update-by-cal-date?workLogDate=${item.calDate}'">수정</button>
                  <button type="button" onclick="deleteArticle()" data-work-log-date="${item.calDate}">삭제</button>
                </c:if>
              </c:if>
            </div>
          </div>
        <c:if test="${!empty item.contentActive}">
          </a>
        </c:if>
      </c:forEach>
    </div>
  </div>

</div>
</body>
</html>
