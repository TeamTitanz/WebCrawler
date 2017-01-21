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

    public CaseLaw(WebPage webPage) throws Exception {

        this.webPage = webPage;
        
        caseList = new ArrayList<String>();

        String caseLawTable = webPage.getDocument().getElementById("srpcaselaw").toString();
        
        Document document = Jsoup.parse(caseLawTable);
        Elements options = document.select("a[href]");

        for (Element element : options) {

            caseList.add(element.attr("href"));

        }
//        System.out.println(caseList);

        
        
                String url ="http://caselaw.findlaw.com/summary/opinion/us-supreme-court/2016/06/20/276801.html";
        
        Domain domain = new Domain(url);
            Anchor anchor = new Anchor(domain, url);
            WebPage webPage2 = new WebPage(anchor);
            webPage2.getDocumentFromWeb();
            
            DetailedCaseLaw queryBuilder = new DetailedCaseLaw(webPage2);

    }

}
