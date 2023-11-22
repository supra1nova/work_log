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
    const memberRole = '${role}';

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
              $('.container').children(':last').height($('.container').children(':last').height() + 100);
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

              const $coreDiv = $('<div>').css({border: '1px solid darkgrey', minWidth: '60%', display: 'flex', justifyContent: 'space-between', alignItems: 'center',}).addClass('day-article');
              $coreDiv.css({width: memberRole === 'STAFF' ? '75%' : '100%', });
              const $innerFirstDiv = $('<div>').css({width: '55%', paddingLeft: '20px', display: 'flex', justifyContent: 'space-between', alignItems: 'center',});
              if (active === 'N') $innerFirstDiv.css('color', 'red');

              const $innerFirstCoreDiv = $('<div>').text(calendarObj.calDate + ' ' + calendarObj.calDayName);
              $innerFirstDiv.append($innerFirstCoreDiv);

              if(memberRole !== 'STAFF') {
                const $innerFirstCoreBtn = $('<button>').text(active === 'Y' ? '휴무일 전환' : '근무일 전환').on('click', updateCalendar);
                $innerFirstCoreBtn.get(0).dataset.calDate = calendarObj.calDate;
                $innerFirstCoreBtn.get(0).dataset.calMonth = calendarObj.calMonth;
                $innerFirstCoreBtn.get(0).dataset.active = calendarObj.active === 'Y' ? 'N' : 'Y';
                $innerFirstDiv.append($innerFirstCoreBtn);
              }

              $coreDiv.append($innerFirstDiv);

              const $coreSecondDiv = $('<div>').css({width: '250px', margin: 'auto 0',});

              if (active === 'Y') {
                const $innerSecondDiv = $('<div>').css(memberRole === 'STAFF' ? {width: '32%', textAlign: 'right', paddingRight: '20px', color: 'red',} : {width: '40%',});

                for(const logObj of logList){
                  if(calendarObj.calDate === logObj.calDate ){
                    if(memberRole === 'MANAGER'){
                      const $innerSecondCoreDiv = $('<div>').css({border: '1px solid darkgrey', margin: '5px', display: 'flex', justifyContent: 'space-between', alignItems: 'center',});
                      $innerSecondCoreDiv.css({color: logObj.contentActive === 'Y' ? 'green' : 'red'});
                      const $innerSecondCoreDivFirstSpan = $('<span>').css({margin: '5px',}).text(logObj.contentRegName);
                      // 미작성 elem 생성
                      const $innerSecondCoreDivSecondSpan = $('<span>').css({padding: '5px', color: 'red',}).text('미작성');
                      // 보기 버튼 elem 생성
                      const $innerSecondCoreDivBtn = $('<button>').css({margin: '5px',}).prop('type', 'button').text('보기').on('click', () => location.href=`/work-log/view?workLogSeq=\${logObj.contentSeq}`);

                      $innerSecondCoreDiv.append($innerSecondCoreDivFirstSpan);
                      $innerSecondCoreDiv.append(logObj.contentActive === 'Y' ? $innerSecondCoreDivBtn : $innerSecondCoreDivSecondSpan);

                      $innerSecondDiv.append($innerSecondCoreDiv);
                    }

                    if(memberRole === 'STAFF') {
                      $innerSecondDiv.text(logObj.contentActive === 'Y' ? '작성 완료' : '미작성').css({color: logObj.contentActive === 'Y' ? 'green' : 'red'});
                      // 수정, 삭제 버튼 elem 생성
                      if(logObj.contentActive === 'Y'){
                        const modBtn = $('<button>').css({backgroundColor: "#ffffec", marginLeft: "5px",}).text('수정').on('click', () => location.href=`/work-log/update?workLogSeq=\${logObj.contentSeq}`);
                        const delBtn = $('<button>').css({backgroundColor: "#faf0f0", marginLeft: "5px",}).text('삭제').on('click', () => deleteArticle(logObj.contentSeq));
                        $coreSecondDiv.append(modBtn)
                        $coreSecondDiv.append(delBtn)
                      }
                      // 등록 버튼 elem 생성
                      if(logObj.contentActive !== 'Y'){
                        const regBtn = $('<button>').css({backgroundColor: "#e3efff", marginLeft: "5px",}).text('등록').on('click', () => location.href=`/work-log/add?workLogDate=\${calendarObj.calDate}`);
                        $coreSecondDiv.append(regBtn);
                      }
                    }
                  }
                }

                $coreDiv.append($innerSecondDiv);
              }

              $outerDiv.append($coreDiv);

              if(memberRole === 'STAFF'){
                $outerDiv.append($coreSecondDiv);
              }

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
          alert(data.description)
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
          console.log(data);
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
<%@ include file="../_include/header.jsp" %>

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
              <div class="day-article" style="width: 75%; border: 1px solid darkgrey; min-width: 60%; display: flex; justify-content: space-between; padding: 0; <c:if test="${!empty item.contentActive}"> cursor: pointer; </c:if> " <c:if test="${!empty item.contentSeq}"> onclick="location.href='/work-log/view?workLogSeq=${item.contentSeq}'" </c:if> >
                <div style="width: 55%; padding-left: 20px; display: flex; justify-content: space-between; align-items: center; <c:if test="${item.active.equals('N')}">color: red</c:if>" >
                  <div>${item.calDate} ${item.calDayName}</div>
                </div>
                <c:if test="${!(item.calDayName.equals('토') || item.calDayName.equals('일'))}">
                  <div style="width: 32%; text-align: right; padding-right: 20px; color:${item.contentActive.equals('Y') ? 'green' : 'red'}" >${item.contentActive.equals('Y') ? '작성 완료' : '미작성'}</div>
                </c:if>
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
