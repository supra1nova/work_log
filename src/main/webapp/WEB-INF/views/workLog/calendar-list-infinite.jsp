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
    $(function() {
      let availableToLoadList = true;
      $(window).on('scroll', async function () {
        // console.log(window.height)
        // console.log(window.innerHeight)
        // console.log(window.scrollY)
        if (availableToLoadList === true) {
          if (window.innerHeight + window.scrollY + 100 > document.body.scrollHeight) {
            availableToLoadList = false;

            const ajaxResult = await getArticleList($('.day-article:last').parent().data('calDate'));

            if(typeof ajaxResult === 'undefined'){
              return null;
            }

            const calendarList = ajaxResult.data.calendarList;
            const logList = ajaxResult.data.list;

            if (calendarList.length < 0) return false;

            const $divWrapper = $('.container > div:nth-child(2) > div:nth-child(2)');

            for (const calendarObj of calendarList) {
              const $outerDiv = $('<div>').css({display: 'flex', justifyContent: 'left', marginBottom: '10px'});
              const active = calendarObj.active;
              $outerDiv.get(0).dataset.active = active;
              $outerDiv.get(0).dataset.calDate = calendarObj.calDate;

              const $coreDiv = $('<div>').css({width: '100%', border: '1px solid darkgrey', minWidth: '60%', display: 'flex', justifyContent: 'space-between', alignItems: 'center',}).addClass('day-article');
              const $innerFirstDiv = $('<div>').css({width: '55%', paddingLeft: '20px', display: 'flex', justifyContent: 'space-between', alignItems: 'center',});
              if (active === 'N') $innerFirstDiv.css('color', 'red');

              const $innerFirstCoreDiv = $('<div>').text(calendarObj.calDate + ' ' + calendarObj.calDayName);
              const $innerFirstCoreBtn = $('<button>').text(active === 'Y' ? '휴무일 전환' : '근무일 전환').on('click', updateCalendar);
              $innerFirstCoreBtn.get(0).dataset.calDate = calendarObj.calDate;
              $innerFirstCoreBtn.get(0).dataset.calMonth = calendarObj.calMonth;
              $innerFirstCoreBtn.get(0).dataset.active = calendarObj.active === 'Y' ? 'N' : 'Y';

              $innerFirstDiv.append($innerFirstCoreDiv);
              $innerFirstDiv.append($innerFirstCoreBtn);

              $coreDiv.append($innerFirstDiv);

              if (active === 'Y') {
                const $innerSecondDiv = $('<div>').css({width: '40%',});
                for(const logObj of logList){
                  if(calendarObj.calDate === logObj.calDate ){
                    const $innerSecondCoreDiv = $('<div>').css({border: '1px solid darkgrey', margin: '5px', display: 'flex', justifyContent: 'space-between', alignItems: 'center',});
                    $innerSecondCoreDiv.css({color: logObj.contentActive === 'Y' ? 'green' : 'red'});
                    const $innerSecondCoreDivFirstSpan = $('<span>').css({margin: '5px',}).text(logObj.contentRegName);

                    const $innerSecondCoreDivSecondSpan = $('<span>').css({padding: '5px', color: 'red',}).text('미작성');
                    const $innerSecondCoreDivBtn = $('<button>').css({margin: '5px',}).text('보기').on('click', () => location.href=`/work-log/view?workLogSeq=\${logObj.contentSeq}`);

                    $innerSecondCoreDiv.append($innerSecondCoreDivFirstSpan);
                    $innerSecondCoreDiv.append(logObj.contentActive === 'Y' ? $innerSecondCoreDivBtn : $innerSecondCoreDivSecondSpan);

                    $innerSecondDiv.append($innerSecondCoreDiv);

                  }
                }
                $coreDiv.append($innerSecondDiv);
              }

              $outerDiv.append($coreDiv);

              $divWrapper.append($outerDiv);
            }
            availableToLoadList = true;
          }
        }
      });
    })

    const cloneWorkingDateElem = ($elem) => {
      if($elem.data('active') !== 'Y') return cloneWorkingDateElem($elem.siblings('div').last());
      return $elem.clone();
    }

    const getArticleList = calDate => {
      const option = {
        url: '/work-log-calendar/list?calDate=' + calDate,
        dataType: 'json',
        success: (data, textStatus, xhr) => {
          if(xhr.status === 204){
            alert('더 이상 데이터가 존재하지 않습니다')
          }
          return data;
        },
        error: data => {
          alert('더 이상 데이터가 존재하지 않습니다')
          return null;
        },
      }

      return $.get(option);
    }

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
      dataToSend.active = active;
      dataToSend.calMonth = calMonth;

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
          // controller 에서 result 가 true 가 아니면 모두 에러 처리
          alert(data.description);
          new Function(data.callback)();
        },
        error: function(data) {
          // 업데이트 로직에 들어가는 변수는 기본적으로 사용자가 임의 컨트롤 불가 -> invalid message 개별로 띄워줄 필요는 없을 듯
          alert(data.responseJSON.description);
          new Function(data.responseJSON.callback)();
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
    <div style="margin: 0.8em 0.8em 2.5em; text-align: center; display: flex; justify-content: center">
      <div style="font-size: 45px; font-weight: bolder">${calendarList.get(0).calMonth.substring(5, 7)}월</div>
    </div>

    <div>
      <%-- 일반 STAFF 의 경우 진행 --%>
      <c:if test="${role.equals('STAFF')}">
        <c:forEach items="${list}" var="item">
          <c:if test="${currDate >= item.calDate}">
            <div style="display: flex; justify-content: left; margin-bottom: 10px;" data-active="${item.active}" data-cal-date="${item.calDate}">
              <div class="day-article" style="width: 82%; border: 1px solid darkgrey; min-width: 60%; display: flex; justify-content: space-between; padding: 0; <c:if test="${!empty item.contentActive}"> cursor: pointer; </c:if> " <c:if test="${!empty item.contentSeq}"> onclick="location.href='/work-log/view?workLogSeq=${item.contentSeq}'" </c:if> >
                <span style="padding-left: 20px; <c:if test="${item.active.equals('N')}">color: red</c:if>" >${item.calDate} ${item.calDayName} <c:if test="${empty item.contentSeq}">${item.contentRegName}</c:if></span>
                <c:if test="${(item.active.equals('Y')) && !empty item.contentSeq}"><span style="padding-right: 20px;" >${item.contentRegName}</span></c:if>
              </div>
              <div style="width: 250px; margin: auto 0">
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

      <%-- MANAGER 의 경우 진행 --%>
      <c:if test="${role.equals('MANAGER')}">
        <c:forEach items="${calendarList}" var="item">
          <c:if test="${currDate >= item.calDate}">
<%--            <div style="display: flex; justify-content: left; margin-bottom: 10px;" data-active="${item.active}" data-cal-month="${item.calMonth}">--%>
            <div style="display: flex; justify-content: left; margin-bottom: 10px;" data-active="${item.active}" data-cal-date="${item.calDate}">
              <div class="day-article" style="width: 100%; border: 1px solid darkgrey; min-width: 60%; display: flex; justify-content: space-between; align-items: center; " >
                <div style="width: 55%; padding-left: 20px; display: flex; justify-content: space-between; align-items: center; <c:if test="${item.active.equals('N')}">color: red</c:if>" >
                  <div>${item.calDate} ${item.calDayName}</div>
                  <c:if test="${!(item.calDayName.equals('토') || item.calDayName.equals('일'))}">
                    <button type="button" onclick="updateCalendar()" data-cal-month="${item.calMonth}" data-cal-date="${item.calDate}" data-active="${item.active.equals('Y') ? 'N' : 'Y'}">${item.active.equals('Y') ? '휴무일 전환' : '근무일 전환'}</button>
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
