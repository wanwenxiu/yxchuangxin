package com.yxld.yxchuangxin.entity;

import java.util.ArrayList;

/**
 * 作者：yishangfei on 2017/1/20 0020 14:15
 * 邮箱：yishangfei@foxmail.com
 */
//当然你也可以定义包含成员变量的事件, 用来传递参数
public class MsgEvent {
    public ArrayList<int[]> data;
    public int result;
    public MsgEvent(ArrayList<int[]> data, int result) {
        this.data = data;
        this.result = result;
    }
}