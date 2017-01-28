  /*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package org.titans.fyp.webcrawler.models;


  import org.titans.fyp.webcrawler.utils.Common;
  import org.titans.fyp.webcrawler.utils.Hasher;

  import java.sql.Timestamp;

  /**
   *
   * @author kjtdi
   */
  public class Anchor {
      private Domain domain;
      private String anchorHash;
      private String anchorUrl;
      private int scanStatus;
      private boolean activated;
      private Timestamp modified;
      private Timestamp created;

      public Anchor(Domain domain, String anchorUrl) throws Exception {
          this.domain = domain;
          //Hashing is useful when crawling billions of webpages.
          this.anchorHash = Hasher.toSha256(anchorUrl);
          this.anchorUrl = anchorUrl;
          this.scanStatus = 0;
          this.activated = true;
          this.modified = Common.getTimeStamp();
          this.created = Common.getTimeStamp(); //To store crawled time stamps
      }

      public Anchor(Domain domain, String anchorHash, String anchorUrl) {
          this.domain = domain;
          this.anchorHash = anchorHash;
          this.anchorUrl = anchorUrl;
      }

      public Anchor(Domain domain, String anchorHash, String anchorUrl, int scanStatus, boolean activated, Timestamp modified, Timestamp created) {
          this.domain = domain;
          this.anchorHash = anchorHash;
          this.anchorUrl = anchorUrl;
          this.scanStatus = scanStatus;
          this.activated = activated;
          this.modified = modified;
          this.created = created;
      }

      public Domain getDomain() {
          return domain;
      }

      public String getAnchorHash() {
          return anchorHash;
      }

      public String getAnchorUrl() {
          return anchorUrl;
      }

      public int getScanStatus() {
          return scanStatus;
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

      public void setDomain(Domain domain) {
          this.domain = domain;
      }

      public void setAnchorHash(String anchorHash) {
          this.anchorHash = anchorHash;
      }

      public void setAnchorUrl(String anchorUrl) {
          this.anchorUrl = anchorUrl;
      }

      public void setScanStatus(int scanStatus) {
          this.scanStatus = scanStatus;
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
