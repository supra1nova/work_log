<%--
  Created by IntelliJ IDEA.
  User: kd
  Date: 2023/09/09
  Time: 2:12 PM
  To change this template use File | Settings | File Templates.
--%>
<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<div class="header">
  <%--  <div class="container tr"  style="display: flex; flex-direction: column; justify-content: space-evenly; height: 100%; padding: 20px 0">--%>
  <div style="display: flex; justify-content: space-between; color: white">
    <%-- TODO: logo image 로 대체 --%>
    <div><a href="/" style="font-size: 50px; color: white">LOGO</a></div>
    <c:if test="${!empty pageContext.request.getSession('loginId')}">
      <div>
        <a style="margin: 0 10px; color: white; font-weight: bolder" href="/account/view/${pageContext.request.getSession('loginId')}">마이 페이지</a>
        <a style="margin: 0 10px; color: white; font-weight: bolder" href="/auth/logout">로그아웃</a>
      </div>
    </c:if>
  </div>
  <div class="tr" style="margin-right: 100px; color: white">
    <span style="margin: 0 10px"><a style="color: white" href="/">메인</a></span>
    <span style="margin: 0 10px"><a style="color: white" href="/member/list">회원 관리</a></span>
    <span style="margin: 0 10px"><a style="color: white" href="/work-log">일일 보고</a></span>
  </div>
</div>