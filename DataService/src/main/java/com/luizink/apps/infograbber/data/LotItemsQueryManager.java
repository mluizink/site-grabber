package com.luizink.apps.infograbber.data;

import org.springframework.data.mongodb.core.MongoOperations;
import org.springframework.data.mongodb.core.query.Criteria;
import org.springframework.data.mongodb.core.query.Query;

import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;
import java.util.List;

/**
 * Created by maurice on 18/12/15.
 *
 * This class is used to house all queries directly related to the LotItems collection.
 *
 */
public class LotItemsQueryManager extends MongoDBConnection{
    DateFormat dateFormat = new SimpleDateFormat("dd-MM-yyyy HH:mm");
    public LotItemsQueryManager() {
        super();
    }

    public void SaveCollection(List<LotItem> lotItems) {
        for (LotItem item : lotItems) {
            mongoOperation.save(item);
        }
    }

    public MongoOperations getMongoOperations() {
        return this.mongoOperation;
    }

    public List<LotItem> find(Query query) {
        return mongoOperation.find(query, LotItem.class);
    }

    public List<LotItem> findBySubjectAndClosingDate(String subject, Date closingDate) {
        return this.findBySubjectAndClosingDate(subject, closingDate, false);
    }

    public List<LotItem> findBySubjectAndClosingDate(String subject, Date closingDate, boolean includeClosed) {
        System.out.println(closingDate.toString());
        Query searchCollection = null;
        if (includeClosed) {
            searchCollection = new Query(Criteria.where("closingDate").exists(true)
                    .andOperator(Criteria.where("closingDate").lt(dateFormat.format(closingDate)))
                    .and("searchSource").is(subject));
        } else {
            searchCollection = new Query(Criteria.where("closingDate").exists(true)
                    .andOperator(Criteria.where("closingDate").lt(dateFormat.format(closingDate)),
                            Criteria.where("closingDate").gt(dateFormat.format(Calendar.getInstance().getTime())))
                    .and("searchSource").is(subject));
        }
        System.out.println(searchCollection.toString());
        return this.find(searchCollection);
    }
}
