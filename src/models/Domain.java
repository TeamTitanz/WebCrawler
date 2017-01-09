/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package models;

import utils.Hasher;
import java.sql.Timestamp;
import utils.Common;

/**
 *
 * @author kjtdi
 */
public class Domain {
    private String domainHash;
    private String domainUrl;
    private boolean activated;
    private Timestamp modified;
    private Timestamp created;

    public Domain() {
    }

    public Domain(String domainUrl) throws Exception {
        //Hashing is useful when crawling billions of webpages.
        this.domainHash = Hasher.toSha256(domainUrl);
        this.domainUrl = domainUrl;
        this.activated = true;
        this.modified = Common.getTimeStamp();
        this.created = Common.getTimeStamp(); //To store crawled time stamps
    }

    public Domain(String domainHash, String domainUrl, boolean activated, Timestamp modified, Timestamp created) {
        this.domainHash = domainHash;
        this.domainUrl = domainUrl;
        this.activated = activated;
        this.modified = modified;
        this.created = created;
    }

    public String getDomainHash() {
        return domainHash;
    }

    public String getDomainUrl() {
        return domainUrl;
    }

    public boolean isActivated() {
        return activated;
    }

    public Timestamp getModified() {
        return modified;
    }

    public Timestamp getCreated() {
        return created;
    }

    public void setDomainHash(String domainHash) {
        this.domainHash = domainHash;
    }

    public void setDomainUrl(String domainUrl) {
        this.domainUrl = domainUrl;
    }

    public void setActivated(boolean activated) {
        this.activated = activated;
    }

    public void setModified(Timestamp modified) {
        this.modified = modified;
    }

    public void setCreated(Timestamp created) {
        this.created = created;
    }
    
    
}
