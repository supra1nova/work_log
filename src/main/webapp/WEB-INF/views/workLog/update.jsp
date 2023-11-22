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
  <title>Simple  update</title>
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

          insEditor.setData(`${info.workLogContent}`)

          $('#editor td:last-child').focus()
        })

      // save 버튼 이벤트
      $('#saveBtn').on('click', () => saveData("workLogContent", $('#editor').get(0).innerHTML))
    })
  </script>
</head>
<body>
<%@ include file="../_include/header.jsp" %>

<div class="container">
  <div>Simple  update page</div>

  <form method="post" action="${pageContext.request.contextPath}/work-log/update" spellcheck="false">
    <input type="text" name="workLogSeq" value="${info.workLogSeq}" hidden readonly>

    <div class="document-container">
      <div id="toolbar-container"></div>

      <div class="editor-container">
        <div id="editor">
          <p>This is the initial editor content.</p>
        </div>
      </div>
    </div>

    <div class="tr">
      <button type="button" class="custom-button" id="saveBtn">저장</button>
      <button type="button" onclick="location.href=document.referrer">취소</button>
    </div>
  </form>
</div>
<%--<script>--%>
<%--  $(function(){--%>
<%--    // textarea에 포커스를 둔 뒤 내용을 입력 -> 포커스를 마지막으로 이동하도록 처리--%>
<%--    $('textarea').focus().val(`${info.Content}`);--%>
<%--  })--%>
<%--</script>--%>
</body>
</html>
