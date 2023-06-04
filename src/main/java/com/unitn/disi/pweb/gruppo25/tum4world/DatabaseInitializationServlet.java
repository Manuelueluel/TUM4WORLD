package com.unitn.disi.pweb.gruppo25.tum4world;

import com.unitn.disi.pweb.gruppo25.tum4world.model.repositories.DonazioneRepo;
import com.unitn.disi.pweb.gruppo25.tum4world.model.repositories.UtenteRepo;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;

/**
 * Servlet cariata all'avvio della Web App, se non esistono crea le tabelle necessarie nel database
 */
@WebServlet(name = "DatabaseInitializationServlet", urlPatterns = "/DatabaseInitializationServlet", loadOnStartup = 1)
public class DatabaseInitializationServlet extends HttpServlet {

    private UtenteRepo utenteRepo;
    private DonazioneRepo donazioneRepo;

    @Override
    public void init() throws ServletException {
        this.utenteRepo = new UtenteRepo();
        this.donazioneRepo = new DonazioneRepo();

        //Creo tabella utenti se non esiste
        utenteRepo.createTableUtenti();

        //Crea tabella donazioni se non esiste
        donazioneRepo.createTableDonazioni();

    }
}