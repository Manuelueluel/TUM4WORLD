package com.unitn.disi.pweb.gruppo25.tum4world.controller;

import com.unitn.disi.pweb.gruppo25.tum4world.model.entities.FraseMotivazionale;
import com.unitn.disi.pweb.gruppo25.tum4world.model.services.FraseMotivazionaleService;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.io.PrintWriter;

@WebServlet(name = "FraseMotivazionaleController", value = "/frase")
public class FraseMotivazionaleController extends HttpServlet {

    private FraseMotivazionaleService fraseMotivazionaleService;

    @Override
    public void init() throws ServletException {
        this.fraseMotivazionaleService = new FraseMotivazionaleService();
    }

    @Override
    protected void doGet(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        resp.setContentType("application/json");
        resp.setCharacterEncoding("UTF-8");

        String fraseJson = fraseMotivazionaleService.getRandomFraseMotivazionaleJson();

        PrintWriter writer = resp.getWriter();
        writer.print(fraseJson);
        writer.flush();
    }

    @Override
    protected void doPost(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        resp.setContentType("application/json");
        resp.setCharacterEncoding("UTF-8");

        String testo = req.getParameter("testo");
        //se il testo inviato è valido inserisco la frase ne db, in caso di errore segnalo lo stato nella response
        if (testo != null) {
            FraseMotivazionale frase = new FraseMotivazionale();
            frase.setTesto(testo);
            if (fraseMotivazionaleService.insertFrase(frase)) {
                resp.setStatus(200);
            } else {
                resp.setStatus(400);
            }
        } else {
            resp.setStatus(400);
        }
    }
}
