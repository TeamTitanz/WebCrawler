/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package pagecollector;

import controller.CaseController;
import java.io.IOException;
import java.util.Date;
import java.text.DateFormat;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Locale;
import java.util.logging.Level;
import java.util.logging.Logger;
import models.Anchor;
import models.AppellateInformation;
import models.Case;
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
            Case consumerLawCase = new Case();
            ArrayList<AppellateInformation> appellateInformation = new ArrayList<AppellateInformation>();
            Domain domain = new Domain("http://caselaw.findlaw.com");
            
            //----------------------------------------start extracting HTML document from summary page------------------------------
            
            Anchor summaryAnchor = new Anchor(domain, "http://caselaw.findlaw.com/summary/opinion/us-supreme-court/2011/06/23/255511.html");
            WebPage summaryWebPage = new WebPage(summaryAnchor);
            summaryWebPage.getDocumentFromWeb();
            Document summaryDocument = summaryWebPage.getDocument();
            
            //get <p> tags in document
            Elements paragraphs = summaryDocument.select("p");
            
            //iterate through each <p> element.
            for(Element p : paragraphs) {
             
              if(!p.text().equals(" ")) {
                 consumerLawCase.setSummary(p.text().replace("'", "''")); //extract summary and set it to Case object
              }
              
            }
            
            //get <h3> tags in document
            Elements h3s = summaryDocument.select("h3");
            
            //iterate through each <h3> elements.
            for(Element h3 : h3s) {

                if(h3.text().equals("Judges")) {
                    
                    if(!h3.nextElementSibling().text().equals("")) { //check whether li tags exists
                        String judge = h3.nextElementSibling().select("li").text(); // extract judge
                    }
                    
                } else if(h3.text().equals("Appellate Information")) {
                    
                    if(!h3.nextElementSibling().text().equals("")) { //check whether li tags exists
                        
                        //extract li tags for appellate information
                        Elements appellateElements = h3.nextElementSibling().select("li");
                        
                        //iterate through li tags
                        for(Element li: appellateElements) {
                            
                            switch(li.text().split(" ")[0]) {
                                case "Decided" : 
                                    Date decidedDate = splitAppellatesDates(li.text());
                                    //Adding appellate Inforation Object to Appellate Information Array List
                                    AppellateInformation aI = new AppellateInformation("Decided", decidedDate);
                                    appellateInformation.add(aI);
                                    break;
                                    
                                case "Submitted" : 
                                    Date submittedDate = splitAppellatesDates(li.text());
                                    aI = new AppellateInformation("Submitted", submittedDate);
                                    appellateInformation.add(aI);
                                    break;
                                    
                                case "Argued" : 
                                    Date arguedDate = splitAppellatesDates(li.text());
                                    aI = new AppellateInformation("Argued", arguedDate);
                                    appellateInformation.add(aI);
                                    break;
                                    
                                case "Published" : 
                                    Date publishedDate = splitAppellatesDates(li.text());
                                    aI = new AppellateInformation("Published", publishedDate);
                                    appellateInformation.add(aI);
                                    break;
                                    
                            }
                            
                        }
                    }
                    
                } else if(h3.text().equals("Court")) {
                    
                    if(!h3.nextElementSibling().text().equals("")) { //check whether li tags exists
                        String court = h3.nextElementSibling().child(0).text();
                        consumerLawCase.setCourt(court); //adding court information to case
                    }
                    
                } else if(h3.text().equals("Counsel")) {
                    
                    if(!h3.nextElementSibling().text().equals("")) { //check whether li tags exists
                        String counsel = h3.nextElementSibling().child(0).text();
                        consumerLawCase.setCounsel(counsel); //adding counsel information to case
                    } 
                    
                }
            }
            
            //----------------------------------------end extracting HTML document from summary page------------------------------
            
            //----------------------------------------start extracting HTML document from Read page------------------------------
            Anchor readAnchor = new Anchor(domain, "http://caselaw.findlaw.com/us-supreme-court/09-993.html");
            WebPage readWebPage = new WebPage(summaryAnchor);
            readWebPage.getDocumentFromWeb();
            Document readDocument = readWebPage.getDocument();
            
            
            
            //CaseController.addCase(consumerLawCase);
        } catch (Exception ex) {
            Logger.getLogger(PageCollector.class.getName()).log(Level.SEVERE, null, ex);
        }
    }
    
    //Split and formating Appellates Dates
    private static Date splitAppellatesDates(String records) {
        
        String[] dateRecord = records.split(" ");
        DateFormat df = new SimpleDateFormat("yyyy/mm/dd", Locale.ENGLISH);
        
        if (dateRecord.length > 1) {
            try {  
                Date result =  (Date) df.parse(dateRecord[1]);
                return result;
            } catch (ParseException ex) {
                Logger.getLogger(PageCollector.class.getName()).log(Level.SEVERE, null, ex);
            }
        }
        
        return null;
    }
    
}
