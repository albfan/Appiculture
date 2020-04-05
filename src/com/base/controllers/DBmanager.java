package com.base.controllers;

import com.base.models.Apiaries;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;

import java.sql.*;

public class DBmanager {

    private static DBmanager INSTANCE = null;
    private Connection connection;
    private String s;
    private PreparedStatement preparedStatement;
    private Statement statement;
    private ResultSet resultSet;
    private ObservableList<Apiaries> apiariesList;


    //Constructor--------------
    private DBmanager() {
        apiariesList = FXCollections.observableArrayList();
    }

    //Singleton Method-------------
    public static DBmanager getINSTANCE() {
        if (INSTANCE == null)
            INSTANCE = new DBmanager();
        return INSTANCE;
    }

    //Methods-------------------
    public Connection openConnection() {

        try {

            Class.forName("org.sqlite.JDBC");
            connection = DriverManager.getConnection("jdbc:sqlite:C:\\Users\\stephane\\Desktop\\workspace\\Appiculture\\resources\\db\\datab.db");
            return connection;

        } catch (ClassNotFoundException e) {
            e.printStackTrace();
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return null;
    }

    public void closeConnection() {

        try {

            connection.close();

        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

    //APIARIES-------(id, name, adress) id is autoincremental-----------------
    public void insertApiaryInDB(Apiaries ap) {

        try {

            s = "INSERT INTO apiaries (name,address) VALUES( ?, ?)";
            preparedStatement = connection.prepareStatement(s);
            preparedStatement.setString(1, ap.getName());
            preparedStatement.setString(2, ap.getAdress());
            int i = preparedStatement.executeUpdate();

        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

    public ObservableList<Apiaries> getApiariesFromDB() {

        try {

            apiariesList.clear();
            s = "SELECT * FROM apiaries";
            statement = connection.createStatement();
            resultSet = statement.executeQuery(s);
            while (resultSet.next()) {

                Apiaries ap = new Apiaries();
                ap.setId(resultSet.getInt(1));
                ap.setName(resultSet.getString(2));
                ap.setAdress(resultSet.getString(3));
                apiariesList.add(ap);
            }
            return apiariesList;

        } catch (SQLException e) {
            e.printStackTrace();
            return null;
        }
    }

    public void modifyApiaryInDB(Apiaries a){
        try {

            s = "UPDATE apiaries SET name=?, address=? WHERE id=?";
            preparedStatement = connection.prepareStatement(s);
            preparedStatement.setString(1, a.getName());
            preparedStatement.setString(2, a.getAdress());
            preparedStatement.setInt(3,a.getId());
            int i = preparedStatement.executeUpdate();

        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

    public void deleteApiariesInDB(ObservableList<Apiaries> delList){
        if (delList.size()>0){
            try {
                for (Apiaries ap : delList) {

                    s = "DELETE FROM apiaries where id= ?";
                    preparedStatement = connection.prepareStatement(s);
                    preparedStatement.setInt(1,ap.getId());
                    preparedStatement.execute();
                }
            }catch (SQLException e) {
                e.printStackTrace();
            }
        }
    }


}
