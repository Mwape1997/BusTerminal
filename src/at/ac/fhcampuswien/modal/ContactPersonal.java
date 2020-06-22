package at.ac.fhcampuswien.modal;

import at.ac.fhcampuswien.DB.DBConnection;
import at.ac.fhcampuswien.bean.LoginBean;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;

public class ContactPersonal extends  Angestellter {
    public LoginBean loginBean;
    ResultSet resultSet;
    String sqlQuery;

    public ContactPersonal (LoginBean loginBean){
        super(loginBean);
        this.loginBean = loginBean;
    }

    public ArrayList<String> getList(){
        ArrayList<String> sqlResult = new ArrayList<>();

        DBConnection dbConnection = DBConnection.getInstance();
        Connection connection = dbConnection.getConnection();
        PreparedStatement preparedStatement = null;
        try {
            sqlQuery = "SELECT Zimmernummer, Buchungsdatum, Beginnzeit, Endzeit FROM busterminal.bucht WHERE vierstellZahl = ? and GebDat = ?";

            preparedStatement = connection.prepareStatement(sqlQuery);
            preparedStatement.setInt(1, Integer.parseInt(loginBean.getSvnr()));
            preparedStatement.setString(2, loginBean.getBirthDate());
            System.out.println(preparedStatement);
            resultSet = preparedStatement.executeQuery();
            //System.out.println(resultSet.toString());
            while(resultSet.next()){
                System.out.println("resultSet nicht leer");
                sqlResult.add(resultSet.getString("Zimmernummer"));
                sqlResult.add(resultSet.getString("Buchungsdatum"));
                sqlResult.add(resultSet.getString("Beginnzeit"));
                sqlResult.add(resultSet.getString("Endzeit"));
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

    public boolean bookRoom() {
        String roomNumber="";
        DBConnection dbConnection = DBConnection.getInstance();
        Connection connection = dbConnection.getConnection();
        PreparedStatement preparedStatement = null;
        ResultSet resultSet;
        String sqlQuery;
        try {

            sqlQuery = "INSERT INTO bucht (vierstellZahl, GebDat, Zimmernummer, Buchungsnummer, Buchungsdatum, Beginnzeit, Endzeit) VALUES (?, ?, ?, ?, ?, ?, ?)";
            preparedStatement = connection.prepareStatement(sqlQuery);
            preparedStatement.setInt(1, Integer.parseInt(loginBean.getSvnr()));
            preparedStatement.setString(2, loginBean.getBirthDate());
            resultSet = preparedStatement.executeQuery();
            if (resultSet.next())
                roomNumber = resultSet.getString("Zimmernummer");
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
        return true;
    }


}
