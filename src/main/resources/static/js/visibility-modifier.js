export function showElement(element) {
    $(element).show();
    $(element).css("visibility", "visible");
    $(element).css("opacity", "1");
}

export function hideElement(element) {
    $(element).hide();
    $(element).css("visibility", "hidden");
    $(element).css("opacity", "0");
}