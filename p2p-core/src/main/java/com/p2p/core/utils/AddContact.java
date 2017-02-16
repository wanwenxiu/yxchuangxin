package com.p2p.core.utils;

import android.text.TextUtils;

import com.p2p.core.P2PValue;

/**
 * Created by USER on 2017/1/12.
 */

public class AddContact {
    private byte[] Message;
    private int contatcId;
    private int SubType;
    private int rflag;
    private int type;
    private int frag;
    private int curVersion;
    private String ipAddress;
    private int IPFlag;

    public AddContact(byte[] message, String ipAddress) {
        this.Message=message;
        PaserMessage(message);
        this.ipAddress=ipAddress;
    }

    private void PaserMessage(byte[] message){
        SubType = P2PValue.subType.UNKOWN;
        contatcId = MyUtils.bytesToInt(message, 16);
        rflag = MyUtils.bytesToInt(message, 12);
        type = MyUtils.bytesToInt(message, 20);
        frag = MyUtils.bytesToInt(message, 24);
        curVersion = (rflag >> 4) & 0x1;
        if (curVersion == 1) {
            SubType = MyUtils.bytesToInt(message, 80);
        }
    }

    public int getContatcId() {
        return contatcId;
    }

    public void setContatcId(int contatcId) {
        this.contatcId = contatcId;
    }

    public int getSubType() {
        return SubType;
    }

    public void setSubType(int subType) {
        SubType = subType;
    }

    public int getRflag() {
        return rflag;
    }

    public void setRflag(int rflag) {
        this.rflag = rflag;
    }

    public int getType() {
        return type;
    }

    public void setType(int type) {
        this.type = type;
    }

    public int getFrag() {
        return frag;
    }

    public void setFrag(int frag) {
        this.frag = frag;
    }

    public int getCurVersion() {
        return curVersion;
    }

    public void setCurVersion(int curVersion) {
        this.curVersion = curVersion;

    }

    public String getIpAddress() {
        return ipAddress;
    }

    public void setIpAddress(String ipAddress) {
        this.ipAddress = ipAddress;
    }

    public int getIPFlag() {
        try {
            if(!TextUtils.isEmpty(ipAddress)){
                String ip=ipAddress.substring(
                        ipAddress.lastIndexOf(".") + 1,
                        ipAddress.length());
                IPFlag=Integer.parseInt(ip);
            }
        } catch (NumberFormatException e) {
            e.printStackTrace();
        }
        return IPFlag;
    }

    public void setIPFlag(int IPFlag) {
        this.IPFlag = IPFlag;
    }

    @Override
    public String toString() {
        return "AddContact{" +
                "IPFlag=" + IPFlag +
                ", ipAddress='" + ipAddress + '\'' +
                ", curVersion=" + curVersion +
                ", frag=" + frag +
                ", type=" + type +
                ", rflag=" + rflag +
                ", SubType=" + SubType +
                ", contatcId=" + contatcId +
                '}';
    }
}
