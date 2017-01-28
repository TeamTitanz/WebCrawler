/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package org.titans.fyp.webcrawler.models;

import java.sql.Date;

/**
 *
 * @author kjtdi
 */
public class Case {
    private int id;
    private String case_no;
    private String court;
    private String party_1;
    private String party_2;
    private Date argued_date;
    private Date decided_date;
    private String content;
    private String summary;
    private String counsel;

    public Case() {
    }

    public Case(int id, String case_no, String court, String party_1, String party_2, Date argued_date, Date decided_date, String content, String summary, String counsel) {
        this.id = id;
        this.case_no = case_no;
        this.court = court;
        this.party_1 = party_1;
        this.party_2 = party_2;
        this.argued_date = argued_date;
        this.decided_date = decided_date;
        this.content = content;
        this.summary = summary;
        this.counsel = counsel;
    }
    
    /**
     * @return the id
     */
    public int getId() {
        return id;
    }

    /**
     * @param id the id to set
     */
    public void setId(int id) {
        this.id = id;
    }

    /**
     * @return the case_no
     */
    public String getCase_no() {
        return case_no;
    }

    /**
     * @param case_no the case_no to set
     */
    public void setCase_no(String case_no) {
        this.case_no = case_no;
    }

    /**
     * @return the court
     */
    public String getCourt() {
        return court;
    }

    /**
     * @param court the court to set
     */
    public void setCourt(String court) {
        this.court = court;
    }

    /**
     * @return the party_1
     */
    public String getParty_1() {
        return party_1;
    }

    /**
     * @param party_1 the party_1 to set
     */
    public void setParty_1(String party_1) {
        this.party_1 = party_1;
    }

    /**
     * @return the party_2
     */
    public String getParty_2() {
        return party_2;
    }

    /**
     * @param party_2 the party_2 to set
     */
    public void setParty_2(String party_2) {
        this.party_2 = party_2;
    }

    /**
     * @return the argued_date
     */
    public Date getArgued_date() {
        return argued_date;
    }

    /**
     * @param argued_date the argued_date to set
     */
    public void setArgued_date(Date argued_date) {
        this.argued_date = argued_date;
    }

    /**
     * @return the decided_date
     */
    public Date getDecided_date() {
        return decided_date;
    }

    /**
     * @param decided_date the decided_date to set
     */
    public void setDecided_date(Date decided_date) {
        this.decided_date = decided_date;
    }

    /**
     * @return the content
     */
    public String getContent() {
        return content;
    }

    /**
     * @param content the content to set
     */
    public void setContent(String content) {
        this.content = content;
    }

    /**
     * @return the summary
     */
    public String getSummary() {
        return summary;
    }

    /**
     * @param summary the summary to set
     */
    public void setSummary(String summary) {
        this.summary = summary;
    }

    /**
     * @return the counsel
     */
    public String getCounsel() {
        return counsel;
    }

    /**
     * @param counsel the counsel to set
     */
    public void setCounsel(String counsel) {
        this.counsel = counsel;
    }
    
    
}
