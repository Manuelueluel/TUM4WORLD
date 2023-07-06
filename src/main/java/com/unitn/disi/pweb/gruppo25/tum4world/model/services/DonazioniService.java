package com.unitn.disi.pweb.gruppo25.tum4world.model.services;

import com.unitn.disi.pweb.gruppo25.tum4world.Utility;
import com.unitn.disi.pweb.gruppo25.tum4world.model.entities.Donazione;
import com.unitn.disi.pweb.gruppo25.tum4world.model.repositories.DonazioniRepo;

import java.util.ArrayList;

public class DonazioniService {

    private DonazioniRepo donazioniRepo;

    public DonazioniService() {
        this.donazioniRepo = new DonazioniRepo();
    }

    public boolean insertDonazione(Donazione donazione) {
        if (donazione == null) {
            return false;
        }
        donazioniRepo.insertDonazione(donazione);
        return true;
    }

    /**
     * Ottiene tutte le donazioni e crea una lista json di oggetti donazione
     * @return  lista donazioni in formato json, vuota [] se non trova donazioni
     */
    public String getAllDonazioniJson() {
        String str = "[";
        ArrayList<Donazione> lista = (ArrayList<Donazione>) donazioniRepo.getAllDonazioni();

        for (int i = 0; i < lista.size(); i++) {
            if (i < lista.size() - 1) {
                str += Utility.toJSON(lista.get(i)) + ",";
            } else {
                str += Utility.toJSON(lista.get(i));
            }
        }
        return str += "]";
    }

}
