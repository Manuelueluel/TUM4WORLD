package com.unitn.disi.pweb.gruppo25.tum4world.controller;

import com.unitn.disi.pweb.gruppo25.tum4world.Utility;
import com.unitn.disi.pweb.gruppo25.tum4world.model.entities.Utente;
import com.unitn.disi.pweb.gruppo25.tum4world.model.services.UtenteService;

import javax.servlet.*;
import javax.servlet.http.*;
import javax.servlet.annotation.*;
import java.io.IOException;
import java.io.PrintWriter;
import java.time.LocalDate;
import java.time.format.DateTimeParseException;
import java.util.ArrayList;


@WebServlet(name = "UtenteController", value = "/utente")
public class UtenteController extends HttpServlet {
    private UtenteService utenteService;

    @Override
    public void init() throws ServletException {
        this.utenteService = new UtenteService();
    }

    @Override
    protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        String param = request.getParameter("username");
        PrintWriter writer = response.getWriter();

        // Impostazione del content type della risposta come application/json
        response.setContentType("application/json");
        response.setCharacterEncoding("UTF-8");

        // Recupero utente con username=param
        String jsonUtente = utenteService.getUtenteJsonByUsername(param);

        if (jsonUtente.equals("") || jsonUtente == null) { //Caso di errore
            response.setStatus(404);

            //Messaggio di errore { "25" : "Messaggio" }, vedi pdf per dettagli
            writer.print("{\"25\":\"Utente non trovato\"}");

        } else {
            // Scrittura del JSON nella risposta
            writer.print(jsonUtente);
        }
        writer.flush();
    }

    /**
     * Metodo POST usato per la registrazione di un nuovo utente.
     * Nella request devono essere presenti tutti i campi richiesti dalla form in
     * registrazione.html altrimenti viene inviato un messaggio json di errore.
     * @param request
     * @param response
     * @throws ServletException
     * @throws IOException
     */
    @Override
    protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        response.setContentType("application/json");
        response.setCharacterEncoding("UTF-8");

        String fname = request.getParameter("fname");
        String lname = request.getParameter("lname");
        LocalDate data_nascita = null;
        try {
            data_nascita = LocalDate.parse(request.getParameter("data-nascita"));
        } catch (DateTimeParseException ignored) {}
        String email = request.getParameter("email");
        String numero_cellulare = request.getParameter("numero-cellulare");
        String username = request.getParameter("username");
        String password = request.getParameter("password");
        String ruolo = request.getParameter("ruolo");
        PrintWriter writer = response.getWriter();

        Utente utente = utenteService.getUtenteByUsername(username);    //Username già esistente?

        //Da qui in poi controllo campi ricevuti
        String jsonMessage = "";   //messaggio json
        ArrayList<String> errors = new ArrayList<>();

        //Tutti i campi devono essere presenti
        if (utente != null || username == null || username.isEmpty()) {
            errors.add("username");
        }
        if (fname == null || fname.isEmpty()) {
            errors.add("nome");
        }
        if (lname == null || lname.isEmpty()) {
            errors.add("cognome");
        }
        if (data_nascita == null) {
            errors.add("data di nascita");
        }
        if (email == null || email.isEmpty()) {
            errors.add("email");
        }
        if (numero_cellulare == null || numero_cellulare.isEmpty()) {
            errors.add("numero di cellulare");
        }
        if (!Utility.isPasswordValid(password)) {        //PW VALIDA MIGA$1al
            errors.add("password");
        }
        if (ruolo == null || ruolo.isEmpty() || !( ruolo.equals("simpatizzante") || ruolo.equals("aderente")) ) {
            errors.add("ruolo");
        }

        /** Se presenti errori nei campi, si compone un messaggio di errore, nella forma:
         *  { "25": [ "nome", "cognome", "password" ] }
         *  i nomi dei campi usati sono gli stessi degli input presenti in registrazione.html
         */
        if (!errors.isEmpty()) {
            jsonMessage += "{\"25\":[";
            for (int i = 0; i < errors.size(); i++) {
                if (i < errors.size() - 1) {
                    jsonMessage += "\"" + errors.get(i) + "\",";
                } else {
                    jsonMessage += "\"" + errors.get(i) + "\"";
                }
            }
            jsonMessage += "], \"success\": false}";
            writer.print(jsonMessage);
            writer.flush();

        } else {   //Utente valido, lo si inserice nel db
            Utente nuovoUtente = new Utente();
            nuovoUtente.setNome(fname);
            nuovoUtente.setCognome(lname);
            nuovoUtente.setUsername(username);
            nuovoUtente.setPassword(password);
            nuovoUtente.setEmail(email);
            nuovoUtente.setNascita(data_nascita);
            nuovoUtente.setTel(numero_cellulare);

            if (ruolo.equals("aderente")) {
                nuovoUtente.setRuolo(Utente.RUOLO_ADERENTE);
            }else {
                nuovoUtente.setRuolo(Utente.RUOLO_SIMPATIZZANTE);
            }
            if (utenteService.insertUtente(nuovoUtente)) {
                //Registrazione avvenuta con successo
                jsonMessage += "{\"success\": true, \"redirectUrl\": \"./messaggioRegistrazione.html\"}";
                writer.print(jsonMessage);
                writer.flush();

            } else {
                //Errore
                response.sendError(HttpServletResponse.SC_INTERNAL_SERVER_ERROR);
            }
        }
    }

    @Override
    protected void doPut(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        //TODO usato per la modifica di un utente già esistente, nel body request Utente completo di tutti i campi
    }

    @Override
    protected void doDelete(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        resp.setContentType("application/json");
        resp.setCharacterEncoding("UTF-8");

        String username = req.getParameter("username");

        if (utenteService.deleteUtente(username)) {
            resp.setStatus(HttpServletResponse.SC_OK);

        } else {
            resp.setStatus(HttpServletResponse.SC_BAD_REQUEST);
        }
    }
}
