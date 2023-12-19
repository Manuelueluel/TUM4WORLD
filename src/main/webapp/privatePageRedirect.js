function redirectToPrivatePageFromNavbar(role) {
    var element = document.getElementById("to_private_page")
    // In base al ruolo, reinderizza
    if (role === 0) { // Amministratore
        element.href = "/TUM4WORLD/privata/amministratore.html";
    } else if (role === 1) { // Aderente
        element.href = "/TUM4WORLD/privata/aderente.html";
    } else if (role === 2) { // Simpatizzante
        element.href = "/TUM4WORLD/privata/simpatizzante.html";
    }
}
