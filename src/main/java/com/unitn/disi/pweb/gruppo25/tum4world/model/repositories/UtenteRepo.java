package com.unitn.disi.pweb.gruppo25.tum4world.model.repositories;

import com.unitn.disi.pweb.gruppo25.tum4world.Database;
import com.unitn.disi.pweb.gruppo25.tum4world.Utility;
import com.unitn.disi.pweb.gruppo25.tum4world.model.entities.Utente;

import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;

public class UtenteRepo {

    public final static String TABLE_UTENTI = "UTENTI";
    public final static String COLUMN_ID = "ID";
    public final static String COLUMN_NOME = "NOME";
    public final static String COLUMN_COGNOME = "COGNOME";
    public final static String COLUMN_NASCITA = "NASCITA";
    public final static String COLUMN_EMAIL = "EMAIL";
    public final static String COLUMN_TEL = "TEL";
    public final static String COLUMN_RUOLO = "RUOLO";
    public final static String COLUMN_USERNAME = "USERNAME";
    public final static String COLUMN_PASSWORD = "PASSWORD";

    public final static String CREATE_TABLE = "CREATE TABLE " +
            TABLE_UTENTI + " (" +
            COLUMN_ID + " INTEGER NOT NULL GENERATED ALWAYS AS IDENTITY (START WITH 1, INCREMENT BY 1)," +
            COLUMN_NOME + " VARCHAR(32) NOT NULL, " +
            COLUMN_COGNOME + " VARCHAR(32) NOT NULL," +
            COLUMN_USERNAME + " VARCHAR(32) NOT NULL," +
            COLUMN_PASSWORD + " VARCHAR(32) NOT NULL," +
            COLUMN_NASCITA + " DATE NOT NULL," +
            COLUMN_EMAIL + " VARCHAR(32) NOT NULL," +
            COLUMN_TEL + " VARCHAR(32) NOT NULL," +
            COLUMN_RUOLO + " INTEGER  NOT NULL," +
            "PRIMARY KEY (ID) " +
            ")";

    //id omesso perché generato automaticamente dal db
    public final static String INSERT_UTENTE = "INSERT INTO " +
            TABLE_UTENTI + " (" +
            COLUMN_NOME + "," +
            COLUMN_COGNOME + "," +
            COLUMN_NASCITA + "," +
            COLUMN_EMAIL + "," +
            COLUMN_TEL + "," +
            COLUMN_RUOLO + "," +
            COLUMN_USERNAME + "," +
            COLUMN_PASSWORD +
            ") VALUES (?, ?, ?, ?, ?, ?, ?, ?)";

    public final static String SELECT_ALL_UTENTI = "SELECT * FROM " + TABLE_UTENTI;

    public final static String SELECT_BY_USERNAME = "SELECT * FROM " +
            TABLE_UTENTI + " WHERE " +
            COLUMN_USERNAME + " = ?";

    public final static String SELECT_BY_USERNAME_AND_PASSWORD = "SELECT * FROM " +
            TABLE_UTENTI + " WHERE " +
            COLUMN_USERNAME + " =? AND " +
            COLUMN_PASSWORD + " =? ";

    public final static String DELETE_UTENTE = "DELETE FROM " +
            TABLE_UTENTI + " WHERE " +
            COLUMN_USERNAME + " =?";

    private final Database database;

    public UtenteRepo() {
        database = Database.getInstance();
    }

    public void createTableUtenti() {
        //Se la table Utenti non esiste, la creo
        if (!database.tableExist(TABLE_UTENTI)) {
            try {
                Statement statement = database.getConnection().createStatement();
                statement.executeUpdate(CREATE_TABLE);
            } catch (SQLException e) {
                throw new RuntimeException(e);
            }

            //Preregistro l'utente admin
            Utente utente = new Utente();
            utente.setNome("admin");
            utente.setCognome("admin");
            utente.setEmail("admin@tum4world.com");
            utente.setNascita(LocalDate.parse("1900-01-01"));
            utente.setUsername("admin");
            utente.setPassword("25Adm1n!");
            utente.setTel("0000000000");
            utente.setRuolo(Utente.RUOLO_AMMINISTRATORE);
            insertUtente(utente);

            //Preregistro utente aderente per testing
            utente = new Utente();
            utente.setNome("aderente");
            utente.setCognome("aderente");
            utente.setEmail("aderente@tum4world.com");
            utente.setNascita(LocalDate.parse("1900-01-01"));
            utente.setUsername("aderente");
            utente.setPassword("25Adm1n!");
            utente.setTel("0000000000");
            utente.setRuolo(Utente.RUOLO_ADERENTE);
            insertUtente(utente);

            //Preregistro utente simpatizzante per testing
            utente = new Utente();
            utente.setNome("simpatizzante");
            utente.setCognome("simpatizzante");
            utente.setEmail("simpatizzante@tum4world.com");
            utente.setNascita(LocalDate.parse("1900-01-01"));
            utente.setUsername("simpatizzante");
            utente.setPassword("25Adm1n!");
            utente.setTel("0000000000");
            utente.setRuolo(Utente.RUOLO_SIMPATIZZANTE);
            insertUtente(utente);
        }
    }

