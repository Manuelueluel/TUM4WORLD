package com.unitn.disi.pweb.gruppo25.tum4world.model.entities;


import java.io.Serializable;
import java.time.LocalDate;
import java.util.Objects;

public class Counter implements Serializable {

    private int id;

    private int contatore;

    private String pagina;

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public int getContatore() {
        return contatore;
    }

    public void setContatore(int numero) {
        this.contatore = numero;
    }

    public String getPagina() {
        return this.pagina;
    }

    public void setPagina(String pagina) {
        this.pagina = pagina;
    }

    @Override
    public int hashCode() {
        return Objects.hash(getId(), getContatore());
    }
}
