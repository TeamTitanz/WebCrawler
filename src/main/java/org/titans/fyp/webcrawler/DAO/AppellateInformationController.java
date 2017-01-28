/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package org.titans.fyp.webcrawler.DAO;


import org.titans.fyp.webcrawler.database.DBConnection;
import org.titans.fyp.webcrawler.database.DBHandler;
import org.titans.fyp.webcrawler.models.AppellateInformation;

import java.sql.SQLException;

/**
 *
 * @author kjtdi
 */
public class AppellateInformationController {
    
    //add Appellate informatio to database
    public static int addAppellateInformation(AppellateInformation appellateInformation) throws SQLException, ClassNotFoundException {
        
        String sql = "INSERT INTO appellate_information (case_id, type, date) VALUES('"
                + appellateInformation.getCaseId()+ "','" + appellateInformation.getType()+ "','" + appellateInformation.getDate()+ "')";
        int res = DBHandler.setData(sql, DBConnection.getConnectionToDB());
        
        return res;
    }
    
}
