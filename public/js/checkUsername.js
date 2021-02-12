$(document).ready(function () {
    $("#username").keyup(function () {
        var username = $("#username").val().trim();
        if (username !== '') {
            $.ajax({
                url: '/signup',
                type: 'post',
                data: {username: username},
                success: function (response) {
                    // alert(response)
                    $('#username').removeClass('is-invalid');
                },
                error: function (respnse){
                    $('#username').addClass('is-invalid');
                }
            });
        }
    });
});
