function renderingChamber(chamber) {
    $('#id-row').html(chamber["id"]);
    $('#name-row').html(chamber["name"]);
    $('#address-row').html(chamber["address"]);
}

function loadChamber() {
    var id = new URL(window.location.href).searchParams.get("id");
    var data = "id=" + id;

    $.ajax({
        url: '/rest/getChambers',
        type: 'GET',
        data: data,
        cache: false,
        success: [
            function (chambersResponsive) {
                if (chambersResponsive !== 0) {
                    renderingChamber(chambersResponsive[0]);
                }
            }
        ]
    });
}


$(document).ready(
    function () {
        loadChamber();
    }
);



