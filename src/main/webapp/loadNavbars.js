function includeHTML(elementId, fileUrl, callback, callbackParam) {
    var xhttp = new XMLHttpRequest();
    xhttp.onreadystatechange = function() {
        if (this.readyState === 4 && this.status === 200) {
            document.getElementById(elementId).innerHTML = this.responseText;
            if (callback) {
                callback(callbackParam);
            }
        }
    };
    xhttp.open("GET", fileUrl, true);
    xhttp.send();
};

function includeNavbarBasedOnStatus(elementId, callback) {
    var xhttp = new XMLHttpRequest();
    xhttp.onreadystatechange = function() {
        if (this.readyState === 4 && this.status === 200) {
            var resp = JSON.parse(xhttp.response)

            if (resp.isLoggedIn) {
                //Se l'utente è loggato carica una navbar differente
                includeHTML(elementId, "/TUM4WORLD/navaccesso.html", callback, resp.ruolo);
            } else {
                includeHTML(elementId, "/TUM4WORLD/nav.html", callback, resp.ruolo);
            }
        }
    };
    xhttp.open("GET", "/TUM4WORLD/status", true);
    xhttp.send();
}