const size = 20;
const order = "ASC";
const orderBy = "name";
const pageNumber = 0;
let chamberBody;

$(document).ready(
    function () {
        loadChambers();
        chamberBody = $(`.click-row`).clone();
        $('.click-row').remove();
        $(window).scroll(function () {
            if ($(window).scrollTop() === $(document).height() - $(window).height()) {
                loadChambers();
            }
        });
    }
);


function renderingChambers(chambers) {
    chambers.forEach(function (chamber) {
        var test = $(chamberBody).clone()
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
    var data = "pageNumber=" + pageNumber + "&" + "order=" + order + "&" + "orderBy=" + orderBy + "&" + "size=" + size;

    $.ajax({
        url: '/rest/getChambers',
        type: 'GET',
        data: data,
        cache: false,
        success: [
            function (chambersResponsive) {
                if (chambersResponsive !== 0) {
                    renderingChambers(chambersResponsive);
                    // setRowClickListener();
                    pageNumber++;
                }
            }
        ]
    });
}





