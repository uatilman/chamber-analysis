const size = 20;
const order = "ASC";
const orderBy = "name";
let pageNumber = 0;
let rowTemplate;

$(document).ready(
    function () {
        loadChambers();
        rowTemplate = $(`.click-row`).clone();
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
        let row = $(rowTemplate).clone()
            .find(".id-column").html(chamber["id"]).end()
            .find(".name-column").html(chamber["name"]).end()
            .find(".address-column").html(chamber["address"]).end();
        $(row.attr("data-href", "chambers?id=" + chamber["id"]));

        $(row).appendTo(".chamber-table");

    });
}

function setRowClickListener() {
    $(".click-row").click(function () {
        window.location = $(this).data("href");
        return false;
    });
}

function loadChambers() {

    let data =
        "pageNumber=" + pageNumber + "&" +
        "order=" + order + "&" +
        "orderBy=" + orderBy + "&" +
        "size=" + size;

    $.ajax({
        url: '/rest/getChambers',
        type: 'GET',
        data: data,
        cache: false,
        success: [
            function (chambersResponsive) {
                if (chambersResponsive !== 0) {
                    renderingChambers(chambersResponsive);
                    setRowClickListener();
                    pageNumber++;
                }
            }
        ]
    });
}





