/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package controller;

import connection.DBConnection;
import handler.DBHandler;
import java.sql.SQLException;
import models.AppellateInformation;

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
