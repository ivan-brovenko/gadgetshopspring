function addRedOutline(element) {
    $(element).addClass("redOutline");
}

function removeRedOutline(element) {
    $(element).removeClass("redOutline");
}

function hideElement(element) {
    $(element).removeClass("show");
}

function showElement(element) {
    $(element).addClass("show");
}

function isEmptyField(field) {
    if ($(field).val() === "") {
        return true;
    }
    return false;
}

function validateFieldWithReqex(element, regex, wrongMessage) {
    removeRedOutline(element);
    hideElement(wrongMessage);
    if (isEmptyField(element) || !element.val().match(regex)) {
        showElement(wrongMessage);
        addRedOutline(element);
        return false;
    }
    return true;
}

function validatePassword(password, password2, wrongMessage) {
    removeRedOutline(password2);
    hideElement(wrongMessage);
    if (isEmptyField(password) || $(password).val() !== $(password2).val()) {
        showElement(wrongMessage);
        addRedOutline(password2);
        return false;
    }
    return true;
}

function validatePasswordLength(password, wrongMessage) {
    removeRedOutline(password);
    hideElement(wrongMessage);
    if ($(password).val().length < 8) {
        showElement(wrongMessage);
        addRedOutline(password);
        return false;
    }
    return true;
}

function validateCaptcha(captcha, wrongMessage) {
    removeRedOutline(captcha);
    hideElement(wrongMessage);
    if ($(captcha).val() === "") {
        showElement(wrongMessage);
        addRedOutline(captcha);
        return false;
    }
    return true;
}

$('#regForm').on('submit', function () {
    var mailReg = /[\w]+@gmail.com/g;
    var nameReg = /(^[A-z]*$)/g;

    var pass8Message = $('#passM');
    var wrongEmailMessage = $('#emailM');
    var duplicatePasswordMessage = $('#pass2');
    var nameMessage = $('#nameMessage');
    var surnameMessage = $('#surnameMessage');
    var captcahMessage = $("#emptyCaptchaMessage");

    var nameInput = $("#name");
    var surnameInput = $("#surname");
    var emailInput = $("#email");
    var passwordInput1 = $("#password");
    var passwordInput2 = $("#password2");
    var captchaInput = $("#captchaNumbers");

    var nameBool = validateFieldWithReqex(nameInput, nameReg, nameMessage);
    var surnameBool = validateFieldWithReqex(surnameInput, nameReg, surnameMessage);
    var emailBool = validateFieldWithReqex(emailInput, mailReg, wrongEmailMessage);
    var passwordBool = validatePassword(passwordInput1, passwordInput2, duplicatePasswordMessage);
    var passwordLength = validatePasswordLength(passwordInput1, pass8Message);
    var captchaNotEmpty = validateCaptcha(captchaInput, captcahMessage);

    return nameBool && surnameBool && emailBool && passwordBool && passwordLength && captchaNotEmpty;
});