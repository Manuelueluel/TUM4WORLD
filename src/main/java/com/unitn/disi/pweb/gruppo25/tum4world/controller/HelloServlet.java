package com.unitn.disi.pweb.gruppo25.tum4world.controller;

import com.unitn.disi.pweb.gruppo25.tum4world.model.entities.Utente;
import com.unitn.disi.pweb.gruppo25.tum4world.model.repositories.DonazioneRepo;
import com.unitn.disi.pweb.gruppo25.tum4world.model.repositories.UtenteRepo;
import com.unitn.disi.pweb.gruppo25.tum4world.model.services.UtenteService;

import java.io.*;
import java.util.ArrayList;
import javax.servlet.ServletException;
import javax.servlet.http.*;
import javax.servlet.annotation.*;

@WebServlet(name = "helloServlet", value = "/hello-servlet")
public class HelloServlet extends HttpServlet {
    //Servlet di prova
    private UtenteService utenteService;

    public void init() {
        utenteService = new UtenteService();
    }

    @Override
    protected void doGet(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        String message = utenteService.getAllUtentiJson();

        System.out.println(message);
    }

    public void destroy() {
    }
}