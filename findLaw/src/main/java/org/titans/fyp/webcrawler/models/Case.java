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
package org.titans.fyp.webcrawler.models;

import java.sql.Date;


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
    private String year;

    public Case() {
    }

    public Case(int id, String case_no, String court, String party_1, String party_2, Date argued_date,
                Date decided_date, String content, String summary, String counsel, String year) {
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
        this.year = year;
    }

    public String getYear() {
        return year;
    }

    public void setYear(String year) {
        this.year = year;
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
