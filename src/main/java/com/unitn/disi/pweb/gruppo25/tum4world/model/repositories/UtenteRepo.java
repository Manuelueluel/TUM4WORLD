package com.unitn.disi.pweb.gruppo25.tum4world.model.repositories;

import com.unitn.disi.pweb.gruppo25.tum4world.Database;
import com.unitn.disi.pweb.gruppo25.tum4world.Utility;
import com.unitn.disi.pweb.gruppo25.tum4world.model.entities.Utente;

import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.time.LocalDate;

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

    private final Database database;

    public UtenteRepo() {
        database = Database.getInstance();
    }

    public void createTableUtenti() {
        //Se la table Utenti non esiste, la creo
        if ( !database.tableExist(TABLE_UTENTI)) {
            try {
                Statement statement = database.getConnection().createStatement();
                statement.executeUpdate(CREATE_TABLE);
            } catch (SQLException e) {
                throw new RuntimeException(e);
            }

            //Preregistro l'utente admin
            Utente admin = new Utente();
            admin.setNome("admin");
            admin.setCognome("admin");
            admin.setEmail("admin@tum4world.com");
            admin.setNascita( LocalDate.parse("1900-01-01"));
            admin.setUsername("admin");
            admin.setPassword("25Adm1n!");
            admin.setTel("0000000000");
            admin.setRuolo(0);

            insertUtente(admin);
        }
    }

    public void insertUtente(Utente utente) {
        //Stringa vuota utente non trovato con tale username, altrimenti già esistente
        if ( getUtenteByUsername(utente.getUsername()).isEmpty()) {

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

        } else {
            //Utente già esistente con tale username
        }
    }

    public String getAllUtenti() {

        StringBuilder stringBuilder = new StringBuilder();
        stringBuilder.append("[");

        try {
            Statement statement = this.database.getConnection().createStatement( ResultSet.TYPE_SCROLL_INSENSITIVE, ResultSet.CONCUR_READ_ONLY);
            ResultSet rs = statement.executeQuery(SELECT_ALL_UTENTI);
            while (rs.next()){
                Utente utente = new Utente();
                utente.setNome(rs.getString(COLUMN_NOME));
                utente.setCognome(rs.getString(COLUMN_COGNOME));
                utente.setNascita(rs.getDate(COLUMN_NASCITA).toLocalDate());
                utente.setEmail(rs.getString(COLUMN_EMAIL));
                utente.setTel(rs.getString(COLUMN_TEL));
                utente.setRuolo(rs.getInt(COLUMN_RUOLO));
                utente.setUsername(rs.getString(COLUMN_USERNAME));
                utente.setPassword(rs.getString(COLUMN_PASSWORD));

                stringBuilder.append(Utility.toJSON(utente));
                if (!rs.isLast()) {
                    stringBuilder.append(",");
                }
            }
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
        return stringBuilder.append("]").toString();
    }


    public String getUtenteByUsername(String username) {
        StringBuilder stringBuilder = new StringBuilder();

        try {
            PreparedStatement preparedStatement = this.database.getConnection().prepareStatement(SELECT_BY_USERNAME);
            preparedStatement.setString(1, username);

            ResultSet rs = preparedStatement.executeQuery();

            while (rs.next()) {
                Utente utente = new Utente();
                utente.setNome(rs.getString(COLUMN_NOME));
                utente.setCognome(rs.getString(COLUMN_COGNOME));
                utente.setNascita(rs.getDate(COLUMN_NASCITA).toLocalDate());
                utente.setEmail(rs.getString(COLUMN_EMAIL));
                utente.setTel(rs.getString(COLUMN_TEL));
                utente.setRuolo(rs.getInt(COLUMN_RUOLO));
                utente.setUsername(rs.getString(COLUMN_USERNAME));
                utente.setPassword(rs.getString(COLUMN_PASSWORD));

                stringBuilder.append(Utility.toJSON(utente));
            }
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }

        return stringBuilder.toString();
    }

}
