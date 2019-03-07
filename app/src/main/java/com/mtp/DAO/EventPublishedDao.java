package com.mtp.DAO;


import com.mtp.Model.EventPublished;

import java.util.List;

public class EventPublishedDao {

    public static Boolean isEmptyTable(){
        return EventPublished.listAll(EventPublished.class).size() == 0;
    }

    public static List<EventPublished> getAll() {
        return EventPublished.listAll(EventPublished.class);
    }

    public static void insert(EventPublished EventPublished){
        EventPublished.save();
    }

    public static Boolean exist(EventPublished EventPublished){
        List<EventPublished> exitEventPublished = EventPublished.findWithQuery(EventPublished.class, "Select * from event_published where name = ?", EventPublished.getName());
        return exitEventPublished.size() > 0;
    }
}