    public boolean insertUtente(Utente utente) {
        //Null utente non trovato, non null trovato
        if (getUtenteByUsername(utente.getUsername()) == null) {

            try {
                PreparedStatement preparedStatement = this.database.getConnection().prepareStatement(INSERT_UTENTE);

                preparedStatement.setString(1, utente.getNome());
                preparedStatement.setString(2, utente.getCognome());
                //Trasforma LocalDate to Date
                preparedStatement.setDate(3, java.sql.Date.valueOf(utente.getNascita()));
                preparedStatement.setString(4, utente.getEmail());
                preparedStatement.setString(5, utente.getTel());
                preparedStatement.setInt(6, utente.getRuolo());
                preparedStatement.setString(7, utente.getUsername());
                preparedStatement.setString(8, utente.getPassword());
                preparedStatement.executeUpdate();

            } catch (SQLException e) {
                throw new RuntimeException(e);
            }
            return true;

        } else {
           return false;
        }
    }

    public List<Utente> getAllUtenti() {
        ArrayList<Utente> lista = null;

        try {
            Statement statement = this.database.getConnection().createStatement(ResultSet.TYPE_SCROLL_INSENSITIVE, ResultSet.CONCUR_READ_ONLY);
            ResultSet rs = statement.executeQuery(SELECT_ALL_UTENTI);
            lista = new ArrayList<>();
            while (rs.next()) {
                Utente utente = new Utente();
                utente.setId(rs.getInt(COLUMN_ID));
                utente.setNome(rs.getString(COLUMN_NOME));
                utente.setCognome(rs.getString(COLUMN_COGNOME));
                utente.setNascita(rs.getDate(COLUMN_NASCITA).toLocalDate());
                utente.setEmail(rs.getString(COLUMN_EMAIL));
                utente.setTel(rs.getString(COLUMN_TEL));
                utente.setRuolo(rs.getInt(COLUMN_RUOLO));
                utente.setUsername(rs.getString(COLUMN_USERNAME));
                utente.setPassword(rs.getString(COLUMN_PASSWORD));

                lista.add(utente);
            }
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
        return lista;
    }

    public Utente getUtenteByUsername(String username) {
        Utente utente = null;

        try {
            PreparedStatement preparedStatement = this.database.getConnection().prepareStatement(SELECT_BY_USERNAME);
            preparedStatement.setString(1, username);

            ResultSet rs = preparedStatement.executeQuery();

            while (rs.next()) {
                utente = new Utente();
                utente.setNome(rs.getString(COLUMN_NOME));
                utente.setCognome(rs.getString(COLUMN_COGNOME));
                utente.setNascita(rs.getDate(COLUMN_NASCITA).toLocalDate());
                utente.setEmail(rs.getString(COLUMN_EMAIL));
                utente.setTel(rs.getString(COLUMN_TEL));
                utente.setRuolo(rs.getInt(COLUMN_RUOLO));
                utente.setUsername(rs.getString(COLUMN_USERNAME));
                utente.setPassword(rs.getString(COLUMN_PASSWORD));
            }
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
        return utente;
    }

    public Utente getUtenteByUsernameAndPassword(String username, String password) {
        Utente utente = null;

        try {
            PreparedStatement preparedStatement = this.database.getConnection().prepareStatement(SELECT_BY_USERNAME_AND_PASSWORD);
            preparedStatement.setString(1, username);
            preparedStatement.setString(2, password);

            ResultSet rs = preparedStatement.executeQuery();

            while (rs.next()) {
                utente = new Utente();
                utente.setNome(rs.getString(COLUMN_NOME));
                utente.setCognome(rs.getString(COLUMN_COGNOME));
                utente.setNascita(rs.getDate(COLUMN_NASCITA).toLocalDate());
                utente.setEmail(rs.getString(COLUMN_EMAIL));
                utente.setTel(rs.getString(COLUMN_TEL));
                utente.setRuolo(rs.getInt(COLUMN_RUOLO));
                utente.setUsername(rs.getString(COLUMN_USERNAME));
                utente.setPassword(rs.getString(COLUMN_PASSWORD));
            }
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
        return utente;
    }

    public boolean deleteUtente(String username) {
        Utente utente = getUtenteByUsername(username);

        if (utente != null) {
            try {
                PreparedStatement preparedStatement = this.database.getConnection().prepareStatement(DELETE_UTENTE);
                preparedStatement.setString(1, username);
                preparedStatement.executeUpdate();

            } catch (SQLException e) {
                throw new RuntimeException(e);
            }
            return true;

        } else {
            return false;
        }
    }
}
