package org.titans.fyp.webcrawler;

import org.titans.fyp.webcrawler.findLaw.Pagination;
import org.titans.fyp.webcrawler.findLaw.QueryBuilder;
import org.titans.fyp.webcrawler.models.Anchor;
import org.titans.fyp.webcrawler.models.Domain;
import org.titans.fyp.webcrawler.models.WebPage;

import java.util.ArrayList;

/**
 * Created by User on 1/28/2017.
 */
public class Initiator {

    public static void main(String [] args) {
        String url = "http://caselaw.findlaw.com";

        try {

            Domain domain = new Domain(url);
            Anchor anchor = new Anchor(domain, url);
            WebPage webPage = new WebPage(anchor);
            webPage.getDocumentFromWeb();

            //by this, it will print the Case URl, and the detiled case URL
            QueryBuilder queryBuilder = new QueryBuilder(webPage);
            ArrayList<Pagination> list = (ArrayList<Pagination>) queryBuilder.getPaginatedUrlList();

        } catch (Exception e) {
            System.out.println("error");
        }
    }
}
