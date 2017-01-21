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
public class Pagination {

    private WebPage webPage;
    private List<String> pagesList;
    private List<CaseLaw> caseLawList;
    private int pageCount = 1;

    public Pagination(WebPage webPage) throws Exception {

        this.webPage = webPage;

        pagesList = new ArrayList<String>();
        caseLawList = new ArrayList<CaseLaw>();

        createPageList(webPage);

        getCaseLawUrlList();

    }

    public void getCaseLawUrlList() throws Exception {

        for (String url : pagesList) {
            Domain domain = new Domain(url);
            Anchor anchor = new Anchor(domain, url);
            WebPage webPage = new WebPage(anchor);
            webPage.getDocumentFromWeb();

            CaseLaw caseLaw = new CaseLaw(webPage);
            
            caseLawList.add(caseLaw);

        }

    }

    public void createPageList(WebPage webPage) {
        String pageSelect = webPage.getDocument().getElementsByClass("pagecount").toString();

        Document document = Jsoup.parse(pageSelect);
        Elements options = document.select("strong");

        for (Element element : options.subList(1, options.size())) {

            pageCount = Integer.parseInt(element.text());

        }
        for (int i = 1; i < pageCount + 1; i++) {

            String link = webPage.getAnchor().getAnchorUrl();
            link += "&" + "pgnum=" + i;
            pagesList.add(link);

        }
    }

    public List<String> getPages() {
        return pagesList;
    }

}
