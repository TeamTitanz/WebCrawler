/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package models;

import utils.Hasher;
import java.io.IOException;
import java.util.logging.Level;
import java.util.logging.Logger;
import org.jsoup.Jsoup;
import org.jsoup.nodes.Document;
import org.jsoup.nodes.Element;
import org.jsoup.select.Elements;
import pagecollector.PageCollector;
import utils.Common;

/**
 *
 * @author kjtdi
 */
public class WebPage {
    private Anchor anchor;
    private String webPageHash;
    
    private Document document;

    public WebPage(Anchor anchor) throws Exception {
        this.anchor = anchor;
        //Hashing is useful when crawling billions of webpages.
        this.webPageHash = Hasher.toSha256(anchor.getAnchorHash() + Common.getTimeStamp());
    }
    
    //Reading webpage using Jsoup
    public void getDocumentFromWeb() {
        try {
            document = Jsoup.connect(anchor.getAnchorUrl()).get(); //assign HTML page
        } catch (IOException ex) {
            Logger.getLogger(PageCollector.class.getName()).log(Level.SEVERE, null, ex);
        }
    }

    public Anchor getAnchor() {
        return anchor;
    }

    public String getWebPageHash() {
        return webPageHash;
    }

    public Document getDocument() {
        return document;
    }

    public void setAnchor(Anchor anchor) {
        this.anchor = anchor;
    }

    public void setWebPageHash(String webPageHash) {
        this.webPageHash = webPageHash;
    }

    public void setDocument(Document document) {
        this.document = document;
    }
    
    
}
