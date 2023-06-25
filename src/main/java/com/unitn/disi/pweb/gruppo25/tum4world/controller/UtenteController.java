package com.unitn.disi.pweb.gruppo25.tum4world.controller;

import com.unitn.disi.pweb.gruppo25.tum4world.model.services.UtenteService;

import javax.servlet.*;
import javax.servlet.http.*;
import javax.servlet.annotation.*;
import java.io.IOException;
import java.io.PrintWriter;


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

    @Override
    protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        //TODO usato per la creazione di un nuovo utente, nel body request Utente completo di tutti i campi
    }

    @Override
    protected void doPut(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        //TODO usato per la modifica di un utente già esistente, nel body request Utente completo di tutti i campi
    }

    @Override
    protected void doDelete(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        //TODO usato per la cancellazione di un utente già esistente, basta lo username come parametro
    }
}
