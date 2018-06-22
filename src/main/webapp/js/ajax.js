var cartCount = $(".totalCount");
var totalPrice = $(".totalPrice");

$(".buyBt").click(function () {
    var productId = $(this).parent().find('.productId').val();
    var productCount = $(this).parent().find('.productCount').val();
    var totalPriceBasket = $(".totalPriceInBasket");

    $.ajax({
        type: "PUT",
        url: "/basket?"+$.param({"productCount":productCount,"productId":productId}),
        success: function (data) {
            cartCount.text(data.totalCount);
            totalPrice.text(data.totalPrice);
            totalPriceBasket.text("Total price: "+data.totalPrice+"$");
        }
    })
});

$(".deleteButton").click(function () {
    var productId = $(this).parent().find('.productId').val();
    var productToHide = $(this).parent().parent();
    var totalPriceBasket = $(".totalPriceInBasket");
    var emptyBasket = $(".emptyBasket");
    var table = $("table");
    var orderBt = $(".orderBt");

    $.ajax({
        type: "DELETE",
        url: "/basket?"+$.param({"productId":productId}),
        success:function (data) {
            cartCount.text(data.totalCount);
            totalPrice.text(data.totalPrice);
            productToHide.css("display","none");
            totalPriceBasket.text("Total price: "+data.totalPrice+"$");
            if (data.totalPrice === 0){
                emptyBasket.toggleClass("hidden");
                table.toggleClass("hidden");
                totalPriceBasket.toggleClass("hidden");
                orderBt.toggleClass("hidden");
            }
        }
    })
});

$(".productCountBasket").change(function () {
    var productCount = $(this).val();
    var totalPriceBasket = $(".totalPriceInBasket");
    var productId = $(this).parent().parent().find(".text-right").parent().find(".productId").val();

    $.post("/basket",{"productId":productId, "productCount":productCount},function (data) {
        cartCount.text(data.totalCount);
        totalPrice.text(data.totalPrice);
        totalPriceBasket.text("Total price: "+data.totalPrice+"$");
    })
});

$(".removeBt").click(function () {
    var trElement = $(this).parent().find("tbody");
    var totalPriceBasket = $(".totalPriceInBasket");
    var emptyBasket = $(".emptyBasket");
    var table = $("table");
    var orderBt = $(".orderBt");
    $.ajax({
        type:"DELETE",
        url: "/basket",
        success:function (data) {
            cartCount.text(data.totalCount);
            totalPrice.text(data.totalPrice);
            emptyBasket.toggleClass("hidden");
            table.toggleClass("hidden");
            totalPriceBasket.toggleClass("hidden");
            orderBt.toggleClass("hidden");
        }
    })
});

$(".makeOrder").click(function () {
    window.location = "/order";
});