var editor = new Quill('#editor-container', {
    theme: 'snow'
});

var form = document.querySelector("form");
form.onsubmit = function() {
    var content = document.querySelector('input[name=postContent]');
    content.value = JSON.stringify(editor.getContents());
};