$(function(){
  // 파일 선택 이벤트
  $('#tempFileUpload').on('change', () => CommonUtils.generateUploadedFileTag($('#fileUpload').get(0), $('.addFileList').get(0)));

  // 파일 삭제 이벤트
  $(document).on('click', '.addFileList > div', event => CommonUtils.removeUploadedFileTag(event, $('#fileUpload').get(0), $('.addFileList').get(0), 'div'));
})

/**
 * 글 및 이미지 저장 함수
 * @param {String} additionalContentFieldName form data에 추가로 저장될 parameter name
 * @param {String} additionalContent form data에 추가로 저장될 parameter value
 *
 */
const saveData = (additionalContentFieldName = undefined, additionalContent= undefined) => {
  const $form = $('form[method=post]');
  const $formData = new FormData($form[0]);

  if (( typeof additionalContentFieldName !== "undefined" ||  additionalContentFieldName !== null || additionalContentFieldName !== "" ) && ( typeof additionalContent !== "undefined" ||  additionalContent !== null || additionalContent !== "" )) {
    $formData.append(additionalContentFieldName, additionalContent);
  }

  const success = data => {
    if(data.result) {
      alert(data.description);
      new Function(data.callback)();
    } else {
      alert(data.description);
    }
  };
  const error = data => {
    const result = data.responseJSON;
    if(data.status === 406) {
      // status 406 NOT_ACCEPTABLE 발생시 -> 반복문으로 validation error 가져와서 alert 에 표기;
      const errorMsg = result.invalidMessage
      alert( Object.values(errorMsg).join(', '));
    } else {
      //나머지 에러 발생 건에 대해서는 aleft 에 기본 description 메세지 표기
      alert(result.description);
    }
  }
  const options = {
    method: $form.prop("method"),
    url: $form.prop('action'),
    data: $formData,
    // contentType: 'application/x-www-form-urlencoded',
    // contentType: 'application/json',
    contentType: false,
    processData: false, // data 값을 query string 으로 변환하는 옵션, 기본값은 true. 따라서 파일 없이 ajax 통신할 경우 별다른 옵션 없이 contentyType만 application/json으로 설정하면 알아서 날아감
    enctype: 'multipart/form-data',
    dataType: 'json',
    success: success,
    error: error,
    async: false,
  }
  $.ajax(options);
}
