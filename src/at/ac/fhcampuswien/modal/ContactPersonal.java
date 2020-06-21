package at.ac.fhcampuswien.modal;

import at.ac.fhcampuswien.DB.DBConnection;
import at.ac.fhcampuswien.bean.LoginBean;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;

public class ContactPersonal {
    public LoginBean loginBean;
    ResultSet resultSet;
    String sqlQuery;

    public ContactPersonal (at.ac.fhcampuswien.bean.LoginBean loginBean){
        this.loginBean = loginBean;
    }

    public String showRoom() {
        String restRoomNumber = null;

        DBConnection dbConnection = DBConnection.getInstance();
        Connection connection = dbConnection.getConnection();
        PreparedStatement preparedStatement = null;
        try {
            sqlQuery = "SELECT rasträume.Zimmernummer as room from rasträume";

            preparedStatement = connection.prepareStatement(sqlQuery);
           // preparedStatement.setInt(1, Integer.parseInt(loginBean.getSvnr()));
          //  preparedStatement.setString(2, loginBean.getBirthDate());
            System.out.println(preparedStatement);
            resultSet = preparedStatement.executeQuery();
            if(resultSet.next()){
                System.out.println("resultSet nicht leer");
                restRoomNumber = Integer.toString(resultSet.getInt("room"));
            }
        } catch (SQLException e) {
            e.printStackTrace();
        } finally {
            if (connection != null) {
                try {
                    connection.close();
                } catch (SQLException e) {
                    e.printStackTrace();
                }
            }
        }
        return restRoomNumber;
    }

    public boolean requestRoom() {
        Boolean sqlResult = false;

        DBConnection dbConnection = DBConnection.getInstance();
        Connection connection = dbConnection.getConnection();
        PreparedStatement preparedStatement = null;
        ResultSet resultSet;
        String sqlQuery;

        try {
            sqlQuery = "INSERT INTO bucht (vierstellZahl, GebDat, Zimmernummer, Buchungsnummer, Buchungsdatum, Beginzeit, Endzeit)\n" +
                    "VALUES (?, ?, ?, ?, ?, ?, ?)\n";

            preparedStatement = connection.prepareStatement(sqlQuery);
            System.out.println(preparedStatement);
            resultSet = preparedStatement.executeQuery();
            if(resultSet.next()){
                System.out.println("resultSet not empty" + resultSet.getInt("zimmernummer"));
                //sqlQuery = "UPDATE busterminal.kontaktperson SET kundennummer = ? WHERE (vierstellZahl = ?) and (GebDat = ?)";
                sqlQuery = "UPDATE busterminal.rasträume SET Zimmernumber = ?";
                preparedStatement = connection.prepareStatement(sqlQuery);
                preparedStatement.setInt(1, resultSet.getInt("restäume"));
              //  preparedStatement.setInt(2, Integer.parseInt(loginBean.getSvnr()));
              //  preparedStatement.setString(3, loginBean.getBirthDate());
                System.out.println(preparedStatement);
                if(preparedStatement.executeUpdate()!=0){
                    System.out.println("updated");
                    sqlResult = true;
                }
            }
        } catch (SQLException e) {
            e.printStackTrace();
        } finally {
            if (connection != null) {
                try {
                    connection.close();
                } catch (SQLException e) {
                    e.printStackTrace();
                }
            }
        }
        return sqlResult;
    }

    public ArrayList<String> getListOfRooms(){
        ArrayList<String> sqlResult = new ArrayList<>();

        DBConnection dbConnection = DBConnection.getInstance();
        Connection connection = dbConnection.getConnection();
        PreparedStatement preparedStatement = null;
        try {
            //sqlQuery = "SELECT terminal.Terminalnummer, terminal.Peronal_Anzahl, terminal.Kapazität FROM busterminal.terminal inner join ladepersonal on terminal.Terminalnummer = ladepersonal.Terminalnummer where ladepersonal.vierstellZahl=? and ladepersonal.GebDat=?";
            sqlQuery = "SELECT * FROM bucht as b\n" +
                       "WHERE vierstellZahl = ?";
            //sqlQuery = "SELECT kontaktperson.vierstellZahl FROM busterminal.kontaktperson where rasträume.Zimmernummer=?";
            preparedStatement = connection.prepareStatement(sqlQuery);
            preparedStatement.setInt(1, Integer.parseInt(loginBean.getSvnr()));
          //  preparedStatement.setString(2, loginBean.getBirthDate());
            System.out.println(preparedStatement);
            resultSet = preparedStatement.executeQuery();
            while(resultSet.next()){
                System.out.println("resultSet not Empty");
                sqlResult.add(resultSet.getString("vierstellZahl"));
                sqlResult.add(Integer.toString(resultSet.getInt("rasträume")));
              //  sqlResult.add(Integer.toString(resultSet.getInt("Kapazität")));
            }
        } catch (SQLException e) {
            e.printStackTrace();
        } finally {
            if (connection != null) {
                try {
                    connection.close();
                } catch (SQLException e) {
                    e.printStackTrace();
                }
            }
        }
        return sqlResult;
    }
}
