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

package org.titans.fyp.webcrawler;


import org.jsoup.nodes.Document;
import org.jsoup.nodes.Element;
import org.jsoup.select.Elements;
import org.titans.fyp.webcrawler.DAO.CaseController;
import org.titans.fyp.webcrawler.models.*;

import java.text.DateFormat;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.Locale;
import java.util.logging.Level;
import java.util.logging.Logger;

/**
 * Information extraction process on the summary and the read page happens here.
 */
public class PageCollector {
    static String domainURL;

    public static void setDomain(String domain) {
        domainURL = domain;
    }

    public static void Crawl(String summaryPageURL, String readPageURL) {
        try {
            Case consumerLawCase = new Case();
            ArrayList<AppellateInformation> appellateInformation = new ArrayList<AppellateInformation>();
            ArrayList<FootNotes> footNotes = new ArrayList<FootNotes>();
            ArrayList<Judge> judges = new ArrayList<Judge>();
            Domain domain = new Domain(domainURL);
            
            //--------start extracting HTML document from summary page---------
            
            Anchor summaryAnchor = new Anchor(domain, summaryPageURL);
            WebPage summaryWebPage = new WebPage(summaryAnchor);
            summaryWebPage.getDocumentFromWeb();
            Document summaryDocument = summaryWebPage.getDocument();
            
            //get <p> tags in document
            Elements paragraphs = summaryDocument.select("p");
            
            //iterate through each <p> element.
            for(Element p : paragraphs) {
             
                if(!p.text().equals(" ")) {
                    //extract summary and set it to Case object
                    consumerLawCase.setSummary(p.text().replace("'", "''"));
                }
              
            }
            
            //get <h3> tags in document
            Elements h3s = summaryDocument.select("h3");
            
            //iterate through each <h3> elements.
            for(Element h3 : h3s) {
                
                //extract judges information and push them to judges array list
                if(h3.text().equals("Judges")) {
                    //check whether li tags exists
                    if(!h3.nextElementSibling().text().equals("")) {
                        // extract judge
                        String judgeName = h3.nextElementSibling().select("li").text().replace("'", "''");
                        Judge judge = new Judge();
                        judge.setName(judgeName);
                        judges.add(judge);
                    }
                    
                } else if(h3.text().equals("Appellate Information")) {

                    if(!h3.nextElementSibling().text().equals("")) { //check whether li tags exists
                        //extract li tags for appellate information
                        Elements appellateElements = h3.nextElementSibling().select("li");

                        //iterate through li tags
                        for(Element li: appellateElements) {

                            String s = li.text().split(" ")[0];
                            if (s.equals("Decided")) {
                                Date decidedDate = splitAppellatesDates(li.text());
                                java.sql.Date decidedSqlDate = new java.sql.Date(decidedDate.getTime());
                                //Adding appellate Inforation Object to Appellate Information Array List
                                AppellateInformation aI = new AppellateInformation("Decided", decidedSqlDate);
                                appellateInformation.add(aI);

                            } else if (s.equals("Submitted")) {
                                AppellateInformation aI;
                                Date submittedDate = splitAppellatesDates(li.text());
                                java.sql.Date submittedSqlDate = new java.sql.Date(submittedDate.getTime());
                                aI = new AppellateInformation("Submitted", submittedSqlDate);
                                appellateInformation.add(aI);

                            } else if (s.equals("Argued")) {
                                AppellateInformation aI;
                                Date arguedDate = splitAppellatesDates(li.text());
                                java.sql.Date arguedSqlDate = new java.sql.Date(arguedDate.getTime());
                                aI = new AppellateInformation("Argued", arguedSqlDate);
                                appellateInformation.add(aI);

                            } else if (s.equals("Published")) {
                                AppellateInformation aI;
                                Date publishedDate = splitAppellatesDates(li.text());
                                java.sql.Date publishedSqlDate = new java.sql.Date(publishedDate.getTime());
                                aI = new AppellateInformation("Published", publishedSqlDate);
                                appellateInformation.add(aI);

                            }
                        }
                    }
                    
                } else if(h3.text().equals("Court")) {
                    //check whether li tags exists
                    if(!h3.nextElementSibling().text().equals("")) {
                        String court = h3.nextElementSibling().child(0).text();
                        //adding court information to case
                        consumerLawCase.setCourt(court.replace("'", "''"));
                    }
                    
                } else if(h3.text().equals("Counsel")) {
                    //check whether li tags exists
                    if(!h3.nextElementSibling().text().equals("")) {
                        String counsel = h3.nextElementSibling().child(0).text();
                        //adding counsel information to case
                        consumerLawCase.setCounsel(counsel.replace("'", "''"));
                    } 
                    
                }
            }
            
            //------end extracting HTML document from summary page------
            
            //------start extracting HTML document from Read page-------

            Anchor readAnchor = new Anchor(domain, readPageURL);
            WebPage readWebPage = new WebPage(readAnchor);
            readWebPage.getDocumentFromWeb();
            Document readDocument = readWebPage.getDocument();
            
            //get <h3> tags in document
            Elements readDocementH3s = readDocument.select("h3");
            
            //iterate through h3 tags
            int index = 0;
            for(Element e: readDocementH3s) {
                String extractedText = e.text();
                
                //extract argued data and decided date. ex: Argued: March 30, 2011    Decided: June 24, 2011
                if(extractedText.startsWith("Argued")) {
                    String[] rawDates = extractedText.split("    ");
                    
                    //parse argued date as Date type
                    DateFormat df = new SimpleDateFormat("MMMM dd, yyyy", Locale.US);
                    Date arguedDate =  (Date) df.parse(rawDates[0].split(":")[1].trim());
                    java.sql.Date arguedSqlDate = new java.sql.Date(arguedDate.getTime());
                    consumerLawCase.setArgued_date(arguedSqlDate);
                    
                    //check whether there is Decided date
                    if(rawDates.length > 1) {
                        //parse decided date as Date type
                        Date decidedDate =  (Date) df.parse(rawDates[1].split(":")[1].trim());
                        java.sql.Date decidedSqlDate = new java.sql.Date(decidedDate.getTime());
                        consumerLawCase.setDecided_date(decidedSqlDate);
                    }
                    
                } else if(extractedText.startsWith("No.")) {
                    //extracting court Number. ex: No. 90-2324
                    String caseNo = extractedText.substring(4);
                    consumerLawCase.setCase_no(caseNo.replace("'", "''"));
                    
                } else if(index == 0 && extractedText.contains(" v. ")) {
                    //add parties to case
                    String[] partiesDetails = extractedText.split(" v. ");
                    consumerLawCase.setParty_1(partiesDetails[0].replace("'", "''"));
                    
                    if(partiesDetails.length > 1) {
                        consumerLawCase.setParty_2(partiesDetails[1].replace("'", "''"));
                    }
                }
                index++;
            }
            
            //get case information
            Elements readDocementContent = readDocument.select
                    (".caselawcontent .searchable-content").first().children();;
            String content = "";
            boolean isContent = false;
            
            for (Element e: readDocementContent) {
                
                //extract content
                if(isContent) {
                    content += e.text();
                } else {
                    //check for FootNotes and if foot note, it will be added to array list
                    if(!e.text().startsWith("Footnote") && !e.text().startsWith("FOOTNOTES") && !e.text().equals("")) {
                        FootNotes fn = new FootNotes();
                        fn.setContent(e.text().replace("'", "''"));
                        footNotes.add(fn);
                    }
                }
                
                //Check whether text is within content Area.
                if(e.text().equals("FOOTNOTES")) {
                    isContent = false;
                } else if(e.text().contains("Argued: ")) {
                    isContent = true;
                }
                
            }
            
            consumerLawCase.setContent(content.replace("'", "''"));
            CaseController.addCase(consumerLawCase, footNotes, appellateInformation,
                    judges, summaryPageURL, readPageURL);

        } catch (Exception ex) {
            Logger.getLogger(PageCollector.class.getName()).log(Level.SEVERE, null, ex);
        }
    }
    
    //Split and formating Appellates Dates
    private static Date splitAppellatesDates(String records) {
        
        String[] dateRecord = records.split(" ");
        DateFormat df = new SimpleDateFormat("MM/dd/yyyy", Locale.ENGLISH);
        
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
