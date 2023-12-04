<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<html>
<head>
  <title>Ajax file bbs view</title>
  <link rel="icon" href="data:image/x-icon" type="image/x-icon">
  <link rel="stylesheet" href="/css/style.css">
  <script src="/js/libs/jquery-3.7.0.min.js"></script>
  <script src="/js/common/common.js"></script>
  <script src="/js/ajax-file/add.js"></script>
</head>
<body>
<jsp:include page="../_include/header.jsp"/>

<div class="container">
  <div>File bbs add page</div>
  <form method="post" action="${pageContext}/bbs/add">
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
      <tr>
        <th>파일 첨부</th>
        <td colspan="3">
          <div>
            <input type="file" name="uploadFileList" multiple readonly hidden id="fileUpload">
            <input type="file" multiple readonly hidden id="tempFileUpload">
            <label for="tempFileUpload" style="font-weight: bolder; cursor: pointer">+ 파일 업로드</label>
          </div>
          <div class="addFileList" style="display: flex; flex-direction: column; justify-content: left"></div>
        </td>
      </tr>
      </tbody>
    </table>

    <div class="tr">
      <button type="button" onclick="saveData()">저장</button>
      <button type="button" onclick="location.href=document.referrer">취소</button>
    </div>
  </form>
</div>
</body>
</html>
