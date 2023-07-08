package com.unitn.disi.pweb.gruppo25.tum4world.model.repositories;

import java.io.*;
import java.util.ArrayList;
import java.util.List;

import com.google.gson.Gson;
import com.google.gson.reflect.TypeToken;
import com.unitn.disi.pweb.gruppo25.tum4world.model.entities.FraseMotivazionale;

public class FraseMotivazionaleRepo implements Serializable {

    private final String FILE_NAME = "./FrasiMotivazionali.json";

    public FraseMotivazionaleRepo() {
    }

    public void createFileAndPopolate() {
        File file = new File(FILE_NAME);
        if (!file.exists()) {   //Se il file non esiste lo inizializzo con una lista di frasi
            System.out.println("creazione lista frasi...");
            List<FraseMotivazionale> lista = new ArrayList<>();

            FraseMotivazionale frase = new FraseMotivazionale();
            frase.setId(0);
            frase.setTesto("Se qualcosa non ti piace, cambiala. Se non puoi cambiarla, cambia il tuo atteggiamento.");
            lista.add(frase);

            frase = new FraseMotivazionale();
            frase.setId(1);
            frase.setTesto("Se vuoi qualcosa che non hai mai avuto, devi fare qualcosa che non hai mai fatto.");
            lista.add(frase);

            frase = new FraseMotivazionale();
            frase.setId(2);
            frase.setTesto("Il mio scopo nella vita? Fare solo cose positive.");
            lista.add(frase);

            frase = new FraseMotivazionale();
            frase.setId(3);
            frase.setTesto("Non è mai troppo tardi per essere ciò che avresti voluto essere.");
            lista.add(frase);

            frase = new FraseMotivazionale();
            frase.setId(4);
            frase.setTesto("Credi in te stesso quando nessun altro lo fa. Ciò ti rende all’istante un vincitore.");
            lista.add(frase);

            frase = new FraseMotivazionale();
            frase.setId(5);
            frase.setTesto("Non puoi mettere limiti a niente. Più sogni, più andrai lontano.");
            lista.add(frase);

            frase = new FraseMotivazionale();
            frase.setId(6);
            frase.setTesto("Non importa chi tu sia, o da dove tu venga. La capacità di trionfare inizia con te. Sempre.");
            lista.add(frase);

            //Scrivo la lista nel file json
            try (FileWriter writer = new FileWriter(file)) {
                Gson gson = new Gson();
                gson.toJson(lista, writer);
            } catch (IOException e) {
                e.printStackTrace();
            }
        }
    }

    public void insertFrase(FraseMotivazionale fraseMotivazionale) {
        Gson gson = new Gson();
        //Ottengo la lista
        ArrayList<FraseMotivazionale> lista = (ArrayList<FraseMotivazionale>) getAllFrasiMotivazionali();
        //Aggiungo la nuova frase alla lista
        fraseMotivazionale.setId(lista.size());
        lista.add(fraseMotivazionale);

        try (FileWriter writer = new FileWriter(FILE_NAME)) {
            //Riscrivo tutta la lista nel file
            gson.toJson(lista, writer);
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    public List<FraseMotivazionale> getAllFrasiMotivazionali() {
        Gson gson = new Gson();
        List<FraseMotivazionale> listaFrasi = null;
        try (FileReader reader = new FileReader(FILE_NAME)) {
            //Deserializza il file ottenendo una lista tipizzata
            listaFrasi = gson.fromJson(reader, new TypeToken<List<FraseMotivazionale>>(){}.getType());
        } catch (IOException e) {
            e.printStackTrace();
        }
        return listaFrasi;
    }

}
