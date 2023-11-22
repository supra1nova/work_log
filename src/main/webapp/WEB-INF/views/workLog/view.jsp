<%--
  Created by IntelliJ IDEA.
  User: kwangdoo
  Date: 2023-09-04
  Time: 오후 6:34
  To change this template use File | Settings | File Templates.
--%>
<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<html>
<head>
  <title>Work-log view</title>
  <link href="/css/style.css" rel="stylesheet">
  <style>
    .ck-editor__editable_inline:not(.ck-comment__input *) {
      /*height: 500px;*/
      overflow-y: auto;
    }
    /* style.css 에서 공통적으로 적용되는 pagination ul, li 가 있음 -> 불릿 목록 표기에 영향이 있으므로 일단은 아래 내용으로 적용*/
    ul, ol {
      display: block;
      margin-top: 0;
      margin-left: 2.666em;
      margin-bottom: 0.8em !important;
      padding: 0.2em 0 0 0;
      line-height: 1.6em;
    }
    li {
      width: 100%;
      padding: 0 10px;
      text-align: left;
    }
    [rowspan="7"] {
      vertical-align: top;
    }
  </style>
  <script src="/js/libs/jquery-3.7.0.min.js"></script>
  <script src="/js/libs/ckeditor.js"></script>
  <script src="/js/ajax-file/add.js"></script>
  <script src="/js/common/common.js"></script>
  <script type="module">
    import editor from '/js/ckeditor/ck-custom-uploader.js';

    $(function () {
      editor($('#editor').get(0))
        .then(insEditor => {
          // 툴바 dom 가져와서 설정
          const toolbarContainer = $('#toolbar-container').get(0);
          toolbarContainer.appendChild( insEditor.ui.view.toolbar.element );

          insEditor.setData(`<p>${info.workLogContent}</p>`)

          // html 스펠 체크 해제 이벤트
          insEditor.editing.view.change( writer => {
            writer.setAttribute( 'spellcheck', 'false', insEditor.editing.view.document.getRoot() );
          } );

          insEditor.enableReadOnlyMode('ck-toolbar');
          insEditor.enableReadOnlyMode('ck-content');
          insEditor.ui.view.toolbar.element.style.display = 'none';
        });

      // save 버튼 이벤트
      $('#deleteBtn').on('click', deleteArticle)
    })

    const deleteArticle = () => {
      const seq = $('input[name=seq]').val();

      const beforeSend = () => {
        if(!confirm("현재 글을 삭제 할까요?")){
          return false;
        }
      }
      const ajaxOptions = {
        url: '/work-log/delete',
        data: JSON.stringify({workLogSeq: seq}),
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
  <div>Work-log view page</div>

  <input type="text" name="seq" value="${info.workLogSeq}" hidden readonly>

  <div style="display: flex; justify-content: space-between">
    <div>
      <span>작성일시: ${info.regDatetime}</span>
    </div>
    <c:if test="${!empty info.updDatetime}">
      <div>
        <span>수정일시: ${info.updDatetime}</span>
      </div>
    </c:if>
  </div>

  <div class="document-container">
    <div id="toolbar-container"></div>
    <div class="editor-container">
      <div id="editor"></div>
    </div>
  </div>

  <div>
    <div>첨부파일</div>
    <div>
      <c:if test="${!empty fileList}">
        <c:forEach items="${fileList}" var="file">
          <div>
            <a href="/work-log/download/${file.bbsSeq}/${file.fileTargetName}" target="_blank"><span style="display: inline-block; width: 18px; height: 18px; text-align: center; vertical-align: center; margin-top: 3px; background-color: dodgerblue; color: white; border-radius: 5px">&#8595;</span></a>
            <a href="/work-log/download/${file.bbsSeq}/${file.fileTargetName}" target="_blank">${file.fileSourceName}</a>&nbsp;&nbsp;
            <a href="/work-log/download/${file.bbsSeq}/${file.fileTargetName}" style="font-size: 12px" target="_blank">${file.fileSize} byte</a>
          </div>
        </c:forEach>
      </c:if>
    </div>
  </div>


  <div class="tr mb150" >
    <c:if test="${memberId.equals(info.regId)}">
      <button type="button" onclick="location.href='${pageContext.request.contextPath}/work-log/update?workLogSeq=${info.workLogSeq}'">수정</button>
      <button type="button" id="deleteBtn">삭제</button>
    </c:if>
    <button type="button" onclick="location.href='${pageContext.request.contextPath}/work-log-calendar?calMonth=${info.workLogDate.substring(0, 7)}'">목록</button>
  </div>
</div>
</body>
</html>
