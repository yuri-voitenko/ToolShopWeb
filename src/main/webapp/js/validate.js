function validateLoginForm(formName) {
    var errorDiv = getErrorDiv();
    var email = document.forms[formName]["email"];
    var password = document.forms[formName]["password"];
    if (!validateEmail(email.value)) {
        errorDiv.innerHTML += "<strong>Email</strong> Sorry, you entered an incorrect email!<br>" +
            "Please fix and try again.<br>";
        email.focus();
        highlight(email, true);
        return false;
    }
    highlight(email, false);
    if (!validatePassword(password.value)) {
        errorDiv.innerHTML += "<strong>Password</strong> Sorry, you entered an incorrect password!<br>" +
            "Please fix and try again.<br>" +
            "<br>Password requirements:<br>" +
            "&emsp;* At least one upper case English letter<br>" +
            "&emsp;* At least one lower case English letter<br>" +
            "&emsp;* At least one digit<br>" +
            "&emsp;* At least one special character<br>" +
            "&emsp;* Minimum eight in length<br>";
        password.focus();
        highlight(password, true);
        return false;
    }
    highlight(password, false);
    return true;
}

function validateRegisterForm(formName) {
    var errorDiv = getErrorDiv();

    var fullName = document.forms[formName]["fullName"];
    if (!validateFullName(fullName.value)) {
        errorDiv.innerHTML += "<strong>Full name</strong> Sorry, you entered an incorrect fullName!<br>" +
            "Please fix and try again.<br>" + "Hint: 'FirstName LastName'<br>";
        fullName.focus();
        highlight(fullName, true);
        return false;
    }
    highlight(fullName, false);

    var phoneNumber = document.forms[formName]["phoneNumber"];
    if (!validatePhoneNumber(phoneNumber.value)) {
        errorDiv.innerHTML += "<strong>Phone number</strong> Sorry, you entered an incorrect phone number!<br>" +
            "Please fix and try again.<br>" + "Hint: Use international format!<br>";
        phoneNumber.focus();
        highlight(phoneNumber, true);
        return false;
    }
    highlight(phoneNumber, false);

    if (!validateLoginForm(formName)) {
        return false;
    }
    var password = document.forms[formName]["password"];
    var passwordCheck = document.forms[formName]["passwordCheck"];
    if (password.value != passwordCheck.value) {
        errorDiv.innerHTML += "<strong>Repeated password</strong> Sorry, you entered an incorrect repeated password!<br>" +
            "Please fix and try again.<br>";
        passwordCheck.focus();
        highlight(passwordCheck, true);
        return false;
    }
    highlight(passwordCheck, false);

    var captchaCode = document.forms[formName]["captcha"];
    if (!validateCaptchaCode(captchaCode.value)) {
        errorDiv.innerHTML += "<strong>Captcha code</strong> Sorry, you entered an incorrect secret code!<br>" +
            "Please fix and try again.<br>" + "Hint: Must be only 10 digits.<br>";
        captchaCode.focus();
        highlight(captchaCode, true);
        return false;
    }
    highlight(captchaCode, false);

    return true;
}

function getErrorDiv() {
    var errorDiv = document.getElementById("errorMessages");
    if (errorDiv == null) {
        errorDiv = document.createElement('div');
        errorDiv.setAttribute("id", "errorMessages");
        errorDiv.className = "alert alert-danger";
        errorDiv.innerHTML = "<strong>Oops! </strong>Something went wrong :( Please fix and try again.<br><br>";
        document.getElementById("container").insertBefore(errorDiv, document.getElementById("container").firstChild);
    }
    errorDiv.innerHTML = "<strong>Oops! </strong>Something went wrong :( Please fix and try again.<br><br>";
    return errorDiv;
}

function validateEmail(email) {
    var regExEmail = /^(([^<>()\[\]\\.,;:\s@"]+(\.[^<>()\[\]\\.,;:\s@"]+)*)|(".+"))@((\[[0-9]{1,3}\.[0-9]{1,3}\.[0-9]{1,3}\.[0-9]{1,3}])|(([a-zA-Z\-0-9]+\.)+[a-zA-Z]{2,}))$/;
    return regExEmail.test(email);
}

function validatePassword(password) {
    var regExPasw = /^(?=.*?[A-Z])(?=.*?[a-z])(?=.*?[0-9])(?=.*?[#?!@$%^&*-]).{8,}$/;
    return regExPasw.test(password);
}

function validateFullName(fullName) {
    var regExFullName = /^([a-zA-Z]{2,}\s[a-zA-z]{1,}'?-?[a-zA-Z]{2,}\s?([a-zA-Z]{1,})?)$/;
    return regExFullName.test(fullName);
}

function validatePhoneNumber(phoneNumber) {
    var digits = phoneNumber.replace(/\D/g, '');
    return digits.length == 11 || digits.length == 12;
}

function validateCaptchaCode(code) {
    var regExCaptchaCode = /^\d{10}$/;
    return regExCaptchaCode.test(code);
}

function highlight(element, flag) {
    if (flag) {
        element.style.border = '#FF0000 1px solid';
    }
    else {
        element.style.border = 'transparent';
    }
}