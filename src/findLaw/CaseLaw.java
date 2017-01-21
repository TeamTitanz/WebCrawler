/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package findLaw;

import java.util.ArrayList;
import java.util.List;
import models.Anchor;
import models.Domain;
import models.WebPage;
import org.jsoup.Jsoup;
import org.jsoup.nodes.Document;
import org.jsoup.nodes.Element;
import org.jsoup.select.Elements;

/**
 *
 * @author ASUS-PC
 */
public class CaseLaw {

    private WebPage webPage;
    private List<String> caseList;
    private String detailedCaseLawUrl;

    public CaseLaw(WebPage webPage) throws Exception {

        this.webPage = webPage;

        caseList = new ArrayList<String>();
        detailedCaseLawUrl = "";

        createCaseList(webPage);
        setDetailedCaseLawUrl();

    }

    private void setDetailedCaseLawUrl() throws Exception {

        for (String url : caseList) {
            Domain domain = new Domain(url);
            Anchor anchor = new Anchor(domain, url);
            WebPage webPage = new WebPage(anchor);
            webPage.getDocumentFromWeb();

            detailedCaseLawUrl = getDetailedCaseLawUrl(webPage);
            
            System.out.println(url);
            System.out.println(detailedCaseLawUrl);
            System.out.println("---");
            

        }

    }

    private String getDetailedCaseLawUrl(WebPage webPage) {

        String buttonText = webPage.getDocument().getElementsByClass("btn_read").toString();

        Document document = Jsoup.parse(buttonText);
        Elements options = document.select("a[href]");
        
        String link = "";

        for (Element element : options) {

            link = (element.attr("href"));

        }
        return link;
        
    }

    private void createCaseList(WebPage webPage) {
        String caseLawTable = webPage.getDocument().getElementById("srpcaselaw").toString();

        Document document = Jsoup.parse(caseLawTable);
        Elements options = document.select("a[href]");

        for (Element element : options) {

            caseList.add(element.attr("href"));

        }
    }

}
