const CommonUtils = (() => {
  const finalResult = {};

  const formReset = () => {
    $('form select:first option:first').prop('selected', true);
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
