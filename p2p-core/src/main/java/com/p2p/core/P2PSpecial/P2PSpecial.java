package com.p2p.core.P2PSpecial;

import android.content.Context;
import android.text.TextUtils;

import com.p2p.core.P2PHandler;
import com.p2p.core.P2PInterface.IP2P;
import com.p2p.core.P2PInterface.ISetting;
import com.p2p.core.network.LoginResult;
import com.p2p.core.network.NetManager;
import com.p2p.core.utils.SharedPrefreUtils;

import java.io.UnsupportedEncodingException;
import java.security.NoSuchAlgorithmException;

/**
 * Created by dxs on 2016/11/28.
 */

public class P2PSpecial {
    private Context mContext;
    public final static String P2P_APPID="P2P_APPID";
    public final static String P2P_APPTOKEN="P2P_APPTOKEN";
    public final static String P2P_PHONEID="P2P_PHONEID";
    public final static String APP_VERSION="APP_VERSION";
    public final static String ID_PATH="jwkj.txt";
    private static class P2PSpecialHolder {
        private static final P2PSpecial INSTANCE = new P2PSpecial();
    }

    private P2PSpecial() {
    }

    public static final P2PSpecial getInstance() {
        return P2PSpecialHolder.INSTANCE;
    }

    public void init(Context mContext){
        this.mContext=mContext;
    }

    public void init(Context mContext, String AppID,String Token,String Version){
        init(mContext);
        saveAPPIDAndToken(AppID,Token,Version);
    }

    public void P2PConnect(IP2P ip2P, ISetting iSetting,P2PConnectCallBack callBack){
        P2PHandler.getInstance().p2pInit(mContext,ip2P,iSetting);
        String realPhoneID= SharedPrefreUtils.getInstance().getStringData(mContext,P2P_PHONEID);
        if(TextUtils.isEmpty(realPhoneID)){
            try {
                String PakageName=mContext.getPackageName();
                realPhoneID=getPhoneUUID(PakageName);
            } catch (NoSuchAlgorithmException e) {
                callBack.P2PConnectResult(false,null);
                e.printStackTrace();
                return;
            } catch (NullPointerException w){
                callBack.P2PConnectResult(false,null);
                w.printStackTrace();
                return;
            } catch (UnsupportedEncodingException e) {
                callBack.P2PConnectResult(false,null);
                e.printStackTrace();
            }
        }
        //3.登录获取code1、code2
        //4.p2p初始化
        Connect(realPhoneID,callBack);
    }

    /**
     * 存储传入的APPID与Token
     * @param AppID 服务器分配的APPID
     * @param Token 服务器分配的Token
     */
    private void saveAPPIDAndToken(String AppID,String Token,String Version){
        String APPID=SharedPrefreUtils.getInstance().getStringData(mContext,P2P_APPID);
        String TOKEN=SharedPrefreUtils.getInstance().getStringData(mContext,P2P_APPTOKEN);
        SharedPrefreUtils.getInstance().putStringData(mContext,APP_VERSION,Version);
        if(TextUtils.isEmpty(APPID)||(!APPID.equals(AppID))){
           SharedPrefreUtils.getInstance().putStringData(mContext,P2P_APPID,AppID);
        }
        if(TextUtils.isEmpty(TOKEN)||(!TOKEN.equals(Token))){
            SharedPrefreUtils.getInstance().putStringData(mContext,P2P_APPTOKEN,Token);
        }
    }

    /**
     * 获取手机唯一标识
     * @param pakageName 包名
     * @return 唯一标识
     * @throws NoSuchAlgorithmException
     */
    private String getPhoneUUID(String pakageName) throws NoSuchAlgorithmException, UnsupportedEncodingException {
        return PhoneIDUtils.getInstance().getRealPhoneID(mContext,pakageName);
    }

    private void Connect(final String userName,final P2PConnectCallBack P2PCallBack){
        new SkipLoginTask(mContext, new SkipLoginTask.LoginCallBack() {
            @Override
            public void onLoginResult(LoginResult result) {
                if(Integer.parseInt(result.error_code)== NetManager.LOGIN_SUCCESS){
                    int codeStr1 = (int) Long.parseLong(result.rCode1);
                    int codeStr2 = (int) Long.parseLong(result.rCode2);
                    boolean connectResult = P2PHandler.getInstance().p2pConnect(
                            result.contactId, codeStr1, codeStr2);
                    if(connectResult){
                        SharedPrefreUtils.getInstance().putStringData(mContext,P2P_PHONEID,userName);
                    }
                    P2PCallBack.P2PConnectResult(connectResult,result);
                }else{
                    P2PCallBack.P2PConnectResult(false,result);
                }
            }
        }).execute(userName,"");
    }

    public interface P2PConnectCallBack{
        void P2PConnectResult(boolean connectState, LoginResult result);
    }
}
