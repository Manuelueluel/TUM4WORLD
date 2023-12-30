/**
 * Controlla la password se rispetta i seguenti punti:
 * - 8 caratteri
 * - contenere M A I G oppure minuscole
 * - almeno un numero
 * - almeno una maiuscola
 * - almeno un $ ! ?
 * @param password stringa
 * @param errorList lista di stringhe che descrivono gli errori presenti nella password
 * @returns {boolean} true se password valida, false altrimenti
 */
function checkForErrorsInPassword(password, errorList) {
    const nErroriPreEsistenti = errorList.length
    // Espressioni regex case sensitive per ricerca delle iniziali dei nomi dei componenti del gruppo
    const aA = /[a]/i;
    const mM = /[m]/i;
    const gG = /[g]/i;
    const iI = /[i]/i;
    // regex numero
    const num = /[0-9]/;
    // regex caratteri maiuscoli
    const maiusc = /[A-Z]/;
    // regex $ ! ?
    const speciali = /[$!?]/;

    if (password.length !== 8) {
        errorList.push("La password deve essere di 8 caratteri")
    }
    if (!(aA.test(password) && mM.test(password) && gG.test(password) && iI.test(password))) {
        errorList.push("La password deve contenere i caratteri M A I G anche minuscoli")
    }
    if (!num.test(password)) {
        errorList.push("La password deve contenere un numero")
    }
    if (!maiusc.test(password)) {
        errorList.push("La password deve contenere almeno un carattere maiuscolo")
    }
    if (!speciali.test(password)) {
        errorList.push("La password deve contenere almeno un carattere tra ! $ ?")
    }
    if (errorList.length === nErroriPreEsistenti) { //Se non stati aggiunti errori alla lista, la password è valida
        return true;
    } else {
        return false
    }
}

function isMaggiorenne(dataNascita) {
    const nascita = new Date(dataNascita)
    const today = new Date()
    const differenzaAnni = today.getFullYear() - nascita.getFullYear()
    // Controlla se sono passati almeno 18 anni
    if (differenzaAnni > 18) {
        return true;
    } else if (differenzaAnni === 18) {
        // Se la differenza è esattamente 18 anni, controlla i mesi
        if (today.getMonth() > nascita.getMonth()) {
            return true;
        } else if (today.getMonth() === nascita.getMonth()) {
            // Se i mesi sono gli stessi, controlla i giorni
            return today.getDate() >= nascita.getDate();
        }
    }

    return false;
}

function isNumeroTelefonicoValid(tel) {
    // fonte https://ihateregex.io/expr/email/
    const regex = /^[\\+]?[(]?[0-9]{3}[)]?[-\\s\\.]?[0-9]{3}[-\\s\\.]?[0-9]{4,6}$/;
    return regex.test(tel)
}

function isEmailValid(email) {
    // fonte https://ihateregex.io/expr/email/
    const regex = /[^@ \t\r\n]+@[^@ \t\r\n]+\.[^@ \t\r\n]+/;
    return regex.test(email)
}