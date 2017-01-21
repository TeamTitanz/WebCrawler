/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package controller;

import connection.DBConnection;
import handler.DBHandler;
import java.sql.SQLException;
import models.Judge;

/**
 *
 * @author kjtdi
 */
public class JudgeController {
    
    //add Judge to database
    public static int addJudge(Judge judge) throws SQLException, ClassNotFoundException {
        
        String sql = "INSERT INTO judges (case_id, name) VALUES('"
                + judge.getCaseId()+ "','" + judge.getName()+ "')";
        int res = DBHandler.setData(sql, DBConnection.getConnectionToDB());    
        
        return res;
    }
    
}
