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
public class DetailedCaseLaw {
    
    private WebPage webPage;
    private String detailedCaseLink;
    
    public DetailedCaseLaw(WebPage webPage){
        
        this.webPage = webPage;
        
        detailedCaseLink = "";

        String buttonText = webPage.getDocument().getElementsByClass("btn_read").toString();
        
        Document document = Jsoup.parse(buttonText);
        Elements options = document.select("a[href]");
        
        for (Element element : options) {

            detailedCaseLink = (element.attr("href"));

        }
        System.out.println(detailedCaseLink);
    }
    
}
