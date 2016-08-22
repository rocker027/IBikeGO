package com.coors.ibikego.daovo;

import java.io.Serializable;
import java.sql.Date;

/**
 * Created by user on 2016/8/22.
 */
public class GroupDetailsVO implements Serializable {
    private Integer groupdetails;
    private Integer groupbike_no;
    private Integer mem_no;
    private Double group_lat;
    private Double group_lng;
    private java.sql.Date group_update_time;

    public Integer getGroupdetails() {
        return groupdetails;
    }

    public void setGroupdetails(Integer groupdetails) {
        this.groupdetails = groupdetails;
    }

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
}
