package com.unitn.disi.pweb.gruppo25.tum4world.controller;


import com.unitn.disi.pweb.gruppo25.tum4world.model.entities.Donazione;
import com.unitn.disi.pweb.gruppo25.tum4world.model.services.DonazioniService;
import com.unitn.disi.pweb.gruppo25.tum4world.model.services.UtenteService;

import javax.servlet.ServletException;
import javax.servlet.annotation.*;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.io.PrintWriter;
import java.time.LocalDate;
import java.time.format.DateTimeParseException;
import java.util.ArrayList;

@WebServlet(name = "DonazioniController", value = "/donazioni")
public class DonazioniController extends HttpServlet {

    private DonazioniService donazioniService;

    @Override
    public void init() throws ServletException {
        this.donazioniService = new DonazioniService();
    }

    @Override
    protected void doGet(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        resp.setContentType("application/json");
        resp.setCharacterEncoding("UTF-8");

        String jsonDonazioni = donazioniService.getAllDonazioniJson();

        PrintWriter writer = resp.getWriter();
        writer.print(jsonDonazioni);
        writer.flush();
    }

    @Override
    protected void doPost(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        resp.setContentType("application/json");
        resp.setCharacterEncoding("UTF-8");
        PrintWriter writer = resp.getWriter();

        Integer quantita = null;
        LocalDate data = null;
        try {
            data = LocalDate.parse(req.getParameter("data"));
            quantita = Integer.valueOf(req.getParameter("quantita"));
        } catch (DateTimeParseException | NumberFormatException ignored) {}

        //Da qui in poi controllo campi ricevuti
        String errMessage = "{\"25\":[";   //json inviato solamente se presenti errori nei vari campi
        ArrayList<String> errors = new ArrayList<>();

        if (data == null){
            errors.add("data");
        }
        if (quantita == null || quantita < 0) {
            errors.add("quantita");
        }

        if (!errors.isEmpty()) {
            for (int i = 0; i < errors.size(); i++) {
                if (i < errors.size() - 1) {
                    errMessage += "\"" + errors.get(i) + "\",";
                } else {
                    errMessage += "\"" + errors.get(i) + "\"";
                }
            }
            errMessage += "]}";
            writer.print(errMessage);
            writer.flush();

        } else {
            Donazione donazione = new Donazione();
            donazione.setData(data);
            donazione.setQuantita(quantita);

            if (donazioniService.insertDonazione(donazione)) {
                resp.setStatus(HttpServletResponse.SC_OK);
            } else {
                resp.setStatus(HttpServletResponse.SC_INTERNAL_SERVER_ERROR);
            }
        }
    }
}
