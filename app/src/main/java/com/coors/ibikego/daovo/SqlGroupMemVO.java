package com.coors.ibikego.daovo;

import java.io.Serializable;
import java.sql.Date;

/**
 * Created by user on 2016/8/23.
 */
public class SqlGroupMemVO implements Serializable {

    private Integer groupdetails_no;
    private Integer groupbike_no;
    private Integer mem_no;
    private String mem_name;
    private String groupbike_key;
    private Double group_lat;
    private Double group_lng;
    private java.sql.Date group_update_time;

    public Integer getGroupbike_no() {
        return groupbike_no;
    }

    public void setGroupbike_no(Integer groupbike_no) {
        this.groupbike_no = groupbike_no;
    }

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

    public String getGroupbike_key() {
        return groupbike_key;
    }

    public void setGroupbike_key(String groupbike_key) {
        this.groupbike_key = groupbike_key;
    }

    public Double getGroup_lat() {
        return group_lat;
    }

    public void setGroup_lat(Double group_lat) {
        this.group_lat = group_lat;
    }

    public Double getGroup_lng() {
        return group_lng;
    }

    public void setGroup_lng(Double group_lng) {
        this.group_lng = group_lng;
    }

    public Date getGroup_update_time() {
        return group_update_time;
    }

    public void setGroup_update_time(Date group_update_time) {
        this.group_update_time = group_update_time;
    }

    public Integer getGroupdetails_no() {
        return groupdetails_no;
    }

    public void setGroupdetails_no(Integer groupdetails_no) {
        this.groupdetails_no = groupdetails_no;
    }

}
