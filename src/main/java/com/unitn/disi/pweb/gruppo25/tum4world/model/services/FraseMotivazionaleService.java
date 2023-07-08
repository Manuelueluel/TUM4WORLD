package com.unitn.disi.pweb.gruppo25.tum4world.model.services;

import com.unitn.disi.pweb.gruppo25.tum4world.Utility;
import com.unitn.disi.pweb.gruppo25.tum4world.model.entities.FraseMotivazionale;
import com.unitn.disi.pweb.gruppo25.tum4world.model.repositories.FraseMotivazionaleRepo;

import java.util.ArrayList;
import java.util.List;
import java.util.Random;

public class FraseMotivazionaleService {

    private FraseMotivazionaleRepo fraseMotivazionaleRepo;

    public FraseMotivazionaleService() {
        this.fraseMotivazionaleRepo = new FraseMotivazionaleRepo();
    }

    public boolean insertFrase(FraseMotivazionale frase) {
        if (frase == null || frase.getTesto().length() >= Integer.MAX_VALUE) {
            return false;
        }
        fraseMotivazionaleRepo.insertFrase(frase);
        return true;
    }

    public List<FraseMotivazionale> getAllFrasiMotivazionali() {
        return fraseMotivazionaleRepo.getAllFrasiMotivazionali();
    }

    public String getAllFrasiMotivazionaliJson() {
        String str = "[";
        ArrayList<FraseMotivazionale> lista = (ArrayList<FraseMotivazionale>) fraseMotivazionaleRepo.getAllFrasiMotivazionali();

        for (int i = 0; i < lista.size(); i++) {
            if (i < lista.size() - 1) {
                str += Utility.toJSON(lista.get(i)) + ",";
            } else {
                str += Utility.toJSON(lista.get(i));
            }
        }
        return str += "]";
    }

    public String getRandomFraseMotivazionaleJson() {
        String str = null;
        ArrayList<FraseMotivazionale> lista = (ArrayList<FraseMotivazionale>) getAllFrasiMotivazionali();
        Random rand = new Random();
        FraseMotivazionale randomFrase = lista.get( rand.nextInt(lista.size()));

        if (randomFrase != null) {
            str = Utility.toJSON(randomFrase);
        }
        return str;
    }

}
