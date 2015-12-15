package com.luizink.apps.infograbber;

import com.jaunt.*;
import com.jaunt.component.Form;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

/**
 * Created by maurice on 12/15/2015.
 */
public class InfoGrabber {

    public InfoGrabber() throws JauntException,java.io.UnsupportedEncodingException {
        this.readSite("url");
    }

    public static void main(String args[]) throws JauntException,java.io.UnsupportedEncodingException {
        System.out.println("Hello from InfoGrabber");
        InfoGrabber infoGrabber = new InfoGrabber();
    }

    public String[] getPageLinks(Document doc) throws java.io.UnsupportedEncodingException {
        Elements pages = doc.findEach("<li class=\" \"");
        ArrayList<String> hrefList = new ArrayList<String>();

        try {
            for (Element page : pages) {
                String href = pages.findFirst("<a").getAt("href");
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

        Elements lots = doc.findEvery("<tr>");
        for (Element lot : lots) {
            Elements lotAttributes = lot.findEvery("<td>");
            int eCount=0;
            LotItem lotItem = new LotItem();
            for(Element attribute : lotAttributes) {

                switch(eCount) {
                    case 0: {
                        lotItem.setId(attribute.getText());
                    }
                    case 1: {
                        lotItem.setDescription(attribute.getText());
                    }
                    case 2: {
                        lotItem.setOffers(attribute.getText());
                    }
                    case 3: {
                        lotItem.setLastOffer(attribute.getText());
                    }
                    case 4: {
                        lotItem.setClosingDate(attribute.getText());
                    }
                }
                eCount++;
            }
            lotItems.add(lotItem);
        }
        return lotItems.toArray(new LotItem[lotItems.size()]);
    }

    public String readSite(String url) throws JauntException,java.io.UnsupportedEncodingException {
        ArrayList<LotItem> lotItems = new ArrayList<LotItem>();

        UserAgent userAgent = new UserAgent();
        userAgent.settings.autoSaveAsHTML = true;
        userAgent.settings.showHeaders = true;
        userAgent.visit("https://www.bva-auctions.com");       //visit google
        userAgent.doc.apply("boek");
        Form form = userAgent.doc.getActiveForm();
        form.submit();

        String[] pages = getPageLinks(userAgent.doc);

        System.out.println("pages: " + pages.length);

        Collections.addAll(lotItems, getLotItems(userAgent.doc));
        //if there are more pages, get those results
        for(int i=0;i<pages.length;i++) {
            System.out.println(pages[i]);
            userAgent.visit(pages[i]);
            //System.out.print(userAgent.getSource());
            Collections.addAll(lotItems, getLotItems(userAgent.doc));
        }
        System.out.println(lotItems.size());

        return "success";
    }

}
