package com.unitn.disi.pweb.gruppo25.tum4world.filters;

import com.unitn.disi.pweb.gruppo25.tum4world.Utility;
import com.unitn.disi.pweb.gruppo25.tum4world.model.entities.Utente;
import com.unitn.disi.pweb.gruppo25.tum4world.model.services.UtenteService;

import javax.servlet.*;
import javax.servlet.annotation.*;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;
import java.io.IOException;

@WebFilter(filterName = "SimpatizzanteFilter")
public class SimpatizzanteFilter implements Filter {
    private UtenteService utenteService;

    public void init(FilterConfig config) throws ServletException {
        this.utenteService = new UtenteService();
    }

    public void destroy() {
    }

    @Override
    public void doFilter(ServletRequest request, ServletResponse response, FilterChain chain) throws ServletException, IOException {
        System.out.println("SIMPATIZZANTE FILTER----------------");

        HttpServletRequest httpRequest = (HttpServletRequest) request;
        HttpServletResponse httpResponse = (HttpServletResponse) response;
        HttpSession session = httpRequest.getSession(false);
        String username = "";
        Utente utente = null;
        int ruoloSession = Utente.RUOLO_SIMPATIZZANTE;

        if (session == null) {
            System.out.println("Session == null");
            httpResponse.sendError( javax.servlet.http.HttpServletResponse.SC_UNAUTHORIZED);

        }else{
            username = session.getAttribute("username").toString();
            utente = utenteService.getUtenteByUsername(username);
            ruoloSession = (int) session.getAttribute("ruolo");

            System.out.println("username=" + username + "\nruolo= " + ruoloSession + "\nutente= " + utente);
            System.out.println("session = "+session);
            if (utente != null && Utility.isUsernameValid(username) && isRuoloValid(ruoloSession) && isRuoloValid(utente.getRuolo())) {
                System.out.println("chain"+ "\n-----------------");
                chain.doFilter(httpRequest, httpResponse);

            }else {
                System.out.println("error"+ "\n-----------------");
                httpResponse.sendError( javax.servlet.http.HttpServletResponse.SC_UNAUTHORIZED);
            }
        }
    }

    private boolean isRuoloValid(int ruolo){
        return ruolo == Utente.RUOLO_SIMPATIZZANTE;
    }
}
