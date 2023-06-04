package com.unitn.disi.pweb.gruppo25.tum4world.controller;

import com.unitn.disi.pweb.gruppo25.tum4world.model.repositories.DonazioneRepo;
import com.unitn.disi.pweb.gruppo25.tum4world.model.repositories.UtenteRepo;

import java.io.*;
import javax.servlet.ServletException;
import javax.servlet.http.*;
import javax.servlet.annotation.*;

@WebServlet(name = "helloServlet", value = "/hello-servlet")
public class HelloServlet extends HttpServlet {

    private UtenteRepo utenteRepo;
    private DonazioneRepo donazioneRepo;

    public void init() {
        utenteRepo = new UtenteRepo();
        donazioneRepo = new DonazioneRepo();
    }

    @Override
    protected void doGet(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        String message = utenteRepo.getAllUtenti() + "\n";
        System.out.println(message);
    }

    /*
        private String queryAll(){
            StringBuilder stringBuilder = new StringBuilder();
            stringBuilder.append(database.getUtenteByUsername("domenico"));

            return stringBuilder.toString();
        }
        public void doGet(HttpServletRequest request, HttpServletResponse response) throws IOException {
            response.setContentType("text/html");


            Utente admin = new Utente();
            admin.setNome("manuel");
            admin.setCognome("manuel");
            admin.setEmail("admin@tum4world.com");
            admin.setNascita( LocalDate.parse("2000-01-01"));
            admin.setUsername("domenico");
            admin.setPassword("25Adm1n!");
            admin.setTel("0000000000");
            admin.setRuolo(0);

            database.insertUtente(admin);
            message = queryAll();

            // Hello
            PrintWriter out = response.getWriter();
            out.println("<html><body>");
            out.println("<h4>" + message + "</h4>");
            out.println("</body></html>");
        }
    */
    public void destroy() {
    }
}