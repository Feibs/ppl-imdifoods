$("form#form-message").submit(function () {
    let senderEmail = document.getElementById("sender-email").value;
    let senderMessage = document.getElementById("sender-message").value;
    $.ajax({
        method: 'POST',
        url: "/message/send",
        data: {
            "sender-email": senderEmail,
            "sender-message": senderMessage
        },
        dataType: "json",
        cache: false,
        success: function () {
            alert("Message sent!");
            $("form#form-message")[0].reset();
        },
        error: function () {
            alert("Failed sending message");
        }
    });
    return false;
});