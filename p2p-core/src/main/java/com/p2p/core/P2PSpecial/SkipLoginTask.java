package com.p2p.core.P2PSpecial;

import android.content.Context;
import android.os.AsyncTask;
import android.util.Log;

import com.p2p.core.network.LoginResult;
import com.p2p.core.network.NetManager;

import org.json.JSONObject;

import java.io.UnsupportedEncodingException;
import java.security.NoSuchAlgorithmException;


/**
 * Created by dxs on 2016/11/29.
 */

public class SkipLoginTask extends AsyncTask<String,Void,JSONObject> {
    private Context mContext;
    private LoginCallBack callBack;
    private String[] loginParams=new String[2];
    public SkipLoginTask(Context mcontext, LoginCallBack callBack){
        this.mContext=mcontext;
        this.callBack=callBack;
    }

    @Override
    protected JSONObject doInBackground(String... params) {
        loginParams[0]=params[0];
        try {
            return NetManager.getInstance(mContext).ThirdLogin("3",params[0],"","","","2");
        } catch (UnsupportedEncodingException e) {
            e.printStackTrace();
        } catch (NoSuchAlgorithmException e) {
            e.printStackTrace();
        }
        return null;
    }

    @Override
    protected void onPostExecute(JSONObject jsonObject) {
        if(jsonObject==null&&callBack!=null){
            callBack.onLoginResult(null);
        }
        LoginResult result = NetManager.createLoginResult(jsonObject);
        switch (Integer.parseInt(result.error_code)){
            //需要处理的错误码
            case NetManager.CONNECT_CHANGE:
                new SkipLoginTask(mContext,callBack).execute(loginParams[0],"","","","2");
                break;
            //不需要处理的错误码
            default:
                callBack.onLoginResult(result);
                break;
        }

    }

    public interface LoginCallBack{
        void onLoginResult(LoginResult result);
    }
}
