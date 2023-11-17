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
  <%-- /favicon.ico 404 (Not Found) 관련 에러 해결 위한 코드 : data-url 표기 형식으로 처리 --%>
  <link rel="icon" href="data:image/x-icon;," type="image/x-icon">
  <style>
    .day-article:hover{
      background-color: #f5dddd;
    }
  </style>
  <script src="/js/libs/jquery-3.7.0.min.js"></script>
  <script src="/js/common/common.js"></script>
  <script>
    const deleteArticle = (contentSeq) => {
      const beforeSend = () => {
        if(!confirm("현재 글을 삭제 할까요?")){
          return false;
        }
      }

      const ajaxOptions = {
        url: '/work-log/delete',
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

    const updateCalendar = () => {
      const calDate = $(event.target).data('calDate');
      const active = $(event.target).data('active')
      const calMonth = $(event.target).data('calMonth')

      const dataToSend = {};
      dataToSend.calDate = calDate;
      // dataToSend.calDate = null;
      // dataToSend.calDate = '2023-11-18';

      dataToSend.active = active;
      // dataToSend.active = 'F';
      // dataToSend.active = null;
      // dataToSend.active = '';

      dataToSend.calMonth = calMonth;
      // dataToSend.calMonth = '0201-11';
      // dataToSend.calMonth = null;
      // dataToSend.calMonth = '';

      const beforeSend = () => {
        if(!confirm("현재 날짜를 휴일로 전환할까요?")){
          return false;
        }
      }

      const ajaxOptions = {
        url: '/work-log-calendar/update',
        data: JSON.stringify(dataToSend),
        contentType: 'application/json',
        dataType: 'json',
        beforeSend: beforeSend,
        success: function(data) {
          if(data.result) {
            alert(data.description);
            // new Function(data.callback)();
          } else {
            console.log(1111)
            alert(data.description);
          }
        },
        error: function(data) {
          console.log(2222);
          console.log(data.responseJSON);
          alert(data.responseJSON.description);
          // new Function(data.responseJSON.callback)();
          // 업데이트 로직에 들어가는 변수는 사용자가 컨트롤 불가하므로 굳이 invalid message 를 개별로 띄워줄 필요는 없을 듯
          // alert(data.description);
          // new Function(data.callback)();
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

  <div style="width: 80%; margin: 0 auto; vertical-align: center; line-height: 40px;">
    <c:set var="today" value="<%=new java.util.Date()%>" />
    <fmt:formatDate var="currDate" value="${today}" pattern="yyyy-MM-dd" />
    <div style="margin: 0.8em 0.8em 2.5em; text-align: center; display: flex; justify-content: space-between">
      <div>
        <span style="font-size: 45px; font-weight: bolder">
          <c:if test="${prevMonth}">
            <a style="font-size: 45px; font-weight: bolder" href="/work-log-calendar?calMonth=${prevMonthValue}"><</a>
          </c:if>
          <c:if test="${!prevMonth}">
            &nbsp;
          </c:if>
        </span>
      </div>
      <div style="font-size: 45px; font-weight: bolder">${calendarList.get(0).calMonth.substring(5, 7)}월</div>
      <div>
        <span style="font-size: 45px; font-weight: bolder">
          <c:if test="${nextMonth}">
            <a style="font-size: 45px; font-weight: bolder" href="/work-log-calendar?calMonth=${nextMonthValue}">></a>
          </c:if>
          <c:if test="${!nextMonth}">
            &nbsp;
          </c:if>
        </span>
      </div>
    </div>

    <div>
      <c:if test="${role.equals('STAFF')}">
        <%-- 일반 STAFF 의 경우 진행 --%>
        <c:forEach items="${list}" var="item">
          <c:if test="${currDate >= item.calDate}">
            <div style="display: flex; justify-content: left; margin-bottom: 10px;">
              <div class="day-article" style="width: 82%; border: 1px solid darkgrey; min-width: 60%; display: flex; justify-content: space-between; padding: 0; <c:if test="${!empty item.contentActive}"> cursor: pointer; </c:if> " <c:if test="${!empty item.contentSeq}"> onclick="location.href='/work-log/view?workLogSeq=${item.contentSeq}'" </c:if> >
                <span style="padding-left: 20px; <c:if test="${item.active.equals('N')}">color: red</c:if>" >${item.calDate} ${item.calDayName} <c:if test="${empty item.contentSeq}">${item.contentRegName}</c:if></span>
                <c:if test="${(item.active.equals('Y')) && !empty item.contentSeq}"><span style="padding-right: 20px;" >${item.contentRegName}</span></c:if>
              </div>
              <div style="width: 250px; margin: auto 0">
                <%-- TODO: 관리자 계정의 경우 휴일로 전환 버튼 보이기 --%>
                <c:if test="${item.active.equals('Y')}">
                  <c:if test="${empty item.contentActive && currDate >= item.calDate}">
                    <button type="button" onclick="location.href='/work-log/add?workLogDate=${item.calDate}'" style="background-color: #e3efff; margin-left: 5px">등록</button>
                  </c:if>
                  <c:if test="${!empty item.contentActive && !item.contentActive.equals('N')}">
                    <button type="button" onclick="location.href='/work-log/update?workLogSeq=${item.contentSeq}'" style="background-color: #ffffec; margin-left: 5px">수정</button>
                    <button type="button" onclick="deleteArticle('${item.contentSeq}')" style="background-color: #faf0f0; margin-left: 5px">삭제</button>
                  </c:if>
                </c:if>
              </div>
            </div>
          </c:if>
        </c:forEach>
      </c:if>

      <c:if test="${role.equals('MANAGER')}">
        <%-- 일반 STAFF 의 경우 진행 --%>
        <c:forEach items="${calendarList}" var="item">
          <c:if test="${currDate >= item.calDate}">
            <div style="display: flex; justify-content: left; margin-bottom: 10px;">
              <div class="day-article" style="width: 100%; border: 1px solid darkgrey; min-width: 60%; display: flex; justify-content: space-between; align-items: center; " >
                <div style="width: 55%; padding-left: 20px; display: flex; justify-content: space-between; align-items: center; <c:if test="${item.active.equals('N')}">color: red</c:if>" >
                  <div>${item.calDate} ${item.calDayName}</div>
                  <c:if test="${!(item.calDayName.equals('토') || item.calDayName.equals('일'))}">
<%--                    <button type="button" onclick="location.href='/work-log-calendar/update?active=${item.active.equals('Y') ? 'N' : 'Y'}'">${item.active.equals('Y') ? '휴일 전환' : '근무일 전환'}</button>--%>
                    <button type="button" onclick="updateCalendar()" data-cal-month="${item.calMonth}" data-cal-date="${item.calDate}" data-active="${item.active.equals('Y') ? 'N' : 'Y'}">${item.active.equals('Y') ? '휴일 전환' : '근무일 전환'}</button>
                  </c:if>
                </div>
                <div style="width: 40%">
                  <c:forEach items="${list}" var="listItem">
                    <c:if test="${item.active.equals('Y') && item.calDate.equals(listItem.calDate)}">
                      <div style="border: 1px solid darkgrey; margin: 5px; display: flex; justify-content: space-between; align-items: center;
                        <c:if test="${empty listItem.contentSeq}">color: red; </c:if>
                        <c:if test="${!empty listItem.contentSeq}">color: green; </c:if>
                        "
                      >
                        <span style="margin: 5px">${listItem.contentRegName}</span>
                        <c:if test="${empty listItem.contentSeq}">
                          <span style="padding: 5px; color: red">미작성</span>
                        </c:if>
                        <c:if test="${!empty listItem.contentSeq}">
                          <button type="button" style="margin: 5px;" onclick="location.href='/work-log/view?workLogSeq=${listItem.contentSeq}'">보기</button>
                        </c:if>
                      </div>
                    </c:if>
                  </c:forEach>
                </div>
              </div>
            </div>
          </c:if>
        </c:forEach>
      </c:if>

    </div>
  </div>

</div>
</body>
</html>
