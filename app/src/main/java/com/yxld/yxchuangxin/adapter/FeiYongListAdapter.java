package com.yxld.yxchuangxin.adapter;


import com.chad.library.adapter.base.BaseQuickAdapter;
import com.chad.library.adapter.base.BaseViewHolder;
import com.yxld.yxchuangxin.R;
import com.yxld.yxchuangxin.entity.AppWuYeFei;

import java.util.List;

/**
 * Created by yishangfei on 2016/10/18 0018.
 */

public class FeiYongListAdapter extends BaseQuickAdapter<AppWuYeFei, BaseViewHolder> {
    public FeiYongListAdapter(List<AppWuYeFei> list) {
        super(R.layout.feiyong_list_item_layout, list);
    }
    @Override
    protected void convert(BaseViewHolder viewHolder, AppWuYeFei item) {
        viewHolder.setText(R.id.month, item.getTime())
                .setText(R.id.money, item.getFees())
                .setText(R.id.status,item.getStatusM())
                .setText(R.id.payment,item.getLateFees());
        if (item.getStatusM().equals("未缴费")){
            viewHolder.setTextColor(R.id.status,android.graphics.Color.RED);
        }else {
            viewHolder.setTextColor(R.id.status, android.graphics.Color.GREEN);
        }
    }
}
