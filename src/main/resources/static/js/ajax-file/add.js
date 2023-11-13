$(function(){
  // 파일 선택 이벤트
  $('#tempFileUpload').on('change', addFiles)

  // 파일 삭제 이벤트
  $(document).on('click', '.addFileList > div', removeFile)
})

// 파일 선택 함수
const addFiles = e => {
  const tempFileArr = $(e.currentTarget)[0].files;

  const sourceFiles = $('#fileUpload')[0].files;
  const dataTransfer = new DataTransfer();

  Array.from(sourceFiles).forEach(file => dataTransfer.items.add(file));
  Array.from(tempFileArr).forEach(file => dataTransfer.items.add(file));

  for (let i = 0; i < tempFileArr.length; i++) {
    $('.addFileList').append(`<div><span style="background-color: red; display: inline-block; width: 18px; height: 18px; text-align: center; color: white; font-weight: bolder; border-radius: 5px">-</span>&nbsp;<span style="cursor: pointer">${tempFileArr[i].name}</span>&nbsp;&nbsp;&nbsp;&nbsp;<span style="font-size: 12px">${tempFileArr[i].size} byte</span><div>`)
  }

  $('#fileUpload')[0].files = dataTransfer.files;

  $('#tempFileUpload')[0].files = new DataTransfer().files;
}

// 파일 삭제 함수
const removeFile = e => {
  const fileIdx = $('.addFileList > div').index(e.currentTarget);

  const sourceFiles = $('#fileUpload')[0].files;

  const dataTransfer = new DataTransfer();

  const sourceFileArr = Array.from(sourceFiles);
  sourceFileArr.splice(fileIdx, 1);
  sourceFileArr.forEach(sourceFile => dataTransfer.items.add(sourceFile));

  $('#fileUpload')[0].files = dataTransfer.files;

  $(e.currentTarget).remove();
}

const saveData = (additionalContent= undefined) => {
  const $form = $('form[method=post]');
  const $formData = new FormData($form[0]);

  if (typeof additionalContent !== "undefined" ||  additionalContent !== null || additionalContent !== "") {
    $formData.append("content", additionalContent);
  }

  const success = data => {
    if(data.result) {
      alert(data.description);
      new Function(data.callback)();
    } else {
      alert(data.description);
    }
  };
  const error = () => {
    alert("알 수 없는 에러가 발생했습니다");
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
