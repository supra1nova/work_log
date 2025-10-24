const CommonUtils = (() => {
  const finalResult = {};

  const formReset = () => {
    $('#command').reset();
    $('form select option:first').prop('selected', true);
    $('input[type=text]').val('');
    $('form').submit()
  }
  finalResult['formReset'] = formReset;

  // form 객체를 serialize 후 데이터 객체를 반환
  const formToDataMaker = (formObj) => {
    let dataObj = {};
    let serializeArray = formObj.serializeArray();
    $(serializeArray).each((idx, objValue) => {
      if(objValue.value !== null && objValue.value !== undefined && objValue.value !== ''){
        dataObj[objValue.name] = objValue.value;
      }
    })
    return dataObj;
  }
  finalResult['formToDataMaker'] = formToDataMaker;

  const convertNRtoBrSpToNbsp = (strData) => {
    return strData.replaceAll('\n', '<br>').replaceAll(' ', '&nbsp;');
  }
  finalResult['convertNRtoBrSpToNbsp'] = convertNRtoBrSpToNbsp;

  /** convert date to string format yyyy-MM-dd hh:mm:ss */
  const convertDateToStr1 = (date) => {
    let month  = date.getMonth() + 1;
    let day    = date.getDate();
    let hour   = date.getHours();
    let minute = date.getMinutes();
    let second = date.getSeconds();

    month  = month  >= 10 ? month  : '0' + month;
    day    = day    >= 10 ? day    : '0' + day;
    hour   = hour   >= 10 ? hour   : '0' + hour;
    minute = minute >= 10 ? minute : '0' + minute;
    second = second >= 10 ? second : '0' + second;

    return date.getFullYear() + '-' + month + '-' + day + ' ' + hour + ':' + minute + ':' + second;
  }
  finalResult['convertDateToStr1'] = convertDateToStr1;

  /**
   * 파일 업로드 관련 태그 생성 함수
   * @param {string} hiddenInput 실제로 controller 로 넘길 데이터가 담기는 DOM (input element)
   * @param {string} listElem 파일 업로드 후 생성될 태그가 위치할 타겟 DOM (div element)
   */
  const generateUploadedFileTag = (hiddenInput, listElem) => {
    const tempInput = $(event.currentTarget).get(0);
    const tempFileArr = tempInput.files;
    const sourceFiles = hiddenInput.files;

    const dataTransfer = new DataTransfer();

    Array.from(sourceFiles).forEach(file => dataTransfer.items.add(file));
    Array.from(tempFileArr).forEach(file => dataTransfer.items.add(file));

    for (const tempFile of tempFileArr) {
      const $fileSpan1 = $('<span>').css({backgroundColor: 'red', display: 'inline-block', width: '18px', height: '18px', textAlign: 'center', color: 'white', fontWeight: 'bolder', borderRadius: '5px'}).text('-');
      const $fileSpan2 = $('<span>').css({cursor: 'pointer'}).text(tempFile.name);
      const $fileSpan3 = $('<span>').css({fontSize: '12px'}).text(tempFile.size + ' byte');
      const $fileDiv = $('<div>');
      $fileDiv.append($fileSpan1).append("&nbsp;").append($fileSpan2).append("&nbsp;&nbsp;&nbsp;&nbsp;").append($fileSpan3);
      $(listElem).append($fileDiv);
    }

    hiddenInput.files = dataTransfer.files;
    tempInput.files = new DataTransfer().files;
  }
  finalResult['generateUploadedFileTag'] = generateUploadedFileTag;

  /**
   * 파일 업로드 관련 태그 식제 함수
   * @param {event} evt 상위 이벤트 객체 - 이를 이용해 클릭된 실제 DOM 객체를 찾아낼 수 있음
   * @param {string} hiddenInput 실제로 controller 로 넘길 데이터가 담기는 DOM
   * @param {string} listElem 파일 업로드 후 생성된 태그가 위치하는 타겟 DOM
   * @param {string} elemType 파일 업로드 후 생성된 태그의 타입, 이를 이용해 index를 찾아냄
   */
  const removeUploadedFileTag = (evt, hiddenInput, listElem, elemType) => {
    const fileIdx = $(listElem).find(elemType).index(evt.currentTarget);
    const sourceFiles = hiddenInput.files;
    const dataTransfer = new DataTransfer();

    const sourceFileArr = Array.from(sourceFiles);
    sourceFileArr.splice(fileIdx, 1);
    sourceFileArr.forEach(sourceFile => dataTransfer.items.add(sourceFile));

    hiddenInput.files = dataTransfer.files;

    $(evt.currentTarget).remove();
  }
  finalResult['removeUploadedFileTag'] = removeUploadedFileTag;

  return finalResult;
})();

const ErrorUtils = (() => {
  const finalResult = {};

  finalResult['setInvalidMessage'] = obj => {
    // $('[id ^= invalid_]').empty();
    // $.each(obj, (k, v) => $('#' + k).text(v));

    $.each(obj, (k, v) => {
      const $span = $('#' + k);
      $span.prop('hidden', false);
      if ($span.text() === '' || $span.text() === undefined || $span.text() === null) $span.text(v);
    });
    $('[id ^= invalid_]').eq(0).siblings(':first').focus()
  }

  return finalResult;
})();





const convertNRtoBrSpToNbsp = (strData) => {
  return strData.replaceAll('\n', '<br>').replaceAll(' ', '&nbsp;');
}

/** convert date to string format yyyy-MM-dd hh:mm:ss */
const convertDateToStr1 = (date) => {
  let month  = date.getMonth() + 1;
  let day    = date.getDate();
  let hour   = date.getHours();
  let minute = date.getMinutes();
  let second = date.getSeconds();

  month  = parseStrToDate(month)
  day    = parseStrToDate(day)
  hour   = parseStrToDate(hour)
  minute = parseStrToDate(minute)
  second = parseStrToDate(second)

  return date.getFullYear() + '-' + month + '-' + day + ' ' + hour + ':' + minute + ':' + second;
}

const parseStrToDate = (str) => {
  return parseInt(str) >= 10 ? str : '0' + str;
}
