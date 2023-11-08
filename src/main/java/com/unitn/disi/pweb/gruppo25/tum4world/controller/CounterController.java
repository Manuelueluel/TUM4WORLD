package com.unitn.disi.pweb.gruppo25.tum4world.controller;


import com.unitn.disi.pweb.gruppo25.tum4world.model.services.CounterService;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.io.PrintWriter;

@WebServlet(name = "CounterController", value = "/counter")
public class CounterController extends HttpServlet {

    private CounterService counterService;

    @Override
    public void init() throws ServletException {
        this.counterService = new CounterService();
    }

    @Override
    protected void doGet(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        resp.setContentType("application/json");
        resp.setCharacterEncoding("UTF-8");

        String jsonCounters = counterService.getAllCountersJson();

        PrintWriter writer = resp.getWriter();
        writer.print(jsonCounters);
        writer.flush();
    }

    @Override
    protected void doPost(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        resp.setContentType("application/json");
        resp.setCharacterEncoding("UTF-8");

        String pagina = req.getParameter("pagina");

        if (counterService.incrementCounter(pagina)) {
            System.out.println(pagina + " incremento con successo");
            resp.setStatus(HttpServletResponse.SC_OK);
        } else {
            System.out.println(pagina + " NON incremento con successo");
            resp.setStatus(HttpServletResponse.SC_INTERNAL_SERVER_ERROR);
        }
    }

    @Override
    protected void doDelete(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        resp.setContentType("application/json");
        resp.setCharacterEncoding("UTF-8");

        this.counterService.resetAllCounters();
    }
}
