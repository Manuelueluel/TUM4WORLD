function includeHTML(elementId, fileUrl) {
    var xhttp = new XMLHttpRequest();
    xhttp.onreadystatechange = function() {
        if (this.readyState == 4 && this.status == 200) {
            document.getElementById(elementId).innerHTML = this.responseText;
        }
    };
    xhttp.open("GET", fileUrl, true);
    xhttp.send();
};

document.addEventListener("DOMContentLoaded", function() {
    includeHTML("navbar", "navaccesso.html");
    includeHTML("navbar2", "barrainferiore.html");
});