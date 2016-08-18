package com.coors.ibikego;

import java.text.SimpleDateFormat;
import java.util.Date;

/**
 * Created by cuser on 2016/8/18.
 */
public class GetStringDate {

    public String getDate_cre(){
        //先行定義時間格式
        SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
        //取得現在時間
        Date dt = new Date();
        //透過SimpleDateFormat的format方法將Date轉為字串

        return sdf.format(dt);
    }

}
