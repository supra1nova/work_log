<%--
  Created by IntelliJ IDEA.
  User: kwangdoo
  Date: 2023-08-09
  Time: 오후 1:50
  To change this template use File | Settings | File Templates.
--%>
<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<html>
<head>
  <title>Login</title>
  <link href="/css/style.css" rel="stylesheet">
  <style>
    input[type=text], input[type=password], select, textarea {
      width: 100%;
      height: 35px;
      padding: 5px;
      border: none;
    }
  </style>
</head>
<body>
<div class="container">
  <form method="post" action="/auth/login" autocomplete="off" style="width: 600px; height: 300px; margin: 200px auto 0; background-color: beige; display: flex; flex-direction: column; justify-content: center">
    <div style="display: flex; justify-content: center"><span style="font-size: 40px; font-weight: bolder">Login</span></div>
    <div>
      <table style="width: 400px; margin: 0 auto 15px; background-color: white">
        <tbody>
          <tr style="height: 30px">
            <td class="tc"><input type="text"  name="loginId" placeholder="아이디를 입력해 주세요"></td>
          </tr>
          <tr style="height: 30px">
            <td class="tc"><input type="password" name="loginPassword" placeholder="암호를 입력해 주세요"></td>
          </tr>
        </tbody>
      </table>

      <c:if test="${!empty param.get('error')}">
        <div class="tc mb10" style="color: firebrick; font-weight: bolder">아이디 또는 패스워드가 일치하지 않습니다.</div>
      </c:if>

      <div style="display: flex; justify-content: space-evenly">
        <button>로그인</button>
        <button>회원가입</button>
      </div>

    </div>
  </form>
</div>
</body>
</html>
