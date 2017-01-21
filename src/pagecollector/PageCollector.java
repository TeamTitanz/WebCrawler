/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package pagecollector;

import findLaw.QueryBuilder;
import java.io.IOException;
import java.util.logging.Level;
import java.util.logging.Logger;
import models.Anchor;
import models.Domain;
import models.WebPage;
import org.jsoup.Jsoup;
import org.jsoup.Jsoup.*;
import org.jsoup.nodes.Document;
import org.jsoup.nodes.Element;
import org.jsoup.select.Elements;

/**
 *
 * @author kjtdi
 */
public class PageCollector {

    /**
     * @param args the command line arguments
     */
    public static void main(String[] args) {
        try {
            
            String url ="http://caselaw.findlaw.com";
            
            Domain domain = new Domain(url);
            Anchor anchor = new Anchor(domain, url);
            WebPage webPage = new WebPage(anchor);
            webPage.getDocumentFromWeb();
            
            QueryBuilder queryBuilder = new QueryBuilder(webPage);
            
            //get data in paragraph tags
            //System.out.println(webPage.getDocument().getElementsByTag("p"));
            
        } catch (Exception ex) {
            Logger.getLogger(PageCollector.class.getName()).log(Level.SEVERE, null, ex);
        }
    }
    
}
