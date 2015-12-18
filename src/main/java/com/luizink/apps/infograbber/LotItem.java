package com.luizink.apps.infograbber;

import org.springframework.data.annotation.Id;
import org.springframework.data.mongodb.core.mapping.Document;

/**
 * Created by maurice on 12/15/2015.
 */
@Document(collection = "lotitems")
public class LotItem {

    public String getId() {
        return id;
    }

    public LotItem() {

    }

    public LotItem(String id, String description, String offers, String lastOffer, String closingDate) {
        setId(id);
        this.description = description;
        this.offers = offers;
        this.lastOffer = lastOffer;
        this.closingDate = closingDate;
    }

    public void setId(String id) {
        this.id = id;
        if (id != null && id.indexOf("-") > 0) {
            String aId = id.substring(0, id.indexOf("-"));
            String iId = id.substring(id.indexOf("-") + 1);
            setAuctionId(aId);
            setItemId(iId);
        } else {
            setItemId(id);
            setAuctionId("0");
        }
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public String getOffers() {
        return offers;
    }

    public void setOffers(String offers) {
        this.offers = offers;
    }

    public String getLastOffer() {
        return lastOffer;
    }

    public void setLastOffer(String lastOffer) {
        this.lastOffer = lastOffer;
    }

    public String getClosingDate() {
        return closingDate;
    }

    public void setClosingDate(String closingDate) {
        this.closingDate = closingDate;
    }

    public String getAuctionId() {
        return auctionId;
    }

    private void setAuctionId(String auctionId) {
        this.auctionId = auctionId;
    }

    public String getSearchSource() {
        return searchSource;
    }

    public void setSearchSource(String searchSource) {
        this.searchSource = searchSource;
    }

    public String getItemId() {
        return itemId;
    }

    private void setItemId(String itemId) {
        this.itemId = itemId;
    }

    public String getName() {
        return title;
    }

    public void setTitle(String name) {
        this.title = name;
    }

    public String getHref() {
        return href;
    }

    public void setHref(String href) {
        this.href = href;
    }

    @Id
    private String id;
    private String title;
    private String description;
    private String offers;
    private String lastOffer;
    private String closingDate;
    private String searchSource;
    private String auctionId;
    private String itemId;
    private String href;
}
