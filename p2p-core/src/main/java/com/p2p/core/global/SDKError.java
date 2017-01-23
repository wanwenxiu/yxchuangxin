package com.p2p.core.global;

/**
 * Created by dxs on 16/12/21.
 * 服务器定义的反馈码规范
 * 在程序内部可能出错，但又不可以向用户解释的地方需要在此定义
 */
//0011\00000001
public class SDKError {
    //0011\00000001\00000001
    public class Web{
        //0011\00000001\00000001\000000000001
        public final static String JSONERROR="30101001";//JSON解析错误
        public final static String SERVERERROR="30101002";//没有服务器可以连通
    }
    //0011\00000001\00000010
    public class P2P{
        public final static String RTSPNOFRAME="30102001";//RTSP没有图像
    }
    //0011\00000001\00000011
    public class APP{
        public final static String MAINSERVER="30103001";//主服务没有启动
        public final static String RECORDINITERROR="30103002";//录像初始化失败
        public final static String WECHATINFORERROR="30103003";//微信信息异常
        public final static String USERINFORERROR="30103004";//用户信息异常
    }

}
