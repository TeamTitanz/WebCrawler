/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package models;

/**
 *
 * @author kjtdi
 */
public class Opinion {
    private int id;
    private int caseId;
    private String judgeName;
    private String content;

    public Opinion() {
    }

    public Opinion(int id, int caseId, String judgeName, String content) {
        this.id = id;
        this.caseId = caseId;
        this.judgeName = judgeName;
        this.content = content;
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
     * @return the caseId
     */
    public int getCaseId() {
        return caseId;
    }

    /**
     * @param caseId the caseId to set
     */
    public void setCaseId(int caseId) {
        this.caseId = caseId;
    }

    /**
     * @return the judgeName
     */
    public String getJudgeName() {
        return judgeName;
    }

    /**
     * @param judgeName the judgeName to set
     */
    public void setJudgeName(String judgeName) {
        this.judgeName = judgeName;
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
    
   
}
