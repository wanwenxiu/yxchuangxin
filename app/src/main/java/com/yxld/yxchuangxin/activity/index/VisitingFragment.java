package com.yxld.yxchuangxin.activity.index;

import android.app.Activity;
import android.content.ContentResolver;
import android.content.Intent;
import android.database.Cursor;
import android.net.Uri;
import android.os.Bundle;
import android.provider.ContactsContract;
import android.support.v4.app.Fragment;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import com.yxld.yxchuangxin.R;
import com.yxld.yxchuangxin.base.BaseEntity;
import com.yxld.yxchuangxin.base.BaseFragment;
import com.yxld.yxchuangxin.contain.Contains;
import com.yxld.yxchuangxin.controller.DoorController;
import com.yxld.yxchuangxin.controller.impl.DoorControllerImpl;
import com.yxld.yxchuangxin.entity.CxwyYezhu;
import com.yxld.yxchuangxin.entity.Door;
import com.yxld.yxchuangxin.entity.OpenDoorCode;
import com.yxld.yxchuangxin.listener.ResultListener;
import com.yxld.yxchuangxin.util.StringUitl;
import com.yxld.yxchuangxin.util.ToastUtil;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;


/**
 * Created by yishangfei on 2016/11/5 0005.
 * 来访邀请
 */
public class VisitingFragment extends BaseFragment implements ResultListener<BaseEntity> {
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
//	/** 门禁下拉框*/
//	private MaterialSpinner doorspinner;

    private List<String> doorNameList = new ArrayList<>();
    private List<Door> doorList = new ArrayList<>();
    private CxwyYezhu yezhu = new CxwyYezhu();
    String address = "";

    private String mac;
    private ImageView tongxunlu;
    //声明姓名，电话
    private String username, usernumber;

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.visitor_invitation_activity_layout, container, false);
        List<CxwyYezhu> list = Contains.cxwyYezhu;
        yezhu = list.get(0);
        Log.d("geek", "业主" + yezhu.toString());
        initDataFromNet();
        initview(view);
        return view;
    }

    private void initview(View view) {
        tongxunlu = (ImageView) view.findViewById(R.id.tongxunlu);
        sure = (TextView) view.findViewById(R.id.sure);
        name = (EditText) view.findViewById(R.id.name);
        phone = (EditText) view.findViewById(R.id.phone);
        sure.setOnClickListener(this);
//		doorspinner = (MaterialSpinner) findViewById(R.id.doorspinner);
        addr = (TextView) view.findViewById(R.id.addr);

        address = yezhu.getYezhuLoupan() + "" + yezhu.getYezhuLoudong() + "栋" + yezhu.getYezhuDanyuan() + "单元" + yezhu.getYezhuFanghao();
        addr.setText(address);
        tongxunlu.setOnClickListener(this);
    }

    @Override
    public void onClick(View v) {
        switch (v.getId()) {
            case R.id.sure:  //确认 请求生成二维码
                if (mac != null && StringUitl.isNoEmpty(mac)) {
                    if (StringUitl.isNotEmpty(getActivity(), name, "请输入姓名") &&
                            StringUitl.isNotEmpty(getActivity(), phone, "请输入电话")
                            ) {
                        if (!StringUitl.isMobileNum(phone.getText().toString())) {
                            ToastUtil.show(getActivity(), "请输入正确手机号码");
                            return;
                        }
                        Map<String, String> parm = new HashMap<String, String>();
                        parm.put("name", name.getText().toString());
                        parm.put("tel", phone.getText().toString());
                        parm.put("houses", "小区");
                        parm.put("yezhuid", yezhu.getYezhuId() + "");
                        parm.put("machineMAC", mac);
                        progressDialog.show();
                        DoorController.GetOPENDoorList(mRequestQueue, parm, OpenDoorCode);
                    }
                } else {
                    ToastUtil.show(getActivity(), "获取门禁失败");
                }
                break;
            case R.id.tongxunlu:
                Uri uri = ContactsContract.Contacts.CONTENT_URI;
                Intent intent = new Intent(Intent.ACTION_PICK,
                        uri);
                startActivityForResult(intent, 0);
                break;
            default:
                break;

        }
    }

    @Override
    protected void initDataFromNet() {
        super.initDataFromNet();
        if (DoorController == null) {
            DoorController = new DoorControllerImpl();
        }


        doorList.clear();
        doorNameList.clear();

        Map<String, String> parm = new HashMap<String, String>();
        parm.put("xiaoquId", yezhu.getYezhuId() + "");
        parm.put("houses", yezhu.getYezhuLoupan());
        parm.put("dong", yezhu.getYezhuLoudong());
        parm.put("danyuan", yezhu.getYezhuDanyuan());

        Log.d("geek", "获取门禁列表" + parm.toString());
        DoorController.GetDoorList(mRequestQueue, parm, this);
    }

    @Override
    public void onResponse(BaseEntity info) {
        if(info != null){
            Log.d("geek", "门禁 info=" + info.toString());
            mac = info.MSG;
        }else{
            ToastUtil.show(getActivity(),"获取门禁数据失败");
            mac = "";
        }

//		if (isEmptyList(info.getRows())) {
//			ToastUtil.show(VisitorInvitationActivity.this, "没有查询到记录");
//		}else{
//			doorList = info.getRows();
//			for (int i =0;i<doorList.size();i++){
//				String name = doorList.get(i).getMenjinname();
//				if(name != null && !name.equals("")){
//					doorNameList.add(name);
//				}else{
//					doorNameList.add("");
//				}
//			};
//			doorspinner.setItems(doorNameList);
//			Log.d("geek","获取的文字doorNameList"+doorNameList.toString());
//		}
        progressDialog.hide();
    }

    @Override
    public void onErrorResponse(String errMsg) {
        onError(errMsg);
    }


    private ResultListener<OpenDoorCode> OpenDoorCode = new ResultListener<com.yxld.yxchuangxin.entity.OpenDoorCode>() {
        @Override
        public void onResponse(OpenDoorCode info) {
            Log.d("geek", "OpenDoorCode info" + info.toString());

            Log.d("geek", "OpenDoorCode 数据" + info.getCode().toString());
            if (info != null && info.getCode().getStr() != null && !"".equals(info.getCode().getStr()) && info.getCode().getShijian() != null
                    && !"".equals(info.getCode().getShijian())) {
                Intent intent = new Intent(getActivity(),
                        phoneOpenDoorActivity.class);
                Bundle bundle1 = new Bundle();
                bundle1.putString("codestr", info.getCode().getStr());
                bundle1.putString("time", info.getCode().getShijian());
                bundle1.putString("address", address);
                intent.putExtras(bundle1);
                startActivity(intent, bundle1);

            } else {
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
                phone.setText(contacts[1]);
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
