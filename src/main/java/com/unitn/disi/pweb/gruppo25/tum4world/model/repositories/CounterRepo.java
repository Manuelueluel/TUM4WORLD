package com.unitn.disi.pweb.gruppo25.tum4world.model.repositories;

import com.unitn.disi.pweb.gruppo25.tum4world.Database;
import com.unitn.disi.pweb.gruppo25.tum4world.model.entities.Counter;

import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.ArrayList;
import java.util.List;

public class CounterRepo {
    public final static String TABLE_COUNTER = "COUNTER";
    public final static String COLUMN_ID = "ID";
    public final static String COLUMN_CONTATORE = "CONTATORE";
    public final static String COLUMN_PAGINA = "PAGINA";

    public final static String CREATE_TABLE = "CREATE TABLE " +
            TABLE_COUNTER + " (" +
            COLUMN_ID + " INTEGER NOT NULL GENERATED ALWAYS AS IDENTITY (START WITH 1, INCREMENT BY 1)," +
            COLUMN_CONTATORE + " INTEGER NOT NULL," +
            COLUMN_PAGINA + " VARCHAR(64)  NOT NULL," +
            "PRIMARY KEY (ID) )";

    //id generato automaticamente dal db
    public final static String INSERT_NEW_COUNTER = "INSERT INTO " +
            TABLE_COUNTER + "(" +
            COLUMN_CONTATORE + "," +
            COLUMN_PAGINA + ") VALUES ( 0, ?)";

    public final static String SELECT_ALL_COUNTERS = "SELECT * FROM " + TABLE_COUNTER;

    public final static String SELECT_PAGINA = "SELECT * FROM " +
            TABLE_COUNTER + " WHERE " +
            COLUMN_PAGINA + " = ?";

    public final static String UPDATE_COUNTER = "UPDATE " +
            TABLE_COUNTER + " SET " +
            COLUMN_CONTATORE + " = ? WHERE " +
            COLUMN_PAGINA + " = ?";

    public final static String RESET_ALL_COUNTERS = "UPDATE " +
            TABLE_COUNTER + " SET " +
            COLUMN_CONTATORE + " = 0";

    private final Database database;

    public CounterRepo() {
        database = Database.getInstance();
    }

    public void createTableCounter() {
        //Se la table Counter non esiste, la creo
        if ( !database.tableExist(TABLE_COUNTER)) {
            try {
                Statement statement = database.getConnection().createStatement();
                statement.executeUpdate(CREATE_TABLE);
                System.out.println("CREATE TABLE COUNTER SUCCESS");
            } catch (SQLException e) {
                throw new RuntimeException(e);
            }
        }
    }

    // Inserisce un nuovo counter
    private void insertCounter(String pagina) {
        try{
            PreparedStatement preparedStatement = this.database.getConnection().prepareStatement(INSERT_NEW_COUNTER);
            preparedStatement.setString(1, pagina);
            preparedStatement.executeUpdate();
        }catch (SQLException e) {
            throw new RuntimeException(e);
        }
    }

    //Aggiorna il contatore di una pagina, se non esiste lo crea
    public boolean incrementCounter(String pagina) {
        try {
            //Legge valore contatore
            PreparedStatement preparedStatement = this.database.getConnection().prepareStatement(SELECT_PAGINA);
            preparedStatement.setString(1, pagina);
            ResultSet rs = preparedStatement.executeQuery();
            int contatore = 0;

            if( rs.next()){ //Pagina esistente
                contatore = rs.getInt(COLUMN_CONTATORE);
            }else{          //Pagina non esistente, la creo
                insertCounter(pagina);
                return false;
            }

            //incrementa valore contatore
            preparedStatement = this.database.getConnection().prepareStatement(UPDATE_COUNTER);
            preparedStatement.setInt(1, ++contatore);
            preparedStatement.setString(2, pagina);
            preparedStatement.executeUpdate();

            return true;
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
    }

    public List<Counter> getAllCounters(){
        ArrayList<Counter> lista = null;

        try {
            Statement statement = this.database.getConnection().createStatement(ResultSet.TYPE_SCROLL_INSENSITIVE, ResultSet.CONCUR_READ_ONLY);
            ResultSet rs = statement.executeQuery(SELECT_ALL_COUNTERS);
            lista = new ArrayList<>();
            while (rs.next()) {
                Counter counter = new Counter();
                counter.setId(rs.getInt(COLUMN_ID));
                counter.setContatore(rs.getInt(COLUMN_CONTATORE));
                counter.setPagina(rs.getString(COLUMN_PAGINA));

                lista.add(counter);
            }
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
        return lista;
    }

    // Azzera tutti i contatori
    public void resetAllCounters() {
        try {
            Statement statement = this.database.getConnection().createStatement(ResultSet.TYPE_SCROLL_INSENSITIVE, ResultSet.CONCUR_READ_ONLY);
            statement.executeUpdate(RESET_ALL_COUNTERS);

        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
    }

}
