package com.p2p.core.network;

import android.util.Log;

import com.p2p.core.utils.MyUtils;

import org.json.JSONObject;

import java.io.Serializable;

//微信第三方登录返回结果处理，与登录稍有区别
public class ThirdPartyLoginResult implements Serializable {
    public String contactId;
    public String rCode1;
    public String rCode2;
    public String phone;
    public String email;
    public String sessionId;
    public String countryCode;
    public String error_code;
    public String imageId;
    public String userlevel;
    public String autoId;

    public ThirdPartyLoginResult(JSONObject json) {
        init(json);
    }

    private void init(JSONObject json) {
        try {
            error_code = json.getString("error_code");
            contactId = json.getString("UserID");
            rCode1 = json.getString("P2PVerifyCode1");
            rCode2 = json.getString("P2PVerifyCode2");
            phone = json.getString("PhoneNO");
            email = json.getString("Email");
            sessionId = json.getString("SessionID");
            countryCode = json.getString("CountryCode");
            imageId = json.getString("ImageID");
            userlevel = json.getString("UserLevel");
            autoId = json.getString("AutoAllotID");
            try {
                contactId = "0" + String.valueOf((Integer.parseInt(contactId) & 0x7fffffff));
            } catch (Exception e) {

            }
        } catch (Exception e) {
            contactId = "";
            rCode1 = "";
            rCode2 = "";
            phone = "";
            email = "";
            sessionId = "";
            countryCode = "";
            imageId = "";
            userlevel = "";
            autoId = "";
            if (!MyUtils.isNumeric(error_code)) {
                Log.e("my", "ThirdPartyLoginResult json解析错误");
                error_code = String.valueOf(NetManager.JSON_PARSE_ERROR);
            }
        }
    }

}
