package com.base.controllers;

import com.base.models.*;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import org.sqlite.SQLiteConfig;

import java.sql.*;
import java.time.LocalDateTime;


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
    private ObservableList<Feedings> feedingsList;
    private ObservableList<Queens> queensList;
    private ObservableList<Productions> productionsList;
    private ObservableList<Hikes> hikesList;
    private ObservableList<Cores> coresList;
    private ObservableList<Alarms>alarmsList;

    //Constructor--------------
    private DBmanager() {
        apiariesList = FXCollections.observableArrayList();
        beehivesList = FXCollections.observableArrayList();
        diseasesList = FXCollections.observableArrayList();
        feedingsList = FXCollections.observableArrayList();
        queensList = FXCollections.observableArrayList();
        productionsList = FXCollections.observableArrayList();
        hikesList = FXCollections.observableArrayList();
        coresList= FXCollections.observableArrayList();
        alarmsList= FXCollections.observableArrayList();
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

            SQLiteConfig config = new SQLiteConfig();
            config.enforceForeignKeys(true);
            connection = DriverManager.getConnection(dbPath, config.toProperties());

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
            preparedStatement.executeUpdate();

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

    public void updateApiaryInDB(Apiaries a) {
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
    public ObservableList<Beehives> getBeehivesFromDB(Apiaries ap) {

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
            preparedStatement.executeUpdate();

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

    public void updateBeehiveInDB(Beehives newBeehive, Beehives oldBeehive) {

        try {

            s = "UPDATE beehives SET number=?, id_apiary=?, date=?, type=?, favorite=? WHERE number=? and id_apiary=? ";
            preparedStatement = connection.prepareStatement(s);
            preparedStatement.setInt(1, newBeehive.getNumber());
            preparedStatement.setInt(2, newBeehive.getId_apiary());
            preparedStatement.setDate(3, newBeehive.getDate());
            preparedStatement.setString(4, newBeehive.getType());
            preparedStatement.setBoolean(5, newBeehive.isFavorite());
            preparedStatement.setInt(6, oldBeehive.getNumber());
            preparedStatement.setInt(7, oldBeehive.getId_apiary());

            int i = preparedStatement.executeUpdate();
//            s = "UPDATE beehives SET number="+newBeehive.getNumber()
//                    +", id_apiary="+newBeehive.getId_apiary()
//                    +", date="+newBeehive.getDate()
//                    +", type="+newBeehive.getType()
//                    +", favorite="+newBeehive.isFavorite()
//                    +" WHERE number="+oldBeehive.getNumber()
//                    +" and id_apiary="+oldBeehive.getId_apiary();
//
//            statement = connection.createStatement();
//            statement.executeUpdate(s);

        } catch (SQLException e) {
            e.printStackTrace();
        }
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

    public void insertDiseaseInDB(Diseases disease) {

        try {

            s = "INSERT INTO diseases ( id_beehive, id_apiary, disease, treatment," +
                    "start_treat_date, end_treat_date ) VALUES( ?, ?, ?, ?, ?, ?)";
            preparedStatement = connection.prepareStatement(s);
            preparedStatement.setInt(1, disease.getId_beehive());
            preparedStatement.setInt(2, disease.getId_apiary());
            preparedStatement.setString(3, disease.getDisease());
            preparedStatement.setString(4, disease.getTreatment());
            preparedStatement.setDate(5, disease.getStartingDate());
            preparedStatement.setDate(6, disease.getEndingDate());
            preparedStatement.executeUpdate();

        } catch (SQLException e) {
            e.printStackTrace();
        }

    }

    public void updateDiseaseInDb(Diseases disease) {

        try {

            s = "UPDATE diseases SET disease=?, treatment=?, start_treat_date=?, end_treat_date=? WHERE id=?";
            preparedStatement = connection.prepareStatement(s);
            preparedStatement.setString(1, disease.getDisease());
            preparedStatement.setString(2, disease.getTreatment());
            preparedStatement.setDate(3, disease.getStartingDate());
            preparedStatement.setDate(4, disease.getEndingDate());
            preparedStatement.setInt(5, disease.getId());
            int i = preparedStatement.executeUpdate();

        } catch (SQLException e) {
            e.printStackTrace();
        }

    }


    public void deleteDiseaseInDB(ObservableList<Diseases> delList) {

        if (delList.size() > 0) {
            try {
                for (Diseases di : delList) {

                    s = "DELETE FROM diseases where id= ?";
                    preparedStatement = connection.prepareStatement(s);
                    preparedStatement.setInt(1, di.getId());
                    preparedStatement.execute();
                }
            } catch (SQLException e) {
                e.printStackTrace();
            }
        }

    }
    //FEEDINGS---(id, id_beehive, id_apiary, feeding_date, solid_quant, liquid_quant ) id is pk -------------

    public ObservableList<Feedings> getFeedings(Beehives beehive) {

        try {
            feedingsList.clear();
            if (null == beehive) {
                s = "SELECT * FROM feedings";
            } else {
                s = "SELECT * FROM feedings WHERE id_beehive=" + beehive.getNumber()
                        + " AND id_apiary=" + beehive.getId_apiary();
            }
            statement = connection.createStatement();
            resultSet = statement.executeQuery(s);
            while (resultSet.next()) {

                Feedings fe = new Feedings();
                fe.setId(resultSet.getInt(1));
                fe.setId_beehive(resultSet.getInt(2));
                fe.setId_Apiary(resultSet.getInt(3));
                fe.setDate(resultSet.getDate(4));
                fe.setSolid_quant(resultSet.getInt(5));
                fe.setLiquid_quant(resultSet.getInt(6));
                fe.setFeeding_used(resultSet.getString(7));

                feedingsList.add(fe);

            }
            return feedingsList;

        } catch (SQLException throwables) {
            throwables.printStackTrace();
        }
        return null;

    }

    public void insertFeedingInDB(Feedings feeding) {

        try {

            s = "INSERT INTO feedings ( id_beehive, id_apiary, feeding_date, solid_quant," +
                    "liquid_quant, feeding_used ) VALUES( ?, ?, ?, ?, ?, ?)";
            preparedStatement = connection.prepareStatement(s);
            preparedStatement.setInt(1, feeding.getId_beehive());
            preparedStatement.setInt(2, feeding.getId_Apiary());
            preparedStatement.setDate(3, feeding.getDate());
            preparedStatement.setDouble(4, feeding.getSolid_quant());
            preparedStatement.setDouble(5, feeding.getLiquid_quant());
            preparedStatement.setString(6, feeding.getFeeding_used());
            preparedStatement.executeUpdate();

        } catch (SQLException e) {
            e.printStackTrace();
        }

    }


    public void deleteFeedingsInDB(ObservableList<Feedings> delList) {

        if (delList.size() > 0) {
            try {
                for (Feedings fe : delList) {

                    s = "DELETE FROM feedings where id= ?";
                    preparedStatement = connection.prepareStatement(s);
                    preparedStatement.setInt(1, fe.getId());
                    preparedStatement.execute();
                }
            } catch (SQLException e) {
                e.printStackTrace();
            }
        }

    }

    public void updateFeedingInDB(Feedings feeding) {

        try {

            s = "UPDATE feedings SET feeding_date=?, solid_quant=?, liquid_quant=?, feeding_used=? WHERE id=?";
            preparedStatement = connection.prepareStatement(s);
            preparedStatement.setDate(1, feeding.getDate());
            preparedStatement.setDouble(2, feeding.getSolid_quant());
            preparedStatement.setDouble(3, feeding.getLiquid_quant());
            preparedStatement.setString(4, feeding.getFeeding_used());
            preparedStatement.setInt(5, feeding.getId());
            int i = preparedStatement.executeUpdate();

        } catch (SQLException e) {
            e.printStackTrace();
        }

    }

    //QUEENS---(id, id_beehive, id_apiary, birthdate, deathdate ) id is pk -----------

    /**
     * Returns Queens list from the beehive parameter. If parameter is null,
     * returns all Queens from database
     *
     * @param beehive
     * @return Queens list
     */
    public ObservableList<Queens> getQueens(Beehives beehive) {

        try {
            queensList.clear();
            if (null == beehive) {
                s = "SELECT * FROM Queens";
            } else {
                s = "SELECT * FROM Queens WHERE id_beehive=" + beehive.getNumber()
                        + " AND id_apiary=" + beehive.getId_apiary();
            }
            statement = connection.createStatement();
            resultSet = statement.executeQuery(s);
            while (resultSet.next()) {

                Queens qu = new Queens();
                qu.setId(resultSet.getInt(1));
                qu.setId_beehive(resultSet.getInt(2));
                qu.setId_apiary(resultSet.getInt(3));
                qu.setBirthdate(resultSet.getDate(4));
                qu.setDeath_date(resultSet.getDate(5));

                queensList.add(qu);

            }
            return queensList;

        } catch (SQLException throwables) {
            throwables.printStackTrace();
        }
        return null;

    }

    public void insertQueenInDB(Queens queen) {

        try {

            s = "INSERT INTO queens ( id_beehive, id_apiary, birthdate, deathdate)" +
                    " VALUES( ?, ?, ?, ?)";
            preparedStatement = connection.prepareStatement(s);
            preparedStatement.setInt(1, queen.getId_beehive());
            preparedStatement.setInt(2, queen.getId_apiary());
            preparedStatement.setDate(3, queen.getBirthdate());
            preparedStatement.setDate(4, queen.getDeath_date());

            preparedStatement.execute();

        } catch (SQLException e) {
            e.printStackTrace();
        }

    }


    /**
     * Given the queen in parameter, this method check if it already exists in dabase
     *
     * @param queen
     * @return true if exist, false if not.
     */
    public boolean checkIfQueenExist(Queens queen) {

        try {
            s = "SELECT * FROM queens WHERE id_beehive=? AND id_apiary=? AND birthdate=?";
            preparedStatement = connection.prepareStatement(s);
            preparedStatement.setInt(1, queen.getId_beehive());
            preparedStatement.setInt(2, queen.getId_apiary());
            preparedStatement.setDate(3, queen.getBirthdate());
            resultSet = preparedStatement.executeQuery();

            return resultSet.next();

        } catch (SQLException throwables) {
            throwables.printStackTrace();
        }
        return true;
    }

    public void updateQueenInDB(Queens queen, Queens oldQueen) {

        try {

            s = "UPDATE queens SET id_beehive=?, id_apiary=?, birthdate=?, deathdate=? WHERE id=?";
            preparedStatement = connection.prepareStatement(s);
            preparedStatement.setInt(1, queen.getId_beehive());
            preparedStatement.setInt(2, queen.getId_apiary());
            preparedStatement.setDate(3, queen.getBirthdate());
            preparedStatement.setDate(4, queen.getDeath_date());
            preparedStatement.setInt(5, oldQueen.getId());
            int i = preparedStatement.executeUpdate();

        } catch (SQLException e) {
            e.printStackTrace();
        }

    }

    public void deleteQueensInDB(ObservableList<Queens> delList) {

        if (delList.size() > 0) {

            try {
                for (Queens qu : delList) {

                    s = "DELETE FROM queens where id= ?";
                    preparedStatement = connection.prepareStatement(s);
                    preparedStatement.setInt(1, qu.getId());
                    preparedStatement.execute();
                }
            } catch (SQLException e) {
                e.printStackTrace();
            }

        }

    }

    //PRODUCTIONS---(id, id_beehive, id_apiary, date, breed_frames_quant
    // , honey_quant, royals_quant, pollen_quant, wax_quant, roy_jelly_quant ) id is pk -----------


    public ObservableList<Productions> getProductions(Beehives beehive) {
        try {
            productionsList.clear();
            if (null == beehive) {
                s = "SELECT * FROM productions";
            } else {
                s = "SELECT * FROM productions WHERE id_beehive=" + beehive.getNumber()
                        + " AND id_apiary=" + beehive.getId_apiary();
            }
            statement = connection.createStatement();
            resultSet = statement.executeQuery(s);
            while (resultSet.next()) {

                Productions pr = new Productions();
                pr.setId(resultSet.getInt(1));
                pr.setId_beehive(resultSet.getInt(2));
                pr.setId_apiary(resultSet.getInt(3));
                pr.setDate(resultSet.getDate(4));
                pr.setBreed_frames_quant(resultSet.getInt(5));
                pr.setHoney_quant(resultSet.getDouble(6));
                pr.setRoyals_quant(resultSet.getInt(7));
                pr.setPollen_quant(resultSet.getDouble(8));
                pr.setWax_quant(resultSet.getDouble(9));
                pr.setRoyalJelly_quant(resultSet.getDouble(10));

                productionsList.add(pr);

            }
            return productionsList;

        } catch (SQLException throwables) {
            throwables.printStackTrace();
        }
        return null;
    }

    public void insertProductionInDB(Productions production) {

        try {

            s = "INSERT INTO productions ( id_beehive, id_apiary, date, breed_frames_quant, honey_quant," +
                    "royals_quant, pollen_quant, wax_quant, roy_jelly_quant)" +
                    " VALUES( ?, ?, ?, ?, ?, ?, ?, ?, ?)";
            preparedStatement = connection.prepareStatement(s);
            preparedStatement.setInt(1, production.getId_beehive());
            preparedStatement.setInt(2, production.getId_apiary());
            preparedStatement.setDate(3, production.getDate());
            preparedStatement.setInt(4, production.getBreed_frames_quant());
            preparedStatement.setDouble(5, production.getHoney_quant());
            preparedStatement.setInt(6, production.getRoyals_quant());
            preparedStatement.setDouble(7, production.getPollen_quant());
            preparedStatement.setDouble(8, production.getWax_quant());
            preparedStatement.setDouble(9, production.getRoyalJelly_quant());

            preparedStatement.execute();

        } catch (SQLException e) {
            e.printStackTrace();
        }

    }

    public void updateProductionInDB(Productions production, Productions oldProduction) {

        try {

            s = "UPDATE productions SET id_beehive=?, id_apiary=?, date=?, breed_frames_quant=?, honey_quant=?," +
                    "royals_quant=?, pollen_quant=?, wax_quant=?, roy_jelly_quant=? WHERE id=?";
            preparedStatement = connection.prepareStatement(s);
            preparedStatement.setInt(1, production.getId_beehive());
            preparedStatement.setInt(2, production.getId_apiary());
            preparedStatement.setDate(3, production.getDate());
            preparedStatement.setInt(4, production.getBreed_frames_quant());
            preparedStatement.setDouble(5, production.getHoney_quant());
            preparedStatement.setInt(6, production.getRoyals_quant());
            preparedStatement.setDouble(7, production.getPollen_quant());
            preparedStatement.setDouble(8, production.getWax_quant());
            preparedStatement.setDouble(9, production.getRoyalJelly_quant());
            preparedStatement.setInt(10, oldProduction.getId());

            int i = preparedStatement.executeUpdate();

        } catch (SQLException e) {
            e.printStackTrace();
        }

    }

    public void deleteProductionsInDB(ObservableList<Productions> delList) {

        if (delList.size() > 0) {
            try {
                for (Productions pr : delList) {

                    s = "DELETE FROM productions where id= ?";
                    preparedStatement = connection.prepareStatement(s);
                    preparedStatement.setInt(1, pr.getId());
                    preparedStatement.execute();
                }
            } catch (SQLException e) {
                e.printStackTrace();
            }
        }

    }

    //Hikes---(id, id_beehive, id_apiary, type, placement_date, withdrawal_date) id is pk -----------


    public ObservableList<Hikes> getHikes(Beehives beehive) {
        hikesList.clear();
        try {
            productionsList.clear();
            if (null == beehive) {
                s = "SELECT * FROM hikes";
            } else {
                s = "SELECT * FROM hikes WHERE id_beehive=" + beehive.getNumber()
                        + " AND id_apiary=" + beehive.getId_apiary();
            }
            statement = connection.createStatement();
            resultSet = statement.executeQuery(s);
            while (resultSet.next()) {

                Hikes hk = new Hikes();
                hk.setId(resultSet.getInt(1));
                hk.setId_beehive(resultSet.getInt(2));
                hk.setId_apiary(resultSet.getInt(3));
                hk.setType(resultSet.getString(4));
                hk.setPlacement_date(resultSet.getDate(5));
                hk.setWithdrawal_date(resultSet.getDate(6));


                hikesList.add(hk);

            }
            return hikesList;

        } catch (SQLException throwables) {
            throwables.printStackTrace();
        }
        return null;
    }

    public void insertHikeInDB(Hikes hike) {

        try {

            s = "INSERT INTO hikes ( id_beehive, id_apiary, type, placement_date, withdrawal_date)" +
                    " VALUES( ?, ?, ?, ?, ?)";

            preparedStatement = connection.prepareStatement(s);

            preparedStatement.setInt(1, hike.getId_beehive());
            preparedStatement.setInt(2, hike.getId_apiary());
            preparedStatement.setString(3, hike.getType());
            preparedStatement.setDate(4, hike.getPlacement_date());
            preparedStatement.setDate(5, hike.getWithdrawal_date());

            preparedStatement.execute();

        } catch (SQLException e) {
            e.printStackTrace();
        }

    }

    public void updateHikesInDB(Hikes hike, Hikes oldhike) {

        try {

            s = "UPDATE hikes SET id_beehive=?, id_apiary=?, type=?, placement_date=?, withdrawal_date=?" +
                    " WHERE id=?";
            preparedStatement = connection.prepareStatement(s);
            preparedStatement.setInt(1, hike.getId_beehive());
            preparedStatement.setInt(2, hike.getId_apiary());
            preparedStatement.setString(3, hike.getType());
            preparedStatement.setDate(4, hike.getPlacement_date());
            preparedStatement.setDate(5, hike.getWithdrawal_date());
            preparedStatement.setInt(6, oldhike.getId());

            int i = preparedStatement.executeUpdate();

        } catch (SQLException e) {
            e.printStackTrace();
        }

    }

    public void deleteHikesInDB(ObservableList<Hikes> delList) {

        if (delList.size() > 0) {
            try {
                for (Hikes hk : delList) {

                    s = "DELETE FROM hikes where id= ?";
                    preparedStatement = connection.prepareStatement(s);
                    preparedStatement.setInt(1, hk.getId());
                    preparedStatement.execute();
                }
            } catch (SQLException e) {
                e.printStackTrace();
            }
        }

    }
    //Cores---(id, id_apiary, date, breeding_frames, notes) id is pk -----------


    public ObservableList<Cores> getCores(Apiaries apiary) {

        coresList.clear();
        try {
            productionsList.clear();
            if (null == apiary) {
                s = "SELECT * FROM cores";
            } else {
                s = "SELECT * FROM cores WHERE id_apiary=" + apiary.getId();
            }
            statement = connection.createStatement();
            resultSet = statement.executeQuery(s);
            while (resultSet.next()) {

                Cores co = new Cores();
                co.setId(resultSet.getInt(1));
                co.setId_apiary(resultSet.getInt(2));
                co.setDate(resultSet.getDate(3));
                co.setBreeding_frames(resultSet.getInt(4));
                co.setNotes(resultSet.getString(5));


                coresList.add(co);

            }
            return coresList;

        } catch (SQLException throwables) {
            throwables.printStackTrace();
        }
        return null;
    }

    public void insertCoresInDB(Cores core) {

        try {

            s = "INSERT INTO cores ( id_apiary, date, breeding_frames, notes)" +
                    " VALUES( ?, ?, ?, ?)";

            preparedStatement = connection.prepareStatement(s);

            preparedStatement.setInt(1, core.getId_apiary());
            preparedStatement.setDate(2, core.getDate());
            preparedStatement.setInt(3, core.getBreeding_frames());
            preparedStatement.setString(4, core.getNotes());

            preparedStatement.execute();

        } catch (SQLException e) {
            e.printStackTrace();
        }

    }

    public void updateCoresInDB(Cores core, Cores oldCore) {

        try {

            s = "UPDATE cores SET id_apiary=?, date=?, breeding_frames=?, notes=?" +
                    " WHERE id=?";
            preparedStatement = connection.prepareStatement(s);
            preparedStatement.setInt(1, core.getId_apiary());
            preparedStatement.setDate(2, core.getDate());
            preparedStatement.setInt(3, core.getBreeding_frames());
            preparedStatement.setString(4, core.getNotes());
            preparedStatement.setInt(5, oldCore.getId());

            int i = preparedStatement.executeUpdate();

        } catch (SQLException e) {
            e.printStackTrace();
        }

    }

    public void deleteCoresInDB(ObservableList<Cores> delList) {

        if (delList.size() > 0) {
            try {
                for (Cores co : delList) {

                    s = "DELETE FROM cores where id= ?";
                    preparedStatement = connection.prepareStatement(s);
                    preparedStatement.setInt(1, co.getId());
                    preparedStatement.execute();
                }
            } catch (SQLException e) {
                e.printStackTrace();
            }
        }

    }

    //Alarms---(id, date, name, text) id is pk -----------


    public ObservableList<Alarms> getAlarms() {

        alarmsList.clear();
        try {
                s = "SELECT * FROM alarms";

            statement = connection.createStatement();
            resultSet = statement.executeQuery(s);
            while (resultSet.next()) {

                Alarms al = new Alarms();
                al.setId(resultSet.getInt(1));
                al.setDate(resultSet.getString(2));
                al.setName(resultSet.getString(3));
                al.setText(resultSet.getString(4));
                al.setFinished(resultSet.getInt(5));

                alarmsList.add(al);

            }
            return alarmsList;

        } catch (SQLException throwables) {
            throwables.printStackTrace();
        }
        return null;
    }

    public void insertAlarmsInDB(Alarms alarm) {

        try {

            s = "INSERT INTO alarms ( date, name, text, finished) VALUES( ?, ?, ?, ?)";

            preparedStatement = connection.prepareStatement(s);

            preparedStatement.setString(1, alarm.getDateStringFormat());
            preparedStatement.setString(2, alarm.getName());
            preparedStatement.setString(3, alarm.getText());
            preparedStatement.setInt(4,alarm.getFinishedIntegerFormat());

            preparedStatement.execute();

        } catch (SQLException e) {
            e.printStackTrace();
        }

    }

    public void updateAlarmsInDB(Alarms alarm, Alarms oldAlarm) {

        try {

            s = "UPDATE alarms SET date=?, name=?, text=?, finished=? WHERE id=?";
            preparedStatement = connection.prepareStatement(s);
            preparedStatement.setString(1, alarm.getDateStringFormat());
            preparedStatement.setString(2, alarm.getName());
            preparedStatement.setString(3, alarm.getText());
            preparedStatement.setInt(4,alarm.getFinishedIntegerFormat());
            preparedStatement.setInt(5, oldAlarm.getId());


            int i = preparedStatement.executeUpdate();

        } catch (SQLException e) {
            e.printStackTrace();
        }

    }

    public void deleteAlarmsInDB(ObservableList<Alarms> delList) {

        if (delList.size() > 0) {
            try {
                for (Alarms al : delList) {

                    s = "DELETE FROM alarms where id= ?";
                    preparedStatement = connection.prepareStatement(s);
                    preparedStatement.setInt(1, al.getId());
                    preparedStatement.execute();
                }
            } catch (SQLException e) {
                e.printStackTrace();
            }
        }

    }




}
