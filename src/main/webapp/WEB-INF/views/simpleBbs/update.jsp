<%--
  Created by IntelliJ IDEA.
  User: kwangdoo
  Date: 2023-09-20
  Time: 오전 10:21
  To change this template use File | Settings | File Templates.
--%>
<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<html>
<head>
  <title>Form file bbs update</title>
  <link rel="stylesheet" href="/css/style.css">
  <script src="/js/libs/jquery-3.7.0.min.js"></script>
  <script src="/js/ajax-file-bbs/update.js"></script>
  <script>
    const origBbsContent = `${info.bbsContent}`;
  </script>
</head>
<body>
<jsp:include page="../_include/header.jsp"/>

<div class="container">
  <div>Form file update page</div>

  <form method="post" action="/ajax-file-bbs/update">
    <input type="text" name="bbsSeq" value="${bbsSeq}" hidden readonly>

    <%-- 테이블 내 넓이 조절 위해 table-layout-fixed로 고정 --%>
    <table class="mb5 table-layout-fixed">
      <colgroup>
        <col style="min-width: 100px">
        <col style="min-width: 150px">
        <col style="min-width: 100px">
        <col style="min-width: 150px">
      </colgroup>
      <tbody>
      <tr>
        <th>제목</th>
        <td colspan="3"><input type="text" name="bbsTitle" value="${info.bbsTitle}" placeholder="제목을 입력해 주세요."></td>
      </tr>
      <tr>
        <th>내용</th>
        <td colspan="3" style="height: 300px; vertical-align: top"><textarea name="bbsContent" placeholder="내용을 입력해 주세요.">${info.bbsContent}</textarea></td>
      </tr>
      <tr>
        <th>파일 첨부</th>
        <td colspan="3">
          <input type="text" name="removeFileList" readonly hidden>
          <input type="file" name="uploadFileList" multiple readonly hidden id="fileUpload">
          <input type="file" multiple readonly hidden id="tempFileUpload">
          <label for="tempFileUpload" style="font-weight: bolder; cursor: pointer">+ 파일 업로드</label>

          <c:if test="${!empty fileList}">
            <div class="savedFileList" style="cursor:pointer;">
              <c:forEach items="${fileList}" var="file">
                <div style="cursor:pointer;" data-file-target-name="${file.fileTargetName}">
                  <span style="background-color: red; display: inline-block; width: 18px; height: 18px; text-align: center; color: white; font-weight: bolder; border-radius: 5px">-</span>&nbsp;<span>${file.fileSourceName}</span>&nbsp;&nbsp;&nbsp;&nbsp;<span style="font-size: 12px">${file.fileSize} byte</span>
                </div>
              </c:forEach>
            </div>
          </c:if>
          <div class="addFileList" style="display: flex; flex-direction: column; justify-content: left"></div>
        </td>
      </tr>
      </tbody>
    </table>

    <div class="tr">
      <button type="button" onclick="saveData()">저장</button>
      <button type="button" onclick="location.href=document.referrer">취소</button>
      <button type="button" onclick="location.href='/ajax-file-bbs/list'">목록</button>
    </div>
  </form>
</div>
</body>

</html>
