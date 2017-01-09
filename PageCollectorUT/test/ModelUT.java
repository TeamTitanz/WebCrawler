/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */

import models.Anchor;
import models.Domain;
import models.WebPage;
import org.junit.After;
import org.junit.AfterClass;
import org.junit.Before;
import org.junit.BeforeClass;
import org.junit.Test;
import static org.junit.Assert.*;

/**
 *
 * @author kjtdi
 */
public class ModelUT {
    
    public ModelUT() {
    }
    
    //Unit Testing model classes
    @Test
    public void webPageLoadFromWeb() throws Exception {
        Domain domain = new Domain("http://www.jsoup.org");
        assertTrue("getDomain Hash: Hash is wrong length", domain.getDomainHash().length() == 64);
        
        Anchor anchor = new Anchor(domain, "http://www.jsoup.org");
        assertTrue("getAnchor Hash: Hash is wrong length", anchor.getAnchorHash().length() == 64);
        
        WebPage webPage = new WebPage(anchor);
        webPage.getDocumentFromWeb();
        assertTrue("Rest Results", webPage.getDocument() != null); 
    }
    
    @BeforeClass
    public static void setUpClass() {
    }
    
    @AfterClass
    public static void tearDownClass() {
    }
    
    @Before
    public void setUp() {
    }
    
    @After
    public void tearDown() {
    }
}
