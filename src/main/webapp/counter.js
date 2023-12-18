function incrementCounter() {
    var xhttp = new XMLHttpRequest()
    var api = "http://localhost:8080/TUM4WORLD/counter";

    xhttp.open("POST", api, true);
    xhttp.setRequestHeader('Content-Type','application/x-www-form-urlencoded');
    var pagina = new URL(window.location.href).pathname;

    //Tolgo eventuali parametri dall'url, per esempio se ci fosse la session id
    var index = pagina.indexOf(";")
    var paginaFiltrata = pagina.substring(0, (index == -1 ? pagina.length : index) )

    xhttp.send("pagina="+paginaFiltrata);
}