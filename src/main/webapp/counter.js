function incrementCounter() {
    var xhttp = new XMLHttpRequest()
    var api = "http://localhost:8080/TUM4WORLD/counter";

    xhttp.open("POST", api, true);
    xhttp.setRequestHeader('Content-Type','application/x-www-form-urlencoded');
    var pagina = new URL(window.location.href).pathname;

    xhttp.send("pagina="+pagina);
}