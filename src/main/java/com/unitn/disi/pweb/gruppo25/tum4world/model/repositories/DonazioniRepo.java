package com.unitn.disi.pweb.gruppo25.tum4world.model.repositories;


import com.unitn.disi.pweb.gruppo25.tum4world.Database;
import com.unitn.disi.pweb.gruppo25.tum4world.model.entities.Donazione;
import com.unitn.disi.pweb.gruppo25.tum4world.model.entities.Utente;

import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.ArrayList;
import java.util.List;

public class DonazioniRepo {

    public final static String TABLE_DONAZIONI = "DONAZIONI";
    public final static String COLUMN_ID = "ID";
    public final static String COLUMN_DATA = "DATA";
    public final static String COLUMN_QUANTITA = "QUANTITA";

    public final static String CREATE_TABLE = "CREATE TABLE " +
            TABLE_DONAZIONI + " (" +
            COLUMN_ID + " INTEGER NOT NULL GENERATED ALWAYS AS IDENTITY (START WITH 1, INCREMENT BY 1)," +
            COLUMN_DATA + " DATE NOT NULL," +
            COLUMN_QUANTITA + " INTEGER  NOT NULL," +
            "PRIMARY KEY (ID) )";

    //id generato automaticamente dal db
    public final static String INSERT_DONAZIONE = "INSERT INTO " +
            TABLE_DONAZIONI + "(" +
            COLUMN_DATA + "," +
            COLUMN_QUANTITA + ") VALUES ( ?, ?)";

    public final static String SELECT_ALL_DONAZIONI = "SELECT * FROM " + TABLE_DONAZIONI;

    private final Database database;
    public DonazioniRepo() {
        database = Database.getInstance();
    }

    public void createTableDonazioni() {
        //Se la table Donazioni non esiste, la creo
        if ( !database.tableExist(TABLE_DONAZIONI)) {
            try {
                Statement statement = database.getConnection().createStatement();
                statement.executeUpdate(CREATE_TABLE);
            } catch (SQLException e) {
                throw new RuntimeException(e);
            }
        }
    }

    public void insertDonazione(Donazione donazione) {
        try{
            PreparedStatement preparedStatement = this.database.getConnection().prepareStatement(INSERT_DONAZIONE);
            //trasforma LocalDate to Date
            preparedStatement.setDate(1, java.sql.Date.valueOf(donazione.getData()));
            preparedStatement.setInt(2, donazione.getQuantita());
            preparedStatement.executeUpdate();

        }catch (SQLException e) {
            throw new RuntimeException(e);
        }
    }

    public List<Donazione> getAllDonazioni(){
        ArrayList<Donazione> lista = null;

        try {
            Statement statement = this.database.getConnection().createStatement(ResultSet.TYPE_SCROLL_INSENSITIVE, ResultSet.CONCUR_READ_ONLY);
            ResultSet rs = statement.executeQuery(SELECT_ALL_DONAZIONI);
            lista = new ArrayList<>();
            while (rs.next()) {
                Donazione donazione = new Donazione();
                donazione.setId(rs.getInt(COLUMN_ID));
                donazione.setData(rs.getDate(COLUMN_DATA).toLocalDate());
                donazione.setQuantita(rs.getInt(COLUMN_QUANTITA));

                lista.add(donazione);
            }
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
        return lista;
    }
}