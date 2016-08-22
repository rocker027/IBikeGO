package com.coors.ibikego.daovo;

import java.io.Serializable;
import java.sql.Date;

/**
 * Created by cuser on 2016/7/23.
 */
public class BlogVO implements Serializable {
    private Integer blog_no;
    private Integer mem_no;
    private String blog_title;
    private String blog_content;
    private java.sql.Date blog_cre;
    private Integer blog_del;
//    private int imageId;

    public BlogVO(Integer blog_no, Integer mem_no, String blog_title, String blog_content, Date blog_cre, Integer blog_del) {
        this.blog_no = blog_no;
        this.mem_no = mem_no;
        this.blog_title = blog_title;
        this.blog_content = blog_content;
        this.blog_cre = blog_cre;
        this.blog_del = blog_del;
//        this.imageId = imageId;
    }

    public Integer getBlog_no() {
        return blog_no;
    }

    public void setBlog_no(Integer blog_no) {
        this.blog_no = blog_no;
    }

    public Integer getMem_no() {
        return mem_no;
    }

    public void setMem_no(Integer mem_no) {
        this.mem_no = mem_no;
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

    public java.sql.Date getBlog_cre() {
        return blog_cre;
    }

    public void setBlog_cre(java.sql.Date blog_cre) {
        this.blog_cre = blog_cre;
    }

    public Integer getBlog_del() {
        return blog_del;
    }

    public void setBlog_del(Integer blog_del) {
        this.blog_del = blog_del;
    }

//    public int getImageId() {
//        return imageId;
//    }
//
//    public void setImageId(int imageId) {
//        this.imageId = imageId;
//    }
}
