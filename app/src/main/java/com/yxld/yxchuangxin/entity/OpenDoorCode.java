package com.yxld.yxchuangxin.entity;

import java.io.Serializable;

/**
 * wwx手机开门获取二维码构建对象
 */
public class OpenDoorCode {

    private String code; //二维码
    private String time; //时间
    private String state;//状态 0 成功 -1 失败

    public OpenDoorCode() {
    }

    public String getCode() {
        return code;
    }

    public void setCode(String code) {
        this.code = code;
    }

    public String getTime() {
        return time;
    }

    public void setTime(String time) {
        this.time = time;
    }

    public String getState() {
        return state;
    }
    public void setState(String state) {
        this.state = state;
    }

    @Override
    public String toString() {
        return "OpenDoorCode{" +
                "code='" + code + '\'' +
                ", time='" + time + '\'' +
                ", state='" + state + '\'' +
                '}';
    }
}
