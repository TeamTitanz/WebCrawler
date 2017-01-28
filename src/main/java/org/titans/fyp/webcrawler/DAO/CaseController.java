/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package org.titans.fyp.webcrawler.DAO;


import org.titans.fyp.webcrawler.database.DBConnection;
import org.titans.fyp.webcrawler.database.DBHandler;
import org.titans.fyp.webcrawler.models.AppellateInformation;
import org.titans.fyp.webcrawler.models.Case;
import org.titans.fyp.webcrawler.models.FootNotes;
import org.titans.fyp.webcrawler.models.Judge;

import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;


/**
 *
 * @author kjtdi
 */
public class CaseController {

    //add Case to database
    public static int addCase(Case caseToDB, ArrayList<FootNotes> footNotes, ArrayList<AppellateInformation> appellateInformation, ArrayList<Judge> judges) throws SQLException, ClassNotFoundException {
        
        String sql = "INSERT INTO cases (case_no, court, party_1, party_2, argued_date, decided_date, content, summary, counsel) VALUES('"
                + caseToDB.getCase_no()+ "','" + caseToDB.getCourt()+ "','" +
                caseToDB.getParty_1()+ "','" + caseToDB.getParty_2()+ "','"+caseToDB.getArgued_date()+"','"+caseToDB.getDecided_date()+"','" + 
                caseToDB.getContent()+ "','" + caseToDB.getSummary() + "','" + caseToDB.getCounsel() + "')";
        int res = DBHandler.setData(sql, DBConnection.getConnectionToDB());
        
        //retrieve latestId
        int latestId = getLatestId();
        
        //add appellateInformation to database one by one
        for(AppellateInformation  aI: appellateInformation) {
            aI.setCaseId(latestId);
            AppellateInformationController.addAppellateInformation(aI);
        }
        
        //add FootNotes to database one by one
        for(FootNotes  fn: footNotes) {
            fn.setCaseId(latestId);
            FootNoteController.addFootNotes(fn);
        }
        
        //add Judges to database one by one
        for(Judge j: judges) {
            j.setCaseId(latestId);
            JudgeController.addJudge(j);
        }
        
        return res;
    }
    
    //TODO: implement using prepared statement
    private static int getLatestId() throws SQLException, ClassNotFoundException {
        String sql = "SELECT id from cases GROUP BY id DESC LIMIT 1";
        ResultSet rst = DBHandler.getData(sql, DBConnection.getConnectionToDB());
        int latestId = -1;
        while(rst.next()){
            latestId = rst.getInt(1);
        }
        return latestId;
    }

}
