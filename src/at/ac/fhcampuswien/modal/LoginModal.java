package at.ac.fhcampuswien.modal;

import at.ac.fhcampuswien.DB.DBConnection;
import at.ac.fhcampuswien.bean.LoginBean;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;

public class LoginModal {

    public boolean checkCredentials(LoginBean loginBean){
        boolean successful = false;
        DBConnection dbConnection = DBConnection.getInstance();
        Connection connection = dbConnection.getConnection();
        PreparedStatement preparedStatement = null;
        ResultSet resultSet;
        String sqlQuery;
        try {
            if (loginBean.getUserPosition().equals("contactPerson")){
                //sqlQuery = "select p.* from person p inner join kontaktperson k on p.svnr = k.svnr and p.GebDat = k.GebDat where k.Kundennummer=?";
                sqlQuery = "select * from kontaktperson where vierstellZahl=? and GebDat=? and Kundennummer=?";
                preparedStatement = connection.prepareStatement(sqlQuery);
                preparedStatement.setInt(1, Integer.parseInt(loginBean.getSvnr()));
                preparedStatement.setString(2, loginBean.getBirthDate());
                preparedStatement.setString(3, loginBean.getCustomerNumber());
                System.out.println(preparedStatement);
            }
            else if (loginBean.getUserPosition().equals("loadingPersonal")){
                //sqlQuery = "select p.* from person p inner join ladepersonal l on p.svnr = l.svnr and p.GebDat = l.GebDat where l.Lizenznummer=?";
                sqlQuery = "select * from ladepersonal where vierstellZahl=? and GebDat=? and Lizenznummer=?";
                preparedStatement = connection.prepareStatement(sqlQuery);
                preparedStatement.setInt(1, Integer.parseInt(loginBean.getSvnr()));
                preparedStatement.setString(2, loginBean.getBirthDate());
                preparedStatement.setString(3, loginBean.getLicenseNumber());
                System.out.println(preparedStatement);
                //select * from ladepersonal where vierstellZahl=1234 and GebDat='2020-06-03' and Lizenznummer='435'
                //select * from ladepersonal where vierstellZahl=1234 and GebDat='2020-06-03' and Lizenznumm='435'
            }
            if (preparedStatement != null){
                resultSet = preparedStatement.executeQuery();
                if (resultSet.next())
                    successful = true;
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }finally {
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

}
