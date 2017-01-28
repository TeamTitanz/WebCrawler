/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package org.titans.fyp.webcrawler.DAO;


import org.titans.fyp.webcrawler.database.DBConnection;
import org.titans.fyp.webcrawler.database.DBHandler;
import org.titans.fyp.webcrawler.models.Judge;

import java.sql.SQLException;

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
