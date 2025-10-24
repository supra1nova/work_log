<%--
  Created by IntelliJ IDEA.
  User: kwangdoo
  Date: 2023-09-19
  Time: 오후 3:33
  To change this template use File | Settings | File Templates.
--%>
<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<html>
<head>
  <title>Ajax file bbs view</title>
  <link rel="stylesheet" href="/css/style.css">
  <script src="/js/libs/jquery-3.7.0.min.js"></script>
  <script src="/js/common/common.js"></script>
</head>
<body>
<jsp:include page="../_include/header.jsp"/>

<div class="container">
  <div>Ajax file bbs view</div>

  <input type="text" name="bbsSeq" value="${bbsSeq}" hidden readonly>
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
      <td colspan="3">${info.bbsTitle}</td>
    </tr>
    <tr>
      <th>작성자</th>
      <td class="tc">${info.bbsRegId}</td>
      <th>작성일시</th>
      <td class="tc">${info.bbsRegDatetime}</td>
    </tr>
    <c:if test="${!empty info.bbsUpdDatetime}">
      <tr>
        <th>수정자</th>
        <td class="tc">${info.bbsUpdId}</td>
        <th>수정일시</th>
        <td class="tc">${info.bbsUpdDatetime}</td>
      </tr>
    </c:if>
    <tr>
      <th>내용</th>
      <td colspan="3" style="height: 310px; vertical-align: top">${info.bbsContent}</td>
    </tr>
    <tr>
      <th>첨부파일</th>
      <td colspan="3">
        <c:if test="${!empty fileList}">
          <c:forEach items="${fileList}" var="file">
            <div>
              <a href="/ajax-file-bbs/download/${file.bbsSeq}/${file.fileTargetName}" target="_blank"><span style="display: inline-block; width: 18px; height: 18px; text-align: center; vertical-align: center; margin-top: 3px; background-color: dodgerblue; color: white; border-radius: 5px">&#8595;</span></a>
              <a href="/ajax-file-bbs/download/${file.bbsSeq}/${file.fileTargetName}" target="_blank">${file.fileSourceName}</a>&nbsp;&nbsp;
              <a href="/ajax-file-bbs/download/${file.bbsSeq}/${file.fileTargetName}" style="font-size: 12px" target="_blank">${file.fileSize} byte</a>
            </div>
          </c:forEach>
        </c:if>
      </td>
    </tr>
    </tbody>
  </table>

  <div class="tr mb150" >
    <%-- TODO: 본인 글에 답글 달기 제한 --%>
    <button type="button" onclick="location.href='/ajax-file-bbs/update/${bbsSeq}'">수정</button>
    <button type="button" onclick="remove()">삭제</button>
    <button type="button" onclick="location.href='/ajax-file-bbs/list'">목록</button>
  </div>
</div>
<script>
  const remove = () => {
    const bbsSeq = $('input[name=bbsSeq]').val();
    $.post({
      url: '/ajax-file-bbs/delete',
      data: JSON.stringify({'bbsSeq': bbsSeq}),
      contentType: 'application/json',
      dataType: 'json',
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
    })
  }
</script>
</body>
</html>
