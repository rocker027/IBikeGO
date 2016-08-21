package com.coors.ibikego.daovo;

import java.io.Serializable;
import java.sql.Timestamp;

/**
 * Created by user on 2016/8/21.
 */
public class SqlGroupDeatilsVO implements Serializable {
    private Integer mem_no;
    private String mem_name;
    private Double Group_lat;
    private Double Group_lng;
    private java.sql.Timestamp Group_update_time;

    public Integer getMem_no() {
        return mem_no;
    }

    public void setMem_no(Integer mem_no) {
        this.mem_no = mem_no;
    }

    public String getMem_name() {
        return mem_name;
    }

    public void setMem_name(String mem_name) {
        this.mem_name = mem_name;
    }

    public Double getGroup_lat() {
        return Group_lat;
    }

    public void setGroup_lat(Double group_lat) {
        Group_lat = group_lat;
    }

    public Double getGroup_lng() {
        return Group_lng;
    }

    public void setGroup_lng(Double group_lng) {
        Group_lng = group_lng;
    }

    public Timestamp getGroup_update_time() {
        return Group_update_time;
    }

    public void setGroup_update_time(Timestamp group_update_time) {
        Group_update_time = group_update_time;
    }
}
