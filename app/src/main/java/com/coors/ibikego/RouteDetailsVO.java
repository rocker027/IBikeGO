package com.coors.ibikego;

public class RouteDetailsVO implements java.io.Serializable {
    private Integer route_det_no;
    private Integer route_no;
    private Double route_det_lati;
    private Double route_det_longi;
    private Float route_det_speed;
    private Double route_det_altitude;
    private Long route_det_time;

    public Integer getRoute_det_no() {
        return route_det_no;
    }

    public void setRoute_det_no(Integer route_det_no) {
        this.route_det_no = route_det_no;
    }

    public Integer getRoute_no() {
        return route_no;
    }

    public void setRoute_no(Integer route_no) {
        this.route_no = route_no;
    }

    public Double getRoute_det_lati() {
        return route_det_lati;
    }

    public void setRoute_det_lati(Double route_det_lati) {
        this.route_det_lati = route_det_lati;
    }

    public Double getRoute_det_longi() {
        return route_det_longi;
    }

    public void setRoute_det_longi(Double route_det_longi) {
        this.route_det_longi = route_det_longi;
    }

    public Float getRoute_det_speed() {
        return route_det_speed;
    }

    public void setRoute_det_speed(Float route_det_speed) {
        this.route_det_speed = route_det_speed;
    }

    public Double getRoute_det_altitude() {
        return route_det_altitude;
    }

    public void setRoute_det_altitude(Double route_det_altitude) {
        this.route_det_altitude = route_det_altitude;
    }

    public Long getRoute_det_time() {
        return route_det_time;
    }

    public void setRoute_det_time(Long route_det_time) {
        this.route_det_time = route_det_time;
    }
}
