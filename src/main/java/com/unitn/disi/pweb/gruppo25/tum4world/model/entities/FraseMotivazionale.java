package com.unitn.disi.pweb.gruppo25.tum4world.model.entities;

import java.util.Objects;

public class FraseMotivazionale {

    public static final int LUNGHEZZA_MASSIMA_TESTO = 32672;

    private int id;
    private String testo;

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getTesto() {
        return testo;
    }

    public void setTesto(String testo) {
        this.testo = testo;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        FraseMotivazionale that = (FraseMotivazionale) o;
        return getId() == that.getId() && getTesto().equals(that.getTesto());
    }

    @Override
    public int hashCode() {
        return Objects.hash(getId(), getTesto());
    }
}
