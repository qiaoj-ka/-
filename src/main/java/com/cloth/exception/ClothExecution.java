package com.cloth.exception;

import com.cloth.entity.ClothList;

public class ClothExecution {

    private boolean flag;

    private ClothList clothList;

    private String reason;

    //失败
    public ClothExecution(boolean flag, String reason){
        this.flag = flag;
        this.reason = reason;
    }

    //成功返回User
    public ClothExecution(boolean flag, ClothList clothList){
        this.flag = flag;
        this.clothList = clothList;
    }

    //成功不返回
    public ClothExecution(boolean flag){
        this.flag = flag;
    }

    public boolean isFlag() {
        return flag;
    }

    public void setFlag(boolean flag) {
        this.flag = flag;
    }

    public ClothList getHouseList() {
        return clothList;
    }

    public void setHouseList(ClothList clothList) {
        this.clothList = clothList;
    }

    public String getReason() {
        return reason;
    }

    public void setReason(String reason) {
        this.reason = reason;
    }
}
