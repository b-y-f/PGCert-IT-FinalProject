const check = function () {
    let pwd1 = $("#inputPassword");
    let pwd2 = $("#reInputPassword");
    if (pwd1.val() !== pwd2.val()) {
        pwd1.addClass('is-invalid');
        pwd2.addClass('is-invalid');
    }else {
        pwd1.removeClass('is-invalid');
        pwd1.addClass('is-valid');
        pwd2.removeClass('is-invalid');
        pwd2.addClass('is-valid');
    }
}

