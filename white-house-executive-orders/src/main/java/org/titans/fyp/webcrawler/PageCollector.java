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
import org.titans.fyp.webcrawler.models.*;

import java.text.DateFormat;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
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

    public static void Crawl(String pageUrl) {
        try {

            Domain domain = new Domain(domainURL);
            //--------start extracting HTML document from summary page---------

            Anchor executiveOrdersPageAnchor = new Anchor(domain, pageUrl);
            WebPage executiveOrdersWebPage = new WebPage(executiveOrdersPageAnchor);
            executiveOrdersWebPage.getDocumentFromWeb();
            Document executiveOrdersDocument = executiveOrdersWebPage.getDocument();

            List<String> infoList = new ArrayList<String>();
            infoList.add(executiveOrdersDocument.getElementsByClass("heading-title").text());
            infoList.add(executiveOrdersDocument.getElementsByClass("heading-subtitle").text());
            infoList.add(executiveOrdersDocument.getElementsByClass("press-article-release").text());
            infoList.add(executiveOrdersDocument.getElementsByClass("press-article-date").text());
            infoList.add(executiveOrdersDocument.getElementsByClass("panel-pane pane-node-title").text());
            infoList.add(executiveOrdersDocument.getElementsByClass("field-items").text());

            for (String info : infoList) {
                System.out.println(info);
            }
            System.out.println();

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
