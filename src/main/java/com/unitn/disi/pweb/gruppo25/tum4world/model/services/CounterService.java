package com.unitn.disi.pweb.gruppo25.tum4world.model.services;

import com.unitn.disi.pweb.gruppo25.tum4world.Utility;
import com.unitn.disi.pweb.gruppo25.tum4world.model.entities.Counter;
import com.unitn.disi.pweb.gruppo25.tum4world.model.repositories.CounterRepo;

import java.util.ArrayList;

public class CounterService {

    private CounterRepo counterRepo;

    public CounterService() {
        this.counterRepo = new CounterRepo();
    }

    public boolean incrementCounter(String pagina) {
        if(pagina == null || pagina.equals("")){
            return false;
        }
        counterRepo.incrementCounter(pagina);
        return true;
    }

    public void resetAllCounters() {
        counterRepo.resetAllCounters();
    }

    public String getAllCountersJson() {
        String str = "[";
        ArrayList<Counter> lista = (ArrayList<Counter>) counterRepo.getAllCounters();

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
