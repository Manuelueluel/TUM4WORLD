package com.unitn.disi.pweb.gruppo25.tum4world.model.entities;


import java.io.Serializable;
import java.time.LocalDate;
import java.util.Objects;

public class Donazione implements Serializable {

    private int id;
    private LocalDate data;
    private int quantita;

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public LocalDate getData() {
        return data;
    }

    public void setData(LocalDate data) {
        this.data = data;
    }

    public int getQuantita() {
        return quantita;
    }

    public void setQuantita(int quantita) {
        this.quantita = quantita;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        Donazione donazione = (Donazione) o;
        return getId() == donazione.getId() && getQuantita() == donazione.getQuantita() && getData().equals(donazione.getData());
    }

    @Override
    public int hashCode() {
        return Objects.hash(getId(), getData(), getQuantita());
    }
}
