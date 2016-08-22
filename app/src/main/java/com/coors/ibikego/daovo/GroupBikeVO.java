package com.coors.ibikego.daovo;

import java.io.Serializable;
import java.sql.Date;

/**
 * Created by user on 2016/8/22.
 */
public class GroupBikeVO implements Serializable {
    private Integer groupbike_no;
    private Integer mem_no;
    private String groupbike_key;
    private java.sql.Date groupbike_cre;

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

    public String getGroupbike_key() {
        return groupbike_key;
    }

    public void setGroupbike_key(String groupbike_key) {
        this.groupbike_key = groupbike_key;
    }

    public Date getGroupbike_cre() {
        return groupbike_cre;
    }

    public void setGroupbike_cre(Date groupbike_cre) {
        this.groupbike_cre = groupbike_cre;
    }
}
