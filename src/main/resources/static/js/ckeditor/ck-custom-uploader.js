import UploadAdapter from "/js/ckeditor/uploadAdapter.js";

export default function makeEditor(target) {
  return DecoupledEditor.create(target, {
    language:'ko',
    codeBlock: {
      languages: [
        { language: 'plaintext', label: 'Plain text' }, // The default language.
        { language: 'html', label: 'HTML' },
        { language: 'css', label: 'CSS' },
        { language: 'javascript', label: 'JavaScript' },
        { language: 'diff', label: 'Diff' },
        { language: 'java', label: 'Java' },
        { language: 'sql', label: 'SQL' },
        { language: 'xml', label: 'XML' },
      ]
    },
    mention: {
      feeds: [
        {
          marker: '@',
          feed: [ '@김길동', '@김개똥', '@나길동', '@김개똥', '@단길동', '@라길동', '@마길동', '@박길동', '@서길동', '@이길동', '@장길동', '@차길동', '@코길동', '@탁길동', '@편길동', '@홍길동'],
          minimumCharacters: 0
        }
      ]
    },
    fontFamily: {
      options: [
        'D2Coding (기본)',
        '궁서체',
        '바탕',
        '돋움',
        'Arial',
        'Courier New',
        'Georgia',
        'Lucida Sans Unicode',
        'Tahoma, Geneva',
        'Times New Roman',
        'Trebuchet MS',
        'Verdana',
      ]
    },
    fontSize: {
      options: [
        '10px',
        '13px',
        '16px (기본값)', // default 'default' or 16
        '19px',
        '22px',
        '25px'
      ],
    },
    toolbar: {
      items: [
        'heading',
        '|', 'style',
        '|', 'fontFamily', 'fontSize',
        '|', 'highlight', 'fontColor', 'fontBackgroundColor',
        '|', 'bold', 'italic', 'underline', 'strikeThrough',
        '|', 'alignment',
        '|', 'removeFormat',
        '|', 'outdent', 'indent',
        '|', 'link', 'insertImage', // default uploadImage
        '|', 'bulletedList', 'numberedList',
        '|', 'todoList',
        '|', 'horizontalLine', 'blockQuote', 'insertTable', 'mediaEmbed',
        '|', 'wordCount', 'specialCharacters',
        '|', 'code', 'codeBlock', 'findAndReplace', 'mention',
        '|', 'showBlocks', 'sourceEditing', 'pageBreak',
        '|', 'undo', 'redo',
      ]
    },
    list: {
      properties: {
        styles: true,
        startIndex: true,
        reversed: true
      }
    },
    table: {
      contentToolbar: [
        'insertTable', 'tableColumn', 'tableRow', 'mergeTableCells', 'tableProperties', 'tableCellProperties', 'toggleTableCaption',
      ],
      // tableCellProperties:  ['backgroundColors', 'borderColors', 'colorPicker', 'defaultProperties'],
    },
    extraPlugins: [MyCustomUploadAdapterPlugin],
  })
};

function MyCustomUploadAdapterPlugin( editor ) {
  editor.plugins.get( 'FileRepository' ).createUploadAdapter = ( loader ) => {
    // Configure the URL to the upload script in your back-end here!
    return new UploadAdapter( loader );
  };
}