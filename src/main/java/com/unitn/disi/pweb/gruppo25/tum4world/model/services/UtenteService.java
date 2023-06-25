package com.unitn.disi.pweb.gruppo25.tum4world.model.services;

import com.unitn.disi.pweb.gruppo25.tum4world.model.repositories.UtenteRepo;
import com.unitn.disi.pweb.gruppo25.tum4world.model.entities.Utente;

public class UtenteService {

    private UtenteRepo utenteRepo;

    public UtenteService(){
        this.utenteRepo = new UtenteRepo();
    }

    public String getAllUtenti() {
        return utenteRepo.getAllUtenti();
    }

    public String getUtenteJsonByUsername(String username) {
        if( username == null || username.equals("")){
            return "";
        }
        return utenteRepo.getUtenteJsonByUsername(username);
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

}
