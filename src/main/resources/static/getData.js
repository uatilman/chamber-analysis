function renderingChambers(chambers) {
    chambers.forEach(function (chamber) {
        var test = $(chamberBody)
            .find(".id-column").html(chamber["id"]).end()
            .find(".name-column").html(chamber["name"]).end()
            .find(".address-column").html(chamber["address"]).end();
        $(test).appendTo(".chamber-table");
    });
}

/*function setRowClickListener() {
    $(".click-row").click(function () {
        window.location = $(this).data("href");
        return false;
    });
}*/
function loadChambers() {
    var data = "pageCounter=" + pageCounter + "&" + "order=" + order + "&" + "orderBy=" + orderBy + "&" + "number=" + number;

    $.ajax({
        url: '/getChambers',
        type: 'GET',
        data: data,
        cache: false,
        success: function (chambersResponsive) {
            if (chambersResponsive !== 0) {
                renderingChambers(chambersResponsive["chambers"]);
                // setRowClickListener();
                pageCounter++;
            }
        }
    });
}


$(document).ready(
    function () {
        loadChambers();

        $(window).scroll(function () {
            if ($(window).scrollTop() === $(document).height() - $(window).height()) {
                loadChambers();
            }
        });
    }
);



