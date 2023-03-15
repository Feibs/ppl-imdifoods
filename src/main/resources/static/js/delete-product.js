import {hideElement, showElement} from "./visibility-modifier.js";

$(function () {
    let id;
    $(".root").on("click", ".delete-button", function () {
        $("#delete-popup").modal("show");
        id = $(this).closest("tr").attr("id");
    });

    $(".root").on("click", ".confirm-button", function () {
        showElement("#loading-content");
        hideElement("#confirmation-popup-content");
        hideElement("#fail-popup-content");

        // create a new FormData object
        let formData = new FormData();
        formData.append("id", id);

        // post create product request
        $.ajax({
            url: "/product/delete",
            type: "POST",
            data: formData,
            contentType: false,
            processData: false,
            success: function () {
                hideElement("#loading-content");
                showElement("#confirmation-popup-content");
                updatePage();
            },
            error: function () {
                hideElement("#loading-content");
                showElement("#fail-popup-content");
            }
        });
    });

    function updatePage() {
        let fadeTimer = 350;
        let currentUrl = window.location.href;
        $.ajax({
            url: currentUrl,
            type: "GET",
            success: function (response) {
                let pageContent = $('#page-content', response).html();
                // update current page
                $('#page-content').fadeTo(fadeTimer, 0);
                setTimeout(function () {
                    $('#page-content').html(pageContent);
                    $('#page-content').fadeTo(fadeTimer, 1);
                }, fadeTimer);
            }
        });
    }
});