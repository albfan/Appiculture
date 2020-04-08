package com.base.controllers;

import com.base.models.Apiaries;
import com.base.models.Beehives;
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
    private ObservableList<Beehives> beehivesList;


    //Constructor--------------
    private DBmanager() {
        apiariesList = FXCollections.observableArrayList();
        beehivesList = FXCollections.observableArrayList();
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

    //APIARIES-------(id, name, adress) id is PK and autoincremental-----------------
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

    // BEEHIVES---(number,id_apiary,date,type,favorite) number is PK and autoincremental

    public ObservableList<Beehives> getHivesFromDB(Apiaries ap){

        try {

            beehivesList.clear();
            s = "SELECT * FROM beehives WHERE id_apiary = "+ap.getId();
            statement = connection.createStatement();
            resultSet = statement.executeQuery(s);
            while (resultSet.next()) {

                Beehives bh = new Beehives();
                bh.setNumber(resultSet.getInt(1));
                bh.setId_apiary(resultSet.getInt(2));
                bh.setDate(resultSet.getDate(3));
                bh.setType(resultSet.getString(4));
                bh.setFavorite(resultSet.getBoolean(5));
                beehivesList.add(bh);
            }
            return beehivesList;

        } catch (SQLException e) {
            e.printStackTrace();
            return null;
        }
    }

    public String insertBeehiveInDB(Beehives bh){//todo pendiente de completar con las verificaciónes de si existen ya los numeros de colmena

        try {

            s = "INSERT INTO beehives VALUES( ?, ?, ?, ?, ?)";
            preparedStatement = connection.prepareStatement(s);
            preparedStatement.setInt(1, bh.getNumber());
            preparedStatement.setInt(2, bh.getId_apiary());
            preparedStatement.setDate(3, bh.getDate());
            preparedStatement.setString(4, bh.getType());
            preparedStatement.setBoolean(5, bh.isFavorite());
            int i = preparedStatement.executeUpdate();

        } catch (SQLException e) {
            e.printStackTrace();
        }

        return "";
    }
}
