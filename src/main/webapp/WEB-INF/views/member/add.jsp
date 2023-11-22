<%--
  Created by IntelliJ IDEA.
  User: kwangdoo
  Date: 2023-09-08
  Time: 오후 5:50
  To change this template use File | Settings | File Templates.
--%>
<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<html>
<head>
  <title>Member add</title>
  <link rel="stylesheet" href="/css/style.css">
  <script src="/js/libs/jquery-3.7.0.min.js"></script>
</head>
<body>
<%@ include file="../_include/header.jsp" %>

<div class="container">
  <div>Member add page</div>

  <form method="post" action="/member/add" autocomplete="off">
    <table class="mb5" style="width: 80%; margin-left: auto; margin-right: auto">
      <colgroup>
        <col style="min-width: 200px">
        <col style="min-width: 400px">
      </colgroup>
      <tbody>
        <tr>
          <th>아이디</th>
          <td class="tc"><input type="text" name="memberId" placeholder="아이디를 입력해 주세요"></td>
        </tr>
        <tr>
          <th>패스워드</th>
          <td class="tc"><input type="password" name="memberPassword" placeholder="비밀번호를 입력해 주세요"></td>
        </tr>
        <tr>
          <th>패스워드 확인</th>
          <td class="tc"><input type="password" name="memberConfPassword" placeholder="비밀번호를 다시 한번 입력해 주세요"></td>
        </tr>
        <tr>
          <th>이름</th>
          <td class="tc"><input type="text" name="memberName" placeholder="이름을 입력해 주세요"></td>
        </tr>
        <tr>
          <th>이메일</th>
          <td class="tc"><input type="text" name="memberEmail" placeholder="이메일을 입력해 주세요"></td>
        </tr>
      </tbody>
    </table>

    <div class="tr mb150" style="width: 80%; margin-left: auto; margin-right: auto">
      <button>저장</button>
      <button type="button" onclick="location.href='/member/list'">취소</button>
    </div>
  </form>
</div>
</body>
<script>
  $(function(){
    // textarea에 포커스를 둔 뒤 내용을 입력 -> 포커스를 마지막으로 이동하도록 처리
    $('input[type=text]').eq(0).focus();
  })
</script>
</html>
