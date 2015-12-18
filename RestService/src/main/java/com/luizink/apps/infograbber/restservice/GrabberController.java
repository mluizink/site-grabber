package com.luizink.apps.infograbber.restservice;

import com.luizink.apps.infograbber.data.LotItem;
import com.luizink.apps.infograbber.data.LotItemsQueryManager;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.ArrayList;
import java.util.Calendar;
import java.util.List;

/**
 * Created by maurice on 18/12/15.
 */
@RestController
public class GrabberController {

    LotItemsQueryManager queryManager = new LotItemsQueryManager();

    ArrayList<LotItem> lotItems = new ArrayList<LotItem>();

    @RequestMapping("/searchBySubject")
    public LotItem[] searchBySubject(String subject) {
        Calendar toDate = Calendar.getInstance();
        toDate.add(Calendar.HOUR, 8);
        List<LotItem> resultLotItems = queryManager.findBySubjectAndClosingDate(subject, toDate.getTime());
        return resultLotItems.toArray(new LotItem[resultLotItems.size()]);
    }
}

