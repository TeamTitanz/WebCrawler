/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package controller;

import connection.DBConnection;
import handler.DBHandler;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import models.Case;

/**
 *
 * @author kjtdi
 */
public class CaseController {
    
    //add Case to database
    public static int addCase(Case caseToDB) throws SQLException, ClassNotFoundException{
        String sql = "INSERT INTO cases (case_no, court, party_1, party_2, argued_date, decided_date, content, summary, counsel) VALUES('"+caseToDB.getCase_no()+"','"+caseToDB.getCourt()+"','"+caseToDB.getParty_1()+"','"+
                caseToDB.getParty_2()+"','2017-01-21','2017-01-21','"+caseToDB.getContent()+"','"+caseToDB.getSummary()+"','"+caseToDB.getCounsel()+"')";
        System.out.println(sql);
        int res = DBHandler.setData(sql, DBConnection.getConnectionToDB());
        return res;
    }
  
}
