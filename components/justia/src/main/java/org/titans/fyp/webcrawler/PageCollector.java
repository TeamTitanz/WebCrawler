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
import org.apache.pdfbox.pdmodel.PDDocument;
import org.apache.pdfbox.text.PDFTextStripper;
import org.jsoup.Jsoup;
import org.jsoup.nodes.Document;
import org.jsoup.nodes.Element;
import org.jsoup.select.Elements;
import org.titans.fyp.webcrawler.models.Anchor;
import org.titans.fyp.webcrawler.models.Domain;
import org.titans.fyp.webcrawler.models.WebPage;

import java.net.URL;
import java.text.DateFormat;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.List;
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

    public static void crawlSupremeCourt(List<String> OpinionsByVolume) {

        for (String link : OpinionsByVolume) {

//            String link = "https://supreme.justia.com/cases/federal/us/580/" ;
            try {
                Domain domain = new Domain(link);
                Anchor anchor = new Anchor(domain, link);
                WebPage caseWebPage = new WebPage(anchor);
                caseWebPage.getDocumentFromWeb();

                Document document = Jsoup.parse(caseWebPage.getDocument().getElementsByClass("has-padding-content-block-30 -zb search-result").toString());
//                System.out.println(document);
                Elements options = document.getElementsByClass("has-padding-content-block-30 -zb search-result");
//                System.out.println(options);

//
//            String baseLink = "https://supreme.justia.com";
                for (Element element : options) {

//                    System.out.println(element);

                    System.out.println(element.getElementsByClass("case-name").select("strong").text());
//                    System.out.println(element.getElementsByClass("color-green").text());
                    crawlSupremeCourtCase(element.getElementsByClass("color-green").text());

//                    break;
                }

            } catch (Exception e) {
                e.printStackTrace();
            }

        }


    }

    private static void crawlSupremeCourtCase(String url) {

//        url = "https://supreme.justia.com/cases/federal/us/580/15-606/";

        try {
            Domain domain = new Domain(url);
            Anchor anchor = new Anchor(domain, url);
            WebPage caseWebPage = new WebPage(anchor);
            caseWebPage.getDocumentFromWeb();

            Document document = Jsoup.parse(caseWebPage.getDocument().getElementsByClass("primary-content").toString());
//                System.out.println(document);


            System.out.println(document.getElementById("opinion").select("p[style]").get(1).text());
            //Name
            System.out.println(document.getElementsByClass("col--three-fourths").text());

            System.out.println(document.getElementById("opinion").select("p[style]").get(5).text());
            //Summary
            System.out.println(document.getElementById("diminished-text").text());
            System.out.println(document.getElementById("opinion").text());

            pdfToText(document.getElementById("opinion-pdf").select("a[href]").attr("href"));

//            System.out.println("***********************************************************************************************");

        } catch (Exception e) {
            e.printStackTrace();
        }

    }

    private static void pdfToText(String pdfURL) {

        pdfURL = "https://" + pdfURL.split("://")[1];
//        System.out.println(pdfURL);

        try {

            PDDocument pddDocument = PDDocument.load((new URL(pdfURL)).openStream());
            PDFTextStripper textStripper = new PDFTextStripper();
            String doc = textStripper.getText(pddDocument);
            pddDocument.close();
            System.out.println(doc);
        } catch (Exception e) {
            e.getMessage();
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
