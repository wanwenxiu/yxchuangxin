package com.p2p.core.P2PSpecial;

import org.json.JSONException;
import org.json.JSONObject;
import org.json.JSONStringer;


/**
 * Created by dxs on 2016/11/29.
 */

public class PhoneInfoBean {
    private String phoneid;

    public PhoneInfoBean(){
    }

    public PhoneInfoBean(JSONObject json){
        initPhoneInfo(json);
    }

    private void initPhoneInfo(JSONObject json) {
        try{
            phoneid=json.getString("phoneid");
        }catch(Exception e){
            e.printStackTrace();
        }
    }

    public String getJSONString(){
        JSONStringer jsonText = new JSONStringer();
        try {
            jsonText.object();
            jsonText.key("phoneid");
            jsonText.value(phoneid);
            jsonText.endObject();
            return jsonText.toString();
        } catch (JSONException e) {
            e.printStackTrace();
        }
        return null;
    }

    public String getPhoneid() {
        return phoneid;
    }

    public void setPhoneid(String phoneid) {
        this.phoneid = phoneid;
    }
}
