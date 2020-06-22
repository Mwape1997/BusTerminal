package at.ac.fhcampuswien.modal;

import at.ac.fhcampuswien.DB.DBConnection;
import at.ac.fhcampuswien.bean.LoginBean;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;

public class LoginModal {

    public boolean checkCredentials(LoginBean loginBean) {
        boolean successful = false;
        DBConnection dbConnection = DBConnection.getInstance();
        Connection connection = dbConnection.getConnection();
        PreparedStatement preparedStatement = null;
        ResultSet resultSet;
        String sqlQuery;
        try {
            if (loginBean.getUserPosition().equals("contactPerson")) {
                //sqlQuery = "select p.* from person p inner join kontaktperson k on p.svnr = k.svnr and p.GebDat = k.GebDat where k.Kundennummer=?";
                sqlQuery = "select * from kontaktperson where vierstellZahl=? and GebDat=? and Kundennummer=?";
                preparedStatement = connection.prepareStatement(sqlQuery);
                preparedStatement.setInt(1, Integer.parseInt(loginBean.getSvnr()));
                preparedStatement.setString(2, loginBean.getBirthDate());
                preparedStatement.setString(3, loginBean.getCustomerNumber());
                System.out.println(preparedStatement);
            } else if (loginBean.getUserPosition().equals("angestellter")) {
                /*get schalterbedienster from angestellter*/
                //sqlQuery = "SELECT * FROM busterminal.angestellter join busterminal.schalterbediensteter on angestellter.vierstellZahl = schalterbediensteter.vierstellZahl and angestellter.GebDat = schalterbediensteter.GebDat";
                sqlQuery = "SELECT * FROM busterminal.angestellter where vierstellZahl=? and GebDat=? and angestelltennummer=?";
                preparedStatement = connection.prepareStatement(sqlQuery);
                preparedStatement.setInt(1, Integer.parseInt(loginBean.getSvnr()));
                preparedStatement.setString(2, loginBean.getBirthDate());
                preparedStatement.setString(3, loginBean.getAngestelltennummer());
                System.out.println(preparedStatement);
            }
            /*
            else if (loginBean.getUserPosition().equals("loadingPersonal")){
                //sqlQuery = "select p.* from person p inner join ladepersonal l on p.svnr = l.svnr and p.GebDat = l.GebDat where l.Lizenznummer=?";
                sqlQuery = "select * from ladepersonal where svnr=? and GebDat=? and Lizenznummer=?";
                preparedStatement = connection.prepareStatement(sqlQuery);
                preparedStatement.setInt(1, Integer.parseInt(loginBean.getSvnr()));
                preparedStatement.setString(2, loginBean.getBirthDate());
                preparedStatement.setString(3, loginBean.getLicenseNumber());
                System.out.println(preparedStatement);
            }

             */
            if (preparedStatement != null) {
                resultSet = preparedStatement.executeQuery();
                if (resultSet.next())
                    successful = true;
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
        return successful;
    }

    public boolean isSchalterbediensteter(LoginBean loginBean){
        Schalterbediensteter schalterbediensteter = new Schalterbediensteter(loginBean);
        return schalterbediensteter.isSchalterbediensteter();
    }

    public String retrieveLicenseNumber(LoginBean loginBean){
        LadePersonal ladePersonal = new LadePersonal(loginBean);
        return ladePersonal.retrieveLicenseNumber();
    }
}
