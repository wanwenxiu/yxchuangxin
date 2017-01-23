package com.p2p.core.utils;

import android.content.Context;
import android.net.wifi.WifiManager;
import android.os.Bundle;
import android.os.Handler;
import android.os.Message;
import android.util.Log;

import java.io.IOException;
import java.lang.ref.WeakReference;
import java.net.DatagramPacket;
import java.net.DatagramSocket;
import java.net.InetAddress;
import java.net.MulticastSocket;
import java.net.SocketException;
import java.util.Arrays;


public class UDPHander {
    private final static String UDPLOCK="UDPLOCK";
    private static UDPHander UDPc = null;
    MulticastSocket datagramSocket = null;
    Handler handler;
    byte[] data;
    int port;
    String ipAdress;
    SendThread sendThread;
    boolean isSend=false;
    String contactId;
    boolean isReceive=false;
    int Count=0;
    public final static int REPLAY_DEVICE_SUCCESS=100;
    private WeakReference<Context> mActivityReference;
    private WifiManager.MulticastLock lock;
    private boolean isListn=false;
    private UDPHander(Context mContext) {
        mActivityReference = new WeakReference<>(mContext);
        WifiManager manager= (WifiManager) mActivityReference.get().getSystemService(Context.WIFI_SERVICE);
        lock=manager.createMulticastLock(UDPLOCK);
    }
    public synchronized static UDPHander getInstance(Context mContext) {
        if (null == UDPc) {
            synchronized (UDPHander.class) {
                UDPc = new UDPHander(mContext.getApplicationContext());
            }
        }
        return UDPc;
    }

    public void setHandler(Handler handler) {
        this.handler = handler;
    }

    public void send(byte[] messgae, int port, String ip,String contactId) throws Exception {
        this.data=messgae;
        this.port=port;
        this.ipAdress=ip;
        this.contactId=contactId;
        openThread();
    }
    public void sendReceive(byte[] messgae, int port, String ip,String contactId)throws Exception{

    }


    public void startListner(final int port) {
        new Thread() {
            @Override
            public void run() {
                listner(port);
            }
        }.start();
    }

    private void listner(int port) {
        // UDP服务器监听的端口
        // 接收的字节大小，客户端发送的数据不能超过这个大小
        byte[] message = new byte[512];
        isListn=true;
        try {
            // 建立Socket连接
            datagramSocket = new MulticastSocket(port);
            //InetAddress group=InetAddress.getByName("255.255.255.255");//设备端的广播地址
            //datagramSocket.joinGroup(group);
            datagramSocket.setBroadcast(true);
            datagramSocket.setLoopbackMode(true);//不接受自己发的包
            DatagramPacket datagramPacket = new DatagramPacket(message,message.length);
                while (isListn) {
                    // 准备接收数据
                    MulticastLock();
                    datagramSocket.receive(datagramPacket);
                    int cmd = MyUtils.bytesToInt(message, 0);
                    int result=MyUtils.bytesToInt(message,4);
                    int deviceId=MyUtils.bytesToInt(message,16);
                    if(cmd==17&&contactId.equals(String.valueOf(deviceId))){
                        if(result==0||result==255){
                            Message msg=new Message();
                            msg.what=cmd;
                            Bundle bundle=new Bundle();
                            bundle.putInt("result",result);
                            bundle.putString("deviceId",String.valueOf(deviceId));
                            msg.setData(bundle);
                            handler.sendMessage(msg);
                        }
                        if(result==0){
                            isSend=false;
                            isReceive=true;
                        }
                    }
                    MulticastUnLock();
                    Log.e("dxsUDP", "cmd=" + cmd + "result="+result+"deviceId="+deviceId+"mesg=" + Arrays.toString(message));
                    AddContact contact=new AddContact(message,datagramPacket.getAddress().getHostAddress());
                    Log.e("dxsTest","contact"+contact.toString());
                }
        } catch (IOException e) {
            Log.e("dxsUDP", "IOException"+e.toString()+"-msg:"+e.getMessage());
            e.printStackTrace();
        }finally {
            MulticastUnLock();
            StopListen();
        }
    }

    private void MulticastLock(){
        if(this.lock!=null){
            try {
                this.lock.acquire();
            } catch (Exception e) {
                Log.e("SDK","MulticastLock error");
            }
        }
    }

    private void MulticastUnLock(){
        if(this.lock!=null){
            try {
                this.lock.release();
            } catch (Exception e) {
                Log.e("SDK","MulticastUnLock error");
            }
        }
    }

    public void StopListen() {
        isListn=false;
        if (null != datagramSocket) {
            datagramSocket.close();
            datagramSocket = null;
        }
    }

    class SendThread extends Thread {
        @Override
        public void run() {
            DatagramSocket udpSocket = null;
            DatagramPacket dataPack=null;
            try{
                Log.e("leleTest","send udp start");
                Count=0;
                udpSocket = new DatagramSocket();
                InetAddress local = null;
                local = InetAddress.getByName(ipAdress);
                dataPack = new DatagramPacket(data, data.length, local, port);
                while (isSend){
                    try {
                        sleep(1000);
                        udpSocket.send(dataPack);
                        Log.e("leleTest","send udp"+Arrays.toString(data));
                    } catch (Exception e) {
                        e.printStackTrace();
                    }
                }
                byte[] receiveData={52,0,0,0};
                dataPack=new DatagramPacket(receiveData, receiveData.length, local, port);
                Log.e("leleTest","isReceive="+isReceive+"Count="+Count);
                while (isReceive&&Count<10){
                    try {
                        udpSocket.send(dataPack);
                        sleep(300);
                        Log.e("leleTest","send udp"+Arrays.toString(receiveData)+"count="+Count);
                        Count++;
                    } catch (Exception e) {
                        e.printStackTrace();
                    }
                }
                if(isReceive){
                    Message msg=new Message();
                    msg.what=17;
                    Bundle bundle=new Bundle();
                    bundle.putInt("result",REPLAY_DEVICE_SUCCESS);
                    bundle.putString("deviceId",contactId);
                    msg.setData(bundle);
                    handler.sendMessage(msg);
                }
            }catch (Exception e){
                e.printStackTrace();
                Log.e("leleTest","send udp exception"+e.getMessage());
            }finally {
                if(udpSocket!=null){
                    udpSocket.close();
                    udpSocket=null;
                }
            }

        }
    }
    public void openThread() {
        isSend=true;
        if (null == sendThread || !sendThread.isAlive()) {
            sendThread = new SendThread();
            sendThread.start();
        }
    }

    public void kill() {
        isSend = false;
        sendThread = null;
    }

}
