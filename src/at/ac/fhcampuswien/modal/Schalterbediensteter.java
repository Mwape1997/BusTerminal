package at.ac.fhcampuswien.modal;

import at.ac.fhcampuswien.DB.DBConnection;
import at.ac.fhcampuswien.bean.LoginBean;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;

public class Schalterbediensteter extends Angestellter {


    public LoginBean loginBean;
    ResultSet resultSet;
    String sqlQuery;

    public Schalterbediensteter (LoginBean loginBean) {
        super(loginBean);
        this.loginBean = loginBean;
    }


    public boolean isSchalterbediensteter() {
        boolean successful = false;
        DBConnection dbConnection = DBConnection.getInstance();
        Connection connection = dbConnection.getConnection();
        PreparedStatement preparedStatement = null;
        ResultSet resultSet;
        String sqlQuery;
        try {
            sqlQuery = "select * from schalterbediensteter where vierstellZahl=? and GebDat=?";
            preparedStatement = connection.prepareStatement(sqlQuery);
            preparedStatement.setInt(1, Integer.parseInt(loginBean.getSvnr()));
            preparedStatement.setString(2, loginBean.getBirthDate());
            resultSet = preparedStatement.executeQuery();
            System.out.println("From is Schalterbediensteter"+preparedStatement);

            if (resultSet.next())
                successful = true;
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
        return successful;
    }

    public ArrayList<String> getNextSchalterbediensteter(){
        ArrayList<String> sqlResult = new ArrayList<>();
        String svnr="";
        String gebDat="";

        DBConnection dbConnection = DBConnection.getInstance();
        Connection connection = dbConnection.getConnection();
        PreparedStatement preparedStatement = null;
        try {
            sqlQuery = "select nach_4stellZahl, nach_GebDat from busterminal.schichtbetrieb where vor_4stellZahl=? and vor_GebDat=?";
            preparedStatement = connection.prepareStatement(sqlQuery);
            preparedStatement.setInt(1, Integer.parseInt(loginBean.getSvnr()));
            preparedStatement.setString(2, loginBean.getBirthDate());
            System.out.println(preparedStatement);
            resultSet = preparedStatement.executeQuery();
            //System.out.println(resultSet.toString());
            while(resultSet.next()){
                System.out.println("resultSet nicht leer");
                svnr = resultSet.getString("nach_4stellZahl");
                gebDat = resultSet.getString("nach_GebDat");
            }
            sqlQuery = "select distinct Vorname, Nachname, angestellter.angestelltennummer from person join busterminal.schichtbetrieb on person.vierstellZahl=? and person.GebDat=?" +
                    "inner join busterminal.angestellter on person.vierstellZahl = angestellter.vierstellZahl and person.GebDat = person.GebDat;";
            preparedStatement = connection.prepareStatement(sqlQuery);
            preparedStatement.setInt(1, Integer.parseInt(svnr));
            preparedStatement.setString(2, gebDat);
            System.out.println(preparedStatement);
            resultSet = preparedStatement.executeQuery();
            if (resultSet.next()){
                sqlResult.add(0, resultSet.getString("Vorname"));
                sqlResult.add(1, resultSet.getString("Nachname"));
                sqlResult.add(2, resultSet.getString("angestelltennummer"));
            }
        }
        catch (SQLException e) {
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
