
var getRandomFraseMotivazionale = function (id) {
    var element = document.getElementById(id);
    var xhttp  = new XMLHttpRequest();
    var api = "/TUM4WORLD/frase";
    xhttp.responseType = "json";

    xhttp.onreadystatechange = function() {
        if (xhttp.readyState === 4 && xhttp.status === 200) {
            element.innerHTML = xhttp.response.testo
        }
    };
    xhttp.open("GET", api, true);
    xhttp.send();
};

window.onload = function() {
    getRandomFraseMotivazionale('fraseMotivazionale')
    setInterval(() => getRandomFraseMotivazionale('fraseMotivazionale'), 20000)
};