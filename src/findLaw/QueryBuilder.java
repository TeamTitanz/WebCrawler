/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package findLaw;

import models.WebPage;
import org.jsoup.Jsoup;
import org.jsoup.nodes.Document;
import org.jsoup.nodes.Element;
import org.jsoup.select.Elements;
import java.util.*;
import models.Anchor;
import models.Domain;

/**
 *
 * @author ASUS-PC
 */
public class QueryBuilder {

    private WebPage webpage;

    private final List<String> courtArray;
    private final List<String> topicArray;
    private final List<String> queryArray;
    private final List<Pagination> paginationArray;

    public List<String> getQueryArray() {
        return queryArray;
    }

    public QueryBuilder(WebPage webpage) throws Exception {
        this.webpage = webpage;

        courtArray = new ArrayList<String>();
        topicArray = new ArrayList<String>();
        queryArray = new ArrayList<String>();
        paginationArray = new ArrayList<Pagination>();

        createCourtList(webpage);
        
        //manually set the Topics to cs_15 = Consumer Protection Law and 
        //createTopicList(webpage);
        topicArray.add("cs_15");
        
        createQueryList();

        getPaginatedUrlList();

    }

    public List<Pagination> getPaginationArray() {
        return paginationArray;
    }

    public WebPage getWebpage() {
        return webpage;
    }

    public List<String> getCourtArray() {
        return courtArray;
    }

    public List<String> getTopicArray() {
        return topicArray;
    }

    public void getPaginatedUrlList() throws Exception {

        for (String url : queryArray) {
            
            Domain domain = new Domain(url);
            Anchor anchor = new Anchor(domain, url);
            WebPage webPage = new WebPage(anchor);
            webPage.getDocumentFromWeb();

            Pagination pagination = new Pagination(webPage);
            paginationArray.add(pagination);

        }

    }

    private void createCourtList(WebPage webpage) {
        String courtSelect = webpage.getDocument().getElementById("court").toString();
        Document document = Jsoup.parse(courtSelect);
        Elements options = document.select("select > option");

        for (Element element : options) {
            if (!element.attr("value").isEmpty()) {
                courtArray.add(element.attr("value"));
            }

        }
    }

    private void createTopicList(WebPage webpage) {
        String topicSelect = webpage.getDocument().getElementById("topic").toString();
        Document document = Jsoup.parse(topicSelect);
        Elements options = document.select("select > option");

        for (Element element : options) {
            if (!element.attr("value").isEmpty()) {
                topicArray.add(element.attr("value"));
            }

        }
    }

    private void createQueryList() {
        String domainUrl = "http://caselaw.findlaw.com/summary/search";

        for (String court : courtArray) {
            for (String topic : topicArray) {

                String query = domainUrl + "?" + "court="
                        + court + "&" + "topic="
                        + topic;
                queryArray.add(query);

            }
        }
    }


}