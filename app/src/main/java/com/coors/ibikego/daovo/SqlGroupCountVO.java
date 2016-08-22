package com.coors.ibikego.daovo;

import java.io.Serializable;

/**
 * Created by user on 2016/8/22.
 */
public class SqlGroupCountVO implements Serializable{
    private Integer groupbike_no;
    private Integer count;

    public Integer getGroupbike_no() {
        return groupbike_no;
    }

    public void setGroupbike_no(Integer groupbike_no) {
        this.groupbike_no = groupbike_no;
    }

    public Integer getCount() {
        return count;
    }

    public void setCount(Integer count) {
        this.count = count;
    }
}
