/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */

package org.titans.fyp.webcrawler.database;


import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;

/**
 *
 * @author Dimuthu
 */
public class DBConnection {
    private static DBConnection dbConnection;
    private Connection connection;

    private  DBConnection() throws ClassNotFoundException, SQLException {
        Class.forName("com.mysql.jdbc.Driver");
        connection =  (Connection) DriverManager.getConnection("jdbc:mysql://166.62.27.168/oblie?useUnicode=true&characterEncoding=UTF-8"
                ,"fyp","nikonD7100"); //databse credentials
    }

    public Connection getConnection(){
        return connection;
    }

    //singleton
    public static DBConnection getDBConnection() throws ClassNotFoundException, SQLException{
        if(dbConnection == null){
            dbConnection = new DBConnection(); //make new database
        }
        return dbConnection;
    }

    public static Connection getConnectionToDB() throws ClassNotFoundException, SQLException{
        return getDBConnection().getConnection();
    }
}
