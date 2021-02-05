var editor = new Quill('#blogContent', {});
editor.disable();

editor.setContents(JSON.parse($("#blogContent").text()), 'silent');