package com.mtp.DAO;


import com.mtp.Model.StaticLocation;

import java.util.List;

public class StaticLocationDao {

    public static Boolean isEmptyTable(){
        return StaticLocation.listAll(StaticLocation.class).size() == 0;
    }

    public static List<StaticLocation> getAll() {
        return StaticLocation.listAll(StaticLocation.class);
    }

    public static void insert(StaticLocation staticLocation){
        staticLocation.save();
    }

    public static Boolean existStaticLocation(StaticLocation staticLocation){
        List<StaticLocation> exitStaticLocation = StaticLocation.findWithQuery(StaticLocation.class, "Select * from static_location where name = ?", staticLocation.getName());
        return exitStaticLocation.size() > 0;
    }
}
