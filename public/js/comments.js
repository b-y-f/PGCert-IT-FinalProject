$(document).ready(function () {
    let toggle = true;
    let postId = $('#postId').val();
    let url = 'http://localhost:3000/fulltext?id=' + postId;

    //reply a comment
    $(document).on('click','.replyButton', function () {

        if (toggle) {
            let reTo="";
            let newClass = "";
            if ($(this).hasClass('replyTo')) {
                reTo = "@" + $(this).prev().text().trim() + " ";
                newClass = "reTo"
            }
            $(this).parents().eq(2).append('<div id="commentBox"><div class="form-floating "><textarea class="form-control" placeholder="Leave a comment here" id="replyContent" style="height: 100px">' + `${reTo}` + '</textarea><label for="floatingTextarea">Say something...</label></div><button type="button" class="btn btn-primary btn-sm submitReplyBtn '+ newClass + '">submit</button>')
            toggle = false;
        } else {
            $('#commentBox').remove();
            toggle = true;
        }
    });

    $(document).on('click','.submitReplyBtn',function () {
        let input = $(this).prev().find($('textarea')).val();

        let replierId = $('#replierId').val();

        let anchor;
        if ($(this).hasClass('reTo')){
            anchor = $(this).parents().eq(2).prev().prev();
        }else {
            anchor = $(this).parents().eq(1).prev()
        }
        let commentId = anchor.find($('.commentId')).val();
        let posterId = anchor.find($('.posterId')).val();


        $.post('/reply-comment',{
            commentId :commentId,
            replyContent: input,
            replierId: replierId,
            posterId: posterId
        });
        $("#blog-container").load(url + ' #blog-container');
        toggle = true;

    });


    //delete comments
    $(document).on('click',".deleteBtn",function () {
        let commentId = $(this).parents().eq(2).prev().find($('.commentId')).val();

        $.get('/delete-comment?id='+commentId);
        $("#blog-container").load(url + ' #blog-container');
    });

    $(document).on('click',".deleteReplyBtn",function () {
        let replyId = $(this).next().val();
        $.get('/delete-reply?id='+replyId);
        $("#blog-container").load(url + ' #blog-container');
    });



});

