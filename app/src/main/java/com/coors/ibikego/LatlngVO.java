package com.coors.ibikego;

import java.io.Serializable;

/**
 * Created by cuser on 2016/8/17.
 */
public class LatlngVO implements Serializable{
    private Double lat;
    private Double lng;
    private Double altitude;
    private Float speed;
    private Long time;
    private Integer mem_no;
    private Integer blog_no;
    private String route_time;

    public Double getLat() {
        return lat;
    }

    public void setLat(Double lat) {
        this.lat = lat;
    }

    public Double getLng() {
        return lng;
    }

    public void setLng(Double lng) {
        this.lng = lng;
    }

    public Double getAltitude() {
        return altitude;
    }

    public void setAltitude(Double altitude) {
        this.altitude = altitude;
    }

    public Float getSpeed() {
        return speed;
    }

    public void setSpeed(Float speed) {
        this.speed = speed;
    }

    public Long getTime() {
        return time;
    }

    public void setTime(Long time) {
        this.time = time;
    }

    public Integer getMem_no() {
        return mem_no;
    }

    public void setMem_no(Integer mem_no) {
        this.mem_no = mem_no;
    }

    public Integer getBlog_no() {
        return blog_no;
    }

    public void setBlog_no(Integer blog_no) {
        this.blog_no = blog_no;
    }

    public String getRoute_time() {
        return route_time;
    }

    public void setRoute_time(String route_time) {
        this.route_time = route_time;
    }
}
