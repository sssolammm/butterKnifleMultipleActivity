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
}
