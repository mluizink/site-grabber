package com.luizink.apps.infograbber.grabservice;

import com.jaunt.*;
import com.jaunt.component.Form;
import com.luizink.apps.infograbber.data.LotItem;
import com.luizink.apps.infograbber.data.LotItemsQueryManager;
import org.springframework.boot.autoconfigure.SpringBootApplication;

import java.util.ArrayList;
import java.util.Calendar;
import java.util.Collections;
import java.util.List;

/**
 * Created by maurice on 12/15/2015.
 */
@SpringBootApplication
public class InfoGrabber {

    public InfoGrabber() throws JauntException,java.io.UnsupportedEncodingException {
        this.readSite("https://www.bva-auctions.com/","apple");
    }


    public static void main(String args[]) throws JauntException,java.io.UnsupportedEncodingException {
        InfoGrabber infoGrabber = new InfoGrabber();
    }

    public String[] getPageLinks(Document doc) throws java.io.UnsupportedEncodingException {
        Elements pages = doc.findEach("<li class=\" \"");
        ArrayList<String> hrefList = new ArrayList<String>();

        try {
            for (Element page : pages) {
                String href = page.findFirst("<a").getAt("href");
                href = href.replaceAll("&amp;","&");
                hrefList.add(href);
            }
        } catch(com.jaunt.NotFound ex) {
            ex.printStackTrace(System.out);
            return new String[0];
        }
        return hrefList.toArray(new String[hrefList.size()]);
    }

    public LotItem[] getLotItems(Document doc) {
        ArrayList<LotItem> lotItems = new ArrayList<LotItem>();
        String searchString = "enpty";
        //Retrieve search subject from query.

        try {
            searchString = doc.findFirst("<input id=\"searchbox\"").getAt("value");
        } catch (Exception e) {
            e.printStackTrace();
        }

        Elements lots = doc.findEvery("<tr>");
        for (Element lot : lots) {
            Elements lotAttributes = lot.findEvery("<td>");
            int eCount=0;
            LotItem lotItem = new LotItem();
            lotItem.setSearchSource(searchString);
            for(Element attribute : lotAttributes) {
                String aText = attribute.getText();
                aText = aText.trim(); // Most values are surrounded by tabs, remove those.
                switch(eCount) {
                    case 1: {
                        lotItem.setId(aText);
                    }
                    case 2: {
                        try {
                            Element titleElement = attribute.findFirst("<a");
                            String title = titleElement.getText();
                            String href = titleElement.getAt("href");
                            lotItem.setTitle(title);
                            lotItem.setHref(href);
                        }catch(com.jaunt.NotFound ex) {
                            lotItem.setTitle("Not found");
                        }
                        lotItem.setDescription(aText);
                    }
                    case 3: {
                        lotItem.setOffers(aText);
                    }
                    case 4: {
                        lotItem.setLastOffer(aText);
                    }
                    case 5: {
                        lotItem.setClosingDate(aText);
                    }
                }
                eCount++;
            }
            if (lotItem.getId() != null) {
                lotItems.add(lotItem);
            }
        }
        return lotItems.toArray(new LotItem[lotItems.size()]);
    }

    public String readSite(String url, String searchQuery) throws JauntException,java.io.UnsupportedEncodingException {

        LotItemsQueryManager queryManager = new LotItemsQueryManager();

        ArrayList<LotItem> lotItems = new ArrayList<LotItem>();

        UserAgent userAgent = new UserAgent();
        userAgent.settings.autoSaveAsHTML = false;
        userAgent.settings.showHeaders = false;
        userAgent.visit(url);
        userAgent.doc.apply(searchQuery);
        Form form = userAgent.doc.getActiveForm();
        form.submit();

        String[] pages = getPageLinks(userAgent.doc);

        System.out.println("pages: " + pages.length);

        Collections.addAll(lotItems, getLotItems(userAgent.doc));
        //if there are more pages, get those results
        for(int i=0;i<pages.length;i++) {
            userAgent.visit(pages[i]);
            //System.out.print(userAgent.getSource());
            Collections.addAll(lotItems, getLotItems(userAgent.doc));
        }
        queryManager.SaveCollection(lotItems);

        System.out.println(lotItems.size());

        //Search and retrieve.
        //Query searchCollection = new Query(Criteria.where("searchSource").is("apple"));
/*
        Calendar toDate = Calendar.getInstance();
        toDate.add(Calendar.HOUR, 8);
        List<LotItem> resultLotItems = queryManager.findBySubjectAndClosingDate("apple", toDate.getTime());

        for(LotItem lotItem : resultLotItems) {
            System.out.println(lotItem.getName() + " " + lotItem.getClosingDate() + " " + lotItem.getHref());
        }

        System.out.println("result: " + resultLotItems.size());
*/

        return "success";
    }

}
