package com.yxld.yxchuangxin.activity.index;

import android.Manifest;
import android.content.ContentResolver;
import android.content.Intent;
import android.database.Cursor;
import android.net.Uri;
import android.os.Bundle;
import android.provider.ContactsContract;
import android.util.Log;
import android.view.MenuItem;
import android.view.View;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import com.mylhyl.acp.Acp;
import com.mylhyl.acp.AcpListener;
import com.mylhyl.acp.AcpOptions;
import com.yxld.yxchuangxin.R;
import com.yxld.yxchuangxin.base.BaseActivity;
import com.yxld.yxchuangxin.contain.Contains;
import com.yxld.yxchuangxin.controller.DoorController;
import com.yxld.yxchuangxin.controller.impl.DoorControllerImpl;
import com.yxld.yxchuangxin.entity.AppYezhuFangwu;
import com.yxld.yxchuangxin.entity.OpenDoorCode;
import com.yxld.yxchuangxin.listener.ResultListener;
import com.yxld.yxchuangxin.util.StringUitl;
import com.yxld.yxchuangxin.util.ToastUtil;

import java.net.URLEncoder;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.regex.Matcher;
import java.util.regex.Pattern;


/**
 * @author wwx
 * @ClassName: VisitorInvitationActivity
 * @Description: 访客邀请
 * @date 2016年5月27日 下午5:14:24
 */
public class VisitorInvitationActivity extends BaseActivity  {
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

    @Override
    protected void initContentView(Bundle savedInstanceState) {
        setContentView(R.layout.visitor_invitation_activity_layout);
        getSupportActionBar().setTitle("来访邀请");
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        List<AppYezhuFangwu> list = Contains.appYezhuFangwus;
        if(list != null){
            house = list.get(0);
            Log.d("geek", "业主" + house.toString());
        }
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        if (item.getItemId() == android.R.id.home) {
            onBackPressed();
        }
        return super.onOptionsItemSelected(item);
    }

    @Override
    protected void initView() {
        tongxunlu= (ImageView) findViewById(R.id.tongxunlu);
        sure = (TextView) findViewById(R.id.sure);
        name = (EditText) findViewById(R.id.name);
        phone = (EditText) findViewById(R.id.phone);
        sure.setOnClickListener(this);
        addr = (TextView) findViewById(R.id.addr);
        tongxunlu.setOnClickListener(this);
        address = house.getXiangmuLoupan() + "" + house.getFwLoudong() + "栋" + house.getFwDanyuan() + "单元" + house.getFwFanghao();
        addr.setText(address);
    }

    @Override
    public void onClick(View v) {
        switch (v.getId()) {
            case R.id.sure:  //确认 请求生成二维码
                    if (StringUitl.isNotEmpty(this, name, "请输入姓名") &&
                            StringUitl.isNotEmpty(this, phone, "请输入电话")
                            ) {
                        if (!StringUitl.isMobileNum(phone.getText().toString())) {
                            ToastUtil.show(this, "请输入正确手机号码");
                            return;
                        }

                        if(DoorController == null ){
                            DoorController = new DoorControllerImpl();
                        }
                        //http://120.25.78.92/coed/getcodes?bName=##&bPhone=##&bRole=##&name=##&phone=##&role=##building=##&buildingHouse=##&buildingUnit=##
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
                            ToastUtil.show(this,"业主信息不完善");
                        }
                }
                break;
            case R.id.tongxunlu:
                Acp.getInstance(VisitorInvitationActivity.this).request(new AcpOptions.Builder().setPermissions(Manifest.permission.READ_CONTACTS).build(),
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
                                Toast.makeText(VisitorInvitationActivity.this, permissions.toString() + "权限拒绝", Toast.LENGTH_SHORT).show();
                            }
                        });
                break;
            default:
                break;

        }
    }


    @Override
    protected void initDataFromLocal() {
    }

    private ResultListener<OpenDoorCode> OpenDoorCode = new ResultListener<com.yxld.yxchuangxin.entity.OpenDoorCode>() {
        @Override
        public void onResponse(OpenDoorCode info) {
            Log.d("geek", "OpenDoorCode info" + info.toString());

            if(info != null && info.getState() != null && "0".equals(info.getState())){
                Intent intent = new Intent(VisitorInvitationActivity.this,
                        phoneOpenDoorActivity.class);
                Bundle bundle1 = new Bundle();
                bundle1.putString("codestr", info.getCode());
                bundle1.putString("time", info.getTime());
                bundle1.putString("address", address);
                intent.putExtras(bundle1);
                startActivity(intent, bundle1);
            }else{
                ToastUtil.show(VisitorInvitationActivity.this, "获取二维码失败");
            }
            progressDialog.hide();
        }

        @Override
        public void onErrorResponse(String errMsg) {
            onError(errMsg);
        }
    };

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
        ContentResolver cr = getContentResolver();
        //取得电话本中开始一项的光标
        Cursor cursor = cr.query(uri, null, null, null, null);
        if (cursor != null) {
            cursor.moveToFirst();
//取得联系人姓名
            int nameFieldColumnIndex = cursor.getColumnIndex(ContactsContract.Contacts.DISPLAY_NAME);
            contact[0] = cursor.getString(nameFieldColumnIndex);
//取得电话号码
            String ContactId = cursor.getString(cursor.getColumnIndex(ContactsContract.Contacts._ID));
            Cursor phone = cr.query(ContactsContract.CommonDataKinds.Phone.CONTENT_URI, null,
                    ContactsContract.CommonDataKinds.Phone.CONTACT_ID + "=" + ContactId, null, null);
            if (phone != null) {
                phone.moveToFirst();
                contact[1] = phone.getString(phone.getColumnIndex(ContactsContract.CommonDataKinds.Phone.NUMBER));
            }
            phone.close();
            cursor.close();
        } else {
            return null;
        }
        return contact;
    }

}
