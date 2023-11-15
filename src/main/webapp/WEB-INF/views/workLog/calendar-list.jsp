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
    // const deleteArticle = () => {
    // const deleteArticle = (regId) => {
    const deleteArticle = (contentSeq) => {
      const workLogDate = $(event.target).eq(0).data('workLogDate');

      const beforeSend = () => {
        if(!confirm("현재 글을 삭제 할까요?")){
          return false;
        }
      }
      const ajaxOptions = {
        // url: '/work-log/delete-by-cal-date',
        url: '/work-log/delete',
        // data: JSON.stringify({workLogDate: workLogDate}),
        // data: JSON.stringify({regId: regId, workLogDate: workLogDate}),
        data: JSON.stringify({workLogSeq: contentSeq}),
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
      <span style="font-size: 45px; font-weight: bolder">11월(static)</span>
    </div>
    <div>
      <c:set var="today" value="<%=new java.util.Date()%>" />
      <fmt:formatDate var="currDate" value="${today}" pattern="yyyy-MM-dd" />
      <c:forEach items="${list}" var="item">
        <c:if test="${currDate >= item.calDate}">
          <div style="display: flex; justify-content: left; margin-bottom: 10px;">
            <div class="day-article" style="width: 82%; border: 1px solid darkgrey; min-width: 60%; <c:if test="${!empty item.contentActive}"> cursor: pointer; </c:if> " <c:if test="${!empty item.contentSeq}"> onclick="location.href='/work-log/view?workLogSeq=${item.contentSeq}'" </c:if> >
              <span style="padding-left: 20px; <c:if test="${item.calDayName.equals('토') || item.calDayName.equals('일')}">color: red</c:if>" >${item.calDate} ${item.calDayName}</span>
            </div>
            <div style="width: 250px; margin: auto 0">
              <%-- TODO: 관리자 계정의 경우 휴일로 전환 버튼 보이기 --%>
              <c:if test="${item.active.equals('Y')}">
                <c:if test="${empty item.contentActive && currDate >= item.calDate}">
                  <button type="button" onclick="location.href='/work-log/add?workLogDate=${item.calDate}'" style="background-color: #e3efff; margin-left: 5px">등록</button>
                </c:if>
                <c:if test="${!empty item.contentActive && !item.contentActive.equals('N')}">
<%--                  <button type="button" onclick="location.href='/work-log/update-by-cal-date?workLogDate=${item.calDate}&regId=${item.contentRegId}'" style="background-color: #ffffec; margin-left: 5px">수정</button>--%>
                  <button type="button" onclick="location.href='/work-log/update?workLogSeq=${item.contentSeq}'" style="background-color: #ffffec; margin-left: 5px">수정</button>
<%--                  <button type="button" onclick="deleteArticle('${item.contentRegId}')" data-work-log-date="${item.calDate}" style="background-color: #faf0f0; margin-left: 5px">삭제</button>--%>
                  <button type="button" onclick="deleteArticle('${item.contentSeq}')" style="background-color: #faf0f0; margin-left: 5px">삭제</button>
                </c:if>
              </c:if>
            </div>
          </div>
        </c:if>
      </c:forEach>
    </div>
  </div>

</div>
</body>
</html>
