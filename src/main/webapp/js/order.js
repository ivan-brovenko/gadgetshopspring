var cardPay = $(".cardPay");
var cashPay = $(".cashPay");

$("select[name=ship]").change(function () {
    var value = $(this).val();
    if (value === "self-delivery") {
        cashPay.removeClass("displayBlock");
    } else if (value === "home-delivery") {
        cashPay.addClass("displayBlock");
    }
});

$("select[name=bill]").change(function () {
    var value = $(this).val();
    if (value === "card") {
        cardPay.addClass("displayBlock");
    } else if (value === "cash") {
        cardPay.removeClass("displayBlock");
    }
});

$("form[name=orderForm]").submit(function (event) {
    var ship = $("select[name=ship]");
    var cartNumber= $(".cartNumber");
    var cvv = $(".cvv");
    var bill = $("select[name=bill]");
    var errorMessage = $(".errorMessage");
    var address = $("textarea[name=address]");

    if (ship.val() === "..." || bill.val() === "...") {
        errorMessage.addClass("displayBlock");
        event.preventDefault();
    }
    if (cvv.val( ) === "" && cvv.is(":visible")){
        errorMessage.addClass("displayBlock");
        event.preventDefault();
    }
    if (address.val() === "" && address.is(":visible")){
        errorMessage.addClass("displayBlock");
        event.preventDefault();
    }
});


