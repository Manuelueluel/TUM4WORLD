package com.unitn.disi.pweb.gruppo25.tum4world.controller;

import com.unitn.disi.pweb.gruppo25.tum4world.model.entities.Utente;
import com.unitn.disi.pweb.gruppo25.tum4world.model.services.UtenteService;

import javax.servlet.*;
import javax.servlet.http.*;
import javax.servlet.annotation.*;
import java.io.IOException;

@WebServlet(name = "LoginServlet", value = "/login")
public class LoginServlet extends HttpServlet {
    public static final int SESSION_MAX_INACTIVE = 60;  //in secondi
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
        Utente utente = utenteService.getUtenteByUsernameAndPassword(username, password);
        System.out.println("LoginServlet---------------------\n"+utente);

        HttpSession session = request.getSession(false);

        if(utente == null) {   //Utente non trovato
            System.out.println("utente == null");
            response.sendError( HttpServletResponse.SC_UNAUTHORIZED);

        } else if (session == null) {   //Utente trovato ma senza session
            System.out.println("session == null => allora creo session");

            session = request.getSession(true);
            session.setMaxInactiveInterval(SESSION_MAX_INACTIVE);   //durata massima session inattiva 1 minuto
            System.out.println("session = "+session);
            reindirizza(utente, session, response, request);

        }else {     //Utente trovato con session

            //Ruolo dell'utente differente dal ruolo che è presente nella sessione
            if (utente.getRuolo() != (int) session.getAttribute("ruolo")) {
                System.out.println("RUOLO DIFFERENTE"+utente.getRuolo()+" "+(int) session.getAttribute("ruolo"));
                response.sendError( javax.servlet.http.HttpServletResponse.SC_UNAUTHORIZED);
            } else {
                System.out.println("STESSO RUOLO "+utente.getRuolo()+" "+(int) session.getAttribute("ruolo"));
                reindirizza(utente, session, response, request);
            }
        }
    }

    /**
     * Imposta attributi della session e in base al ruolo dell'utente viene reindirizzato
     * @param utente    utente da reindirizzare
     * @param session   in base al ruolo utente impostati attributi "username" e "ruolo"
     * @param response  in base al ruolo reindirizza alla pagina privata
     * @throws IOException
     * @throws ServletException
     */
    private void reindirizza(Utente utente, HttpSession session, HttpServletResponse response, HttpServletRequest request) throws IOException, ServletException {
        switch (utente.getRuolo()) {
            case Utente.RUOLO_AMMINISTRATORE:
                session.setAttribute("username", utente.getUsername());
                session.setAttribute("ruolo", Utente.RUOLO_AMMINISTRATORE);
                String encode = response.encodeRedirectURL("./privata/amministratore.html");
                response.sendRedirect(encode);
                //request.getRequestDispatcher(encode).forward(request, response);
                break;
            case Utente.RUOLO_ADERENTE:
                session.setAttribute("username", utente.getUsername());
                session.setAttribute("ruolo", Utente.RUOLO_ADERENTE);
                encode = response.encodeRedirectURL("./privata/aderente.html");
                response.sendRedirect(encode);
                break;
            case Utente.RUOLO_SIMPATIZZANTE:
                session.setAttribute("username", utente.getUsername());
                session.setAttribute("ruolo", Utente.RUOLO_SIMPATIZZANTE);
                encode = response.encodeRedirectURL("./privata/simpatizzante.html");
                response.sendRedirect(encode);
                break;
            default:
                response.sendError(HttpServletResponse.SC_BAD_REQUEST);
                break;
        }
    }
}
