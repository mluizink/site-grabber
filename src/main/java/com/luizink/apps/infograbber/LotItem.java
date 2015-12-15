package com.luizink.apps.infograbber;

/**
 * Created by maurice on 12/15/2015.
 */
public class LotItem {
    public String getId() {
        return id;
    }

    public LotItem() {

    }

    public LotItem(String id, String description, String offers, String lastOffer, String closingDate) {
        this.id = id;
        this.description = description;
        this.offers = offers;
        this.lastOffer = lastOffer;
        this.closingDate = closingDate;
    }

    public void setId(String id) {
        this.id = id;
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

    private String id;
    private String description;
    private String offers;
    private String lastOffer;
    private String closingDate;

}
