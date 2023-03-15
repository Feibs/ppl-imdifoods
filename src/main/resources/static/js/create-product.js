import {hideElement, showElement} from './visibility-modifier.js'

$(function () {
    // display inputted image
    $("#image-input").on("change", function () {
        let reader = new FileReader();
        reader.addEventListener("load", () => {
            $("#image-preview").attr("src", reader.result);
            $("#image-preview").css("display", "block");
            $("#image-upload").css("display", "none");
        });
        reader.readAsDataURL(this.files[0]);
    });

    // clear input fields
    $("#add-new-button").on("click", function () {
        $.each(["name", "description", "composition", "price", "stock", "image"], function (index, value) {
            $("[name=" + value + "]").val('');
        });
        $("#image-preview").css("display", "none");
        $("#image-upload").css("display", "block");
    });

    // validate file input
    $('#simpan-button').on("click", function (e) {
        // checks image files
        if (!$('#image-input')[0].files || !$('#image-input')[0].files[0]) {
            e.preventDefault();
            alert('Masukkan foto produk');
            return;
        }

        // delete trailing spaces
        $.each(["name", "description", "composition", "price", "stock"], function (index, value) {
            let trimmedValue = $("[name=" + value + "]").val().trim();
            $("[name=" + value + "]").val(trimmedValue);
        });

        // make sure all fields are filled
        let requiredFields = $("form").find('[required]').filter(function () {
            return $(this).val() === '';
        });
        if (requiredFields.length == 0) {
            e.preventDefault();
            $("#save-popup").modal("show");
        } // else display default input error message
    });

    $(".confirm-button").on("click", function () {
        showElement("#loading-content");
        hideElement("#confirmation-popup-content");
        hideElement("#fail-popup-content");

        // create a new FormData object
        let formData = new FormData($("#product-form")[0]);

        // post create product request
        $.ajax({
            url: $("form").attr("action"),
            type: "POST",
            data: formData,
            dataType: "json",
            contentType: false,
            processData: false,
            success: function () {
                hideElement("#loading-content");
                showElement("#confirmation-popup-content");
            },
            error: function () {
                hideElement("#loading-content");
                showElement("#fail-popup-content");
            }
        });
    });
});