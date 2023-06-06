package com.unitn.disi.pweb.gruppo25.tum4world.model.services;

import com.unitn.disi.pweb.gruppo25.tum4world.model.repositories.UtenteRepo;

public class UtenteService {

    private UtenteRepo utenteRepo;

    public UtenteService(){
        this.utenteRepo = new UtenteRepo();
    }

    public String getAllUtenti() {
        return utenteRepo.getAllUtenti();
    }

    public String getUtenteByUsername(String username) {
        if( username == null || username.equals("")){
            return "";
        }
        return utenteRepo.getUtenteByUsername(username);
    }

}
