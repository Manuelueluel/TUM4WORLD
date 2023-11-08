package com.unitn.disi.pweb.gruppo25.tum4world;

import com.unitn.disi.pweb.gruppo25.tum4world.model.repositories.CounterRepo;
import com.unitn.disi.pweb.gruppo25.tum4world.model.repositories.DonazioniRepo;
import com.unitn.disi.pweb.gruppo25.tum4world.model.repositories.FraseMotivazionaleRepo;
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
    private DonazioniRepo donazioniRepo;
    private FraseMotivazionaleRepo fraseMotivazionaleRepo;
    private CounterRepo counterRepo;

    @Override
    public void init() throws ServletException {
        this.utenteRepo = new UtenteRepo();
        this.donazioniRepo = new DonazioniRepo();
        this.fraseMotivazionaleRepo = new FraseMotivazionaleRepo();
        this.counterRepo = new CounterRepo();

        //Creo tabella utenti se non esiste
        utenteRepo.createTableUtenti();

        //Crea tabella donazioni se non esiste
        donazioniRepo.createTableDonazioni();

        //Crea file json e viene popolato da alcune frasi motivazionali di default
        fraseMotivazionaleRepo.createFileAndPopolate();

        //Crea tabella contatori
        counterRepo.createTableCounter();

    }
}