package com.unitn.disi.pweb.gruppo25.tum4world.model.services;

import com.unitn.disi.pweb.gruppo25.tum4world.Utility;
import com.unitn.disi.pweb.gruppo25.tum4world.model.repositories.UtenteRepo;
import com.unitn.disi.pweb.gruppo25.tum4world.model.entities.Utente;

import java.util.ArrayList;

public class UtenteService {

    private UtenteRepo utenteRepo;

    public UtenteService(){
        this.utenteRepo = new UtenteRepo();
    }

    /**
     * Ottiene tutti gli utenti e crea una lista json di oggetti utente
     * @return  lista utenti in formato json, vuota [] se non trova utenti
     */
    public String getAllUtentiJson() {
        String str = "[";
        ArrayList<Utente> lista = (ArrayList<Utente>) utenteRepo.getAllUtenti();

        for (int i = 0; i < lista.size(); i++) {
            if (i < lista.size() - 1) {
                str += Utility.toJSON(lista.get(i)) + ",";
            } else {
                str += Utility.toJSON(lista.get(i));
            }
        }
        return str += "]";
    }

    public String getUtenteJsonByUsername(String username) {
        if( username == null || username.equals("")){
            return Utility.toJSON(null);
        }
        return Utility.toJSON(utenteRepo.getUtenteByUsername(username));
    }

    public Utente getUtenteByUsername(String username) {
        if( username == null || username.equals("")){
            return null;
        }
        return utenteRepo.getUtenteByUsername(username);
    }

    public Utente getUtenteByUsernameAndPassword(String username, String password) {
        if( username == null || username.equals("") || password == null || password.equals("")){
            return null;
        }
        return utenteRepo.getUtenteByUsernameAndPassword(username, password);
    }

    public boolean insertUtente(Utente utente) {
        if (utente == null) {
            return false;
        }
        System.out.println("service utente");
        return utenteRepo.insertUtente(utente);
    }
}
