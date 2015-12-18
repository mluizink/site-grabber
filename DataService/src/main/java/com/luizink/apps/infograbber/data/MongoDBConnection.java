package com.luizink.apps.infograbber.data;

import org.springframework.context.ApplicationContext;
import org.springframework.context.support.GenericXmlApplicationContext;
import org.springframework.data.mongodb.core.MongoOperations;

/**
 * Created by maurice on 18/12/15.
 */
public abstract class MongoDBConnection {
    protected MongoOperations mongoOperation;

    public MongoDBConnection() {
        ApplicationContext ctx = new GenericXmlApplicationContext("SpringConfig.xml");
        mongoOperation = (MongoOperations)ctx.getBean("mongoTemplate");
    }

}
