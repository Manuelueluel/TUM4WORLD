package com.unitn.disi.pweb.gruppo25.tum4world.controller;

import com.unitn.disi.pweb.gruppo25.tum4world.Utility;
import com.unitn.disi.pweb.gruppo25.tum4world.model.entities.Utente;
import com.unitn.disi.pweb.gruppo25.tum4world.model.services.UtenteService;

import javax.servlet.*;
import javax.servlet.http.*;
import javax.servlet.annotation.*;
import java.io.IOException;
import java.io.PrintWriter;

@WebServlet(name = "LoginServlet", value = "/login")
public class LoginServlet extends HttpServlet {
    private UtenteService utenteService;

    @Override
    public void init() throws ServletException {
        this.utenteService = new UtenteService();
    }

    @Override
    protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        response.sendError( HttpServletResponse.SC_METHOD_NOT_ALLOWED);
    }

    @Override
    protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        String username = request.getParameter("username");
        String password = request.getParameter("password");
        HttpSession session = request.getSession(false);
        Utente utenteByUsername = utenteService.getUtenteByUsername(username);
        Utente utente = null;
        PrintWriter writer = response.getWriter();

        response.setContentType("application/json");
        response.setCharacterEncoding("UTF-8");

        if (utenteByUsername == null){ // username non valido
            writer.print("{\"25\":\"Username non valido\", \"success\":false}");

        } else {
            utente = utenteService.getUtenteByUsernameAndPassword(username, password);

            if (utente == null) { //username valido ma password no
                writer.print("{\"25\":\"Password non valida\", \"success\":false}");

            } else { //username valido e password valida
                if (session == null) {   //Utente trovato ma senza session
                    session = request.getSession(true);
                    session.setMaxInactiveInterval(Utility.SESSION_MAX_INACTIVE);
                    redirect(utente, session, response);

                }else {     //Utente trovato con session
                    //Ruolo dell'utente differente dal ruolo che è presente nella sessione
                    if (utente.getRuolo() != (int) session.getAttribute("ruolo")) {
                        writer.print("{\"25\":\"Ruolo non valido\", \"success\":false}");
                    } else {
                        redirect(utente, session, response);
                    }
                }
            }
        }
        writer.flush();
    }

    /**
     * Reindirizza l'utente alla propria pagina personale in base al proprio ruolo
     * @param utente
     * @param session
     * @param response
     * @throws IOException
     */
    private static void redirect(Utente utente, HttpSession session, HttpServletResponse response) throws IOException {
        PrintWriter writer = response.getWriter();

        switch (utente.getRuolo()) {
            case Utente.RUOLO_AMMINISTRATORE:
                session.setAttribute("username", utente.getUsername());
                session.setAttribute("ruolo", Utente.RUOLO_AMMINISTRATORE);
                String encode = response.encodeRedirectURL("./privata/amministratore.html");
                //response.sendRedirect(encode);
                writer.print("{\"success\":true, \"redirectUrl\":\""+encode+"\"}");
                break;
            case Utente.RUOLO_ADERENTE:
                session.setAttribute("username", utente.getUsername());
                session.setAttribute("ruolo", Utente.RUOLO_ADERENTE);
                encode = response.encodeRedirectURL("./privata/aderente.html");
                //response.sendRedirect(encode);
                writer.print("{\"success\":true, \"redirectUrl\":\""+encode+"\"}");
                break;
            case Utente.RUOLO_SIMPATIZZANTE:
                session.setAttribute("username", utente.getUsername());
                session.setAttribute("ruolo", Utente.RUOLO_SIMPATIZZANTE);
                encode = response.encodeRedirectURL("./privata/simpatizzante.html");
                //response.sendRedirect(encode);
                writer.print("{\"success\":true, \"redirectUrl\":\""+encode+"\"}");
                break;
            default:
                response.setStatus(HttpServletResponse.SC_BAD_REQUEST);
                writer.print("{\"25\":\"Errore inatteso\", \"success\":false}");
                break;
        }
        writer.flush();
    }
}
