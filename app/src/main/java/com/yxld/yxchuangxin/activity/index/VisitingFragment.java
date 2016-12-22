package com.yxld.yxchuangxin.activity.index;

import android.Manifest;
import android.content.ContentResolver;
import android.content.Intent;
import android.database.Cursor;
import android.net.Uri;
import android.os.Bundle;
import android.provider.ContactsContract;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import com.mylhyl.acp.Acp;
import com.mylhyl.acp.AcpListener;
import com.mylhyl.acp.AcpOptions;
import com.yxld.yxchuangxin.R;
import com.yxld.yxchuangxin.base.BaseFragment;
import com.yxld.yxchuangxin.contain.Contains;
import com.yxld.yxchuangxin.controller.DoorController;
import com.yxld.yxchuangxin.controller.impl.DoorControllerImpl;
import com.yxld.yxchuangxin.entity.AppYezhuFangwu;
import com.yxld.yxchuangxin.entity.CxwyYezhu;
import com.yxld.yxchuangxin.entity.Door;
import com.yxld.yxchuangxin.entity.OpenDoorCode;
import com.yxld.yxchuangxin.listener.ResultListener;
import com.yxld.yxchuangxin.util.StringUitl;
import com.yxld.yxchuangxin.util.ToastUtil;

import java.net.URLEncoder;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

import static android.R.id.list;
import static com.yxld.yxchuangxin.R.mipmap.fangwu;


/**
 * Created by yishangfei on 2016/11/5 0005.
 * 来访邀请
 */
public class VisitingFragment extends BaseFragment  {
    /**
     * 门禁实现类
     */
    private DoorController DoorController;
    /**
     * 用户地址
     */
    private TextView addr;
    /**
     * 姓名
     */
    private EditText name;
    /**
     * 电话
     */
    private EditText phone;
    /**
     * 确认按钮
     */
    private TextView sure;

    private AppYezhuFangwu house = new AppYezhuFangwu();
    String address = "";

