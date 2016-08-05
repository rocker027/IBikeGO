package com.coors.ibikego;

import java.io.Serializable;

/**
 * Created by cuser on 2016/7/24.
 */
public class TravelVO implements Serializable {
    private Integer tra_no;
    private Integer mem_no;
    private Integer loc_no;
    private Integer tra_class_no;

    public TravelVO(Integer tra_no, Integer mem_no, Integer loc_no, Integer tra_class_no) {
        this.tra_no = tra_no;
        this.mem_no = mem_no;
        this.loc_no = loc_no;
        this.tra_class_no = tra_class_no;
    }

    public Integer getTra_no() {
        return tra_no;
    }

    public void setTra_no(Integer tra_no) {
        this.tra_no = tra_no;
    }

    public Integer getMem_no() {
        return mem_no;
    }

    public void setMem_no(Integer mem_no) {
        this.mem_no = mem_no;
    }

    public Integer getLoc_no() {
        return loc_no;
    }

    public void setLoc_no(Integer loc_no) {
        this.loc_no = loc_no;
    }

    public Integer getTra_class_no() {
        return tra_class_no;
    }

    public void setTra_class_no(Integer tra_class_no) {
        this.tra_class_no = tra_class_no;
    }
}
