package com.coors.ibikego;

/**
 * Created by user on 2016/8/22.
 */
public class RanPwd {
    private String str="";
    public String RanPwd(int length){
        for (int i = 0; i < length; i++) {    //產生長度 8 的密碼   長度字改
            int ran =(int)((Math.random() * 3));
            if(ran==0){
                this.str +=(char)((Math.random() * 10) +48);    //數字
            }else if(ran==1){
                this.str += (char) (((Math.random() * 26) + 65));    //大寫英文
            }else{
                this.str += ((char) ((Math.random() * 26) + 97));    //小寫英文
            }
        }
        return str;
    }
}
