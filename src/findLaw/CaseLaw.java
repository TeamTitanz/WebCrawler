/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package findLaw;

import java.util.ArrayList;
import java.util.List;
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

    public CaseLaw(WebPage webPage) {

        this.webPage = webPage;
        
        caseList = new ArrayList<String>();

        String caseLawTable = webPage.getDocument().getElementById("srpcaselaw").toString();
        
        Document document = Jsoup.parse(caseLawTable);
        Elements options = document.select("a[href]");

        for (Element element : options) {

            caseList.add(element.attr("href"));

        }
        System.out.println(caseList);

    }

}
