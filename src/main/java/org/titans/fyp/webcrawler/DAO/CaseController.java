/*******************************************************************************
 * Copyright 2016 Titans
 * Permission is hereby granted, free of charge, to any person obtaining a copy
 * of this software and associated documentation files (the "Software"), to deal
 * in the Software without restriction, including without limitation the rights
 * to use, copy, modify, merge, publish, distribute, sublicense, and/or sell
 * copies of the Software, and to permit persons to whom the Software is
 * furnished to do so, subject to the following conditions:
 *
 * The above copyright notice and this permission notice shall be included in all
 * copies or substantial portions of the Software.
 *
 * THE SOFTWARE IS PROVIDED "AS IS", WITHOUT WARRANTY OF ANY KIND, EXPRESS OR
 * IMPLIED, INCLUDING BUT NOT LIMITED TO THE WARRANTIES OF MERCHANTABILITY,
 * FITNESS FOR A PARTICULAR PURPOSE AND NONINFRINGEMENT. IN NO EVENT SHALL THE
 * AUTHORS OR COPYRIGHT HOLDERS BE LIABLE FOR ANY CLAIM, DAMAGES OR OTHER LIABILITY,
 * WHETHER IN AN ACTION OF CONTRACT, TORT OR OTHERWISE, ARISING FROM, OUT OF OR IN
 * CONNECTION WITH THE SOFTWARE OR THE USE OR OTHER DEALINGS IN THE SOFTWARE.
 *
 ******************************************************************************/
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


public class CaseController {

    //add Case to database
    public static int addCase(Case caseToDB, ArrayList<FootNotes> footNotes, ArrayList<AppellateInformation>
            appellateInformation, ArrayList<Judge> judges, String summaryPageURL, String readPageURL)
            throws SQLException, ClassNotFoundException {

        String sql = "INSERT INTO cases (case_no, court, party_1, party_2, argued_date, decided_date, content, " +
                "summary, counsel) VALUES('"
                + caseToDB.getCase_no()+ "','" + caseToDB.getCourt()+ "','" +
                caseToDB.getParty_1()+ "','" + caseToDB.getParty_2()+ "','"+
                caseToDB.getArgued_date()+"','"+ caseToDB.getDecided_date()+"','" +
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

        //add urls to the database
        ContentURLsController.addURLs(summaryPageURL, readPageURL, latestId);
        
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
