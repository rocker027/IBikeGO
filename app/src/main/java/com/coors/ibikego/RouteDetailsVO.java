package com.coors.ibikego;

public class RouteDetailsVO implements java.io.Serializable {
    private Integer route_det_no;
    private Integer route_no;
    private Double route_det_lati;
    private Double route_det_longi;

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

}
