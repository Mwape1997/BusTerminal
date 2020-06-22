package at.ac.fhcampuswien.modal;

import at.ac.fhcampuswien.DB.DBConnection;
import at.ac.fhcampuswien.bean.LoginBean;

import java.awt.*;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;

public class LadePersonal extends Angestellter {
    public LoginBean loginBean;
    ResultSet resultSet;
    String sqlQuery;

    public LadePersonal (LoginBean loginBean){
        super(loginBean);
        this.loginBean = loginBean;
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

    public String retrieveLicenseNumber() {
        String licenseNumber="";
        DBConnection dbConnection = DBConnection.getInstance();
        Connection connection = dbConnection.getConnection();
        PreparedStatement preparedStatement = null;
        ResultSet resultSet;
        String sqlQuery;
        try {

            sqlQuery = "select lizenznummer from busterminal.ladepersonal where vierstellZahl=? and GebDat=?";
            preparedStatement = connection.prepareStatement(sqlQuery);
            preparedStatement.setInt(1, Integer.parseInt(loginBean.getSvnr()));
            preparedStatement.setString(2, loginBean.getBirthDate());
            resultSet = preparedStatement.executeQuery();
            if (resultSet.next())
                licenseNumber = resultSet.getString("lizenznummer");
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
        return licenseNumber;
    }


}
