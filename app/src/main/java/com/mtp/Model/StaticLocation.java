package com.mtp.Model;

import com.orm.SugarRecord;
import com.orm.dsl.Column;
import com.orm.dsl.Table;

@Table(name="static_location")
public class StaticLocation extends SugarRecord {

    @Column(name="name")
    private String name;

    @Column(name="longitude")
    private Double longitude;

    @Column(name="latitude")
    private Double latitude;

    public StaticLocation() { }

    public StaticLocation(String name, Double longitude, Double latitude){
        this.name = name;
        this.longitude = longitude;
        this.latitude = latitude;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public Double getLongitude() {
        return longitude;
    }

    public void setLongitude(Double longitude) {
        this.longitude = longitude;
    }

    public Double getLatitude() {
        return latitude;
    }

    public void setLatitude(Double latitude) {
        this.latitude = latitude;
    }
}
