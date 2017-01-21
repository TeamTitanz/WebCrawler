/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */

package handler;


import java.sql.Connection;
import java.sql.ResultSet;
import java.sql.SQLException;

/**
 *
 * @author Dimuthu
 */
public class DBHandler {

    //use to write data to db
    public static int setData(String query, Connection connection) throws SQLException{
        java.sql.Statement stm =  connection.createStatement();
        int res = stm.executeUpdate(query , stm.RETURN_GENERATED_KEYS);
        return res;
    }

    //use to read data from db
    public static ResultSet getData(String query, Connection connection) throws SQLException{
        java.sql.Statement stm =  connection.createStatement();
        ResultSet res = stm.executeQuery(query);
        return res;
    }

    
}
