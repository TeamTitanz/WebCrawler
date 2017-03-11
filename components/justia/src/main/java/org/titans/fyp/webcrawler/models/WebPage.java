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
package org.titans.fyp.webcrawler.models;

import org.apache.log4j.Logger;
import org.jsoup.Jsoup;
import org.jsoup.nodes.Document;
import org.titans.fyp.webcrawler.utils.Common;
import org.titans.fyp.webcrawler.utils.Hasher;

import java.io.IOException;

public class WebPage {
    private Anchor anchor;
    private String webPageHash;
    final static Logger logger = Logger.getLogger(WebPage.class);

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
            logger.error(ex.getMessage());
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
