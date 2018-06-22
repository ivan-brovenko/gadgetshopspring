function addRedOutline(element) {
    element.classList.add("redOutline");
}

function removeRedOutline(element) {
    element.classList.remove("redOutline");
}

function hideElement(element) {
    element.classList.remove("show");
}

function showElement(element) {
    element.classList.add("show");
}

function isEmptyField(field) {
    if (field.value === "") {
        return true;
    }
    return false;
}

function validateFieldWithReqex(element, regex, wrongMessage) {
    removeRedOutline(element);
    hideElement(wrongMessage);
    if (isEmptyField(element) || !element.value.match(regex)) {
        showElement(wrongMessage);
        addRedOutline(element);
        return false;
    }
    return true;
}

function validatePassword(form, wrongMessage) {
    removeRedOutline(form.password2);
    hideElement(wrongMessage);
    if (isEmptyField(form.password) || form.password.value !== form.password2.value) {
        showElement(wrongMessage);
        addRedOutline(form.password2);
        return false;
    }
    return true;
}

function validatePasswordLength(form, wrongMessage) {
    removeRedOutline(form.password);
    hideElement(wrongMessage);
    if (form.password.value.length < 8) {
        showElement(wrongMessage);
        addRedOutline(form.password);
        return false;
    }
    return true;
}

function validate(form) {
    var mailReg = /[\w]+@gmail.com/g;
    var nameReg = /(^[A-z]*$)/g;
    var emptyFields = document.getElementById("fieldM");
    var wrongLength = document.getElementById('passM');
    var wrongEmail = document.getElementById('emailM');
    var wrongMatch = document.getElementById('pass2');
    var nameMessage = document.getElementById('nameMessage');
    var surnameMessage = document.getElementById('surnameMessage');

    var nameBool = validateFieldWithReqex(form.name, nameReg, nameMessage);
    var surnameBool = validateFieldWithReqex(form.surname, nameReg, surnameMessage);
    var emailBool = validateFieldWithReqex(form.email, mailReg, wrongEmail);
    var passwordBool = validatePassword(form, wrongMatch);
    var passwordLength = validatePasswordLength(form, wrongLength);
    return nameBool && surnameBool && emailBool && passwordBool && passwordLength;
}