    private ImageView tongxunlu;
    //声明姓名，电话
    private String username, usernumber;

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.visitor_invitation_activity_layout, container, false);
        List<AppYezhuFangwu> list = Contains.appYezhuFangwus;
        house = list.get(0);
        Log.d("geek", "业主" + house.toString());
        initview(view);
        return view;
    }

    private void initview(View view) {
        tongxunlu = (ImageView) view.findViewById(R.id.tongxunlu);
        sure = (TextView) view.findViewById(R.id.sure);
        name = (EditText) view.findViewById(R.id.name);
        phone = (EditText) view.findViewById(R.id.phone);
        sure.setOnClickListener(this);
        addr = (TextView) view.findViewById(R.id.addr);
        address = house.getXiangmuLoupan() + "" + house.getFwLoudong() + "栋" + house.getFwDanyuan() + "单元" + house.getFwFanghao();
        addr.setText(address);
        tongxunlu.setOnClickListener(this);
    }

    @Override
    public void onClick(View v) {
        switch (v.getId()) {
            case R.id.sure:  //确认 请求生成二维码
              if (StringUitl.isNotEmpty(getActivity(), name, "请输入姓名") &&
                StringUitl.isNotEmpty(getActivity(), phone, "请输入电话")
                ) {
                if (!StringUitl.isMobileNum(phone.getText().toString())) {
                    ToastUtil.show(getActivity(), "请输入正确手机号码");
                    return;
                }

                if(DoorController == null ){
                    DoorController = new DoorControllerImpl();
                }
                  if(house != null && house.getFwyzType() != null && Contains.user.getYezhuShouji() != null
                          && house.getFwLoupanId() != null && house.getFwLoudong() != null
                          && house.getFwDanyuan() != null){

                      String nameyz = "";
                      if(Contains.user.getYezhuName() == null || "".equals(Contains.user.getYezhuName())){
                          nameyz= Contains.user.getYezhuShouji();
                      }else{
                          nameyz = Contains.user.getYezhuName();
                          try {
                              //编码授权人业主名字
                              nameyz =  URLEncoder.encode(nameyz,"UTF-8").toString();
                          }catch (Exception e){
                              Log.d("geek","业主姓名编码失败");
                          }
                      }

                      //编码被授权人名字
                      String bName = name.getText().toString();
                      try {
                          bName =  URLEncoder.encode(bName,"UTF-8").toString();
                      }catch (Exception e){
                          Log.d("geek","业主姓名编码失败");
                      }

                      //coed/getcodes/{bName}/{bPhone}/{bRole}/{name}/{phone}/{role}/{building}/{buildingHouse}/{buildingUnit}
                      DoorController.GetFangKeDoorCODE(mRequestQueue,new Object[]{bName,
                              phone.getText().toString(),"3",
                              nameyz,Contains.user.getYezhuShouji(),String.valueOf(house.getFwyzType()),
                              String.valueOf(house.getFwLoupanId()),house.getFwLoudong(),house.getFwDanyuan()},OpenDoorCode);
                  }else{
                      ToastUtil.show(getActivity(),"业主信息不完善");
                  }
            }
                break;
            case R.id.tongxunlu:
                Acp.getInstance(getActivity()).request(new AcpOptions.Builder().setPermissions(Manifest.permission.READ_CONTACTS).build(),
                        new AcpListener() {
                            @Override
                            public void onGranted() {
                                Uri uri = ContactsContract.Contacts.CONTENT_URI;
                                Intent intent = new Intent(Intent.ACTION_PICK,
                                        uri);
                                startActivityForResult(intent, 0);
                            }

                            @Override
                            public void onDenied(List<String> permissions) {
                                Toast.makeText(getActivity(), permissions.toString() + "权限拒绝", Toast.LENGTH_SHORT).show();
                            }
                        });
                break;
            default:
                break;

        }
    }

    private ResultListener<OpenDoorCode> OpenDoorCode = new ResultListener<com.yxld.yxchuangxin.entity.OpenDoorCode>() {
        @Override
        public void onResponse(OpenDoorCode info) {
            Log.d("geek", "OpenDoorCode info" + info.toString());

            if(info != null && info.getState() != null && "0".equals(info.getState())){
            Intent intent = new Intent(getActivity(),
                    phoneOpenDoorActivity.class);
            Bundle bundle1 = new Bundle();
            bundle1.putString("codestr", info.getCode());
            bundle1.putString("time", info.getTime());
            bundle1.putString("address", address);
            intent.putExtras(bundle1);
            startActivity(intent, bundle1);
            }else{
                ToastUtil.show(getActivity(), "获取二维码失败");
            }
            progressDialog.hide();
        }

        @Override
        public void onErrorResponse(String errMsg) {
            onError(errMsg);
        }
    };


    @Override
    protected void initDataFromLocal() {

    }

    @Override
    public void onActivityResult(int requestCode, int resultCode, Intent data) {
        switch (requestCode) {
            case 0:
                if (data == null) {
                    return;
                }
                //处理返回的data,获取选择的联系人信息
                Uri uri = data.getData();
                String[] contacts = getPhoneContacts(uri);
                name.setText(contacts[0]);
                String tel=contacts[1];
                Pattern p = Pattern.compile("[^0-9]");
                Matcher m = p.matcher(tel);
                tel = m.replaceAll("");
                phone.setText(tel);
                break;
        }
        super.onActivityResult(requestCode, resultCode, data);
    }

    //其中getPhoneContacts（uri）方法，因为手机的联系人和手机号并不再同一个数据库中，所以我们需要分别做处理

    private String[] getPhoneContacts(Uri uri) {
        String[] contact = new String[2];
        //得到ContentResolver对象
        ContentResolver cr = getActivity().getContentResolver();
        //取得电话本中开始一项的光标
        Cursor cursor = cr.query(uri, null, null, null, null);
        if (cursor != null) {
            cursor.moveToFirst();
//取得联系人姓名
            int nameFieldColumnIndex = cursor.getColumnIndex(ContactsContract.Contacts.DISPLAY_NAME);
            if(contact[0] != null){
                contact[0] = cursor.getString(nameFieldColumnIndex);
            }else{
                contact[0] = "";
                contact[1] = "";
                ToastUtil.show(getActivity(),"未获取到信息：请检查您是否已经允许欣社区访问通讯录权限");
                return contact;
            }
//取得电话号码
            String ContactId = cursor.getString(cursor.getColumnIndex(ContactsContract.Contacts._ID));
            Cursor phone = cr.query(ContactsContract.CommonDataKinds.Phone.CONTENT_URI, null,
                    ContactsContract.CommonDataKinds.Phone.CONTACT_ID + "=" + ContactId, null, null);
            if (phone != null) {
                phone.moveToFirst();
                if(contact[1] != null){
                    contact[1] = phone.getString(phone.getColumnIndex(ContactsContract.CommonDataKinds.Phone.NUMBER));
                }else{
                    contact[0] = "";
                    contact[1] = "";
                    ToastUtil.show(getActivity(),"未获取到信息：请检查您是否已经允许欣社区访问通讯录权限");
                    return contact;
                }
            }
            phone.close();
            cursor.close();
        } else {
            return null;
        }
        return contact;
    }


}
