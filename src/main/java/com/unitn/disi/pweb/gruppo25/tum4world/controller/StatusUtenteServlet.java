package com.unitn.disi.pweb.gruppo25.tum4world.controller;

import com.unitn.disi.pweb.gruppo25.tum4world.model.entities.Utente;

import javax.servlet.*;
import javax.servlet.http.*;
import javax.servlet.annotation.*;
import java.io.IOException;
import java.io.PrintWriter;

@WebServlet(name = "StatusUtenteServlet", value = "/status")
public class StatusUtenteServlet extends HttpServlet {

    /**
     * Restituisce un oggetto json con ruolo dell'utente e se è loggato o meno
     * @param request
     * @param response
     * @throws ServletException
     * @throws IOException
     */
    @Override
    protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        HttpSession session = request.getSession(false);
        PrintWriter writer = response.getWriter();
        boolean isLoggedIn = (session != null && session.getAttribute(Utente.ISLOGGEDIN_ATTRIBUTE) != null);

        response.setContentType("application/json");
        response.setCharacterEncoding("UTF-8");

        String json = "{ \"" + Utente.ISLOGGEDIN_ATTRIBUTE + "\":" + isLoggedIn +
                ",\"ruolo\":" + (session == null ? null : session.getAttribute(Utente.RUOLO_ATTRIBUTE)) + "}";
        writer.print(json);
        writer.flush();
    }

    @Override
    protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        response.sendError( HttpServletResponse.SC_METHOD_NOT_ALLOWED);
    }
}
