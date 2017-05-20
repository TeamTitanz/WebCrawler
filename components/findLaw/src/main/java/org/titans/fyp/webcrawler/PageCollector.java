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


import org.apache.log4j.Logger;
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

/**
 * Information extraction process on the summary and the read page happens here.
 */
public class PageCollector {
    static String domainURL;

    final static Logger logger = Logger.getLogger(PageCollector.class);

    public static void setDomain(String domain) {
        domainURL = domain;
    }

    public static void Crawl(String summaryPageURL, String readPageURL) {

        logger.info("URL:" + summaryPageURL + "," + readPageURL);

        try {
            Case lawCase = new Case();
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
            for (Element p : paragraphs) {

                if (!p.text().equals(" ")) {
                    //extract summary and set it to Case object
                    lawCase.setSummary(p.text().replace("'", "''"));
                }
            }

            //get <h3> tags in document
            Elements h3s = summaryDocument.select("h3");

            //iterate through each <h3> elements.
            for (Element h3 : h3s) {

                //extract judges information and push them to judges array list
                if (h3.text().equals("Judges")) {
                    //check whether li tags exists
                    if (!h3.nextElementSibling().text().equals("")) {
                        // extract judge
                        String judgeName = h3.nextElementSibling().select("li").text().replace("'", "''");
                        Judge judge = new Judge();
                        judge.setName(judgeName);
                        judges.add(judge);
                    }

                } else if (h3.text().equals("Appellate Information")) {

                    if (!h3.nextElementSibling().text().equals("")) { //check whether li tags exists
                        //extract li tags for appellate information
                        Elements appellateElements = h3.nextElementSibling().select("li");

                        //iterate through li tags
                        for (Element li : appellateElements) {

                            String s = li.text().split(" ")[0];
                            if (s.equals("Decided")) {
                                Date decidedDate = splitAppellatesDates(li.text());
//                                System.out.println("decidedDate:"+decidedDate+"=");
                                java.sql.Date decidedSqlDate;

                                try {
                                    decidedSqlDate = new java.sql.Date(decidedDate.getTime());
                                } catch (Exception ex) {
                                    decidedSqlDate = null;
                                }

                                //Adding appellate Information Object to Appellate Information Array List
                                AppellateInformation aI = new AppellateInformation("Decided", decidedSqlDate);
                                appellateInformation.add(aI);

                            } else if (s.equals("Submitted")) {
                                AppellateInformation aI;
                                Date submittedDate = splitAppellatesDates(li.text());
//                                System.out.println("submittedDate:"+submittedDate+"=");

                                java.sql.Date submittedSqlDate;
                                try {
                                    submittedSqlDate = new java.sql.Date(submittedDate.getTime());
                                } catch (Exception ex) {
                                    submittedSqlDate = null;
                                }

                                aI = new AppellateInformation("Submitted", submittedSqlDate);
                                appellateInformation.add(aI);

                            } else if (s.equals("Argued")) {
                                AppellateInformation aI;
                                Date arguedDate = splitAppellatesDates(li.text());
//                                System.out.println("arguedDate:"+arguedDate+"=");
                                java.sql.Date arguedSqlDate;
                                try {
                                    arguedSqlDate = new java.sql.Date(arguedDate.getTime());
                                } catch (Exception ex) {
                                    arguedSqlDate = null;
                                }

                                aI = new AppellateInformation("Argued", arguedSqlDate);
                                appellateInformation.add(aI);

                            } else if (s.equals("Published")) {
                                AppellateInformation aI;
                                Date publishedDate = splitAppellatesDates(li.text());
//                                System.out.println("publishedDate:"+publishedDate+"=");
                                java.sql.Date publishedSqlDate;
                                try {
                                    publishedSqlDate = new java.sql.Date(publishedDate.getTime());
                                } catch (Exception ex) {
                                    publishedSqlDate = null;
                                }

                                aI = new AppellateInformation("Published", publishedSqlDate);
                                appellateInformation.add(aI);

                            }
                        }
                    }

                } else if (h3.text().equals("Court")) {
                    //check whether li tags exists
                    if (!h3.nextElementSibling().text().equals("")) {
                        String court = h3.nextElementSibling().child(0).text();
                        //adding court information to case
                        lawCase.setCourt(court.replace("'", "''"));
                    }

                } else if (h3.text().equals("Counsel")) {
                    //check whether li tags exists
                    if (!h3.nextElementSibling().text().equals("")) {
                        String counsel = h3.nextElementSibling().child(0).text();
                        //adding counsel information to case
                        lawCase.setCounsel(counsel.replace("'", "''"));
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
            Elements readDocumentH3s = readDocument.select("h3");

            //iterate through h3 tags
            int index = 0;
            for (Element e : readDocumentH3s) {
                String extractedText = e.text();
                DateFormat df = new SimpleDateFormat("MMMM dd, yyyy", Locale.US);

                //extract argued data and decided date. ex: Argued: March 30, 2011    Decided: June 24, 2011
                if (extractedText.startsWith("Argued")) {
                    String[] rawDates = extractedText.split("    ");

                    //parse argued date as Date type
                    Date arguedDate = (Date) df.parse(rawDates[0].split(":")[1].trim());
                    java.sql.Date arguedSqlDate;
                    try {
                        arguedSqlDate = new java.sql.Date(arguedDate.getTime());
                    } catch (Exception ex) {
                        arguedSqlDate = null;
                    }

                    lawCase.setArgued_date(arguedSqlDate);

                    //check whether there is Decided date
                    if (rawDates.length > 1) {
                        //parse decided date as Date type
                        Date decidedDate = (Date) df.parse(rawDates[1].split(":")[1].trim());
                        java.sql.Date decidedSqlDate;
                        try {
                            decidedSqlDate = new java.sql.Date(decidedDate.getTime());
                        } catch (Exception ex) {
                            decidedSqlDate = null;
                        }

                        lawCase.setDecided_date(decidedSqlDate);
                    }

                } else if (extractedText.contains("Decided:")) {
                    //parse decided date as Date type
                    Date decidedDate = (Date) df.parse(extractedText.split("Decided:")[1].trim());
                    java.sql.Date decidedSqlDate;
                    try {
                        decidedSqlDate = new java.sql.Date(decidedDate.getTime());
                    } catch (Exception ex) {
                        decidedSqlDate = null;
                    }
                    lawCase.setDecided_date(decidedSqlDate);

                } else if (extractedText.startsWith("No.")) {
                    //extracting court Number. ex: No. 90-2324
                    String caseNo = extractedText.substring(4);
                    lawCase.setCase_no(caseNo.replace("'", "''"));

                } else if (index == 0 && extractedText.contains(" v. ")) {
                    //add parties to case
                    String[] partiesDetails = extractedText.split(" v. ");
                    lawCase.setParty_1(partiesDetails[0].replace("'", "''"));

                    if (partiesDetails.length > 1) {
                        String[] items = partiesDetails[1].split("\\(");

                        String case_year = null;
                        //add the case year
                        try {
                            case_year = items[1].replace(")", "");
                            SimpleDateFormat formatter = new SimpleDateFormat("yyyy");
                            formatter.parse(case_year);
                        } catch (Exception ex) {
                            if (lawCase.getDecided_date() != null) {
                                case_year = Integer.toString(lawCase.getDecided_date().getYear());
                            }
                        }

                        lawCase.setYear(case_year);
                        //add party 2
                        if (items[0].substring(items[0].length() - 1).equals(",")) {
                            items[0] = items[0].substring(0, items[0].length() - 1);
                        }
                        lawCase.setParty_2(items[0]);
                    }
                }
                index++;
            }

            //get case information
            Elements readDocementContent = readDocument.select
                    (".caselawcontent .searchable-content").first().children();
            ;
            String content = "";
            boolean isContent = false;

            for (Element e : readDocementContent) {

                //extract content
                if (isContent) {
                    content += e.text();
                } else {
                    //check for FootNotes and if foot note, it will be added to array list
                    if (!e.text().startsWith("Footnote") && !e.text().startsWith("FOOTNOTES") && !e.text().equals("")) {
                        FootNotes fn = new FootNotes();
                        fn.setContent(e.text().replace("'", "''"));
                        footNotes.add(fn);
                    }
                }

                //Check whether text is within content Area.
                if (e.text().equals("FOOTNOTES")) {
                    isContent = false;
                } else if (e.text().contains("Argued: ")) {
                    isContent = true;
                }

            }

            lawCase.setContent(content.replace("'", "''"));
            CaseController.addCase(lawCase, footNotes, appellateInformation,
                    judges, summaryPageURL, readPageURL);

        } catch (Exception ex) {
            logger.error(ex.getMessage());
        }
    }

    //Split and formatting Appellates Dates
    private static Date splitAppellatesDates(String records) {

        String[] dateRecord = records.split(" ");
        DateFormat df = new SimpleDateFormat("MM/dd/yyyy", Locale.ENGLISH);

        if (dateRecord.length > 1) {
            try {
                Date result = (Date) df.parse(dateRecord[1]);
                return result;
            } catch (ParseException ex) {
                logger.error(ex.getMessage());
            }
        }
        return null;
    }
}
