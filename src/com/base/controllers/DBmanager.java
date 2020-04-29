package com.base.controllers;

import com.base.models.Apiaries;
import com.base.models.Beehives;
import com.base.models.Diseases;
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
    private ObservableList<Diseases> diseasesList;

    //Constructor--------------
    private DBmanager() {
        apiariesList = FXCollections.observableArrayList();
        beehivesList = FXCollections.observableArrayList();
        diseasesList = FXCollections.observableArrayList();
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

            String dbPath = "jdbc:sqlite:resources/db/datab.db";
            Class.forName("org.sqlite.JDBC");
            connection = DriverManager.getConnection(dbPath);

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

            if (null != preparedStatement) {
                if (!preparedStatement.isClosed())
                    preparedStatement.close();
            }
            if (null != statement) {
                if (!statement.isClosed())
                    statement.close();
            }
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

    /**
     * Return a list with all the apiaries from the Database
     *
     * @return ObservableList<Apiaries>
     */
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

    public Apiaries getApiary(int apiaryID) {

        try {

            s = "SELECT * FROM apiaries where id=" + apiaryID;
            statement = connection.createStatement();
            resultSet = statement.executeQuery(s);
            while (resultSet.next()) {

                Apiaries ap = new Apiaries();
                ap.setId(resultSet.getInt(1));
                ap.setName(resultSet.getString(2));
                ap.setAdress(resultSet.getString(3));
                return ap;

            }

        } catch (SQLException throwables) {
            throwables.printStackTrace();
        }
        return null;
    }

    public void modifyApiaryInDB(Apiaries a) {
        try {

            s = "UPDATE apiaries SET name=?, address=? WHERE id=?";
            preparedStatement = connection.prepareStatement(s);
            preparedStatement.setString(1, a.getName());
            preparedStatement.setString(2, a.getAdress());
            preparedStatement.setInt(3, a.getId());
            int i = preparedStatement.executeUpdate();

        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

    public void deleteApiariesInDB(ObservableList<Apiaries> delList) {
        if (delList.size() > 0) {
            try {
                for (Apiaries ap : delList) {

                    s = "DELETE FROM apiaries where id= ?";
                    preparedStatement = connection.prepareStatement(s);
                    preparedStatement.setInt(1, ap.getId());
                    preparedStatement.execute();
                }
            } catch (SQLException e) {
                e.printStackTrace();
            }
        }
    }

    // BEEHIVES---(number,id_apiary,date,type,favorite) number is PK and autoincremental---------------------------

    /**
     * Return a list with all beehives in database if parameter is null. Or the
     * beehives owned by the apiary parameter if parameter is not null.
     *
     * @param ap
     * @return beehivesList
     */
    public ObservableList<Beehives> getHivesFromDB(Apiaries ap) {

        try {

            beehivesList.clear();
            if (null == ap) {
                s = "SELECT * FROM beehives ";
            } else {
                s = "SELECT * FROM beehives WHERE id_apiary = " + ap.getId();
            }
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

    public void insertBeehiveInDB(Beehives bh) {

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

    }

    /**
     * This method receive the number of a beehive and check in the database if it already exists .
     * Returns true if already exists or false if not.
     *
     * @param number the id number of the beehive.
     * @return boolean - true if already exists or false if not
     */
    public boolean beehiveExist(int number, int apiaryID) {

        boolean exist = false;
        try {

            s = "SELECT * FROM beehives where number =? and id_apiary=? ";
            preparedStatement = connection.prepareStatement(s);
            preparedStatement.setInt(1, number);
            preparedStatement.setInt(2, apiaryID);
            resultSet = preparedStatement.executeQuery();

//            statement = connection.createStatement();
//            resultSet = statement.executeQuery(s);
            exist = resultSet.next();

        } catch (SQLException throwables) {
            throwables.printStackTrace();
        }

        return exist;
    }

    public void deleteBeehivesInDB(ObservableList<Beehives> delList) {

        if (delList.size() > 0) {
            try {
                for (Beehives bh : delList) {

                    s = "DELETE FROM beehives where number= ? and id_apiary=? ";
                    preparedStatement = connection.prepareStatement(s);
                    preparedStatement.setInt(1, bh.getNumber());
                    preparedStatement.setInt(2, bh.getId_apiary());
                    preparedStatement.execute();
                }
            } catch (SQLException e) {
                e.printStackTrace();
            }
        }
    }


    //DISEASES---(id, id_beehive, disease, treatment, start_treat_date, end_treat_date ) id is pk -------------

    /**
     * If parameter is null, returns all diseases from DB. if parameter is not null, returns the diseases of
     * beehive from parameter.
     *
     * @param beehive
     * @return
     */
    public ObservableList<Diseases> getDiseases(Beehives beehive) {

        try {

            diseasesList.clear();
            if (null == beehive) {
                s = "SELECT * FROM diseases";
            } else {
                s = "SELECT * FROM diseases WHERE id_beehive=" + beehive.getNumber()
                        + " AND id_apiary=" + beehive.getId_apiary();
            }
            statement = connection.createStatement();
            resultSet = statement.executeQuery(s);
            while (resultSet.next()) {

                Diseases di = new Diseases();
                di.setId(resultSet.getInt(1));
                di.setId_beehive(resultSet.getInt(2));
                di.setId_apiary(resultSet.getInt(3));
                di.setDisease(resultSet.getString(4));
                di.setTreatment(resultSet.getString(5));
                di.setStartingDate(resultSet.getDate(6));
                di.setEndingDate(resultSet.getDate(7));

                diseasesList.add(di);

            }
            return diseasesList;

        } catch (SQLException throwables) {
            throwables.printStackTrace();
        }

        return null;
    }

    public void insertDiseaseInDB(Diseases disease){

    }


}
