package com.unitn.disi.pweb.gruppo25.tum4world;

import java.sql.*;

public class Database {
    private static Database instance;
    private final Connection connection;
    private final String URL = "jdbc:derby://localhost:1527/TUM4WORLD;create=true";
    private final String USER = "admin";
    private final String PASSWORD = "25Adm1n!";

    private Database() {
        try {
            Class.forName("org.apache.derby.jdbc.ClientDriver");
            this.connection = DriverManager.getConnection(URL, USER, PASSWORD);

        } catch (ClassNotFoundException | SQLException e) {
            throw new RuntimeException(e);
        }
    }

    public static Database getInstance() {
        if (instance == null) {
            instance = new Database();
        }
        return instance;
    }

    public Connection getConnection(){
        return this.connection;
    }


    public boolean tableExist( String tableName) {
        boolean exist = false;
        try {
            DatabaseMetaData md = this.connection.getMetaData();
            ResultSet rs = md.getTables(null, null, "%", null);
            while (rs.next()) {
                //Nella terza colonna dei metadati si trova il nome della tabella
                if (rs.getString(3).equals(tableName)) {
                    exist = true;
                }
            }
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
        return exist;
    }

}

