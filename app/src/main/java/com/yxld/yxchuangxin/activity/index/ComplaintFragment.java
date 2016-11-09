package com.yxld.yxchuangxin.activity.index;

import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.EditText;

import com.jaredrummler.materialspinner.MaterialSpinner;
import com.yxld.yxchuangxin.R;
import com.yxld.yxchuangxin.base.BaseEntity;
import com.yxld.yxchuangxin.base.BaseFragment;
import com.yxld.yxchuangxin.contain.Contains;
import com.yxld.yxchuangxin.controller.ComplaintController;
import com.yxld.yxchuangxin.controller.impl.ComplaintControllerImpl;
import com.yxld.yxchuangxin.listener.ResultListener;
import com.yxld.yxchuangxin.util.StringUitl;
import com.yxld.yxchuangxin.util.ToastUtil;

import java.util.HashMap;
import java.util.Map;

/**
 * Created by yishangfei on 2016/11/5 0005.
 * 我的投诉
 */
public class ComplaintFragment extends BaseFragment {
    private ComplaintController complaintController;
    private MaterialSpinner fenwei;
    private EditText destail;

    String[] scope = { "综合管理服务", "公共区域清洁卫生服务", "公共区域秩序维护服务", "公共区域绿化日常养护服务",
            "公共部位、公用设备、日常运行、报验维修服务" };

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.complaint, container, false);
        initview(view);
        return view;
    }

    private void initview(View view) {
        view.findViewById(R.id.submit).setOnClickListener(this);
        fenwei = (MaterialSpinner) view.findViewById(R.id.fenwei);
        destail = (EditText) view.findViewById(R.id.edContext);

        fenwei.setItems(scope);
    }
    private ResultListener<BaseEntity> submitlistener = new ResultListener<BaseEntity>() {

        @Override
        public void onResponse(BaseEntity info) {
            progressDialog.hide();
            if (info.status != STATUS_CODE_OK) {
                onError(info.MSG);
                return;
            }
            if(info.MSG != null){
                ToastUtil.show(getActivity(), "投诉成功");
                getActivity().finish();
            }else{
                onError("请求失败");
            }
        }

        @Override
        public void onErrorResponse(String errMsg) {
            onError("请求失败");
        }
    };

    @Override
    public void onClick(View v) {
        switch (v.getId()) {
            case R.id.returnWrap:
               getActivity().finish();
                break;
            case R.id.submit:
                initDataFromNet();
                break;
        }
    }

    @Override
    protected void initDataFromNet() {
        super.initDataFromNet();
        if (complaintController == null) {
            complaintController = new ComplaintControllerImpl();
        }

        if(Contains.cxwyYezhu == null || Contains.cxwyYezhu.size() == 0){
            ToastUtil.show(getActivity(), "请配置房屋信息再进行投诉");
            return;
        }
        if(StringUitl.isNotEmpty(getActivity(), destail, "请输入具体内容")){
            Map<String, String> parm = new HashMap<String, String>();
            parm.put("ts.tousuXiangmu", Contains.cxwyYezhu.get(0).getYezhuLoupan());
            parm.put("ts.tousuName", Contains.cxwyMallUser.getUserUserName());
            parm.put("ts.tousuShouji", Contains.cxwyMallUser.getUserTel());
            parm.put("ts.tousuNeirong", destail.getText().toString());
            parm.put("ts.tousuFanwei", fenwei.getText().toString());
            complaintController.getComplaintSubmit(mRequestQueue, parm, submitlistener);
        }
    }

    @Override
    protected void initDataFromLocal() {

    }
}
