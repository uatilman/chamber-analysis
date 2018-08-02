var chamberBody =
    "                    <tr class='click-row' data-href=''>" +
    "                        <td class='id-column'></td>" +
    "                        <td class='name-column'></td>" +
    "                        <td class='address-column'></td>" +
    "                    </tr>";
var size = 20;
var order = "ASC";
var orderBy = "name";
var pageNumber = 0;

function renderingChambers(chambers) {
    $('.id-column').last().html("test");
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



