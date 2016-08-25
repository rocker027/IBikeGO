package com.coors.ibikego.daovo;

import java.io.Serializable;
import java.sql.Date;

/**
 * Created by user on 2016/8/25.
 */
public class SqlSearchVO implements Serializable {
    private Integer tra_no;
    private java.sql.Date tra_cre;
    private String tra_name;
    private String tra_content;
    private String tra_tel;
    private String tra_add;
    private Double tra_lati;
    private Double tra_longi;
    private Integer mem_no;
    private String mem_name;
    private Integer blog_no;
    private java.sql.Date blog_cre;
    private String blog_title;
    private String blog_content;

    public Integer getTra_no() {
        return tra_no;
    }

    public void setTra_no(Integer tra_no) {
        this.tra_no = tra_no;
    }

    public String getTra_name() {
        return tra_name;
    }

    public void setTra_name(String tra_name) {
        this.tra_name = tra_name;
    }

    public String getTra_content() {
        return tra_content;
    }

    public void setTra_content(String tra_content) {
        this.tra_content = tra_content;
    }

    public Double getTra_lati() {
        return tra_lati;
    }

    public void setTra_lati(Double tra_lati) {
        this.tra_lati = tra_lati;
    }

    public Double getTra_longi() {
        return tra_longi;
    }

    public void setTra_longi(Double tra_longi) {
        this.tra_longi = tra_longi;
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

    public Integer getBlog_no() {
        return blog_no;
    }

    public void setBlog_no(Integer blog_no) {
        this.blog_no = blog_no;
    }

    public String getBlog_title() {
        return blog_title;
    }

    public void setBlog_title(String blog_title) {
        this.blog_title = blog_title;
    }

    public String getBlog_content() {
        return blog_content;
    }

    public void setBlog_content(String blog_content) {
        this.blog_content = blog_content;
    }

    public Date getBlog_cre() {
        return blog_cre;
    }

    public void setBlog_cre(Date blog_cre) {
        this.blog_cre = blog_cre;
    }

    public Date getTra_cre() {
        return tra_cre;
    }

    public void setTra_cre(Date tra_cre) {
        this.tra_cre = tra_cre;
    }

    public String getTra_tel() {
        return tra_tel;
    }

    public void setTra_tel(String tra_tel) {
        this.tra_tel = tra_tel;
    }

    public String getTra_add() {
        return tra_add;
    }

    public void setTra_add(String tra_add) {
        this.tra_add = tra_add;
    }

}
