/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package controller;

import connection.DBConnection;
import handler.DBHandler;
import java.sql.SQLException;
import models.FootNotes;

/**
 *
 * @author kjtdi
 */
public class FootNoteController {
    
    //add Foot Notes to database
    public static int addFootNotes(FootNotes footNotes) throws SQLException, ClassNotFoundException {
        
        String sql = "INSERT INTO foot_notes (case_id, content) VALUES('"
                + footNotes.getCaseId()+ "','" + footNotes.getContent() + "')";
        int res = DBHandler.setData(sql, DBConnection.getConnectionToDB());    
        
        return res;
    }
}
