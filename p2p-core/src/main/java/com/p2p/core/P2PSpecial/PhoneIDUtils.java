package com.p2p.core.P2PSpecial;

import android.content.Context;
import android.telephony.TelephonyManager;
import android.text.TextUtils;

import org.json.JSONException;
import org.json.JSONObject;

import java.io.BufferedReader;
import java.io.FileInputStream;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.LineNumberReader;
import java.io.OutputStream;
import java.io.UnsupportedEncodingException;
import java.security.NoSuchAlgorithmException;
import java.util.UUID;

/**
 * Created by dxs on 2016/11/28.
 */

public class PhoneIDUtils {
    private static class PhoneIDUtilsHolder {
        private static final PhoneIDUtils INSTANCE = new PhoneIDUtils();
    }

    private PhoneIDUtils() {
    }

    public static final PhoneIDUtils getInstance() {
        return PhoneIDUtilsHolder.INSTANCE;
    }

    public String getRealPhoneID(Context mContext,String pakageName) throws NoSuchAlgorithmException, UnsupportedEncodingException {
        PhoneInfoBean info=getRealPhoneInfoFromeSD(mContext);
        if(info!=null&&(!TextUtils.isEmpty(info.getPhoneid()))){
            return info.getPhoneid();
        }
        String PhoneID=getPhoneID(mContext);
        info=new PhoneInfoBean();
        info.setPhoneid(PhoneID);
        WriteRealPhoneIDToSD(mContext,info);
        return PhoneID;
    }

    private String getPhoneID(Context mContext){
        String PhoneID=getIMEI(mContext)+getMac();
        if(TextUtils.isEmpty(PhoneID)){
            return getUUID();
        }
        return PhoneID;
    }

    private String getIMEI(Context context) {
        TelephonyManager tm = (TelephonyManager) context.getSystemService(Context.TELEPHONY_SERVICE);
        if(tm==null){
            return "";
        }
        return tm.getDeviceId()==null?"":tm.getDeviceId();
    }

    private String getMac() {
        String macSerial = null;
        String str = "";
        try {
            Process pp = Runtime.getRuntime().exec("cat /sys/class/net/wlan0/address ");
            InputStreamReader ir = new InputStreamReader(pp.getInputStream());
            LineNumberReader input = new LineNumberReader(ir);
            for (; null != str; ) {
                str = input.readLine();
                if (str != null) {
                    macSerial = str.trim();
                }
            }
        } catch (IOException ex) {
            // 赋予默认值
            ex.printStackTrace();
        }
        return macSerial;
    }

    private String getUUID() {
        return UUID.randomUUID().toString();
    }

    private PhoneInfoBean getRealPhoneInfoFromeSD(Context mContext){
        String PhoneInfoStr=ReadFromeSD(mContext,P2PSpecial.ID_PATH);
        PhoneInfoBean PhoneInfo=null;
        if(TextUtils.isEmpty(PhoneInfoStr)){
            return null;
        }
        try {
            JSONObject person = new JSONObject(PhoneInfoStr);
            PhoneInfo=new PhoneInfoBean(person);
        } catch (JSONException e) {
            e.printStackTrace();
        }
        return PhoneInfo;
    }

    private void WriteRealPhoneIDToSD(Context mContext,PhoneInfoBean phoneInfo){
        String json=phoneInfo.getJSONString();
        if(!TextUtils.isEmpty(json)){
            saveToSDCard(mContext,P2PSpecial.ID_PATH,json);
        }
    }

    public void saveToSDCard(Context mContext,String filename,String content){
        OutputStream out=null;
        try {
            out=mContext.openFileOutput(filename,Context.MODE_PRIVATE);
            out.write(content.getBytes());
        } catch (IOException e) {
            e.printStackTrace();
        }finally {
            if(out!=null){
                try {
                    out. close();
                } catch (IOException e) {
                    e.printStackTrace();
                }
            }

        }
    }

    public String ReadFromeSD(Context mContext,String filename){
        try {
            FileInputStream inStream = mContext.openFileInput(filename);
            BufferedReader br = new BufferedReader(new InputStreamReader(inStream));
            String readline = "";
            StringBuffer sb = new StringBuffer();
            while ((readline = br.readLine()) != null) {
                sb.append(readline);
            }
            br.close();
            return sb.toString();
        } catch (Exception e) {
            e.printStackTrace();
        }
        return null;
    }
}
