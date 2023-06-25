package com.unitn.disi.pweb.gruppo25.tum4world.model.entities;

import java.io.Serializable;
import java.time.LocalDate;
import java.util.Objects;

public class Utente implements Serializable {
    public final static int RUOLO_AMMINISTRATORE  = 0;
    public final static int RUOLO_ADERENTE = 1;
    public final static int RUOLO_SIMPATIZZANTE = 2;

    private int id;
    private String nome;
    private String cognome;
    private LocalDate nascita;
    private String email;
    private String tel;
    private int ruolo;  //0=amministratore, 1=aderente, 2=simpatizzante
    private String username;
    private String password;

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getNome() {
        return nome;
    }

    public void setNome(String nome) {
        this.nome = nome;
    }

    public String getCognome() {
        return cognome;
    }

    public void setCognome(String cognome) {
        this.cognome = cognome;
    }

    public LocalDate getNascita() {
        return nascita;
    }

    public void setNascita(LocalDate nascita) {
        this.nascita = nascita;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public String getTel() {
        return tel;
    }

    public void setTel(String tel) {
        this.tel = tel;
    }

    public int getRuolo() {
        return ruolo;
    }

    public void setRuolo(int ruolo) {
        this.ruolo = ruolo;
    }

    public String getUsername() {
        return username;
    }

    public void setUsername(String username) {
        this.username = username;
    }

    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        Utente utente = (Utente) o;
        return getRuolo() == utente.getRuolo() && getNome().equals(utente.getNome()) && getCognome().equals(utente.getCognome()) && getNascita().equals(utente.getNascita()) && getEmail().equals(utente.getEmail()) && getTel().equals(utente.getTel()) && getUsername().equals(utente.getUsername()) && getPassword().equals(utente.getPassword());
    }

    @Override
    public int hashCode() {
        return Objects.hash(getNome(), getCognome(), getNascita(), getEmail(), getTel(), getRuolo(), getUsername(), getPassword());
    }

    @Override
    public String toString() {
        return "Utente{" +
                "nome='" + nome + '\'' +
                ", cognome='" + cognome + '\'' +
                ", nascita=" + nascita +
                ", email='" + email + '\'' +
                ", tel='" + tel + '\'' +
                ", ruolo=" + ruolo +
                ", username='" + username + '\'' +
                ", password='" + password + '\'' +
                '}';
    }
}

