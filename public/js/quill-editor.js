var editor = new Quill('#editor-container', {
    theme: 'snow',
    placeholder: 'Compose an epic...',
});

const ec = $("#editor-container")
if (ec.text()!==''){
    // console.log('empty');
    editor.setContents(JSON.parse(ec.text()), 'silent');
}

const form = document.querySelector("form");
form.onsubmit = function () {


    var content = document.querySelector('input[name=postContent]');
    content.value = JSON.stringify(editor.getContents());
    return true;

};