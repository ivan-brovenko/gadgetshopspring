<label>Type the numbers below</label>
<div id="captcha">
    <img id="captcha_img" src="/cap?id=${id}">
</div>
<input type="text" id="captchaNumbers" name="captchaNumbers"
       style="${(not empty errors.captchaNumbers)?"outline:solid red":""}">
<div id="emptyCaptchaMessage" class="messages">Fill the field!</div>
<div class="messages serv">${errors.captchaNumbers}</div>