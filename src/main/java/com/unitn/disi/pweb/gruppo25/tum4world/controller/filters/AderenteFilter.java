package com.unitn.disi.pweb.gruppo25.tum4world.controller.filters;

import com.unitn.disi.pweb.gruppo25.tum4world.Utility;
import com.unitn.disi.pweb.gruppo25.tum4world.model.entities.Utente;
import com.unitn.disi.pweb.gruppo25.tum4world.model.services.UtenteService;

import javax.servlet.*;
import javax.servlet.annotation.*;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;
import java.io.IOException;

@WebFilter(filterName = "AderenteFilter")
public class AderenteFilter implements Filter {

    public void init(FilterConfig config) throws ServletException {
    }

    public void destroy() {
    }

    @Override
    public void doFilter(ServletRequest request, ServletResponse response, FilterChain chain) throws ServletException, IOException {
        HttpServletRequest httpRequest = (HttpServletRequest) request;
        HttpServletResponse httpResponse = (HttpServletResponse) response;
        HttpSession session = httpRequest.getSession(false);

        boolean isLoggedIn = (session != null && session.getAttribute(Utente.ISLOGGEDIN_ATTRIBUTE) != null);

        if ( !isLoggedIn) {
            httpResponse.sendError( javax.servlet.http.HttpServletResponse.SC_UNAUTHORIZED);

        } else {
            int ruolo = (int) session.getAttribute(Utente.RUOLO_ATTRIBUTE);

            if ( !(ruolo == Utente.RUOLO_ADERENTE)) {
                httpResponse.sendError( javax.servlet.http.HttpServletResponse.SC_UNAUTHORIZED);
            }
        }
        chain.doFilter(httpRequest, httpResponse);
    }
}
