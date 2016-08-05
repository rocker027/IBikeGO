package com.coors.ibikego;

import java.io.Serializable;

/**
 * Created by cuser on 2016/7/25.
 */
public class RouteVO implements Serializable {
    private Integer route_no;
    private Integer mem_no;
    private Integer blog_no;
    private String route_name;
    private java.sql.Date route_cre;
    private java.sql.Date route_start;
    private java.sql.Date route_stop;

    public Integer getRoute_no()
    {
        return route_no;
    }
    public void setRoute_no(Integer route_no)
    {
        this.route_no = route_no;
    }
    public Integer getMem_no()
    {
        return mem_no;
    }
    public void setMem_no(Integer mem_no)
    {
        this.mem_no = mem_no;
    }
    public Integer getBlog_no()
    {
        return blog_no;
    }
    public void setBlog_no(Integer blog_no)
    {
        this.blog_no = blog_no;
    }
    public String getRoute_name()
    {
        return route_name;
    }
    public void setRoute_name(String route_name)
    {
        this.route_name = route_name;
    }
    public java.sql.Date getRoute_cre()
    {
        return route_cre;
    }
    public void setRoute_cre(java.sql.Date route_cre)
    {
        this.route_cre = route_cre;
    }
    public java.sql.Date getRoute_start()
    {
        return route_start;
    }
    public void setRoute_start(java.sql.Date route_start)
    {
        this.route_start = route_start;
    }
    public java.sql.Date getRoute_stop()
    {
        return route_stop;
    }
    public void setRoute_stop(java.sql.Date route_stop)
    {
        this.route_stop = route_stop;
    }

}