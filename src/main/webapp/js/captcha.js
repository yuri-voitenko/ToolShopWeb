$(document).ready(function (e) {
    $('#captcha_img').click(function (e) {
        e.preventDefault();
        $('#captcha_img').attr('src', "/getCaptchaImage?ver=" + Math.random());
    });
});