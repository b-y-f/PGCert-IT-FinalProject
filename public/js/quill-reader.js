var editor = new Quill('#blogContent', {});

editor.disable();
let aaa = $("#blogContent").text();
let aa = JSON.parse(aaa);

editor.setContents(aa,'silent');