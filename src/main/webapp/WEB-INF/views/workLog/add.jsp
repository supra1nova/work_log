<%--
  Created by IntelliJ IDEA.
  User: kwangdoo
  Date: 2023-10-06
  Time: 오후 1:31
  To change this template use File | Settings | File Templates.
--%>
<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<html>
<head>
  <title>Work log add</title>
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
      margin-left: 2em;
      margin-bottom: 0.5em !important;
      padding: 0.2em 0 0 0;
      line-height: 1.6em;
    }
    li {
      width: 100%;
      text-align: left;
    }
    /*.ck-content .table {*/
    /*  height: 550px;*/
    /*}*/
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

          // insEditor.setData(`<p>test</p>`)
          insEditor.setData(`\${testArea}`)

          // html 스펠 체크 해제 이벤트
          insEditor.editing.view.change( writer => {
            writer.setAttribute( 'spellcheck', 'false', insEditor.editing.view.document.getRoot() );
          } );

          $('#editor td:last-child').focus()
        })

      // save 버튼 이벤트
      $('#saveBtn').on('click', () => saveData($('#editor').get(0).innerHTML))
    })

    let testArea = `<p>
      <table style="border: none; width: 100%;">
        <colgroup>
          <col style="width: 15%; min-width: 100px">
          <col style="width: 25%">
          <col style="width: 10%">
          <col style="width: 100px; min-width: 100px">
          <col style="width: 100px; min-width: 100px">
          <col style="width: 100px; min-width: 100px">
          <col style="width: 100px; min-width: 100px">
        </colgroup>
        <thead>
          <tr style="text-align: center;">
            <th style="text-align: center; background-color: darkgrey">부서명</th>
            <td style="text-align: center; background-color: white">개발팀</td>
            <th rowspan="3" style="text-align: center; background-color: darkgrey">결재</th>
            <th style="text-align: center; background-color: darkgrey">담당</th>
            <th style="text-align: center; background-color: darkgrey">팀리드</th>
            <th style="text-align: center; background-color: darkgrey">팀장</th>
            <th style="text-align: center; background-color: darkgrey">대표</th>
          </tr>
          <tr style="text-align: center">
            <th style="text-align: center; background-color: darkgrey">작성자</th>
            <td style="background-color: white; text-align: center">${info.regName}</td>
            <td rowspan="2" style="background-color: white; text-align: center"></td>
            <td rowspan="2" style="background-color: white; text-align: center"></td>
            <td rowspan="2" style="background-color: white; text-align: center"></td>
            <td rowspan="2" style="background-color: white; text-align: center"></td>
          </tr>
          <tr style="text-align: center; height: 100%">
            <th style="text-align: center; background-color: darkgrey">작성일</th>
            <td style="background-color: white; text-align: center">\${new Date().getFullYear() + '년 ' + (new Date().getMonth() + 1) + '월 ' + new Date().getDate() + '일'}</td>
          </tr>
        </thead>
        <tbody>
          <tr>
            <td class="ck-editor__editable ck-editor__nested-editable" role="textbox" contenteditable="true" colspan="7" style="vertical-align: top; height: 300px"></td>
          </tr>
        </tbody>
      </table>
      </p>
    `;
  </script>
</head>
<body>

<div class="container">
  <div>Work log add page</div>

  <form method="post">
    <div class="mb5">
      <input type="text" style="border-radius: 5px" name="title" placeholder="제목을 입력해 주세요.">
    </div>

    <div class="document-container">
      <div id="toolbar-container"></div>

      <div class="editor-container">
        <div id="editor">
          <p>This is the initial editor content.</p>
        </div>
      </div>
    </div>

    <div class="tr" style="margin-top: 10px">
      <button type="button" class="custom-button" id="saveBtn">저장</button>
      <button type="button" class="custom-button" onclick="location.href=document.referrer">취소</button>
    </div>
  </form>

  <div id="test1"></div>

</div>

</body>
</html>
