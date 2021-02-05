var editor = new Quill('#editor-container', {
    theme: 'snow',
    placeholder: 'Compose an epic...',
});

if ($("#editor-container").text()!==''){
    // console.log('empty');
    editor.setContents(JSON.parse($("#editor-container").text()), 'silent');
}

const form = document.querySelector("form");
form.onsubmit = function () {


    var content = document.querySelector('input[name=postContent]');
    content.value = JSON.stringify(editor.getContents());
    return true;

};