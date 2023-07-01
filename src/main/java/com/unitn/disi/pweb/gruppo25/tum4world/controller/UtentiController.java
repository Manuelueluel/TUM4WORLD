package com.unitn.disi.pweb.gruppo25.tum4world.controller;

import com.unitn.disi.pweb.gruppo25.tum4world.model.services.UtenteService;

import javax.servlet.*;
import javax.servlet.http.*;
import javax.servlet.annotation.*;
import java.io.IOException;
import java.io.PrintWriter;

@WebServlet(name = "UtentiController", value = "/utenti")
public class UtentiController extends HttpServlet {

    private UtenteService utenteService;

    @Override
    public void init() throws ServletException {
        this.utenteService = new UtenteService();
    }

    @Override
    protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        // Impostazione del content type della risposta come application/json
        response.setContentType("application/json");
        response.setCharacterEncoding("UTF-8");

        // Recupero utenti
        String jsonUtenti = utenteService.getAllUtentiJson();;

        // Scrittura del JSON nella risposta
        PrintWriter writer = response.getWriter();
        writer.print(jsonUtenti);
        writer.flush();
    }
}
