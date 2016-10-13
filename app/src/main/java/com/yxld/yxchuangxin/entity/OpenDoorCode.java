package com.yxld.yxchuangxin.entity;

import java.io.Serializable;

/**
 * wwx手机开门获取二维码构建对象
 */
public class OpenDoorCode {
    private OpenDoorCode code;

    private String str; //二维码
    private String shijian; //时间

    public OpenDoorCode() {
    }

    public OpenDoorCode getCode() {
        return code;
    }

    public void setCode(OpenDoorCode code) {
        this.code = code;
    }

    public String getStr() {
        return str;
    }

    public void setStr(String str) {
        this.str = str;
    }

    public String getShijian() {
        return shijian;
    }

    public void setShijian(String shijian) {
        this.shijian = shijian;
    }

    @Override
    public String toString() {
        return "OpenDoorCode{" +
                "code=" + code +
                ",str='" + str + '\'' +
                ", shijian='" + shijian + '\'' +
                '}';
    }
}
