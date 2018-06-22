$(".humb i").click(function () {
    $("ul.menu").slideToggle();
});

$(".last").click(function () {
    $(".languages ul").slideToggle();
});

$(".lang-mini").click(function () {
    $(".lang-list").slideToggle();
});

autoSlide();
var temp1;

function autoSlide() {
    temp1 = setTimeout(function () {
        var i1 = document.getElementById('i1');
        var i2 = document.getElementById('i2');
        var i3 = document.getElementById('i3');

        if (i1.checked) {
            i2.checked = true;
            autoSlide();
        } else if (i2.checked) {
            i3.checked = true;
            autoSlide();
        } else if (i3.checked) {
            i1.checked = true;
            autoSlide();
        }
    }, 30000);
}