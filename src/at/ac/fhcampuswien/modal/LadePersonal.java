package at.ac.fhcampuswien.modal;

import at.ac.fhcampuswien.DB.DBConnection;
import at.ac.fhcampuswien.bean.LoginBean;

import java.awt.*;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;

public class LadePersonal {
    public LoginBean loginBean;
    ResultSet resultSet;
    String sqlQuery;

    public LadePersonal (LoginBean loginBean){
        this.loginBean = loginBean;
    }

    public String showErmKarte() {
        String ermKartenNummer = null;

        DBConnection dbConnection = DBConnection.getInstance();
        Connection connection = dbConnection.getConnection();
        PreparedStatement preparedStatement = null;
        try {
            sqlQuery = "select ermäßigungskarten.Kartennummer as karte from ermäßigungskarten inner join ladepersonal on ladepersonal.Kartennummer = ermäßigungskarten.Kartennummer where vierstellZahl=? and GebDat=?";
            preparedStatement = connection.prepareStatement(sqlQuery);
            preparedStatement.setInt(1, Integer.parseInt(loginBean.getSvnr()));
            preparedStatement.setString(2, loginBean.getBirthDate());
            System.out.println(preparedStatement);
            resultSet = preparedStatement.executeQuery();
            //System.out.println(resultSet.toString());
            if(resultSet.next()){
                System.out.println("resultSet nicht leer");
                ermKartenNummer = Integer.toString(resultSet.getInt("karte"));
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
        return ermKartenNummer;
    }

    public boolean reqErmKarte() {
        Boolean sqlResult = false;

        DBConnection dbConnection = DBConnection.getInstance();
        Connection connection = dbConnection.getConnection();
        PreparedStatement preparedStatement = null;
        ResultSet resultSet;
        String sqlQuery;

        try {
            sqlQuery = "SELECT ermäßigungskarten.Kartennummer FROM busterminal.ladepersonal right join ermäßigungskarten on ermäßigungskarten.Kartennummer=ladepersonal.Kartennummer where ladepersonal.vierstellZahl is null";
            preparedStatement = connection.prepareStatement(sqlQuery);
            //preparedStatement.setInt(1, Integer.parseInt(loginBean.getSvnr()));
            //preparedStatement.setString(2, loginBean.getBirthDate());
            System.out.println(preparedStatement);
            resultSet = preparedStatement.executeQuery();
            //System.out.println(resultSet.toString());
            if(resultSet.next()){
                System.out.println("resultSet nicht leer" + resultSet.getInt("Kartennummer"));
                sqlQuery = "UPDATE busterminal.ladepersonal SET Kartennummer = ? WHERE (vierstellZahl = ?) and (GebDat = ?)";
                preparedStatement = connection.prepareStatement(sqlQuery);
                preparedStatement.setInt(1, resultSet.getInt("Kartennummer"));
                preparedStatement.setInt(2, Integer.parseInt(loginBean.getSvnr()));
                preparedStatement.setString(3, loginBean.getBirthDate());
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

    public ArrayList<String> getTerminalOverwiev(){
        ArrayList<String> sqlResult = new ArrayList<>();

        DBConnection dbConnection = DBConnection.getInstance();
        Connection connection = dbConnection.getConnection();
        PreparedStatement preparedStatement = null;
        try {
            sqlQuery = "SELECT terminal.Terminalnummer, terminal.Peronal_Anzahl, terminal.Kapazität FROM busterminal.terminal inner join ladepersonal on terminal.Terminalnummer = ladepersonal.Terminalnummer where ladepersonal.vierstellZahl=? and ladepersonal.GebDat=?";
            preparedStatement = connection.prepareStatement(sqlQuery);
            preparedStatement.setInt(1, Integer.parseInt(loginBean.getSvnr()));
            preparedStatement.setString(2, loginBean.getBirthDate());
            System.out.println(preparedStatement);
            resultSet = preparedStatement.executeQuery();
            //System.out.println(resultSet.toString());
            while(resultSet.next()){
                System.out.println("resultSet nicht leer");
                sqlResult.add(resultSet.getString("Terminalnummer"));
                sqlResult.add(Integer.toString(resultSet.getInt("Peronal_Anzahl")));
                sqlResult.add(Integer.toString(resultSet.getInt("Kapazität")));
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
