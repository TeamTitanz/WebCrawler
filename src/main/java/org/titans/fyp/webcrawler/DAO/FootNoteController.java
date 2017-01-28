/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package org.titans.fyp.webcrawler.DAO;



import org.titans.fyp.webcrawler.database.DBConnection;
import org.titans.fyp.webcrawler.database.DBHandler;
import org.titans.fyp.webcrawler.models.FootNotes;

import java.sql.SQLException;

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
