package com.mtp.DAO;


import com.mtp.Model.EventStatic;
import com.mtp.Model.EventStatic;

import java.util.List;

public class EventStaticDao {

    public static Boolean isEmptyTable(){
        return EventStatic.listAll(EventStatic.class).size() == 0;
    }

    public static List<EventStatic> getAll() {
        return EventStatic.listAll(EventStatic.class);
    }

    public static void insert(EventStatic EventStatic){
        EventStatic.save();
    }

    public static Boolean exist(EventStatic EventStatic){
        List<EventStatic> exitEventStatic = EventStatic.findWithQuery(EventStatic.class, "Select * from event_static where name = ?", EventStatic.getName());
        return exitEventStatic.size() > 0;
    }
}
